//package com.example.smartdoctor;
//
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.content.res.Configuration;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.v4.widget.DrawerLayout;
//import android.support.v7.app.ActionBarDrawerToggle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import android.widget.Toast;
//
//import com.example.smartdoctor.helper.SQLiteHandler;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//
//import afyapepe.mobile.helper.SQLiteHandler;
//
//public class registrarpage extends AppCompatActivity {
//
//    private ListView mDrawerList;
//    private DrawerLayout mDrawerLayout;
//    private ArrayAdapter<String> mAdapter2;
//    private ActionBarDrawerToggle mDrawerToggle;
//    private String mActivityTitle;
//    private SQLiteHandler db;
//    private SessionManager session;
//
//
//    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
//    public static final int CONNECTION_TIMEOUT = 10000;
//    public static final int READ_TIMEOUT = 15000;
//    private RecyclerView mRVFishPrice;
//  //  private AdapterFish mAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.tutal);
//        //Make call to AsyncTask
//
//
//
//
//        db = new SQLiteHandler(getApplicationContext());
//
//
//        // SqLite database handler
//        db = new SQLiteHandler(getApplicationContext());
//
//        // session manager
//        session = new SessionManager(getApplicationContext());
//
//        if (!session.isLoggedIn()) {
//            logoutUser();
//        }
//
//        // Fetching user details from SQLite
//        //HashMap<String, String> user = db.getUserDetails();
//
//        //String name = user.get("name");
//        //String email = user.get("email");
//
//        // Displaying the user details on the screen
//        // txtName.setText(name);
//        // txtEmail.setText(email);
//
//        mDrawerList = (ListView)findViewById(R.id.navList);
//        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
//        mActivityTitle = getTitle().toString();
//
//        addDrawerItems();
//        setupDrawer();
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//        new AsyncFetch().execute();
//    }
//
//    private class AsyncFetch extends AsyncTask<String, String, String> {
//        ProgressDialog pdLoading = new ProgressDialog(registrarpage.this);
//        HttpURLConnection conn;
//        URL url = null;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            //this method will be running on UI thread
//            pdLoading.setMessage("\tLoading...");
//            pdLoading.setCancelable(false);
//            pdLoading.show();
//
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            try {
//
//                // Enter URL address where your json file resides
//                // Even you can make call to php file which returns json data
//                url = new URL("http://afyapepe.com/afyapepe/usersr.php");
//
//            } catch (MalformedURLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//                return e.toString();
//            }
//            try {
//
//                // Setup HttpURLConnection class to send and receive data from php and mysql
//                conn = (HttpURLConnection) url.openConnection();
//                conn.setReadTimeout(READ_TIMEOUT);
//                conn.setConnectTimeout(CONNECTION_TIMEOUT);
//                conn.setRequestMethod("GET");
//
//                // setDoOutput to true as we recieve data from json file
//                conn.setDoOutput(true);
//
//            } catch (IOException e1) {
//                // TODO Auto-generated catch block
//                e1.printStackTrace();
//                return e1.toString();
//            }
//
//            try {
//
//                int response_code = conn.getResponseCode();
//
//                // Check if successful connection made
//                if (response_code == HttpURLConnection.HTTP_OK) {
//
//                    // Read data sent from server
//                    InputStream input = conn.getInputStream();
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
//                    StringBuilder result = new StringBuilder();
//                    String line;
//
//                    while ((line = reader.readLine()) != null) {
//                        result.append(line);
//                    }
//
//                    // Pass data to onPostExecute method
//                    return (result.toString());
//
//                } else {
//
//                    return ("unsuccessful");
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//                return e.toString();
//            } finally {
//                conn.disconnect();
//            }
//
//
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//
//            //this method will be running on UI thread
//
//            pdLoading.dismiss();
//            List<DataFish> data=new ArrayList<>();
//
//            pdLoading.dismiss();
//            try {
//
//                JSONArray jArray = new JSONArray(result);
//
//                // Extract data from json and store into ArrayList as class objects
//                for(int i=0;i<jArray.length();i++){
//                    JSONObject json_data = jArray.getJSONObject(i);
//                    DataFish fishData = new DataFish();
//                    fishData.fishImage= "";
//                    fishData.fishName= json_data.getString("firstname");
//                    fishData.catName= json_data.getString("secondName");
//                    fishData.sizeName= json_data.getString("age");
//                    fishData.price= json_data.getString("created_at");
//                    data.add(fishData);
//                }
//
//                // Setup and Handover data to recyclerview
//                mRVFishPrice = (RecyclerView)findViewById(R.id.fishPriceList);
//                mAdapter = new AdapterFish(registrarpage.this, data);
//                mRVFishPrice.setLayoutManager(new LinearLayoutManager(registrarpage.this));
//
//                mRVFishPrice.setAdapter(mAdapter);
//
//            } catch (JSONException e) {
//                Toast.makeText(registrarpage.this, e.toString(), Toast.LENGTH_LONG).show();
//            }
//
//        }
//
//    }
//
//
//
//    private void addDrawerItems() {
//        String[] osArray = { "Home", "Consultation Fee", "Todays Patient" };
//        mAdapter2 = new ArrayAdapter<String>(this,R.layout.custi, osArray);
//        mDrawerList.setAdapter(mAdapter2);
//
//        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//
//
//                switch (position) {
//                    case 0:
//                        startActivity(new Intent(getApplicationContext(), registrarpage.class));
//                        break;
//                    case 1:
//                        startActivity(new Intent(getApplicationContext(), consulfee.class));
//                        break;
//                    case 2:
//                        startActivity(new Intent(getApplicationContext(), apatient.class));
//                        break;
//
//                }
//
//
//
//
//            }
//        });
//    }
//
//    private void setupDrawer() {
//        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
//
//            /** Called when a drawer has settled in a completely open state. */
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//                getSupportActionBar().setTitle("Navigation");
//                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
//            }
//
//            /** Called when a drawer has settled in a completely closed state. */
//            public void onDrawerClosed(View view) {
//                super.onDrawerClosed(view);
//                getSupportActionBar().setTitle(mActivityTitle);
//                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
//            }
//        };
//
//        mDrawerToggle.setDrawerIndicatorEnabled(true);
//        mDrawerLayout.setDrawerListener(mDrawerToggle);
//    }
//
//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        // Sync the toggle state after onRestoreInstanceState has occurred.
//        mDrawerToggle.syncState();
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        mDrawerToggle.onConfigurationChanged(newConfig);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//
//
//
//
//
//
//
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_refresh) {
//            logoutUser();
//
//            return true;
//        }
//
//        // Activate the navigation drawer toggle
//        if (mDrawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//
//
//
//
//
//
//    private void logoutUser() {
//        session.setLogin(false);
//
//        db.deleteUsers();
//
//        // Launching the login activity
//        Intent intent = new Intent(registrarpage.this, LoginActivity.class);
//        startActivity(intent);
//        finish();
//    }
//
//
//
//
//}