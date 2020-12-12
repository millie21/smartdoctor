package com.example.smartdoctor.SharedPreferences;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.smartdoctor.Doctor.DoctorLoginActivity;
import com.example.smartdoctor.Objects.Doctors;
import com.example.smartdoctor.Patient.UserLoginActivity;

public class DoctorSharedPrefManager {

    //the constants
    private static final String SHARED_PREF_NAME2 = "simplifiedcodingsharedpref2";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_SPEC = "keyspec";
    private static final String KEY_DOCFEES = "keydocfees";
   // private static final String KEY_ID = "keyid";

    private static DoctorSharedPrefManager mInstance;
    private static Context mCtx;

    private DoctorSharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized DoctorSharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DoctorSharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void doctorLogin(Doctors doctors) {
        SharedPreferences dsharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME2, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = dsharedPreferences.edit();
        editor.putString(KEY_USERNAME, doctors.getUsername());
        editor.putString(KEY_EMAIL, doctors.getEmail());
        editor.putString(KEY_SPEC, doctors.getSpec());
        editor.putString(KEY_DOCFEES, doctors.getDocFees());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME2, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    //this method will give the logged in user
    public Doctors getDoctors() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME2, Context.MODE_PRIVATE);
        return new Doctors(
              //  sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_SPEC, null),
                sharedPreferences.getString(KEY_DOCFEES, null)
        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME2, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, DoctorLoginActivity.class));
    }
}