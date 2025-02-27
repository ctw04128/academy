package com.ctw.workstation.item;

public enum DataType {
    RACK ("Rack"),
    RACK_ASSET ("Rack Asset"),
    TEAM ("Team"),
    TEAM_MEMBER ("Team member"),
    BOOKING ("Booking");
    private final String name;
    DataType(String name) {
        this.name = name;
    }
    public String toString() {
        return name;
    }
}