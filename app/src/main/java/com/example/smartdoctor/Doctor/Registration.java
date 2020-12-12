package com.example.smartdoctor.Doctor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartdoctor.Api;
import com.example.smartdoctor.Objects.User;
import com.example.smartdoctor.OptionLoginActivity;
import com.example.smartdoctor.Patient.UserRegisterActivity;
import com.example.smartdoctor.R;
import com.example.smartdoctor.RequestHandler;
import com.example.smartdoctor.SharedPreferences.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;

public class Registration extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    EditText editTextFirstname,editTextLastname , editTextEmail, editTextPassword, editTextConfirmPassword, editTextPhonenumber;
    RadioGroup radioGroupGender;

    ProgressBar progressBar;
    ListView listView;
    Button buttonAddUpdate;

    List<Hero> heroList;

    boolean isUpdating = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_registration);

        editTextFirstname = (EditText) findViewById(R.id.editTextFirstname);
        editTextLastname = (EditText) findViewById(R.id.editTextLastname);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextConfirmPassword = (EditText) findViewById(R.id.editTextConfirmPassword);
        editTextPhonenumber = (EditText) findViewById(R.id.editTextPhoneNumber);
        radioGroupGender = (RadioGroup) findViewById(R.id.radioGender);

        findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on button register
                //here we will register the user to server
                registerUser();
            }
        });

        findViewById(R.id.textViewLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on login
                //we will open the login screen
                finish();
                startActivity(new Intent(Registration.this, OptionLoginActivity.class));
            }
        });

        buttonAddUpdate = (Button) findViewById(R.id.buttonRegister);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        listView = (ListView) findViewById(R.id.listViewHeroes);

        heroList = new ArrayList<>();


        buttonAddUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isUpdating) {
                    updateHero();
                } else {
                    registerUser();
                }
            }
        });
        readHeroes();

    }

    private void registerUser() {
        final String fname = editTextFirstname.getText().toString().trim();
        final String lname = editTextLastname.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String cpassword = editTextConfirmPassword.getText().toString().trim();
        final String contact = editTextPhonenumber.getText().toString().trim();
        final String gender = ((RadioButton) findViewById(radioGroupGender.getCheckedRadioButtonId())).getText().toString();

        //first we will do the validations

        if (TextUtils.isEmpty(fname)) {
            editTextFirstname.setError("Please enter firstname");
            editTextFirstname.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(lname)) {
            editTextLastname.setError("Please enter your lastname");
            editTextLastname.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(contact)) {
            editTextPhonenumber.setError("Please enter your phonenumber");
            editTextPhonenumber.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Enter a password");
            editTextPassword.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(cpassword)) {
            editTextConfirmPassword.setError("Confirm your password");
            editTextConfirmPassword.requestFocus();
            return;
        }

        //if it passes all the validations

        class RegisterUser extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("fname", fname);
                params.put("lname", lname);
                params.put("email", email);
                params.put("contact", contact);
                params.put("password", password);
                params.put("cpassword", cpassword);
                params.put("gender", gender);

                //returing the response
                return requestHandler.sendPostRequest(Api.ROOT_URL_SIGN_UP, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //displaying the progress bar while user registers on the server
                progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //hiding the progressbar after completion
                progressBar.setVisibility(View.GONE);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("user");

                        //creating a new user object
                        User user = new User(
                                userJson.getInt("pid"),
                                userJson.getString("fname"),
                                userJson.getString("lname"),
                                userJson.getString("email"),
                                userJson.getString("contact"),
                                userJson.getString("gender")

                        );

                        //storing the user in shared preferences
                      //  SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                        //starting the profile activity
                      //  finish();
                      //  startActivity(new Intent(getApplicationContext(), ProfileView.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //executing the async task
        RegisterUser ru = new RegisterUser();
        ru.execute();
    }

    private void readHeroes() {
        Registration.PerformNetworkRequest request = new Registration.PerformNetworkRequest(Api.URL_READ_HEROES, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void updateHero() {
        String fname = editTextFirstname.getText().toString().trim();
        String lname = editTextLastname.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String cpassword = editTextConfirmPassword.getText().toString().trim();
        String contact = editTextPhonenumber.getText().toString().trim();
        String gender = ((RadioButton) findViewById(radioGroupGender.getCheckedRadioButtonId())).getText().toString();

        if (TextUtils.isEmpty(fname)) {
            editTextFirstname.setError("Please enter firstname");
            editTextFirstname.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(lname)) {
            editTextLastname.setError("Please enter your lastname");
            editTextLastname.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(contact)) {
            editTextPhonenumber.setError("Please enter your phonenumber");
            editTextPhonenumber.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Enter a password");
            editTextPassword.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(cpassword)) {
            editTextConfirmPassword.setError("Confirm your password");
            editTextConfirmPassword.requestFocus();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("fname", fname);
        params.put("lname", lname);
        params.put("email", email);
        params.put("contact", contact);
        params.put("password", password);
        params.put("cpassword", cpassword);
        params.put("gender", gender);


        Registration.PerformNetworkRequest request = new Registration.PerformNetworkRequest(Api.URL_UPDATE_HERO, params, CODE_POST_REQUEST);
        request.execute();

        buttonAddUpdate.setText("Add");

        editTextFirstname.setText("");
        editTextLastname.setText("");
        editTextEmail.setText("");
        editTextPhonenumber.setText("");

        isUpdating = false;
    }

    private void deleteHero(int id) {
        Registration.PerformNetworkRequest request = new Registration.PerformNetworkRequest(Api.URL_DELETE_HERO + id, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void refreshHeroList(JSONArray heroes) throws JSONException {
        heroList.clear();

        for (int i = 0; i < heroes.length(); i++) {
            JSONObject obj = heroes.getJSONObject(i);

            heroList.add(new Hero(
                    obj.getInt("id"),
                    obj.getString("firstname"),
                    obj.getString("lastname"),
                    obj.getString("email"),
                    obj.getString("phonenumber")
            ));
        }

        Registration.HeroAdapter adapter = new Registration.HeroAdapter(heroList);
        listView.setAdapter(adapter);
    }

    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {
        String url;
        HashMap<String, String> params;
        int requestCode;

        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    refreshHeroList(object.getJSONArray("heroes"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }
    class HeroAdapter extends ArrayAdapter<Hero> {
        List<Hero> heroList;

        public HeroAdapter(List<Hero> heroList) {
            super(Registration.this, R.layout.layout_hero_list, heroList);
            this.heroList = heroList;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_hero_list, null, true);

            TextView textViewName = listViewItem.findViewById(R.id.textViewName);

            TextView textViewUpdate = listViewItem.findViewById(R.id.textViewUpdate);
            TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);

            final Hero hero = heroList.get(position);

            textViewName.setText(hero.getFirstName());

            textViewUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isUpdating = true;
                  //  editTextPatientId.setText(String.valueOf(hero.getId()));
                    editTextFirstname.setText(hero.getFirstName());
                    editTextLastname.setText(hero.getLastName());
                    editTextEmail.setText(hero.getEmail());
                    editTextPhonenumber.setText(hero.getPhonenumber());
                    buttonAddUpdate.setText("Update");
                }
            });

            textViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);

                    builder.setTitle("Delete " + hero.getFirstName())
                            .setMessage("Are you sure you want to delete it?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteHero(hero.getId());
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            });

            return listViewItem;
        }
    }



}














