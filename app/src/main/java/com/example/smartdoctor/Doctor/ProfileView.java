package com.example.smartdoctor.Doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.smartdoctor.R;
import com.example.smartdoctor.SharedPreferences.SharedPrefManager;
import com.example.smartdoctor.Objects.User;
import com.example.smartdoctor.Patient.UserLoginActivity;

public class ProfileView extends AppCompatActivity {
    TextView textViewId, textViewFirstname, textViewEmail, textViewGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_profile_view);

        this.getSupportActionBar().hide();

        //if the user is not logged in
        //starting the login activity
//        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
//            finish();
//            startActivity(new Intent(this, UserLoginActivity.class));
//        }

        textViewId = (TextView) findViewById(R.id.textViewId);
        textViewFirstname = (TextView) findViewById(R.id.textViewUsername);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewGender = (TextView) findViewById(R.id.textViewGender);
       // textViewPhonenumber = (TextView) findViewById(R.id.textViewPhonenumber);

        //getting the current user
        User user = SharedPrefManager.getInstance(this).getUser();

        //setting the values to the textviews
        textViewId.setText(String.valueOf(user.getPid()));
        textViewFirstname.setText(user.getFname());
        textViewEmail.setText(user.getEmail());
        textViewGender.setText(user.getGender());

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
