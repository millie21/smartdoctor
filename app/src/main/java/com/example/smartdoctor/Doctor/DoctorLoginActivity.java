package com.example.smartdoctor.Doctor;

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
import android.widget.Toast;

import com.example.smartdoctor.Api;
import com.example.smartdoctor.SharedPreferences.DoctorSharedPrefManager;
import com.example.smartdoctor.Objects.Doctors;
import com.example.smartdoctor.Nurses.NurseHomePage;
import com.example.smartdoctor.Patient.PatientHomePage;
import com.example.smartdoctor.R;
import com.example.smartdoctor.RequestHandler;
import com.example.smartdoctor.SharedPreferences.SharedPrefManager;
import com.example.smartdoctor.Objects.User;
import com.example.smartdoctor.Patient.UserRegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class DoctorLoginActivity extends AppCompatActivity {

    EditText editTextUsername, editTextPassword, editTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_doctor_login);

        this.getSupportActionBar().hide();

        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);

        //if user presses on login
        //calling the method login
        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doctorLogin();
            }
        });

        //if user presses on not registered
//        findViewById(R.id.textViewRegister).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //open register screen
//                finish();
//                startActivity(new Intent(getApplicationContext(), UserRegisterActivity.class));
//            }
//        });
    }

    private void doctorLogin() {
        //first getting the values
        final String password = editTextPassword.getText().toString();
        final String email = editTextEmail.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter your password");
            editTextPassword.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Please enter your email");
            editTextEmail.requestFocus();
            return;
        }

        //if everything is fine

        class DoctorLogin extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressBar.setVisibility(View.GONE);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("doctor");
                        //  String gender = obj.getString("gender");
                        // String test = obj.getString("gender");

                        //creating a new user object
                        Doctors doctors = new Doctors(
                                userJson.getString("username"),
                                userJson.getString("email"),
                                userJson.getString("spec"),
                                userJson.getString("docFees")
                        );

                        //storing the user in shared preferences
                        DoctorSharedPrefManager.getInstance(getApplicationContext()).doctorLogin(doctors);

                            startActivity(new Intent(getApplicationContext(), DoctorHomePage.class));

                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid username or email", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("password", password);
                params.put("email", email);

                //returing the response
                return requestHandler.sendPostRequest(Api.ROOT_URL_LOGIN_DOCTOR, params);
            }
        }

        DoctorLogin ul = new DoctorLogin();
        ul.execute();
    }
}
