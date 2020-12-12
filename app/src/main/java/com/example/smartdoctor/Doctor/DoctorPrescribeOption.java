package com.example.smartdoctor.Doctor;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartdoctor.Api;
import com.example.smartdoctor.Objects.Appointments;
import com.example.smartdoctor.Objects.CreatPresc;
import com.example.smartdoctor.Objects.Doctors;
import com.example.smartdoctor.Objects.Prescriptions;
import com.example.smartdoctor.Objects.User;
import com.example.smartdoctor.Patient.CreateAppointment;
import com.example.smartdoctor.Patient.PatientViewAppointments;
import com.example.smartdoctor.R;
import com.example.smartdoctor.RequestHandler;
import com.example.smartdoctor.SharedPreferences.DoctorSharedPrefManager;
import com.example.smartdoctor.SharedPreferences.SharedPrefManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class DoctorPrescribeOption extends AppCompatActivity {

    EditText editTextDisease, editTextStatus, editTextPrescription;
    Spinner editTextDoctor,editTextTime;
    TextView textViewId, textViewFirstname, textViewEmail, textViewGender,textViewLastname, textViewContact, textViewUserstatus, textViewDoctorstatus;
    TextView editTextOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_doctor_prescribe_option);


        editTextDisease = (EditText) findViewById(R.id.editTextDisease2);
        editTextStatus = (EditText) findViewById(R.id.editTextStatus2);
        editTextPrescription = (EditText) findViewById(R.id.editTextPrescription2);
        findViewById(R.id.buttonCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPrescription();
            }
        });

    }

    private void createPrescription() {

        Doctors doctors = DoctorSharedPrefManager.getInstance(this).getDoctors();
        User user = SharedPrefManager.getInstance(this).getUser();


        final String username = doctors.getUsername();

        final String pid = String.valueOf(user.getPid());
        final String fname = user.getFname();
        final String lname = user.getLname();

        final String disease = editTextDisease.getText().toString().trim();
        final String status = editTextStatus.getText().toString().trim();
        final String prescription = editTextPrescription.getText().toString().trim();

//

        //if it passes all the validations

        class BookAppointment extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();

                params.put("pid",pid);
                params.put("doctor",username);
                params.put("fname",fname);
                params.put("lname",lname);
                params.put("disease",disease);
                params.put("status",status);
                params.put("prescription",prescription);


                //returing the response
                return requestHandler.sendPostRequest(Api.URL_CREATE_PRESCRIPTIONS, params);
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
                        JSONObject userJson = obj.getJSONObject("prescriptions");

                        //creating a new user object
                        CreatPresc creatPresc = new CreatPresc(

                                userJson.getString("disease"),
                                userJson.getString("status"),
                                userJson.getString("prescription")
                        );

                        //storing the user in shared preferences
                        //SharedPrefManager.getInstance(getApplicationContext()).userLogin(appointments);

                        //starting the profile activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), DoctorViewAppointments.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //executing the async task
        BookAppointment ru = new BookAppointment();
        ru.execute();
    }

}
