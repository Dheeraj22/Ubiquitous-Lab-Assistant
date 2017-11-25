package com.example.android.ubiquitouslabassistantsystem;

/**
 * Created by Dheeraj_Kamath on 11/22/2017.
 */

public class Lights {
    String name;
    String deviceStatus;
    int flatIcon;

    public Lights(String name, String deviceStatus, int flatIcon){
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
