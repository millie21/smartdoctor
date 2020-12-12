package com.example.smartdoctor.Patient;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.smartdoctor.R;

public class ViewSingleAppointment extends AppCompatActivity {

    Button btnsearch,btnrefresh,btnhome;
    TextView tvltname,tvbtname,tvltid,tvbtid;
    EditText etlocation;
    String ltname,btname,ltid,btid;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_view_single_appointment);

        builder = new AlertDialog.Builder(ViewSingleAppointment.this);

//        btnsearch = (Button)findViewById(R.id.btnsearch);
//        btnrefresh = (Button)findViewById(R.id.btnrefresh);
//        btnhome = (Button)findViewById(R.id.btnhome);
//
//        tvltname = (TextView)findViewById(R.id.tvltname);
//        tvbtname = (TextView)findViewById(R.id.tvbtname);
//        tvltid = (TextView)findViewById(R.id.tvltid);
//        tvbtid = (TextView)findViewById(R.id.tvbtid);
//
//        etlocation = (EditText) findViewById(R.id.etlocation);
//
//        Bundle bundle = getIntent().getExtras();
//        ltname = bundle.getString("ltname");
//        btname = bundle.getString("btname");
//        ltid = bundle.getString("ltid");
//        btid = bundle.getString("btid");
//
//        tvltname.setText(ltname);
//        if(ltname.equals("") && btname.equals("")){
//            tvbtname.setText("");
//        }else {
//            tvbtname.setText(" > " + btname);
//        }
//        tvltid.setText(ltid);
//        tvbtid.setText(btid);

//        Search();
//        Refresh();
//        Home();

    }


//    public void Search(){
//        btnsearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(etlocation.equals("")){
//                    builder.setTitle("Error");
//                    builder.setMessage("Please fill in the location first !!!");
//                    builder.setCancelable(false)
//                            .setPositiveButton("OK",new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog,int id) {
//                                    // if this button is clicked, close
//                                    // current activity
//                                    // LocationCriteria.this.finish();
//                                }
//                            });
//                    builder.create();
//                    builder.show();
//                }else {
//                    String location = etlocation.getText().toString();
//
//                    Intent locateintent = new Intent(LocationCriteria.this, SearchResults.class);
//
//                    locateintent.putExtra("ltid", ltid);
//                    locateintent.putExtra("btid", btid);
//                    locateintent.putExtra("ltname", ltname);
//                    locateintent.putExtra("btname", btname);
//                    locateintent.putExtra("location", location);
//                    locateintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                    startActivity(locateintent);
//                }
//            }
//        });
//
//    }

//    public void Refresh(){
//        btnrefresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent refreshintent = new Intent(LocationCriteria.this, LivestockCriteria.class);
//                startActivity(refreshintent);
//            }
//        });
//    }


//    public void Home(){
//        btnhome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent homeintent = new Intent(LocationCriteria.this, MainActivity.class);
//                startActivity(homeintent);
//            }
//        });
//    }

}
