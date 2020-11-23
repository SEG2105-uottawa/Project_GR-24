package com.example.servicenovigrad.data;

public class WorkingHours {
    private static final String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", ""};

    private String day, openTime, closeTime;

    public WorkingHours(int dayIndex, String open, String close) {
        this.day = days[dayIndex];
        openTime = open;
        closeTime = close;
    }

    public WorkingHours(int dayIndex) {
        this(dayIndex, "", "");
    }

    public WorkingHours() {
        this(7, "", "");
    }

    public String getDay() {
        return day;
    }

    public void setDay(int dayIndex) {
        day = days[dayIndex];
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String open) {
        openTime = open;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String close) {
        closeTime = close;
    }
}
