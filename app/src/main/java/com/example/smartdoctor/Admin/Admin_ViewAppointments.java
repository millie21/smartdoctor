package com.example.smartdoctor.Admin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
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
import com.example.smartdoctor.Patient.CreateAppointment;
import com.example.smartdoctor.Patient.ViewAllAppointments;
import com.example.smartdoctor.Patient.ViewPrescriptions;
import com.example.smartdoctor.R;
import com.example.smartdoctor.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;

public class Admin_ViewAppointments extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    EditText editTextDoctor, editTextFee, editTextDate ;
    Spinner editTextSpecialization,editTextTime;

    String email;

    ProgressBar progressBar;
    ListView listView;
    // Button buttonAddUpdate;

    List<Appointments> appointmentsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_admin_view_appointments);

        this.getSupportActionBar().hide();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        listView = (ListView) findViewById(R.id.listViewAppointments);

        appointmentsList = new ArrayList<>();

        readHeroes();
    }

    private void readHeroes() {
       PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_APPOINTMENTS, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void refreshAppointmentsList(JSONArray appointments) throws JSONException {
        appointmentsList.clear();

        for (int i = 0; i < appointments.length(); i++) {
            JSONObject obj = appointments.getJSONObject(i);

            appointmentsList.add(new Appointments(
                     obj.getInt("id"),
                    obj.getString("doctor"),
                    obj.getString("docFees"),
                    obj.getString("appdate"),
                    obj.getString("apptime"),
                    obj.getString("userStatus"),
                    obj.getString("doctorStatus")
            ));
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
            super(Admin_ViewAppointments.this, R.layout.layout_admin_appointment_list, appointmentsList);
            this.appointmentsList = appointmentsList;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_admin_appointment_list, null, true);

            TextView textViewappdate = listViewItem.findViewById(R.id.textViewappdate);
            TextView textViewDoctor = listViewItem.findViewById(R.id.textViewDoctor);
            TextView textViewdocFees = listViewItem.findViewById(R.id.textViewFees);
            TextView textViewTime = listViewItem.findViewById(R.id.textViewTime);

            final Appointments appointments = appointmentsList.get(position);

            textViewappdate.setText(appointments.getAppdate());
            textViewDoctor.setText(appointments.getDoctor());
            textViewdocFees.setText(appointments.getDocFees());
            textViewTime.setText(appointments.getApptime());


            return listViewItem;
        }
    }

}
