package com.example.android.ubiquitouslabassistantsystem;

/**
 * Created by Dheeraj_Kamath on 11/27/2017.
 */

public class Curtains {
    private String name;
    private String deviceStatus;
    private int flatIcon;

    public Curtains(String name, String deviceStatus, int flatIcon){
        this.name = name;
        this.deviceStatus = deviceStatus;
        this.flatIcon = flatIcon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public int getFlatIcon() {
        return flatIcon;
    }

    public void setFlatIcon(int flatIcon) {
        this.flatIcon = flatIcon;
    }
}
