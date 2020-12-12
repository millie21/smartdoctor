package com.example.smartdoctor.Doctor;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartdoctor.Api;
import com.example.smartdoctor.Objects.BlochainHealthData;
import com.example.smartdoctor.Objects.HealthData;
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

public class PatientHealthData extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    ProgressBar progressBar;
    ListView listView;
    List<BlochainHealthData> datasList;

    boolean isUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_patient_health_data);
        this.getSupportActionBar().hide();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        listView = (ListView) findViewById(R.id.listViewData);

        datasList = new ArrayList<>();

        readHeroes();
    }
    private void readHeroes() {
       PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_HEALTHDATA, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void refreshAppointmentsList(JSONArray appointments) throws JSONException {
        datasList.clear();

        for (int i = 0; i < appointments.length(); i++) {
            JSONObject obj = appointments.getJSONObject(i);

            datasList.add(new BlochainHealthData(
                    obj.getString("fname"),
                    obj.getString("PreviousHash"),
                  //   obj.getString("specialization"),
                    obj.getString("Temperature"),
                    obj.getString("Heartrate"),
                    obj.getString("Respiration"),
                    obj.getString("Spo2")

            ));
        }

        PatientHealthData.DataAdapter adapter = new PatientHealthData.DataAdapter(datasList);
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
                    refreshAppointmentsList(object.getJSONArray("datas"));
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

    class DataAdapter extends ArrayAdapter<BlochainHealthData> {
        List<BlochainHealthData> datasList;

        public DataAdapter(List<BlochainHealthData> datasList) {
            super(PatientHealthData.this, R.layout.layout_blochain_data, datasList);
            this.datasList = datasList;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_blochain_data, null, true);

            TextView textViewtemperature = listViewItem.findViewById(R.id.textViewfname);
            TextView textViewheartrate = listViewItem.findViewById(R.id.textViewheartrate);
            TextView textViewrespiration = listViewItem.findViewById(R.id.textViewrespiration);
            TextView textViewspo2 = listViewItem.findViewById(R.id.textViewspo2);
            TextView textViewfname = listViewItem.findViewById(R.id.textViewtemperature);
            TextView textViewprevhash = listViewItem.findViewById(R.id.textViewprevhash);

            final BlochainHealthData blochainHealthData = datasList.get(position);

            textViewtemperature.setText(blochainHealthData.getTemperature());
            textViewheartrate.setText(blochainHealthData.getHeartrate());
            textViewrespiration.setText(blochainHealthData.getRespiration());
            textViewspo2.setText(blochainHealthData.getSpo2());
            textViewfname.setText(blochainHealthData.getFname());
            textViewprevhash.setText(blochainHealthData.getPreviousHash());

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
