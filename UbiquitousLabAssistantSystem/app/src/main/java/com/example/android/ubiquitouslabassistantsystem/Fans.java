package com.example.android.ubiquitouslabassistantsystem;

/**
 * Created by Dheeraj_Kamath on 11/25/2017.
 */

public class Fans {
    String name;
    String deviceStatus;
    int flatIcon;

    public Fans(String name, String deviceStatus, int flatIcon){
        this.name = name;
        this.deviceStatus = deviceStatus;
        this.flatIcon = flatIcon;
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFlatIcon() {
        return flatIcon;
    }

    public void setFlatIcon(int flatIcon) {
        this.flatIcon = flatIcon;
    }
}
