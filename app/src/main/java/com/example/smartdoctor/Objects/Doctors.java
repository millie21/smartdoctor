package com.example.smartdoctor.Objects;

public class Doctors {

  //  private int pid;
    private String username,email,  spec, docFees;

    public Doctors(String username, String email, String spec, String docFees) {
       // this.pid = pid;
        this.username = username;
        this.email = email;
        this.spec = spec;
        this.docFees = docFees;

    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getSpec() {
        return spec;
    }

    public String getDocFees() {
        return docFees;
    }



}
