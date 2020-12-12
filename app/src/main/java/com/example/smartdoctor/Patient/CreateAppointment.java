package com.example.smartdoctor.Patient;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartdoctor.Api;
import com.example.smartdoctor.Objects.Appointments;
import com.example.smartdoctor.Doctor.HomeActivity;
import com.example.smartdoctor.Doctor.ProfileView;
import com.example.smartdoctor.R;
import com.example.smartdoctor.RequestHandler;
import com.example.smartdoctor.SharedPreferences.SharedPrefManager;
import com.example.smartdoctor.Objects.User;
import com.example.smartdoctor.Patient.UserLoginActivity;
import com.example.smartdoctor.Patient.UserRegisterActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

public class CreateAppointment extends AppCompatActivity {

    EditText editTextFee, editTextDate ;
    Spinner  editTextDoctor,editTextTime;
    TextView textViewId, textViewFirstname, textViewEmail, textViewGender,textViewLastname, textViewContact, textViewUserstatus, textViewDoctorstatus;
    TextView editTextOne;

  //specialiation
    ArrayList<String> listItems=new ArrayList<>();
    ArrayAdapter<String> spadapter;
    Spinner sp;

    //doctors

    ArrayList<String> listItemsdoctor=new ArrayList<>();
    ArrayAdapter<String> adapterdoctor;
    Spinner spdoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_create_appointment);

        this.getSupportActionBar().hide();

        spdoctor=(Spinner)findViewById(R.id.editTextDoctor);
        sp=(Spinner)findViewById(R.id.editTextSpecialization);

        //spec spinner
        spadapter=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.txt,listItems);
        sp.setAdapter(spadapter);

        //doctor spinner
        adapterdoctor=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.txt,listItemsdoctor);
        spdoctor.setAdapter(adapterdoctor);

        editTextDoctor = (Spinner) findViewById(R.id.editTextDoctor);
        editTextFee = (EditText) findViewById(R.id.editTextConsultationFees);
        editTextDate = (EditText) findViewById(R.id.editTextDate);
        editTextTime = (Spinner) findViewById(R.id.editTextTime);
        editTextOne = (TextView)findViewById(R.id.editTextOne);
        findViewById(R.id.buttonCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookAppointment();
            }
        });

    }

//doctor spinner
public void onStart(){
    super.onStart();
    BackTask bt=new BackTask();
    super.onStart();
    BackTask2 bt2=new BackTask2();
    bt.execute();
    bt2.execute();
}
    private class BackTask extends AsyncTask<Void,Void,Void> {
        ArrayList<String> list;

        protected void onPreExecute() {
            super.onPreExecute();
            list = new ArrayList<>();
        }

        protected Void doInBackground(Void... params) {
            InputStream is = null;
            String result = "";
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://192.168.56.1/HeroApi/v1/doctorspinner.php");
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                // Get our response as a String.
                is = entity.getContent();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //convert response to string
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    result += line;
                }
                is.close();
                //result=sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // parse json data
            try {
                JSONArray jArray = new JSONArray(result);
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jsonObject = jArray.getJSONObject(i);
                    // add interviewee name to arraylist
                    list.add(jsonObject.getString("username"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            listItemsdoctor.addAll(list);
            adapterdoctor.notifyDataSetChanged();
        }


    }
// spec spinner
//
//    public void onStart2(){
//        super.onStart2();
//        BackTask2 bt2=new BackTask2();
//        bt2.execute();
//    }
    private class BackTask2 extends AsyncTask<Void,Void,Void> {
        ArrayList<String> list2;

        protected void onPreExecute() {
            super.onPreExecute();
            list2 = new ArrayList<>();
        }

        protected Void doInBackground(Void... params) {
            InputStream is = null;
            String result = "";
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://192.168.56.1/HeroApi/v1/getdoctorspecialization.php");
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                // Get our response as a String.
                is = entity.getContent();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //convert response to string
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    result += line;
                }
                is.close();
                //result=sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // parse json data
            try {
                JSONArray jArray = new JSONArray(result);
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jsonObject = jArray.getJSONObject(i);
                    // add interviewee name to arraylist
                    list2.add(jsonObject.getString("spec"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            listItems.addAll(list2);
            spadapter.notifyDataSetChanged();
        }

    }

    private void bookAppointment() {

        User user = SharedPrefManager.getInstance(this).getUser();

        final String pid = String.valueOf(user.getPid());
        final String fname = user.getFname();
        final String lname = user.getLname();
        final String gender = user.getGender();
        final String email = user.getEmail();
        final String contact = user.getContact();

        final String doctor = editTextDoctor.getSelectedItem().toString();
        final String docFees = editTextFee.getText().toString().trim();
        final String appdate = editTextDate.getText().toString().trim();
        final String apptime = editTextTime.getSelectedItem().toString();
        final String userStatus = editTextOne.getText().toString().trim();
        final String doctorStatus = editTextOne.getText().toString().trim();
        //first we will do the validations


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
                        Appointments appointments = new Appointments(
                                userJson.getInt("id"),

                              //  userJson.getString("specialization"),
//                                userJson.getString("pid"),
//                                userJson.getString("fname"),
//                                userJson.getString("lname"),
//                                userJson.getString("gender"),
//                                userJson.getString("email"),
//                                userJson.getString("contact"),
                                userJson.getString("doctor"),
                                userJson.getString("docFees"),
                                userJson.getString("appdate"),
                                userJson.getString("apptime"),
                                userJson.getString("userstatus"),
                                userJson.getString("doctorstatus")
                        );

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
