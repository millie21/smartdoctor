package com.example.smartdoctor.Admin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.smartdoctor.Api;
import com.example.smartdoctor.Doctor.ProfileView;
import com.example.smartdoctor.Objects.Doctors;
import com.example.smartdoctor.Objects.User;
import com.example.smartdoctor.OptionLoginActivity;
import com.example.smartdoctor.Patient.UserRegisterActivity;
import com.example.smartdoctor.R;
import com.example.smartdoctor.RequestHandler;
import com.example.smartdoctor.SharedPreferences.DoctorSharedPrefManager;
import com.example.smartdoctor.SharedPreferences.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Admin_DoctorReg extends AppCompatActivity {

    EditText editTextUsername,editTextEmail , editTextSpec, editTextPassword, editTextDocFees;
    RadioGroup radioGroupGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_admin__doctor_reg);

        this.getSupportActionBar().hide();

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextSpec = (EditText) findViewById(R.id.editTextSpec);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextDocFees = (EditText) findViewById(R.id.editTextPhoneNumber);

        findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on button register
                //here we will register the user to server
                registerDoctor();
            }
        });


    }

    private void registerDoctor() {
        final String username = editTextUsername.getText().toString().trim();
        final String spec = editTextSpec.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String docFees = editTextDocFees.getText().toString().trim();

        //first we will do the validations

        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Please enter the username");
            editTextUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(spec)) {
            editTextSpec.setError("Please enter the specifications");
            editTextSpec.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(docFees)) {
            editTextDocFees.setError("Please enter the doctor fees");
            editTextDocFees.requestFocus();
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

        //if it passes all the validations

        class registerDoctor extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                params.put("email", email);
                params.put("spec", spec);
                params.put("docFees", docFees);


                //returing the response
                return requestHandler.sendPostRequest(Api.URL_CREATE_DOC, params);
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
                        JSONObject userJson = obj.getJSONObject("doctors");

                        //creating a new user object
                        Doctors doctors = new Doctors(

                                userJson.getString("username"),
                                userJson.getString("email"),
                                userJson.getString("spec"),
                                userJson.getString("docFees")

                        );

                        //storing the user in shared preferences
                        DoctorSharedPrefManager.getInstance(getApplicationContext()).doctorLogin(doctors);

                        //starting the profile activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), AdminHomePage.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //executing the async task
        registerDoctor ru = new registerDoctor();
        ru.execute();
    }

}
