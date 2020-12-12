package com.example.smartdoctor.Objects;

public class HealthData {

//        private int pid;
        private String Temperature, Heartrate, Respiration, Spo2;

        public HealthData(String Temperature, String Heartrate, String Respiration,String Spo2) {

            this.Temperature = Temperature;
            this.Heartrate = Heartrate;
            this.Respiration = Respiration;
            this.Spo2 = Spo2;

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

    }
