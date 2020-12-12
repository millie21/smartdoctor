package com.example.smartdoctor.Admin;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartdoctor.Api;
import com.example.smartdoctor.Doctor.Doctor_ViewPatients;
import com.example.smartdoctor.Doctor.Hero;
import com.example.smartdoctor.Objects.Doctors;
import com.example.smartdoctor.R;
import com.example.smartdoctor.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;

public class Admin_ViewDoctors extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    ProgressBar progressBar;
    ListView listView;

    List<Doctors> doctorsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_admin__view_doctors);

        this.getSupportActionBar().hide();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        listView = (ListView) findViewById(R.id.listViewDoctors);

        doctorsList = new ArrayList<>();

        readDocs();

    }

    private void readDocs() {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_DOC, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void deleteDocs(String email) {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_DELETE_DOC + email, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void refreshHeroList(JSONArray doctors) throws JSONException {
        doctorsList.clear();

        for (int i = 0; i < doctors.length(); i++) {
            JSONObject obj = doctors.getJSONObject(i);

            doctorsList.add(new Doctors(
                    obj.getString("username"),
                    obj.getString("email"),
                    obj.getString("spec"),
                    obj.getString("docFees")
            ));
        }

        DoctorAdapter adapter = new DoctorAdapter(doctorsList);
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
                    refreshHeroList(object.getJSONArray("doctors"));
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

    class DoctorAdapter extends ArrayAdapter<Doctors> {
        List<Doctors> doctorsList;

        public DoctorAdapter(List<Doctors> doctorsList) {
            super(Admin_ViewDoctors.this, R.layout.layout_doctor_list, doctorsList);
            this.doctorsList = doctorsList;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_doctor_list, null, true);

            TextView textViewUsername = listViewItem.findViewById(R.id.textViewUsername);
            TextView textViewEmail = listViewItem.findViewById(R.id.textViewEmail);
            TextView textViewSpec = listViewItem.findViewById(R.id.textViewSpec);
            TextView textViewDocFees = listViewItem.findViewById(R.id.textViewdocFees);

//            TextView textViewUpdate = listViewItem.findViewById(R.id.textViewUpdate);
            Button textViewDelete = listViewItem.findViewById(R.id.textViewDeletedoc);

            final Doctors doctors = doctorsList.get(position);

            textViewUsername.setText(doctors.getUsername());
            textViewEmail.setText(doctors.getEmail());
            textViewSpec.setText(doctors.getSpec());
            textViewDocFees.setText(doctors.getDocFees());



            textViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Admin_ViewDoctors.this);

                    builder.setTitle("Delete " + doctors.getUsername())
                            .setMessage("Are you sure you want to delete it?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteDocs(doctors.getEmail());
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            });

            return listViewItem;
        }
    }

}

