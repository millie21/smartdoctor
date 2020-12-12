package com.example.smartdoctor.Nurses;

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
import com.example.smartdoctor.Doctor.ProfileView;
import com.example.smartdoctor.R;
import com.example.smartdoctor.Doctor.Registration;

public class NurseHomePage extends AppCompatActivity {

    CardView cardViewProfile;
    CardView cardViewPatients;
    CardView cardViewNurses;
    CardView cardViewAppointments;
    CardView cardViewAboutus;
    CardView cardViewHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_nurse_home_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cardViewProfile = (CardView) findViewById(R.id.card_view);
        cardViewPatients = (CardView) findViewById(R.id.card_view2);
        cardViewNurses = (CardView) findViewById(R.id.card_view3);
        cardViewAppointments = (CardView) findViewById(R.id.card_view4);
        cardViewAboutus = (CardView) findViewById(R.id.card_view5);
        cardViewHelp = (CardView) findViewById(R.id.card_view6);

        cardViewProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), ProfileView.class);
                view.getContext().startActivity(intent);

            }
        });

        cardViewPatients.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), Registration.class);
                view.getContext().startActivity(intent);

            }
        });

        cardViewNurses.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), Doctor_ViewNurses.class);
                view.getContext().startActivity(intent);

            }
        });

        cardViewAppointments.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), DoctorViewAppointments.class);
                view.getContext().startActivity(intent);

            }
        });

        cardViewAboutus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), Doctor_ViewAboutUs.class);
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

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


    }

}
