//package com.example.smartdoctor.Patient;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.ListAdapter;
//import android.widget.ListView;
//import android.widget.SimpleAdapter;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.smartdoctor.AppController;
//import com.example.smartdoctor.AppointmentsAdapter;
//import com.example.smartdoctor.Objects.Appointments;
//import com.example.smartdoctor.Objects.User;
//import com.example.smartdoctor.R;
//import com.example.smartdoctor.SharedPreferences.SharedPrefManager;
//import com.example.smartdoctor.helper.SQLiteHandler;
//import com.example.smartdoctor.helper.SessionManager;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.ProtocolException;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.DefaultRetryPolicy;
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.RetryPolicy;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//
//import static com.example.smartdoctor.AppController.TAG;
//
//public class ViewPastAppointments extends AppCompatActivity {
//
//    ListView TaskListView;
//
//    private ProgressDialog pDialog;
//    private SQLiteHandler db;
//    private SessionManager session;
//
//    AppointmentsAdapter adapter;
//    RetryPolicy setRetryPolicy;
//
//    List<Appointments> appointmentsList = new ArrayList<>();
//
//    private static String breedurl = "http://172.28.128.1/HeroApi/v1/getmyappointments.php";
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.content_view_past_appointments);
//
//        db = new SQLiteHandler(ViewPastAppointments.this);
//
//        session = new SessionManager(ViewPastAppointments.this);
//
//        HashMap<String, String> user = db.getUserDetails();
//
//        String email = user.get("email");
//
//        View empty = findViewById(R.id.list_empty);
//        TaskListView = (ListView) findViewById(R.id.listview11);
//        TaskListView.setEmptyView(empty);
//
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("email", email);
//
//        final StringRequest stringRequest = new StringRequest(Request.Method.POST, breedurl,
//
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d(TAG, response.toString());
//                        //progressBar.dismiss();
//                        try {
//                            JSONArray request = new JSONArray(response);
//                            for (int i = 0; i < request.length(); i++) {
//                                Appointments appointments = new Appointments();
//                                JSONObject jsonObject = null;
//                                jsonObject = request.getJSONObject(i);
//                                appointments.setDoctor(jsonObject.getString("doctor"));
//                                appointments.setDocFees(jsonObject.getString("docFees"));
//                                appointments.setAppdate(jsonObject.getString("apptime"));
//
//                                appointmentsList.add(appointments);
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(ViewPastAppointments.this, e.toString(), Toast.LENGTH_LONG).show();
//                        }
//
//                        adapter.notifyDataSetChanged();
//
//                        pDialog.dismiss();
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                if (pDialog != null) {
//                    pDialog.dismiss();
//                    pDialog = null;
//                }
//                Toast.makeText(ViewPastAppointments.this, error.toString(), Toast.LENGTH_LONG).show();
//                error.printStackTrace();
//            }
//        })
//
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                db = new SQLiteHandler(ViewPastAppointments.this);
//
//                // Fetching user details from SQLite
//                HashMap<String, String> user = db.getUserDetails();
//
//                String email = user.get("email");
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("email", email);
//
//                return params;
//            }
//        };
//
//        int socketTimeout = 90000; // 30 seconds. You can change it
//        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//
//        stringRequest.setRetryPolicy(policy);
//        AppController.getInstance().addToRequestQueue(stringRequest);
//
//        //error 500
//        params = new HashMap<String, String>();
//        params.put("email", email);
//    }
//}
