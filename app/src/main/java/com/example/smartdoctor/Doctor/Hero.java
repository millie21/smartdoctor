package com.example.smartdoctor.Doctor;

public class Hero {
    private int id;
    private String firstname, lastname, email, phonenumber, title, detail;
//    private int rating;
//    private String teamaffiliation;

    public Hero(int id, String firstname, String lastname, String email, String phonenumber) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phonenumber = phonenumber;


    }

    public int getId() {
        return id;
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

}
