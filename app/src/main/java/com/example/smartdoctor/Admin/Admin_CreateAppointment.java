package com.example.smartdoctor.Admin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartdoctor.Api;
import com.example.smartdoctor.Objects.Appointments;
import com.example.smartdoctor.Patient.PatientViewAppointments;
import com.example.smartdoctor.R;
import com.example.smartdoctor.RequestHandler;
import com.example.smartdoctor.SharedPreferences.SharedPrefManager;
import com.example.smartdoctor.Objects.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Admin_CreateAppointment extends AppCompatActivity {

    EditText editTextDoctor, editTextFee, editTextDate ;
    Spinner editTextSpecialization,editTextTime;
  //  RadioGroup radioGroupGender;
  TextView textViewId, textViewFirstname, textViewEmail, textViewGender,textViewLastname, textViewContact, textViewUserstatus, textViewDoctorstatus;
  TextView editTextOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_content_create_appointment);

        this.getSupportActionBar().hide();

        //if the user is already logged in we will directly start the profile activity
//        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
//            finish();
//            startActivity(new Intent(this, HomeActivity.class));
//            return;
//        }

//        User user = SharedPrefManager.getInstance(this).getUser();
//
//        textViewId.setText(String.valueOf(user.getPid()));
//        textViewFirstname.setText(user.getFname());
//        textViewLastname.setText(user.getLname());
//        textViewGender.setText(user.getGender());
//        textViewEmail.setText(user.getEmail());
//        textViewContact.setText(user.getContact());

        editTextSpecialization = (Spinner) findViewById(R.id.editTextSpecialization);
        editTextDoctor = (EditText) findViewById(R.id.editTextDoctors);
        editTextFee = (EditText) findViewById(R.id.editTextConsultationFees);
        editTextDate = (EditText) findViewById(R.id.editTextDate);
        editTextTime = (Spinner) findViewById(R.id.editTextTime);
        editTextOne = (TextView)findViewById(R.id.editTextOne);
//        textViewId = (TextView) findViewById(R.id.textViewId);
//        textViewFirstname = (TextView) findViewById(R.id.textViewUsername);
//        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
//        textViewGender = (TextView) findViewById(R.id.textViewGender);
        findViewById(R.id.buttonCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookAppointment();
            }
        });

    }

    private void bookAppointment() {

        User user = SharedPrefManager.getInstance(this).getUser();

//        final String pid = textViewId.getText().toString();
//        final String fname = textViewFirstname.getText().toString();
//        final String lname = textViewLastname.getText().toString();
//        final String gender = textViewGender.getText().toString();
//        final String email = textViewGender.getText().toString();
//        final String contact = textViewContact.getText().toString();
        final String pid = String.valueOf(user.getPid());
        final String fname = user.getFname();
        final String lname = user.getLname();
        final String gender = user.getGender();
        final String email = user.getEmail();
        final String contact = user.getContact();

      //  final String specialization = editTextSpecialization.getSelectedItem().toString();
        final String doctor = editTextDoctor.getText().toString().trim();
        final String docFees = editTextFee.getText().toString().trim();
        final String appdate = editTextDate.getText().toString().trim();
        final String apptime = editTextTime.getSelectedItem().toString();
        final String userStatus = editTextOne.getText().toString().trim();
        final String doctorStatus = editTextOne.getText().toString().trim();
        //first we will do the validations


        if (TextUtils.isEmpty(doctor)) {
            editTextDoctor.setError("Please enter the doctor");
            editTextDoctor.requestFocus();
            return;
        }

        //if it passes all the validations

        class BookAppointment extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
               // params.put("specialization", specialization);
                params.put("pid",pid);
                params.put("fname",fname);
                params.put("lname",lname);
                params.put("gender",gender);
                params.put("email",email);
                params.put("contact",contact);
                params.put("doctor", doctor);
                params.put("docFees", docFees);
                params.put("appdate", appdate);
                params.put("apptime",apptime);
                params.put("userStatus",userStatus);
                params.put("doctorStatus",doctorStatus);

                //returing the response
                return requestHandler.sendPostRequest(Api.URL_CREATE_APPOINTMENTS, params);
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
                        JSONObject userJson = obj.getJSONObject("appointments");

                        //creating a new user object
                     //   Appointments appointments = new Appointments(
//                               // userJson.getInt("appointmentid"),
//
//                              //  userJson.getString("specialization"),
////                                userJson.getString("pid"),
////                                userJson.getString("fname"),
////                                userJson.getString("lname"),
////                                userJson.getString("gender"),
////                                userJson.getString("email"),
////                                userJson.getString("contact"),
//                                userJson.getString("doctor"),
//                                userJson.getString("docFees"),
//                                userJson.getString("appdate"),
//                                userJson.getString("apptime"),
//                                userJson.getString("userstatus"),
//                                userJson.getString("doctorstatus")
//                        );

                        //storing the user in shared preferences
                        //SharedPrefManager.getInstance(getApplicationContext()).userLogin(appointments);

                        //starting the profile activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), PatientViewAppointments.class));
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
