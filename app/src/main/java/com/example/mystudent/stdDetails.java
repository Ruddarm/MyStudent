package com.example.mystudent;

import java.io.Serializable;

public class stdDetails implements Serializable {
    private  String username;
    private String pswd;
    private String name;
    private String stdclass;
    private String stdbatch;
    private String dob;
    private String bloodgrup;
    private String parentName,ParentRelation,ParentNumber,ParentEmail;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentRelation() {
        return ParentRelation;
    }

    public void setParentRelation(String parentRelation) {
        ParentRelation = parentRelation;
    }

    public String getParentNumber() {
        return ParentNumber;
    }

    public void setParentNumber(String parentNumber) {
        ParentNumber = parentNumber;
    }

    public String getParentEmail() {
        return ParentEmail;
    }

    public void setParentEmail(String parentEmail) {
        ParentEmail = parentEmail;
    }

    public String getBloodgrup() {
        return bloodgrup;
    }

    public void setBloodgrup(String bloodgrup) {
        this.bloodgrup = bloodgrup;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    private String gender;
    stdDetails(){}

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    stdDetails(String Username , String pswd){
        this.username=Username;
        this.pswd=pswd;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public void setStdbatch(String stdbatch) {
        this.stdbatch = stdbatch;
    }

    public void setStdclass(String stdclass) {
        this.stdclass = stdclass;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public String getPswd() {
        return pswd;
    }

    public String getStdbatch() {
        return stdbatch;
    }

    public String getStdclass() {
        return stdclass;
    }

    public String getUsername() {
        return username;
    }
}
