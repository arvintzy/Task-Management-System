package com.ashish;

import java.io.Serializable;

public class UserInfo implements Serializable
{
    String userName="",userEmail="",mobileNo="";
    int temp_val;
    public UserInfo (){

    }
    public UserInfo(String name,String email,String mobileNo){
        userName=name;
        userEmail=email;
        this.mobileNo=mobileNo;


    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}
