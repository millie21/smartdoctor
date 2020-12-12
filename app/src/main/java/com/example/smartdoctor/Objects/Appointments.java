package com.example.smartdoctor.Objects;

public class Appointments {


        private int id;
        private String  doctor, docFees, appdate, apptime, userStatus, doctorStatus;


        public Appointments(int id, String  doctor, String docFees, String appdate, String apptime, String userStatus, String doctorStatus){ // int appointmentid,) {
            this.id = id;
           // this.specialization = specialization;
            this.doctor = doctor;
            this.docFees = docFees;
            this.appdate = appdate;
            this.apptime = apptime;
            this.userStatus = userStatus;
            this.doctorStatus = doctorStatus;



        }

        public int getId() {
            return id;
        }

        public String getDoctor() {
            return doctor;
        }

        public String getDocFees() {
            return docFees;
        }

        public String getAppdate() {
            return appdate;
        }

        public String getApptime() {
            return apptime;
        }


        public String getUserStatus(){
            return userStatus;
        }
        public String getDoctorStatus(){
            return doctorStatus;
        }
    }
