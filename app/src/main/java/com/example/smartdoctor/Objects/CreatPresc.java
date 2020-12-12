package com.example.smartdoctor.Objects;

public class CreatPresc {


    //private int appointmentid;
    private String  disease, status, prescription;


    public CreatPresc( String disease, String status, String prescription){ // int appointmentid,) {

        this.disease = disease;
        this.status = status;
        this.prescription = prescription;


    }

    public String getDisease() {
        return disease;
    }

    public String getStatus() {
        return status;
    }

    public String getPrescription(){
        return prescription;
    }
}
