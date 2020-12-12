package com.example.smartdoctor.Patient;

public class Contact {

   // private int pid;
    private String email, name, message, personal_contact;

    public Contact(String name,  String email,String personal_contact, String message) {

        this.email = email;
        this.message = message;
        this.name = name;
        this.personal_contact = personal_contact;

    }

    public String getName() {
        return name;
    }

    public String getPersonal_contact() {
        return personal_contact;
    }

    public String getEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }

}
