package com.example.perfecttime;

public class ListItemDara {
    private String Name;
    private String Memo;
    private int Time_h;
    private int Time_m;
    private boolean AmPm;
    private boolean important;

    public ListItemDara(String name, String memo, int time_h, int time_m, boolean amPm, boolean important) {
        Name = name;
        Memo = memo;
        Time_h = time_h;
        Time_m = time_m;
        AmPm = amPm;
        this.important = important;
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
