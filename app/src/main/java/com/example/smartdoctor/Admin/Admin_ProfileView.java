package com.example.smartdoctor.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

//import com.example.smartdoctor.SharedPreferences.AdminSharedPrefManager;
//import com.example.smartdoctor.AdminSharedPrefManager;
//import com.example.smartdoctor.SharedPreferences.AdminSharedPrefManager;
//import com.example.smartdoctor.AdminSharedPrefManager;
//import com.example.smartdoctor.AdminSharedPrefManager;
import com.example.smartdoctor.AdminSharedPrefManager;
import com.example.smartdoctor.Objects.Admins;
import com.example.smartdoctor.R;
import com.example.smartdoctor.SharedPreferences.SharedPrefManager;
import com.example.smartdoctor.Objects.User;
import com.example.smartdoctor.Patient.UserLoginActivity;

public class Admin_ProfileView extends AppCompatActivity {
    TextView textViewEmail;//, textViewGendertextViewId, textViewFirstname;
    TextView textViewUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_admin_profile_view);

        this.getSupportActionBar().hide();
        //if the user is not logged in
        //starting the login activity
//        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
//            finish();
//            startActivity(new Intent(this, UserLoginActivity.class));
//        }

       // textViewId = (TextView) findViewById(R.id.textViewId);
        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
      //  textViewGender = (TextView) findViewById(R.id.textViewGender);
       // textViewPhonenumber = (TextView) findViewById(R.id.textViewPhonenumber);

        //getting the current user
        Admins admins = AdminSharedPrefManager.getInstance(this).getAdmins();

        //setting the values to the textviews
      //  textViewId.setText(String.valueOf(user.getPid()));
        textViewUsername.setText(admins.getUsername());
        textViewEmail.setText(admins.getEmail());
      //  textViewGender.setText(user.getGender());

        //when the user presses logout button
        //calling the logout method
        findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });
    }
}
