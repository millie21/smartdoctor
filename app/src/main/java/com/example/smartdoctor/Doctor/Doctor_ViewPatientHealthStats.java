package com.example.smartdoctor.Doctor;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.smartdoctor.Api;
import com.example.smartdoctor.Objects.HealthData;
import com.example.smartdoctor.R;
import com.example.smartdoctor.RequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Doctor_ViewPatientHealthStats extends AppCompatActivity {

    EditText editTextTemperature,editTextHeartrate , editTextRespiration, editTextSpo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_patient_health_stats);

        editTextTemperature = (EditText) findViewById(R.id.editTextTemperature);
        editTextHeartrate = (EditText) findViewById(R.id.editTextHeartrate);
        editTextRespiration = (EditText) findViewById(R.id.editTextRespiration);
        editTextSpo2 = (EditText) findViewById(R.id.editTextSpo2);

        findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on button register
                //here we will register the user to server
                postData();
            }
        });

    }

    private void postData() {
        final String Temperature = editTextTemperature.getText().toString().trim();
        final String Heartrate = editTextHeartrate.getText().toString().trim();
        final String Respiration = editTextRespiration.getText().toString().trim();
        final String Spo2 = editTextSpo2.getText().toString().trim();

        //first we will do the validations

        if (TextUtils.isEmpty(Temperature)) {
            editTextTemperature.setError("Please enter temperature");
            editTextTemperature.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(Heartrate)) {
            editTextHeartrate.setError("Please enter your heartrate");
            editTextHeartrate.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(Respiration)) {
            editTextRespiration.setError("Please enter your phonenumber");
            editTextRespiration.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(Spo2)) {
            editTextSpo2.setError("Enter a password");
            editTextSpo2.requestFocus();
            return;
        }

        //if it passes all the validations

        class PostData extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();


                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("Temperature", Temperature);
                params.put("Heartrate", Heartrate);
                params.put("Respiration", Respiration);
                params.put("Spo2", Spo2);

                //returing the response
                return requestHandler.sendPostRequest(Api.URL_CREATE_HEALTHDATA, params);
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
                        JSONObject userJson = obj.getJSONObject("data");

                        //creating a new user object
                        HealthData healthData = new HealthData(
                              //  userJson.getInt("pid"),
                                userJson.getString("Temperature"),
                                userJson.getString("Heartrate"),
                                userJson.getString("Respiration"),
                                userJson.getString("Spo2")
                            //    userJson.getString("gender")

                        );

                        //storing the user in shared preferences
                    //    SharedPrefManager.getInstance(getApplicationContext()).userLogin(healthData);

                        //starting the profile activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), ProfileView.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //executing the async task
        PostData pd = new PostData();
        pd.execute();
    }

//    private void updateData() {
//        final String Temperature = editTextTemperature.getText().toString().trim();
//        final String Heartrate = editTextHeartrate.getText().toString().trim();
//        final String Respiration = editTextRespiration.getText().toString().trim();
//        final String Spo2 = editTextSpo2.getText().toString().trim();
//
//
//        HashMap<String, String> params = new HashMap<>();
//        params.put("Temperature", Temperature);
//        params.put("Heartrate", Heartrate);
//        params.put("Respiration", Respiration);
//        params.put("Spo2", Spo2);
//
//
//        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_UPDATE_HERO, params, CODE_POST_REQUEST);
//        request.execute();
//
//        buttonAddUpdate.setText("Add");
//
//        isUpdating = false;
//    }

}
