package com.example.android.ubiquitouslabassistantsystem;

/**
 * Created by Dheeraj_Kamath on 11/10/2017.
 */

public class Appliance {
    String name;
    int noOfDevices;
    int flatIcon;

    public Appliance(String name, int noOfDevices, int flatIcon){
        this.name = name;
        this.noOfDevices = noOfDevices;
        this.flatIcon = flatIcon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNoOfDevices() {
        return noOfDevices;
    }

    public void setNoOfDevices(int noOfDevices) {
        this.noOfDevices = noOfDevices;
    }

    public int getFlatIcon() {
        return flatIcon;
    }

    public void setFlatIcon(int flatIcon) {
        this.flatIcon = flatIcon;
    }
}
