package com.example.smartdoctor.Patient;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.smartdoctor.R;

public class PatientViewAppointments extends AppCompatActivity {

    CardView cardViewCreateAppointments;
    CardView cardViewViewPast;
    CardView cardViewViewUpcoming;
    CardView cardViewAllAppointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_patient_view_appointments);

        this.getSupportActionBar().hide();

        cardViewCreateAppointments = (CardView) findViewById(R.id.card_view);
        cardViewViewUpcoming = (CardView) findViewById(R.id.card_view2);
        cardViewAllAppointments = (CardView) findViewById(R.id.card_viewhistory);

        cardViewCreateAppointments.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), CreateAppointment.class);
                view.getContext().startActivity(intent);

            }
        });

        cardViewViewUpcoming.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), ViewPrescriptions.class);
                view.getContext().startActivity(intent);

            }
        });
        cardViewAllAppointments.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), ViewAllAppointments.class);
                view.getContext().startActivity(intent);

            }
        });
    }

}
