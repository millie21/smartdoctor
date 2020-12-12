package com.example.smartdoctor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.smartdoctor.Objects.Admins;
import com.example.smartdoctor.Patient.UserLoginActivity;

public class AdminSharedPrefManager {

        //the constants
        private static final String SHARED_PREF_NAME3 = "simplifiedcodingsharedpref3";
        private static final String KEY_EMAIL = "keyemail";
        private static final String KEY_USERNAME = "keyusername";
//        private static final String KEY_SPEC = "keyspec";
//        private static final String KEY_DOCFEES = "keydocfees";
        // private static final String KEY_ID = "keyid";

        private static com.example.smartdoctor.AdminSharedPrefManager mInstance;
        private static Context mCtx;

        private AdminSharedPrefManager(Context context) {
            mCtx = context;
        }

        public static synchronized com.example.smartdoctor.AdminSharedPrefManager getInstance(Context context) {
            if (mInstance == null) {
                mInstance = new AdminSharedPrefManager(context);
            }
            return mInstance;
        }

        //method to let the user login
        //this method will store the user data in shared preferences
        public void adminLogin(Admins admins) {
            SharedPreferences dsharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME3, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = dsharedPreferences.edit();
            editor.putString(KEY_EMAIL, admins.getEmail());
            editor.putString(KEY_USERNAME, admins.getUsername());

            editor.apply();
        }

        //this method will checker whether user is already logged in or not
        public boolean isLoggedIn() {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME3, Context.MODE_PRIVATE);
            return sharedPreferences.getString(KEY_USERNAME, null) != null;
        }

        //this method will give the logged in user
        public Admins getAdmins() {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME3, Context.MODE_PRIVATE);
            return new Admins(
                    //  sharedPreferences.getInt(KEY_ID, -1),
                    sharedPreferences.getString(KEY_EMAIL, null),
//                    sharedPreferences.getString(KEY_SPEC, null),
                    sharedPreferences.getString(KEY_USERNAME, null)
//                    sharedPreferences.getString(KEY_DOCFEES, null)
            );
        }

        //this method will logout the user
        public void logout() {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME3, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            mCtx.startActivity(new Intent(mCtx, UserLoginActivity.class));
        }
    }