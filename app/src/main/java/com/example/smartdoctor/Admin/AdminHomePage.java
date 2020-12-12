package com.example.smartdoctor.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.smartdoctor.Doctor.DoctorViewAppointments;
import com.example.smartdoctor.Doctor.Doctor_ViewAboutUs;
import com.example.smartdoctor.Doctor.Doctor_ViewHelp;
import com.example.smartdoctor.Doctor.Doctor_ViewNurses;
import com.example.smartdoctor.Doctor.Registration;
import com.example.smartdoctor.R;

public class AdminHomePage extends AppCompatActivity {
    CardView ActiveDocs, RegDocs, RegPatients, SchedAppointments, Prescs, Queries, Prof;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_admin_home_page);

        this.getSupportActionBar().hide();

        ActiveDocs = (CardView) findViewById(R.id.card_view1);
        RegDocs = (CardView) findViewById(R.id.card_view2);
        RegPatients = (CardView) findViewById(R.id.card_view3);
        SchedAppointments = (CardView) findViewById(R.id.card_view4);
        Prescs = (CardView) findViewById(R.id.card_view5);
        Queries = (CardView) findViewById(R.id.card_view6);
        Prof = (CardView) findViewById(R.id.card_view7);


        ActiveDocs.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), Admin_ViewDoctors.class);
                view.getContext().startActivity(intent);

            }
        });


        RegDocs.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), Admin_DoctorReg.class);
                view.getContext().startActivity(intent);

            }
        });

        RegPatients.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), Admin_ViewPatients.class);
                view.getContext().startActivity(intent);

            }
        });
        SchedAppointments.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), Admin_ViewAppointments.class);
                view.getContext().startActivity(intent);

            }
        });

        Prescs.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), Admin_ViewPrescriptions.class);
                view.getContext().startActivity(intent);

            }
        });

        Queries.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), Admin_ViewQueries.class);
                view.getContext().startActivity(intent);

            }
        });

        Prof.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), Admin_ProfileView.class);
                view.getContext().startActivity(intent);

            }
        });

    }

}
