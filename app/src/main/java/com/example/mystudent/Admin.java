package com.example.mystudent;

import java.io.Serializable;

public class Admin implements Serializable {
    private String Name,UserName,Pswd,Deesignation,DOB,Gender,BloodGrup,JoingYear;

    public String getJoingYear() {
        return JoingYear;
    }

    public void setJoingYear(String joingYear) {
        JoingYear = joingYear;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPswd() {
        return Pswd;
    }

    public void setPswd(String pswd) {
        Pswd = pswd;
    }

    public String getDeesignation() {
        return Deesignation;
    }

    public void setDeesignation(String deesignation) {
        Deesignation = deesignation;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getBloodGrup() {
        return BloodGrup;
    }

    public void setBloodGrup(String bloodGrup) {
        BloodGrup = bloodGrup;
    }
}

