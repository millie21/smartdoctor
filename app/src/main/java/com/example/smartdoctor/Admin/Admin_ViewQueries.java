package com.example.smartdoctor.Admin;

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
import com.example.smartdoctor.Objects.Issue;
import com.example.smartdoctor.Objects.Prescriptions;
import com.example.smartdoctor.Patient.Contact;
import com.example.smartdoctor.R;
import com.example.smartdoctor.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;

public class Admin_ViewQueries extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    ProgressBar progressBar;
    ListView listView;
    // Button buttonAddUpdate;

    List<Issue> issueList;

    boolean isUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_admin__view_queries);

        this.getSupportActionBar().hide();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        listView = (ListView) findViewById(R.id.listViewPrescriptions);

        issueList = new ArrayList<>();

        readHeroes();
    }
    private void readHeroes() {
      PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_ISSUE, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void refreshAppointmentsList(JSONArray issues) throws JSONException {
        issueList.clear();

        for (int i = 0; i < issues.length(); i++) {
            JSONObject obj = issues.getJSONObject(i);

            issueList.add(new Issue(
                     obj.getString("name"),
                    obj.getString("email"),
                    obj.getString("contact"),
                    obj.getString("message")

            ));
        }

        AppointmentAdapter adapter = new AppointmentAdapter(issueList);
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
                    refreshAppointmentsList(object.getJSONArray("contacts"));
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

    class AppointmentAdapter extends ArrayAdapter<Issue> {
        List<Issue> issueList;

        public AppointmentAdapter(List<Issue> issueList) {
            super(Admin_ViewQueries.this, R.layout.layout_issues_list, issueList);
            this.issueList = issueList;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_issues_list, null, true);

            TextView textViewname = listViewItem.findViewById(R.id.textname);
            TextView textViewemail = listViewItem.findViewById(R.id.textemail);
            TextView textViewcontact = listViewItem.findViewById(R.id.textcontact);
            TextView textViewmessage = listViewItem.findViewById(R.id.textmessage);

            final Issue issue = issueList.get(position);

            textViewname.setText(issue.getName());
            textViewemail.setText(issue.getEmail());
            textViewcontact.setText(issue.getContact());
            textViewmessage.setText(issue.getMessage());

            return listViewItem;
        }
    }

}
