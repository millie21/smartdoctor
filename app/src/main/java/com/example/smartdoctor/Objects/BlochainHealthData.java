package com.example.smartdoctor.Objects;

public class BlochainHealthData {

           //        private int pid;
        private String Temperature, Heartrate, Respiration, Spo2, fname, PreviousHash;

        public BlochainHealthData(String fname, String PreviousHash,String Temperature, String Heartrate, String Respiration,String Spo2) {

            this.Temperature = Temperature;
            this.Heartrate = Heartrate;
            this.Respiration = Respiration;
            this.Spo2 = Spo2;
            this.fname = fname;
            this.PreviousHash = PreviousHash;

        }

        public String getTemperature() {
            return Temperature;
        }

        public String getHeartrate() {
            return Heartrate;
        }

        public String getRespiration() {
            return Respiration;
        }

        public String getSpo2() {
            return Spo2;
        }

    public String getFname() {
        return fname;
    }

    public String getPreviousHash() {
        return PreviousHash;
    }

    }

