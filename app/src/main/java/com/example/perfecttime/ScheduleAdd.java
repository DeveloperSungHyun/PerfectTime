package com.example.perfecttime;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.room.Room;

import com.example.perfecttime.RoomDataBase.AllDay_DataBase.AllDay;
import com.example.perfecttime.RoomDataBase.AllDay_DataBase.AllDayDao;
import com.example.perfecttime.RoomDataBase.AllDay_DataBase.AllDayDataBase;
import com.example.perfecttime.RoomDataBase.DateDataBase.DateDay;
import com.example.perfecttime.RoomDataBase.DateDataBase.DateDayDao;
import com.example.perfecttime.RoomDataBase.DateDataBase.DateDayDataBase;
import com.example.perfecttime.RoomDataBase.WeekDataBase.WeekDataBase;
import com.example.perfecttime.RoomDataBase.WeekDataBase.WeekDay;
import com.example.perfecttime.RoomDataBase.WeekDataBase.WeekDayDao;

import java.util.Calendar;

public class ScheduleAdd extends Activity {

    AllDay allDay;

    AllDayDao allDayDao;
    WeekDayDao weekDayDao;
    DateDayDao dateDayDao;

    Intent getIntentData;

    TextView TextView_Date;
    TextView TextView_Time_H, TextView_Time_M, TextView_AmPm;
    EditText EditText_Name, EditText_Memo;
    Switch Switch_sound, Switch_vibration, Switch_notification;
    Switch Switch_beforehand, Switch_HolidayOff, Switch_Important;

    int Time_h, Time_m;
    String Name, Memo;
    boolean sound, vibration, notification, beforehand, HolidayOff, Important;

    int Week;
    int INT_Year, INT_Month, INT_Date;

