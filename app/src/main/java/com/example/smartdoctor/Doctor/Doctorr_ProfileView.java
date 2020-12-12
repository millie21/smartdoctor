package com.example.smartdoctor.Doctor;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.smartdoctor.Objects.Doctors;
import com.example.smartdoctor.R;
import com.example.smartdoctor.SharedPreferences.DoctorSharedPrefManager;
import com.example.smartdoctor.SharedPreferences.SharedPrefManager;

public class Doctorr_ProfileView extends AppCompatActivity {

    TextView textViewUsername, textViewEmail, textViewSpec, textViewdocFees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_doctorr__profile_view);

        this.getSupportActionBar().hide();

        textViewUsername = (TextView) findViewById(R.id.textViewUsername1);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail1);
        textViewSpec = (TextView) findViewById(R.id.textspecialization1);
        textViewdocFees = (TextView) findViewById(R.id.textViewdocFees1);

        //getting the current user
        Doctors doctors = DoctorSharedPrefManager.getInstance(this).getDoctors();

        //setting the values to the textviews
        //textViewId.setText(String.valueOf(user.getPid()));
        textViewUsername.setText(doctors.getUsername());
        textViewEmail.setText(doctors.getEmail());
        textViewSpec.setText(doctors.getSpec());
        textViewdocFees.setText(doctors.getDocFees());

        //when the user presses logout button
        //calling the logout method
        findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                DoctorSharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });
    }
}
