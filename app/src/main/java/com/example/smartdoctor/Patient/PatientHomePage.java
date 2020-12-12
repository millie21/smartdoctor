package com.example.smartdoctor.Patient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.smartdoctor.Doctor.DoctorViewAppointments;
import com.example.smartdoctor.Doctor.Doctor_ViewAboutUs;
import com.example.smartdoctor.Doctor.Doctor_ViewHelp;
import com.example.smartdoctor.Doctor.Doctor_ViewNurses;
import com.example.smartdoctor.Doctor.ProfileView;
import com.example.smartdoctor.MainLoginActivity;
import com.example.smartdoctor.R;
import com.example.smartdoctor.Doctor.Registration;
import com.example.smartdoctor.SessionManager;
import com.example.smartdoctor.SharedPreferences.SharedPrefManager;
import com.example.smartdoctor.Objects.User;

public class PatientHomePage extends AppCompatActivity {

    TextView textViewFirstname, textViewEmail, textViewLastname;

    CardView cardViewProfile;
    CardView cardViewPatients;
    CardView cardViewNurses;
    CardView cardViewAppointments;
    CardView cardViewAboutus;
    CardView cardViewHelp;
    CardView cardViewStats,cardViewPresc ;

    //session test
   // private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_patient_home_page);

        this.getSupportActionBar().hide();

        cardViewProfile = (CardView) findViewById(R.id.card_view);
        cardViewAppointments = (CardView) findViewById(R.id.card_view4);
        cardViewAboutus = (CardView) findViewById(R.id.card_view5);
        cardViewHelp = (CardView) findViewById(R.id.card_view6);
        cardViewStats = (CardView) findViewById(R.id.card_view8);
        cardViewPresc = (CardView) findViewById(R.id.card_view9);

        //session

//        session = new SessionManager(getApplicationContext());
//
//        if (!session.isLoggedIn()) {
//            logoutUser();
//        }
        cardViewPresc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), ViewPrescriptions.class);
                view.getContext().startActivity(intent);

            }
        });

        cardViewProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), ProfileView.class);
                view.getContext().startActivity(intent);

            }
        });

        cardViewAppointments.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), PatientViewAppointments.class);
                view.getContext().startActivity(intent);

            }
        });

        cardViewAboutus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), PatientViewAboutUs.class);
                view.getContext().startActivity(intent);

            }
        });

        cardViewHelp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), PatientViewHelp.class);
                view.getContext().startActivity(intent);

            }
        });

        cardViewStats.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), PatientHealthStats.class);
               // Intent intent = new Intent(view.getContext(), ViewPastAppointments.class);
                view.getContext().startActivity(intent);

            }
        });

        findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });

        textViewFirstname = (TextView) findViewById(R.id.textViewFirstname);
        textViewLastname = (TextView) findViewById(R.id.textViewLastname);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);

        //getting the current user
        User user = SharedPrefManager.getInstance(this).getUser();

        //setting the values to the textviews
        textViewFirstname.setText(user.getFname());
        textViewLastname.setText(user.getLname());
        textViewEmail.setText(user.getEmail());

    }
//    private void logoutUser() {
//        session.setLogin(false);
//
//        //db.deleteUsers();
//
//        // Launching the login activity
//        Intent intent = new Intent(PatientHomePage.this, MainLoginActivity.class);
//        startActivity(intent);
//        finish();
//    }

}


