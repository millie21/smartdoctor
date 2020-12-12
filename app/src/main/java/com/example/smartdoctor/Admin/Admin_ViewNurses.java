package com.example.smartdoctor.Admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartdoctor.Api;
import com.example.smartdoctor.Doctor.Doctor_AllNurses;
import com.example.smartdoctor.Objects.Nurse;
import com.example.smartdoctor.R;
import com.example.smartdoctor.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;

public class Admin_ViewNurses extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    EditText editTextNurseId, editTextFirstName, editTextLastName, editTextEmail, editTextPhoneNumber;
    // RatingBar ratingBar;
    Spinner spinnerDepartment;
    ProgressBar progressBar;
    ListView listView;
    Button buttonAddUpdate;
    Button buttonViewPatients;
    List<Nurse> nurseList;
    boolean isUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_doctor__view_nurses);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.getSupportActionBar().hide();

        editTextNurseId = (EditText) findViewById(R.id.editTextPatientId);
        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPhoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);
        spinnerDepartment = (Spinner) findViewById(R.id.spinnerDepartment);

        buttonAddUpdate = (Button) findViewById(R.id.buttonAddUpdate);
        buttonViewPatients = (Button) findViewById(R.id.buttonview);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        listView = (ListView) findViewById(R.id.listViewHeroes);

        nurseList = new ArrayList<>();

        buttonViewPatients.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), Doctor_AllNurses.class);
                view.getContext().startActivity(intent);

            }
        });

        buttonAddUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isUpdating) {
                    updateNurse();
                } else {
                    createNurse();
                }
            }
        });
        readNurses();
    }
    private void createNurse() {
        String firstname = editTextFirstName.getText().toString().trim();
        String lastname = editTextLastName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String phonenumber = editTextPhoneNumber.getText().toString().trim();
        String team = spinnerDepartment.getSelectedItem().toString();



        if (TextUtils.isEmpty(firstname)) {
            editTextFirstName.setError("Please enter first name");
            editTextFirstName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(lastname)) {
            editTextLastName.setError("Please enter last name");
            editTextLastName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Please enter email");
            editTextEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(phonenumber)) {
            editTextPhoneNumber.setError("Please enter phonenumber");
            editTextPhoneNumber.requestFocus();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("firstname", firstname);
        params.put("lastname", lastname);
        params.put("phonenumber", phonenumber);
        params.put("email", email);
        params.put("team",team);

        Admin_ViewNurses.PerformNetworkRequest request = new Admin_ViewNurses.PerformNetworkRequest(Api.URL_CREATE_NURSE, params, CODE_POST_REQUEST);
        request.execute();
    }

    private void readNurses() {
        Admin_ViewNurses.PerformNetworkRequest request = new Admin_ViewNurses.PerformNetworkRequest(Api.URL_READ_NURSE, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void updateNurse() {
        String nurseid = editTextNurseId.getText().toString();
        String firstname = editTextFirstName.getText().toString().trim();
        String lastname = editTextLastName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String phonenumber = editTextPhoneNumber.getText().toString().trim();
        String team = spinnerDepartment.getSelectedItem().toString();
        // int phonenumber = (int) ratingBar.getRating();


        if (TextUtils.isEmpty(firstname)) {
            editTextFirstName.setError("Please enter first name");
            editTextFirstName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(lastname)) {
            editTextLastName.setError("Please enter last name");
            editTextLastName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Please enter email");
            editTextEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(phonenumber)) {
            editTextPhoneNumber.setError("Please enter phonenumber");
            editTextPhoneNumber.requestFocus();
            return;
        }


        HashMap<String, String> params = new HashMap<>();
        params.put("nurseid", nurseid);
        params.put("firstname", firstname);
        params.put("lastname", lastname);
        params.put("email", email);
        params.put("phonenumber", phonenumber);
        params.put("team",team);


        Admin_ViewNurses.PerformNetworkRequest request = new Admin_ViewNurses.PerformNetworkRequest(Api.URL_UPDATE_NURSE, params, CODE_POST_REQUEST);
        request.execute();

        buttonAddUpdate.setText("Add");

        editTextFirstName.setText("");
        editTextLastName.setText("");
        editTextEmail.setText("");
        editTextPhoneNumber.setText("");
        spinnerDepartment.setSelection(0);

        isUpdating = false;
    }

    private void deleteHero(int nurseid) {
        Admin_ViewNurses.PerformNetworkRequest request = new Admin_ViewNurses.PerformNetworkRequest(Api.URL_DELETE_NURSE + nurseid, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void refreshHeroList(JSONArray nurses) throws JSONException {
        nurseList.clear();

        for (int i = 0; i < nurses.length(); i++) {
            JSONObject obj = nurses.getJSONObject(i);

            nurseList.add(new Nurse(
                    obj.getInt("nurseid"),
                    obj.getString("firstname"),
                    obj.getString("lastname"),
                    obj.getString("email"),
                    obj.getString("phonenumber"),
                    obj.getString("team")
            ));
        }

        Admin_ViewNurses.NurseAdapter adapter = new Admin_ViewNurses.NurseAdapter(nurseList);
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
                    refreshHeroList(object.getJSONArray("nurses"));
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
    class NurseAdapter extends ArrayAdapter<Nurse> {
        List<Nurse> nurseList;

        public NurseAdapter(List<Nurse> nurseList) {
            super(Admin_ViewNurses.this, R.layout.layout_hero_list, nurseList);
            this.nurseList = nurseList;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_hero_list, null, true);

            TextView textViewName = listViewItem.findViewById(R.id.textViewName);
            TextView textViewUpdate = listViewItem.findViewById(R.id.textViewUpdate);
            TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);

            final Nurse nurse = nurseList.get(position);

            textViewName.setText(nurse.getFirstName());

            textViewUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isUpdating = true;
                    editTextNurseId.setText(String.valueOf(nurse.getNurseId()));
                    editTextFirstName.setText(nurse.getFirstName());
                    editTextLastName.setText(nurse.getLastName());
                    editTextEmail.setText(nurse.getEmail());
                    editTextPhoneNumber.setText(nurse.getPhonenumber());
                    spinnerDepartment.setSelection(((ArrayAdapter<String>) spinnerDepartment.getAdapter()).getPosition(nurse.getTeam()));

                    buttonAddUpdate.setText("Update");
                }
            });

            textViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Admin_ViewNurses.this);

                    builder.setTitle("Delete " + nurse.getFirstName())
                            .setMessage("Are you sure you want to delete it?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteHero(nurse.getNurseId());
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