//       // buttonViewPatients = (Button) findViewById(R.id.buttonview);
//        editTextPatientId = (EditText) findViewById(R.id.editTextPatientId);
//        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
//        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
//        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
//        editTextPhoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);
//        // ratingBar = (RatingBar) findViewById(R.id.ratingBar);
//        // spinnerTeam = (Spinner) findViewById(R.id.spinnerTeamAffiliation);
//
//        buttonAddUpdate = (Button) findViewById(R.id.buttonAddUpdate);
//        buttonViewPatients = (Button) findViewById(R.id.buttonview);
//
//        progressBar = (ProgressBar) findViewById(R.id.progressBar);
//        listView = (ListView) findViewById(R.id.listViewHeroes);
//
//        heroList = new ArrayList<>();
//
//        buttonViewPatients.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                Intent intent = new Intent(view.getContext(), Doctor_ViewPatients.class);
//                view.getContext().startActivity(intent);
//
//            }
//        });
//
//        buttonAddUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (isUpdating) {
//                    updateHero();
//                } else {
//                    createHero();
//                }
//            }
//        });
//        readHeroes();
//    }
//    private void createHero() {
//        String firstname = editTextFirstName.getText().toString().trim();
//        String lastname = editTextLastName.getText().toString().trim();
//        String email = editTextEmail.getText().toString().trim();
//        String phonenumber = editTextPhoneNumber.getText().toString().trim();
//
//
//        if (TextUtils.isEmpty(firstname)) {
//            editTextFirstName.setError("Please enter first name");
//            editTextFirstName.requestFocus();
//            return;
//        }
//
//        if (TextUtils.isEmpty(lastname)) {
//            editTextLastName.setError("Please enter last name");
//            editTextLastName.requestFocus();
//            return;
//        }
//        if (TextUtils.isEmpty(email)) {
//            editTextEmail.setError("Please enter email");
//            editTextEmail.requestFocus();
//            return;
//        }
//        if (TextUtils.isEmpty(phonenumber)) {
//            editTextPhoneNumber.setError("Please enter phonenumber");
//            editTextPhoneNumber.requestFocus();
//            return;
//        }
//
//        HashMap<String, String> params = new HashMap<>();
//        params.put("firstname", firstname);
//        params.put("lastname", lastname);
//        params.put("phonenumber", phonenumber);
//        params.put("email", email);
//
//       Registration.PerformNetworkRequest request = new Registration.PerformNetworkRequest(Api.URL_CREATE_HERO, params, CODE_POST_REQUEST);
//        request.execute();
//    }
//
//    private void readHeroes() {
//        Registration.PerformNetworkRequest request = new Registration.PerformNetworkRequest(Api.URL_READ_HEROES, null, CODE_GET_REQUEST);
//        request.execute();
//    }
//
//    private void updateHero() {
//        String id = editTextPatientId.getText().toString();
//        String firstname = editTextFirstName.getText().toString().trim();
//        String lastname = editTextLastName.getText().toString().trim();
//        String email = editTextEmail.getText().toString().trim();
//        String phonenumber = editTextPhoneNumber.getText().toString().trim();
//
//        // int phonenumber = (int) ratingBar.getRating();
//
//       // String team = spinnerTeam.getSelectedItem().toString();
//
//
//        if (TextUtils.isEmpty(firstname)) {
//            editTextFirstName.setError("Please enter first name");
//            editTextFirstName.requestFocus();
//            return;
//        }
//
//        if (TextUtils.isEmpty(lastname)) {
//            editTextLastName.setError("Please enter last name");
//            editTextLastName.requestFocus();
//            return;
//        }
//
//        if (TextUtils.isEmpty(email)) {
//            editTextEmail.setError("Please enter email");
//            editTextEmail.requestFocus();
//            return;
//        }
//        if (TextUtils.isEmpty(phonenumber)) {
//            editTextPhoneNumber.setError("Please enter phonenumber");
//            editTextPhoneNumber.requestFocus();
//            return;
//        }
//
//        HashMap<String, String> params = new HashMap<>();
//        params.put("id", id);
//        params.put("firstname", firstname);
//        params.put("lastname", lastname);
//        params.put("email", email);
//        params.put("phonenumber", phonenumber);
//
//
//        Registration.PerformNetworkRequest request = new Registration.PerformNetworkRequest(Api.URL_UPDATE_HERO, params, CODE_POST_REQUEST);
//        request.execute();
//
//        buttonAddUpdate.setText("Add");
//
//        editTextFirstName.setText("");
//        editTextLastName.setText("");
//        editTextEmail.setText("");
//        editTextPhoneNumber.setText("");
//
//        isUpdating = false;
//    }
//
//    private void deleteHero(int id) {
//        Registration.PerformNetworkRequest request = new Registration.PerformNetworkRequest(Api.URL_DELETE_HERO + id, null, CODE_GET_REQUEST);
//        request.execute();
//    }
//
//    private void refreshHeroList(JSONArray heroes) throws JSONException {
//        heroList.clear();
//
//        for (int i = 0; i < heroes.length(); i++) {
//            JSONObject obj = heroes.getJSONObject(i);
//
//            heroList.add(new Hero(
//                    obj.getInt("id"),
//                    obj.getString("firstname"),
//                    obj.getString("lastname"),
//                    obj.getString("email"),
//                    obj.getString("phonenumber")
//            ));
//        }
//
//        Registration.HeroAdapter adapter = new Registration.HeroAdapter(heroList);
//        listView.setAdapter(adapter);
//    }
//
//    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {
//        String url;
//        HashMap<String, String> params;
//        int requestCode;
//
//        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
//            this.url = url;
//            this.params = params;
//            this.requestCode = requestCode;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressBar.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            progressBar.setVisibility(GONE);
//            try {
//                JSONObject object = new JSONObject(s);
//                if (!object.getBoolean("error")) {
//                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
//                    refreshHeroList(object.getJSONArray("heroes"));
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        protected String doInBackground(Void... voids) {
//            RequestHandler requestHandler = new RequestHandler();
//
//            if (requestCode == CODE_POST_REQUEST)
//                return requestHandler.sendPostRequest(url, params);
//
//
//            if (requestCode == CODE_GET_REQUEST)
//                return requestHandler.sendGetRequest(url);
//
//            return null;
//        }
//    }
//    class HeroAdapter extends ArrayAdapter<Hero> {
//        List<Hero> heroList;
//
//        public HeroAdapter(List<Hero> heroList) {
//            super(Registration.this, R.layout.layout_hero_list, heroList);
//            this.heroList = heroList;
//        }
//
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            LayoutInflater inflater = getLayoutInflater();
//            View listViewItem = inflater.inflate(R.layout.layout_hero_list, null, true);
//
//            TextView textViewName = listViewItem.findViewById(R.id.textViewName);
//
//            TextView textViewUpdate = listViewItem.findViewById(R.id.textViewUpdate);
//            TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);
//
//            final Hero hero = heroList.get(position);
//
//            textViewName.setText(hero.getFirstName());
//
//            textViewUpdate.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    isUpdating = true;
//                    editTextPatientId.setText(String.valueOf(hero.getId()));
//                    editTextFirstName.setText(hero.getFirstName());
//                    editTextLastName.setText(hero.getLastName());
//                    editTextEmail.setText(hero.getEmail());
//                    editTextPhoneNumber.setText(hero.getPhonenumber());
//                    buttonAddUpdate.setText("Update");
//                }
//            });
//
//            textViewDelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
//
//                    builder.setTitle("Delete " + hero.getFirstName())
//                            .setMessage("Are you sure you want to delete it?")
//                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    deleteHero(hero.getId());
//                                }
//                            })
//                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//
//                                }
//                            })
//                            .setIcon(android.R.drawable.ic_dialog_alert)
//                            .show();
//
//                }
//            });
//
//            return listViewItem;
//        }
//    }
//
//
//
//}
