package com.example.admin.girlpower;
/*
this class just extracts data from the database and
 */


public class ExtractData  {

    private String Emergency_1,Emergency_2,Emergency_3;

    //these will be the emergency numbers
  //  private String number_1,number_2,number_3;

    public ExtractData() {


    }// end constructor

    public String getEmergency_1() {
        return Emergency_1;
    }

    public void setEmergency_1(String emergency_1) {
        Emergency_1 = emergency_1;
    }

    public String getEmergency_2() {
        return Emergency_2;
    }

    public void setEmergency_2(String emergency_2) {
        Emergency_2 = emergency_2;
    }

    public String getEmergency_3() {
        return Emergency_3;
    }

    public void setEmergency_3(String emergency_3) {
        Emergency_3 = emergency_3;
    }
}//end ExtractData
