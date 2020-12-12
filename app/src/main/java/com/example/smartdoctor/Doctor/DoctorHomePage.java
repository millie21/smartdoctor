package com.example.smartdoctor.Doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.smartdoctor.Objects.Doctors;
import com.example.smartdoctor.R;
import com.example.smartdoctor.SharedPreferences.DoctorSharedPrefManager;
import com.example.smartdoctor.SharedPreferences.SharedPrefManager;

public class DoctorHomePage extends AppCompatActivity {
    CardView PatientAppointments, PatientPrescriptions, PatientHealthData;
    CardView DoctorProfile, cardViewHelp ;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_doctor_home_page);

        this.getSupportActionBar().hide();

        PatientAppointments = (CardView) findViewById(R.id.card_view_1);
        PatientPrescriptions = (CardView) findViewById(R.id.card_view_2);
        PatientHealthData = (CardView) findViewById(R.id.card_view_3);
        DoctorProfile = (CardView) findViewById(R.id.card_view_4);
        cardViewHelp = (CardView) findViewById(R.id.card_view_6);

        DoctorProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(),Doctorr_ProfileView.class);
                view.getContext().startActivity(intent);

            }
        });

        PatientAppointments.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), DoctorViewAppointments.class);
                view.getContext().startActivity(intent);

            }
        });

        PatientPrescriptions.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), PatientPrescriptions.class);
                view.getContext().startActivity(intent);

            }
        });

        PatientHealthData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), PatientHealthData.class);
                view.getContext().startActivity(intent);

            }
        });

        cardViewHelp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), Doctor_ViewHelp.class);
                view.getContext().startActivity(intent);

            }
        });

        findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                DoctorSharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });

        textView = (TextView) findViewById(R.id.textViewUsername);

        Doctors doctors = DoctorSharedPrefManager.getInstance(this).getDoctors();

        textView.setText(doctors.getEmail());

    }

}
