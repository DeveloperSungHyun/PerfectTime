package com.example.perfecttime;

public class ListItemDara {
    private String Name;
    private String Memo;
    private String Day;
    private int Time_h;
    private int Time_m;
    private boolean AmPm;
    private boolean important;

    private int viewtype;

    boolean sound, vibration, popup;

    public boolean isSound() {
        return sound;
    }

    public void setSound(boolean sound) {
        this.sound = sound;
    }

    public boolean isVibration() {
        return vibration;
    }

    public void setVibration(boolean vibration) {
        this.vibration = vibration;
    }

    public boolean isPopup() {
        return popup;
    }

    public void setPopup(boolean popup) {
        this.popup = popup;
    }

    public ListItemDara(String name, String memo, int time_h, int time_m, boolean amPm, boolean important, boolean sound, boolean vibration, boolean popup, String day, int viewtype) {
        Name = name;
        Memo = memo;
        Time_h = time_h;
        Time_m = time_m;
        AmPm = amPm;
        this.important = important;

        this.sound = sound;
        this.vibration = vibration;
        this.popup = popup;

        Day = day;

        this.viewtype = viewtype;

    }

    public int getViewtype() {
        return viewtype;
    }

    public void setViewtype(int viewtype) {
        this.viewtype = viewtype;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMemo() {
        return Memo;
    }

    public void setMemo(String memo) {
        Memo = memo;
    }

    public int getTime_h() {
        return Time_h;
    }

    public void setTime_h(int time_h) {
        Time_h = time_h;
    }

    public int getTime_m() {
        return Time_m;
    }

    public void setTime_m(int time_m) {
        Time_m = time_m;
    }

    public boolean isAmPm() {
        return AmPm;
    }

    public void setAmPm(boolean amPm) {
        AmPm = amPm;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }
}
