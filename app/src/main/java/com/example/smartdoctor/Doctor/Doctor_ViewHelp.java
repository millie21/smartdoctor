package com.example.smartdoctor.Doctor;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartdoctor.Api;
import com.example.smartdoctor.Objects.Issue;
import com.example.smartdoctor.R;
import com.example.smartdoctor.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;

public class Doctor_ViewHelp extends AppCompatActivity {
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    EditText editTextHelpId, editTextHelpTitle, editTextHelpDetail;
    // RatingBar ratingBar;
    //Spinner spinnerTeam;
    ProgressBar progressBar;
    ListView listView;
    Button buttonAddUpdate;
    Button buttonViewPatients;
    List<Issue> issueList;
    boolean isUpdating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_doctor__view_help);

        this.getSupportActionBar().hide();

        // buttonViewPatients = (Button) findViewById(R.id.buttonview);
        editTextHelpId = (EditText) findViewById(R.id.editTextHelpId);
        editTextHelpTitle = (EditText) findViewById(R.id.editTextHelpTitle);
        editTextHelpDetail = (EditText) findViewById(R.id.editTextHelpDetail);
//        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
//        editTextPhoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);
        // ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        // spinnerTeam = (Spinner) findViewById(R.id.spinnerTeamAffiliation);

        buttonAddUpdate = (Button) findViewById(R.id.buttonAddUpdate);
        buttonViewPatients = (Button) findViewById(R.id.buttonview);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        listView = (ListView) findViewById(R.id.listViewHeroes);

        issueList = new ArrayList<>();

//        buttonViewPatients.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                Intent intent = new Intent(view.getContext(),Doctor_ViewPatients.class);
//                view.getContext().startActivity(intent);
//
//            }
//        });

        buttonAddUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isUpdating) {
                  //  updateHero();
                } else {
                    createIssue();
                }
            }
        });
        readIssues();
    }
    private void createIssue() {
        String title = editTextHelpTitle.getText().toString().trim();
        String detail = editTextHelpDetail.getText().toString().trim();



        if (TextUtils.isEmpty(title)) {
            editTextHelpTitle.setError("Please enter a brief title");
            editTextHelpTitle.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(detail)) {
            editTextHelpDetail.setError("Please enter last name");
            editTextHelpDetail.requestFocus();
            return;
        }


        HashMap<String, String> params = new HashMap<>();
        params.put("title", title);
        params.put("detail", detail);


        Doctor_ViewHelp.PerformNetworkRequest request = new Doctor_ViewHelp.PerformNetworkRequest(Api.URL_CREATE_ISSUE, params, CODE_POST_REQUEST);
        request.execute();
    }

    private void readIssues() {
        Doctor_ViewHelp.PerformNetworkRequest request = new Doctor_ViewHelp.PerformNetworkRequest(Api.URL_READ_HEROES, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void refreshHeroList(JSONArray issues) throws JSONException {
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

        Doctor_ViewHelp.IssueAdapter adapter = new Doctor_ViewHelp.IssueAdapter(issueList);
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
                    refreshHeroList(object.getJSONArray("issues"));
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
    class IssueAdapter extends ArrayAdapter<Issue> {
        List<Issue> issueList;

        public IssueAdapter(List<Issue> issueList) {
            super(Doctor_ViewHelp.this, R.layout.layout_hero_list, issueList);
            this.issueList = issueList;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_hero_list, null, true);

            TextView textViewName = listViewItem.findViewById(R.id.textViewName);

            TextView textViewUpdate = listViewItem.findViewById(R.id.textViewUpdate);
            TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);

            final Issue issue = issueList.get(position);

            textViewName.setText(issue.getMessage());

//            textViewUpdate.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    isUpdating = true;
//                    editTextHelpId.setText(String.valueOf(hero.getHelpId()));
//                    editTextHelpTitle.setText(hero.getHelpTitle());
//                    editTextHelpDetail.setText(hero.getHelpDetail());
//                    buttonAddUpdate.setText("Update");
//                }
//            });

            textViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Doctor_ViewHelp.this);

                    builder.setTitle("Delete " + issue.getMessage())
                            .setMessage("Are you sure you want to delete it?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                  //  deleteHero(hero.getId());
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
