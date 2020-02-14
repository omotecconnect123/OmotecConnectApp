package com.example.Omotecconnect;

public class AppConfig {

    static String ip = "https://omotecconnect.000webhostapp.com/omotecapp/";

    // Server user login url
    public static String URL_LOGIN = ip + "StudentLogin.php";
    public static String URL_Trainer_LOGIN = ip + "Trainerlogin.php";


    // Server user register url
    public static String URL_REGISTER = ip + "register.php";

    public static String URL_HOME_CR = ip + "course_home.php";

    public static String URL_HOME_CR_DET = ip + "course_details.php";

    public static String URL_HOME_SPEC_CR_DET = ip + "special_cr_det.php";

    public static String URL_HOME_IMG = ip + "Course_icons/";


    public static String URL_FORGOT_PASS = ip + "ForgotPassword.php";

}
