package com.example.mystudent;

import java.io.Serializable;

public class busDetails implements Serializable {
    private String busNumber,DriverName,DriverNumber,DriverID;

    private  boolean isAssign,busStatus;
    public boolean isBusStatus() {
        return busStatus;
    }

    public void setBusStatus(boolean busStatus) {
        this.busStatus = busStatus;
    }

    public String getDriverID() {
        return DriverID;
    }

    public void setDriverID(String driverID) {
        DriverID = driverID;
    }


    public boolean isAssign() {
        return isAssign;
    }

    public void setAssign(boolean assign) {
        isAssign = assign;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getDriverName() {
        return DriverName;
    }

    public void setDriverName(String driverName) {
        DriverName = driverName;
    }

    public String getDriverNumber() {
        return DriverNumber;
    }

    public void setDriverNumber(String driverNumber) {
        DriverNumber = driverNumber;
    }
}