    int UpDateNum = 0;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_add);

        TextView_Time_H = findViewById(R.id.TextView_Time_H);
        TextView_Time_M = findViewById(R.id.TextView_Time_M);
        TextView_AmPm = findViewById(R.id.TextView_AmPm);
        TextView_Date = findViewById(R.id.TextView_Date);

        EditText_Name = findViewById(R.id.EditText_Name);
        EditText_Memo = findViewById(R.id.EditText_Memo);

        Switch_sound = findViewById(R.id.Switch_sound);
        Switch_vibration = findViewById(R.id.Switch_vibration);
        Switch_notification = findViewById(R.id.Switch_notification);
        Switch_beforehand = findViewById(R.id.Switch_beforehand);
        Switch_HolidayOff = findViewById(R.id.Switch_HolidayOff);

        Switch_Important = findViewById(R.id.Switch_Important);

        getIntentData = getIntent();

        TextView_Date.setText(getIntentData.getStringExtra("Day"));

        if(getIntentData.getStringExtra("FragScene").equals("FragWeek")){
            Week = getIntentData.getIntExtra("Week", 0);
        }
        if(getIntentData.getStringExtra("FragScene").equals("FragDate")){
            INT_Year = getIntentData.getIntExtra("Date_y", 0);
            INT_Month = getIntentData.getIntExtra("Date_m", 0);
            INT_Date = getIntentData.getIntExtra("Date_d", 0);
        }



        if(getIntentData.getStringExtra("FragScene").equals("FragAll_UpDate")){
            UpDateNum = getIntentData.getIntExtra("DateNumBer", 0);

            Name = getIntentData.getStringExtra("Name");
            Memo = getIntentData.getStringExtra("Memo");

            Time_h = getIntentData.getIntExtra("Time_h", 0);
            Time_m = getIntentData.getIntExtra("Time_m", 0);

            sound = getIntentData.getBooleanExtra("sound", false);
            vibration = getIntentData.getBooleanExtra("vibration", false);
            notification = getIntentData.getBooleanExtra("notification", false);

            beforehand = getIntentData.getBooleanExtra("beforehand", false);
            HolidayOff = getIntentData.getBooleanExtra("HolidayOff", false);
            Important = getIntentData.getBooleanExtra("Important", false);

            EditText_Name.setText(Name);
            EditText_Memo.setText(Memo);

            TextView_Time_H.setText(Integer.toString(Time_h));
            TextView_Time_M.setText(Integer.toString(Time_m));

            if(Time_h < 12){
                TextView_AmPm.setText("오전");
                if(Time_h == 0)
                    TextView_Time_H.setText("12");
                else
                    TextView_Time_H.setText(Integer.toString(Time_h));
            }else{
                TextView_AmPm.setText("오후");
                if(Time_h == 24)
                    TextView_Time_H.setText("12");
                else
                    TextView_Time_H.setText(Integer.toString(Time_h - 12));
            }

            TextView_Time_M.setText(Integer.toString(Time_m));


            //    Switch Switch_sound, Switch_vibration, Switch_notification;
            //    Switch Switch_beforehand, Switch_HolidayOff, Switch_Important;
            Switch_sound.setChecked(sound);
            Switch_vibration.setChecked(vibration);
            Switch_notification.setChecked(notification);

            Switch_beforehand.setChecked(beforehand);
            Switch_HolidayOff.setChecked(HolidayOff);
            Switch_Important.setChecked(Important);

        }

        Calendar calendar = Calendar.getInstance();
        if(Time_h == 0 && Time_m == 0){
            Time_h = calendar.get(Calendar.HOUR_OF_DAY);
            Time_m = calendar.get(Calendar.MINUTE);
            TextView_Time_H.setText(Integer.toString(calendar.get(Calendar.HOUR)));
            TextView_Time_M.setText(Integer.toString(Time_m));
        }

        findViewById(R.id.LinearLayout_TimeText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog dialog = new TimePickerDialog(ScheduleAdd.this,
                        new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        Time_h = i;
                        Time_m = i1;

                        if(Time_h < 12){
                            TextView_AmPm.setText("오전");
                            if(Time_h == 0)
                                TextView_Time_H.setText("12");
                            else
                                TextView_Time_H.setText(Integer.toString(Time_h));
                        }else{
                            TextView_AmPm.setText("오후");
                            if(Time_h == 24)
                                TextView_Time_H.setText("12");
                            else
                                TextView_Time_H.setText(Integer.toString(Time_h - 12));
                        }

                        TextView_Time_M.setText(Integer.toString(Time_m));

                    }
                }, Time_h, Time_m, false);
                dialog.show();
            }
        });

        Switch_sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sound = b;
            }
        });
        Switch_vibration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                vibration = b;
            }
        });
        Switch_notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                notification = b;
            }
        });
        Switch_beforehand.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                beforehand = b;
            }
        });
        Switch_HolidayOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                HolidayOff = b;
            }
        });
        Switch_Important.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Important = b;
            }
        });

    }

    void AllDate(String DateSet){
        AllDayDataBase allDayDataBase = Room.databaseBuilder(getApplicationContext(), AllDayDataBase.class, "AllDay_DB")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        allDayDao = allDayDataBase.allDayDao();

        allDay = new AllDay();
        allDay.setName(Name);
        allDay.setMemo(Memo);
        allDay.setTime_h(Time_h);
        allDay.setTime_m(Time_m);


        allDay.setImportant(Important);

        allDay.setSound(sound);
        allDay.setVibration(vibration);
        allDay.setNotification(notification);

        allDay.setAutoOff_Time(10);
        allDay.setWarning(beforehand);
        allDay.setHoliday(HolidayOff);

        switch (DateSet){
            case "Insert":{
                allDayDao.setInsert(allDay); break;
            }
            case "UpDate": {
                Log.d("==================UpDate", "OK");
                allDay.setId(UpDateNum);
                allDayDao.setUpDate(allDay); break;
            }
        }
    }

    public void SettingFinishButton(View view) {
        Name = EditText_Name.getText().toString();
        Memo = EditText_Memo.getText().toString();
        if(!Name.equals("")){

            if(getIntentData.getStringExtra("FragScene").equals("FragAll_UpDate")){
                AllDate("UpDate");
            }

            switch (getIntentData.getStringExtra("FragScene")) {
                case "FragAll": {

                    AllDate("Insert");
                    break;
                }
                case "FragWeek": {
                    WeekDataBase weekDataBase = Room.databaseBuilder(getApplicationContext(), WeekDataBase.class, "WeekDay_DB")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();

                    weekDayDao = weekDataBase.weekDayDao();

                    WeekDay weekDay = new WeekDay();

                    weekDay.setWeek(Week);

                    weekDay.setName(Name);
                    weekDay.setMemo(Memo);
                    weekDay.setTime_h(Time_h);
                    weekDay.setTime_m(Time_m);

                    weekDay.setImportant(Important);

                    weekDay.setSound(sound);
                    weekDay.setVibration(vibration);
                    weekDay.setNotification(notification);

                    weekDay.setAutoOff_Time(10);
                    weekDay.setWarning(beforehand);
                    weekDay.setHoliday(HolidayOff);

                    weekDayDao.setInsert(weekDay);

                    break;
                }
                case "FragDate": {
                    DateDayDataBase dateDayDataBase = Room.databaseBuilder(getApplicationContext(), DateDayDataBase.class, "DateDayData_DB")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();

                    dateDayDao = dateDayDataBase.dateDayDao();

                    DateDay dateDay = new DateDay();

                    dateDay.setDate_y(INT_Year);
                    dateDay.setDate_m(INT_Month);
                    dateDay.setDate_d(INT_Date);

                    dateDay.setName(Name);
                    dateDay.setMemo(Memo);
                    dateDay.setTime_h(Time_h);
                    dateDay.setTime_m(Time_m);


                    dateDay.setImportant(Important);

                    dateDay.setSound(sound);
                    dateDay.setVibration(vibration);
                    dateDay.setNotification(notification);

                    dateDay.setAutoOff_Time(10);
                    dateDay.setWarning(beforehand);
                    dateDay.setHoliday(HolidayOff);

                    dateDayDao.setInsert(dateDay);

                    break;
                }
            }
            finish();
        }else{
            Toast.makeText(getApplicationContext(),
                    "이름을 작성해주세요", Toast.LENGTH_SHORT).show();
            EditText_Name.setHintTextColor(0xFFFF4949);
        }
    }
}
