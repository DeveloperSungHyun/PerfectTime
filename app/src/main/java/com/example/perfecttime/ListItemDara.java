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

    public ListItemDara(String name, String memo, int time_h, int time_m, boolean amPm, boolean important, String day, int viewtype) {
        Name = name;
        Memo = memo;
        Time_h = time_h;
        Time_m = time_m;
        AmPm = amPm;
        this.important = important;
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
