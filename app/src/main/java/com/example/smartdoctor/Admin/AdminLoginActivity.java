package com.example.smartdoctor.Admin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


//import com.example.smartdoctor.AdminSharedPrefManager;
//import com.example.smartdoctor.SharedPreferences.AdminSharedPrefManager;
//import com.example.smartdoctor.SharedPreferences.AdminSharedPrefManager;
//import com.example.smartdoctor.AdminSharedPrefManager;
//import com.example.smartdoctor.AdminSharedPrefManager;
import com.example.smartdoctor.AdminSharedPrefManager;
import com.example.smartdoctor.Objects.Admins;
import com.example.smartdoctor.Api;
import com.example.smartdoctor.Doctor.DoctorHomePage;
import com.example.smartdoctor.SharedPreferences.DoctorSharedPrefManager;
import com.example.smartdoctor.Objects.Doctors;
import com.example.smartdoctor.R;
import com.example.smartdoctor.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AdminLoginActivity extends AppCompatActivity {

    EditText editTextUsername, editTextPassword, editTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_admin_login);

        this.getSupportActionBar().hide();

        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);

        //if user presses on login
        //calling the method login
        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminLogin();
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

    private void adminLogin() {
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

        class AdminLogin extends AsyncTask<Void, Void, String> {

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
                        JSONObject userJson = obj.getJSONObject("admin");
                        //  String gender = obj.getString("gender");
                        // String test = obj.getString("gender");

                        //creating a new user object
                        Admins admins = new Admins(

                                userJson.getString("email"),
                                userJson.getString("username")
                        );

                        //storing the user in shared preferences
                        AdminSharedPrefManager.getInstance(getApplicationContext()).adminLogin(admins);

                            startActivity(new Intent(getApplicationContext(), AdminHomePage.class));

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
                return requestHandler.sendPostRequest(Api.ROOT_URL_LOGIN_ADMIN, params);
            }
        }

        AdminLogin ul = new AdminLogin();
        ul.execute();
    }
}
