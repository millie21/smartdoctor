package com.example.smartdoctor.Objects;

public class User {

    private int pid;
    private String email, gender, fname, lname, contact;

    public User(int pid, String fname, String lname, String email,String contact, String gender) {
        this.pid = pid;
        this.email = email;
        this.gender = gender;
        this.fname = fname;
        this.lname = lname;
        this.contact = contact;

    }

    public int getPid() {
        return pid;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getContact() {
        return contact;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

}
