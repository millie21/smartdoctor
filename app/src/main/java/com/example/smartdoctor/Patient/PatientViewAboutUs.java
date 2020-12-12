package com.example.smartdoctor.Patient;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.smartdoctor.Api;
import com.example.smartdoctor.R;
import com.example.smartdoctor.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class PatientViewAboutUs extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    EditText  editTextName, editTextPersonal_contact, editTextEmail, editTextMessage;
    // RatingBar ratingBar;
    //Spinner spinnerTeam;
    ProgressBar progressBar;
    ListView listView;
    Button buttonSend;
    Button buttonViewPatients;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_patient_view_about_us);

        this.getSupportActionBar().hide();

            editTextName = (EditText) findViewById(R.id.editTextName);
            editTextPersonal_contact = (EditText) findViewById(R.id.editTextContact);
            editTextEmail = (EditText) findViewById(R.id.editTextEmail);
            editTextMessage = (EditText) findViewById(R.id.editTextMessage);


            buttonSend = (Button) findViewById(R.id.buttonSend);


            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            listView = (ListView) findViewById(R.id.listViewHeroes);

            buttonSend.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){

                    createContactMessage();

                }
            });

        }
        private void createContactMessage() {
        final String name = editTextName.getText().toString().trim();
        final String personal_contact = editTextPersonal_contact.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String message = editTextMessage.getText().toString().trim();


            if (TextUtils.isEmpty(name)) {
                editTextName.setError("Please enter your name");
                editTextName.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(personal_contact)) {
                editTextPersonal_contact.setError("Please enter your number");
                editTextPersonal_contact.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(email)) {
                editTextEmail.setError("Please enter an email");
                editTextEmail.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(message)) {
                editTextMessage.setError("Please enter a message");
                editTextMessage.requestFocus();
                return;
            }

       class SendMess extends AsyncTask<Void, Void, String> {

                private ProgressBar progressBar;

                @Override
                protected String doInBackground(Void... voids) {
                    //creating request handler object
                    RequestHandler requestHandler = new RequestHandler();

            HashMap<String, String> params = new HashMap<>();
            params.put("name", name);
            params.put("email", email);
            params.put("personal_contact", personal_contact);
            params.put("message", message);

            //returing the response
            return requestHandler.sendPostRequest(Api.URL_CREATE_CONTACTS, params);
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

//                //getting the user from the response
//                JSONObject userJson = obj.getJSONObject("user");
//
//                //creating a new user object
//                Contact contact = new Contact(
//                        userJson.getString("name"),
//                        userJson.getString("email"),
//                        userJson.getString("personal_contact"),
//                        userJson.getString("message")
//
//                );

                //storing the user in shared preferences
                //SharedPrefManager.getInstance(getApplicationContext()).userLogin(contact);

                //starting the profile activity
                finish();
                startActivity(new Intent(getApplicationContext(), PatientHomePage.class));
            } else {
                Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

    //executing the async task
            SendMess ru = new SendMess();
        ru.execute();
   }

 }
