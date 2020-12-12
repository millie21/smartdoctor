package com.example.smartdoctor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.smartdoctor.Objects.Appointments;

import java.util.List;

//import afyapepe.mobile.activity.Stock;

/**
 * Created by Millie Agallo on 11/6/2017.
 */

public class AppointmentsAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Appointments> appointmentsList;

    public AppointmentsAdapter(Activity activity, List<Appointments> appointmentsList){
        this.activity = activity;
        this.appointmentsList= appointmentsList;
    }

    @Override
    public int getCount() {
        return appointmentsList.size();
    }

    @Override
    public Object getItem(int location) {
        return appointmentsList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {


        if (inflater == null)
            inflater = (LayoutInflater)activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if( view == null)
            view = inflater.inflate(R.layout.list_appointments,null);

        TextView Doctor = (TextView)view.findViewById(R.id.tvid);
        TextView DocFees = (TextView)view.findViewById(R.id.tvname2);
        TextView Appdate = (TextView)view.findViewById(R.id.tvid5);

        Appointments appointments = appointmentsList.get(position);

        Doctor.setText(appointments.getDoctor());
        DocFees.setText(appointments.getDocFees());
        Appdate.setText(appointments.getAppdate());

        return view;
//String count = ""+lstView.getAdapter().getCount(); textView.setText(count);
        //or
        //String.valueOf(lstView.getAdapter().getCount());
    }
}