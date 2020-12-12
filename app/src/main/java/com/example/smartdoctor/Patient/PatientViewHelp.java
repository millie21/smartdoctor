package com.example.smartdoctor.Patient;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import com.example.smartdoctor.R;

public class PatientViewHelp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_patient_view_help);

        this.getSupportActionBar().hide();

        WebView mywebview = (WebView) findViewById(R.id.webView);

        mywebview.loadUrl("http://192.168.58.1/frontendapp/about.php");



    }

}
