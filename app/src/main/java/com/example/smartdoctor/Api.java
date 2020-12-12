package com.example.smartdoctor;

/**
 * Created by Belal on 9/9/2017.
 */

public class Api {

    private static final String ROOT_IP = "http://192.168.56.1";

   // private static final String ROOT_IP = "https://radsproj.000webhostapp.com";

    private static final String ROOT_URL = ROOT_IP + "/HeroApi/v1/Api.php?apicall=";

    private static final String ROOT_URL2 = ROOT_IP + "/HeroApi/v1/Issue.php?apicall=";

    private static final String ROOT_URL3 = ROOT_IP + "/HeroApi/v1/Nurses.php?apicall=";

    private static final String ROOT_URL4 = ROOT_IP + "/HeroApi/v1/Appointments.php?apicall=";

    private static final String ROOT_URL5 = ROOT_IP + "/HeroApi/v1/Contacts.php?apicall=";

    private static final String ROOT_URL6 = ROOT_IP + "/HeroApi/v1/Prescriptions.php?apicall=";

    private static final String ROOT_URL7 = ROOT_IP + "/HeroApi/v1/HealthData.php?apicall=";

    private static final String ROOT_URL_MAIN = ROOT_IP + "/HeroApi/v1/Api2.php?apicall=";

    public static final String ROOT_URL_SIGN_UP = ROOT_URL_MAIN + "signup";

    public static final String ROOT_URL_LOGIN = ROOT_URL_MAIN + "login";

    public static final String ROOT_URL_LOGIN_DOCTOR = ROOT_URL_MAIN + "doctorlogin";

    public static final String ROOT_URL_LOGIN_ADMIN = ROOT_URL_MAIN + "adminlogin";

            //"http://192.168.117.1/HeroApi/v1/Nurses.php?apicall=";

    public static final String URL_CREATE_HERO = ROOT_URL + "createhero";
    public static final String URL_READ_HEROES = ROOT_URL + "getheroes";
    public static final String URL_UPDATE_HERO = ROOT_URL + "updatehero";
    public static final String URL_DELETE_HERO = ROOT_URL + "deletehero&id=";

    public static final String URL_CREATE_ISSUE = ROOT_URL5 + "createissue";
    public static final String URL_READ_ISSUE = ROOT_URL5 + "getcontact";

    public static final String URL_CREATE_NURSE = ROOT_URL3 + "createnurse";
    public static final String URL_READ_NURSE = ROOT_URL3 + "getnurses";
    public static final String URL_UPDATE_NURSE = ROOT_URL3 + "updatenurse";
    public static final String URL_DELETE_NURSE = ROOT_URL3 + "deletenurse&id=";

    public static final String URL_CREATE_APPOINTMENTS = ROOT_URL4 + "createappointment";
  //  public static final String URL_READ_APPOINTMENTS = ROOT_URL4 + "getappointment";
//temporary
    public static final String URL_READ_APPOINTMENTS = ROOT_URL4 + "getactiveappointment";
    public static final String URL_READ_APPOINTMENTS_PATIENTS = ROOT_URL4 + "getactivepatientsappointment";
//    public static final String URL_UPDATE_APPOINTMENTS = ROOT_URL4 + "updatenurse";
//    public static final String URL_DELETE_APPOINTMENTS = ROOT_URL4 + "deletenurse&id=";

    public static final String URL_CREATE_CONTACTS = ROOT_URL5 + "createcontact";
    public static final String URL_READ_CONTACTS = ROOT_URL5 + "getcontacts";
//    public static final String URL_UPDATE_CONTACTS = ROOT_URL5 + "updatenurse";
//    public static final String URL_DELETE_CONTACTS = ROOT_URL5 + "deletenurse&id=";

    public static final String URL_CREATE_PRESCRIPTIONS = ROOT_URL6 + "createprescription";
    public static final String URL_READ_PRESCRIPTIONS = ROOT_URL6 + "getprescription";
//    public static final String URL_UPDATE_PRESCRIPTIONS = ROOT_URL6 + "updatenurse";
//    public static final String URL_DELETE_PRESCRIPTIONS = ROOT_URL6 + "deletenurse&id=";

    public static final String URL_CREATE_HEALTHDATA = ROOT_URL7 + "createdata";
    public static final String URL_READ_HEALTHDATA = ROOT_URL7 + "getblockchaindata";
  //  public static final String URL_READ_HEALTHDATA = ROOT_URL7 + "getdata";
//    public static final String URL_UPDATE_PRESCRIPTIONS = ROOT_URL6 + "updatenurse";
//    public static final String URL_DELETE_PRESCRIPTIONS = ROOT_URL6 + "deletenurse&id=";

    public static final String URL_DOC_CANCEL_APPOINTMENTS = ROOT_URL4 + "cancelappointment&id=";
    public static final String URL_PATIENT_CANCEL_APPOINTMENTS = ROOT_URL4 + "patientcancelappointment&id=";

    private static final String ROOT_URL8 = ROOT_IP + "/HeroApi/v1/Doctors.php?apicall=";

    public static final String URL_CREATE_DOC = ROOT_URL8 + "createdoctors";
    public static final String URL_READ_DOC = ROOT_URL8 + "getdoctors";
    public static final String URL_DELETE_DOC = ROOT_URL8 + "deletedoctors&email=";

//    public static final String URL_UPDATE_NURSE = ROOT_URL3 + "updatenurse";
//    public static final String URL_DELETE_NURSE = ROOT_URL3 + "deletenurse&id=";

}
