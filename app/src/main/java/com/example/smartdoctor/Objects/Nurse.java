package com.example.smartdoctor.Objects;

public class Nurse {
    private int nurseid;
    private String firstname, lastname, email, phonenumber, team;


    public Nurse(int nurseid, String firstname, String lastname, String email, String phonenumber, String team) {
        this.nurseid = nurseid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phonenumber = phonenumber;
        this.team = team;


    }

    public int getNurseId() {
        return nurseid;
    }

    public String getFirstName() {
        return firstname;
    }

    public String getLastName() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }
    public String getTeam() {
        return team;
    }
}
