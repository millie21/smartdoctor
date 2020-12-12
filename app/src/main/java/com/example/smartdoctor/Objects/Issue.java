package com.example.smartdoctor.Objects;

public class Issue {
   // private int helpid;
    private String name, email, contact, message;
//    private int rating;
//    private String teamaffiliation;

    public Issue( String name, String email, String contact, String message) {

        this.name = name;
        this.email = email;
        this.contact = contact;
        this.message = message;

    }

    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getContact() {
        return contact;
    }
    public String getMessage() {
        return message;
    }
}
