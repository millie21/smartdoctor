package com.example.smartdoctor.Admin;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartdoctor.Api;
import com.example.smartdoctor.Objects.Prescriptions;
import com.example.smartdoctor.R;
import com.example.smartdoctor.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;

public class Admin_ViewPrescriptions extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    ProgressBar progressBar;
    ListView listView;
    // Button buttonAddUpdate;

    List<Prescriptions> prescriptionsList;

    boolean isUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_view_upcoming_appointments);

        this.getSupportActionBar().hide();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        listView = (ListView) findViewById(R.id.listViewPrescriptions);

        prescriptionsList = new ArrayList<>();

        readHeroes();
    }
    private void readHeroes() {
        Admin_ViewPrescriptions.PerformNetworkRequest request = new Admin_ViewPrescriptions.PerformNetworkRequest(Api.URL_READ_PRESCRIPTIONS, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void refreshAppointmentsList(JSONArray appointments) throws JSONException {
        prescriptionsList.clear();

        for (int i = 0; i < appointments.length(); i++) {
            JSONObject obj = appointments.getJSONObject(i);

            prescriptionsList.add(new Prescriptions(
                    // obj.getString("specialization"),
                    obj.getString("doctor"),
                    obj.getString("appdate"),
                    obj.getString("prescription")

            ));
        }

        Admin_ViewPrescriptions.AppointmentAdapter adapter = new Admin_ViewPrescriptions.AppointmentAdapter(prescriptionsList);
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
                    refreshAppointmentsList(object.getJSONArray("prescriptions"));
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

    class AppointmentAdapter extends ArrayAdapter<Prescriptions> {
        List<Prescriptions> prescriptionsList;

        public AppointmentAdapter(List<Prescriptions> prescriptionsList) {
            super(Admin_ViewPrescriptions.this, R.layout.layout_prescriptions_list, prescriptionsList);
            this.prescriptionsList = prescriptionsList;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_prescriptions_list, null, true);

            TextView textViewappdate = listViewItem.findViewById(R.id.textViewappdate);
            TextView textViewDoctor = listViewItem.findViewById(R.id.textViewDoctor);
            TextView textViewprescription = listViewItem.findViewById(R.id.textViewFees);

            final Prescriptions prescriptions = prescriptionsList.get(position);

            textViewappdate.setText(prescriptions.getAppdate());
            textViewDoctor.setText(prescriptions.getDoctor());
            textViewprescription.setText(prescriptions.getPrescription());

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
