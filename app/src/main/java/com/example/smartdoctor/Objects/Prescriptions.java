package com.example.smartdoctor.Objects;

public class Prescriptions {


     //private int appointmentid;
    private String  doctor, appdate, prescription;


    public Prescriptions( String  doctor, String appdate, String prescription){ // int appointmentid,) {

        this.doctor = doctor;
        this.appdate = appdate;
        this.prescription = prescription;


    }

    public String getDoctor() {
        return doctor;
    }

    public String getAppdate() {
        return appdate;
    }

    public String getPrescription(){
        return prescription;
    }
}
