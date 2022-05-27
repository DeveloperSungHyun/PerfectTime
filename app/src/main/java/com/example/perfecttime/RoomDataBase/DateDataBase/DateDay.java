package com.example.perfecttime.RoomDataBase.DateDataBase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class DateDay {
    @PrimaryKey(autoGenerate = true)
    private int id = 0;

    private int Date_y, Date_m, Date_d;
    private String Name;
    private String Memo;
    private int Time_h, Time_m;
    private boolean Important;

    private boolean Sound;
    private boolean Vibration;
    private boolean Notification;

    private int AutoOff_Time;
    private boolean Warning;
    private boolean Holiday;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDate_y() {
        return Date_y;
    }

    public void setDate_y(int date_y) {
        Date_y = date_y;
    }

    public int getDate_m() {
        return Date_m;
    }

    public void setDate_m(int date_m) {
        Date_m = date_m;
    }

    public int getDate_d() {
        return Date_d;
    }

    public void setDate_d(int date_d) {
        Date_d = date_d;
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

    public boolean isImportant() {
        return Important;
    }

    public void setImportant(boolean important) {
        Important = important;
    }

    public boolean isSound() {
        return Sound;
    }

    public void setSound(boolean sound) {
        Sound = sound;
    }

    public boolean isVibration() {
        return Vibration;
    }

    public void setVibration(boolean vibration) {
        Vibration = vibration;
    }

    public boolean isNotification() {
        return Notification;
    }

    public void setNotification(boolean notification) {
        Notification = notification;
    }

    public int getAutoOff_Time() {
        return AutoOff_Time;
    }

    public void setAutoOff_Time(int autoOff_Time) {
        AutoOff_Time = autoOff_Time;
    }

    public boolean isWarning() {
        return Warning;
    }

    public void setWarning(boolean warning) {
        Warning = warning;
    }

    public boolean isHoliday() {
        return Holiday;
    }

    public void setHoliday(boolean holiday) {
        Holiday = holiday;
    }
}
