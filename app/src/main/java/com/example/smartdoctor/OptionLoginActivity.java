package com.example.smartdoctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.smartdoctor.Admin.AdminLoginActivity;
import com.example.smartdoctor.Doctor.DoctorLoginActivity;
import com.example.smartdoctor.Nurses.NurseLoginActivity;
import com.example.smartdoctor.Patient.UserLoginActivity;

public class OptionLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_option_login);

        this.getSupportActionBar().hide();

        findViewById(R.id.topatientlogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(OptionLoginActivity.this, UserLoginActivity.class));
            }
        });

        findViewById(R.id.todoctorlogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(OptionLoginActivity.this, DoctorLoginActivity.class));
            }
        });

//        findViewById(R.id.tonurselogin).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                finish();
//                startActivity(new Intent(OptionLoginActivity.this, NurseLoginActivity.class));
//            }
//        });

        findViewById(R.id.toadminlogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
                startActivity(new Intent(OptionLoginActivity.this, AdminLoginActivity.class));
            }
        });
    }

}
