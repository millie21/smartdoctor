package com.example.smartdoctor.Objects;

public class Admins {

        //  private int pid;
        private String email, username;

        public Admins(String username, String email) {
            // this.pid = pid;
            this.email = email;
            this.username = username;

        }

        public String getUsername() {
            return username;
        }

        public String getEmail() {
            return email;
        }

    }
