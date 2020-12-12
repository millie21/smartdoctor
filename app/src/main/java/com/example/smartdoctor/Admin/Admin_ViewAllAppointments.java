package com.example.smartdoctor.Admin;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartdoctor.Api;
import com.example.smartdoctor.Objects.Appointments;
import com.example.smartdoctor.R;
import com.example.smartdoctor.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;

public class Admin_ViewAllAppointments extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    EditText editTextDoctor, editTextFee, editTextDate ;
    Spinner editTextSpecialization,editTextTime;

    ProgressBar progressBar;
    ListView listView;
   // Button buttonAddUpdate;

    List<Appointments> appointmentsList;

    boolean isUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_view_all_appointments);

        this.getSupportActionBar().hide();

//        //if the user is already logged in we will directly start the profile activity
//        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
//            finish();
//            startActivity(new Intent(this, HomeActivity.class));
//            return;
//        }

//        editTextSpecialization = (Spinner) findViewById(R.id.editTextSpecialization);
//        editTextDoctor = (EditText) findViewById(R.id.editTextDoctors);
//        editTextFee = (EditText) findViewById(R.id.editTextConsultationFees);
//        editTextDate = (EditText) findViewById(R.id.editTextDate);
//        editTextTime = (Spinner) findViewById(R.id.editTextTime);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        listView = (ListView) findViewById(R.id.listViewAppointments);

        appointmentsList = new ArrayList<>();
//        findViewById(R.id.buttonCreate).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //if user pressed on button register
//                //here we will register the user to server
//                bookAppointment();
//            }
//        });
        readHeroes();
    }
//    private void bookAppointment() {
//     //   final String specialization = editTextSpecialization.getSelectedItem().toString();
//        final String doctor = editTextDoctor.getText().toString().trim();
//        final String docFees = editTextFee.getText().toString().trim();
//        final String appdate = editTextDate.getText().toString().trim();
//        final String apptime = editTextTime.getSelectedItem().toString();
//        //first we will do the validations
//
//
//        if (TextUtils.isEmpty(doctor)) {
//            editTextDoctor.setError("Please enter the doctor");
//            editTextDoctor.requestFocus();
//            return;
//        }
//
//        class BookAppointment extends AsyncTask<Void, Void, String> {
//
//            private ProgressBar progressBar;
//
//            @Override
//            protected String doInBackground(Void... voids) {
//                //creating request handler object
//                RequestHandler requestHandler = new RequestHandler();
//
//                //creating request parameters
//                HashMap<String, String> params = new HashMap<>();
//              //  params.put("specialization", specialization);
//                params.put("doctor", doctor);
//                params.put("docFees", docFees);
//                params.put("appdate", appdate);
//                params.put("apptime",apptime);
//
//                //returing the response
//                return requestHandler.sendPostRequest(Api.URL_CREATE_APPOINTMENTS, params);
//            }
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                //displaying the progress bar while user registers on the server
//                progressBar = (ProgressBar) findViewById(R.id.progressBar);
//                progressBar.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                //hiding the progressbar after completion
//                progressBar.setVisibility(View.GONE);
//
//                try {
//                    //converting response to json object
//                    JSONObject obj = new JSONObject(s);
//
//                    //if no error in response
//                    if (!obj.getBoolean("error")) {
//                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
//
//                        //getting the user from the response
//                        JSONObject userJson = obj.getJSONObject("appointments");
//
//                        //creating a new user object
//                        Appointments appointments = new Appointments(
//                                // userJson.getInt("appointmentid"),
//                             //   userJson.getString("specialization"),
//                                userJson.getString("doctor"),
//                                userJson.getString("docFees"),
//                                userJson.getString("appdate"),
//                                userJson.getString("apptime"),
//                                userJson.getString("userstatus"),
//                                userJson.getString("doctorstatus")
//                        );
//
//                        //storing the user in shared preferences
//                        //SharedPrefManager.getInstance(getApplicationContext()).userLogin(appointments);
//
//                        //starting the profile activity
//                        finish();
//                        startActivity(new Intent(getApplicationContext(), PatientViewAppointments.class));
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        //executing the async task
//        BookAppointment ru = new BookAppointment();
//        ru.execute();
//    }

    private void readHeroes() {
        Admin_ViewAllAppointments.PerformNetworkRequest request = new Admin_ViewAllAppointments.PerformNetworkRequest(Api.URL_READ_APPOINTMENTS, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void refreshAppointmentsList(JSONArray appointments) throws JSONException {
        appointmentsList.clear();

        for (int i = 0; i < appointments.length(); i++) {
            JSONObject obj = appointments.getJSONObject(i);

//            appointmentsList.add(new Appointments(
//                   // obj.getString("specialization"),
//                    obj.getString("doctor"),
//                    obj.getString("docFees"),
//                    obj.getString("appdate"),
//                    obj.getString("apptime"),
//                    obj.getString("userStatus"),
//                    obj.getString("doctorStatus")
//            ));
        }

        AppointmentAdapter adapter = new AppointmentAdapter(appointmentsList);
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
                    refreshAppointmentsList(object.getJSONArray("appointments"));
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

    class AppointmentAdapter extends ArrayAdapter<Appointments> {
        List<Appointments> appointmentsList;

        public AppointmentAdapter(List<Appointments> appointmentsList) {
            super(Admin_ViewAllAppointments.this, R.layout.layout_appointment_list, appointmentsList);
            this.appointmentsList = appointmentsList;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_appointment_list, null, true);

            TextView textViewappdate = listViewItem.findViewById(R.id.textViewappdate);
            TextView textViewDoctor = listViewItem.findViewById(R.id.textViewDoctor);
            TextView textViewdocFees = listViewItem.findViewById(R.id.textViewFees);

            final Appointments appointments = appointmentsList.get(position);

            textViewappdate.setText(appointments.getAppdate());
            textViewDoctor.setText(appointments.getDoctor());
            textViewdocFees.setText(appointments.getDocFees());

//            textViewUpdate.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    isUpdating = true;
//                  //  editTextPatientId.setText(String.valueOf(appointments.getId()));
//                    editTextFirstName.setText(appointments.getFirstName());
//                    editTextLastName.setText(appointments.getLastName());
//                    editTextEmail.setText(appointments.getEmail());
//                    editTextPhoneNumber.setText(appointments.getPhonenumber());
//                    // spinnerTeam.setSelection(((ArrayAdapter<String>) spinnerTeam.getAdapter()).getPosition(hero.getTeamaffiliation()));
//                    buttonAddUpdate.setText("Update");
//                }
//            });

//            textViewDelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(ViewAllAppointments.this);
//
//                    builder.setTitle("Delete " + appointments.getName())
//                            .setMessage("Are you sure you want to delete it?")
//                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    deleteHero(appointments.getId());
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

            return listViewItem;
        }
    }

}
