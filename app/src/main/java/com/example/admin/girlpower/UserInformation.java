package com.example.admin.girlpower;
/*
this class stores our user information. and Passes this to the main2Activity.
Also will help store data in the Firebase.
 */



public class UserInformation {

    public String Name,Surname,Email, Phone,Emergency_1,Emergency_2,Emergency_3;


    //default constructor.
   public UserInformation(){
    }//end constructor 1

    // will be passed to the database.
    public UserInformation(String name, String surname,String email, String phone,String mobil1,String mobil2,String mobil3) {
        Name = name;
        Surname = surname;
        Email = email;
        Phone = phone;
        Emergency_1 = mobil1;
        Emergency_2 = mobil2;
        Emergency_3 = mobil3;
    }//end constructor 2


}//end UserInformation class
