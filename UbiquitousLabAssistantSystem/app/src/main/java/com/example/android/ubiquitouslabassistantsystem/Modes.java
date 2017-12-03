package com.example.android.ubiquitouslabassistantsystem;

/**
 * Created by Dheeraj_Kamath on 11/29/2017.
 */

public class Modes {
    private String name;
    private int flatIcon;

    public Modes(String name, int flatIcon){
        this.name = name;
        this.flatIcon = flatIcon;
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
