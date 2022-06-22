package com.example.perfecttime;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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

    Intent getIntentData;
    Calendar calendar;

    AllDay allDay;
    WeekDay weekDay;

    AllDayDao allDayDao;
    WeekDayDao weekDayDao;
    DateDayDao dateDayDao;

    TextView TextView_Date;
    TextView TextView_Time_H, TextView_Time_M, TextView_AmPm;
    EditText EditText_Name, EditText_Memo;
    Switch Switch_sound, Switch_vibration, Switch_popup, Switch_beforehand, Switch_Important;
    Spinner Spinner_WeekDay;

    int Time_h, Time_m;
    String Name, Memo;
    boolean sound, vibration, popup, beforehand, Important;

    int Week = 0;
    int INT_Year, INT_Month, INT_Date;

    int UpDateNum = 0;

    void id_settings(){
        TextView_Time_H = findViewById(R.id.TextView_Time_H);
        TextView_Time_M = findViewById(R.id.TextView_Time_M);
        TextView_AmPm = findViewById(R.id.TextView_AmPm);
        TextView_Date = findViewById(R.id.TextView_Date);

        EditText_Name = findViewById(R.id.EditText_Name);
        EditText_Memo = findViewById(R.id.EditText_Memo);

        Switch_Important = findViewById(R.id.Switch_Important);//중요표시

        Switch_sound = findViewById(R.id.Switch_sound);//소리
        Switch_vibration = findViewById(R.id.Switch_vibration);//진동
        Switch_popup = findViewById(R.id.Switch_popup);//팦업창
        Switch_beforehand = findViewById(R.id.Switch_beforehand);//알림에고

        Spinner_WeekDay = findViewById(R.id.Spinner_WeekDay);//요일 선택
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        setContentView(R.layout.schedule_add);

        id_settings();

        getIntentData = getIntent();//getData Intent

        getData_start_setting();



        //시간설정
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

        Switch_Important.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Important = b;
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
        Switch_popup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                popup = b;
            }
        });
        Switch_beforehand.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                beforehand = b;
            }
        });


    }

    void getData_start_setting(){

        String exit_SceneGetData = getIntentData.getStringExtra("FragScene");
        //날짜 타입의 알람
        if(exit_SceneGetData.equals("FragDate_UpDate") ||
                exit_SceneGetData.equals("FragDate")){
            INT_Year = getIntentData.getIntExtra("Date_y", 0);
            INT_Month = getIntentData.getIntExtra("Date_m", 0);
            INT_Date = getIntentData.getIntExtra("Date_d", 0);

            TextView_Date.setVisibility(View.VISIBLE);
            Spinner_WeekDay.setVisibility(View.GONE);
            TextView_Date.setText(INT_Year + "년 " + INT_Month + "월 " + INT_Date + "일");

            DatePickerDialog datePickerDialog;

            datePickerDialog = new DatePickerDialog(ScheduleAdd.this, new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    INT_Year = i;
                    INT_Month = i1 + 1;
                    INT_Date = i2;

                    TextView_Date.setText(INT_Year + "년 " + INT_Month + "월 " + INT_Date + "일");
                }

            }, INT_Year, INT_Month, INT_Date);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

            datePickerDialog.show();
        }

        if(exit_SceneGetData.equals("FragAll_UpDate") ||
                exit_SceneGetData.equals("FragAll")){
            TextView_Date.setVisibility(View.VISIBLE);
            Spinner_WeekDay.setVisibility(View.GONE);
            TextView_Date.setText(getIntentData.getStringExtra("Day"));
        }
        if(exit_SceneGetData.equals("FragWeek_UpDate") ||
                exit_SceneGetData.equals("FragWeek")){
            TextView_Date.setVisibility(View.GONE);
            Spinner_WeekDay.setVisibility(View.VISIBLE);
            Week = getIntentData.getIntExtra("Week", 0);

            //
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.WeekDay, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);

            Spinner_WeekDay.setAdapter(adapter);
            Spinner_WeekDay.setSelection(Week);
            Spinner_WeekDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Week = i;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }


        if(  exit_SceneGetData.equals("FragAll") ||
                exit_SceneGetData.equals("FragWeek") ||
                exit_SceneGetData.equals("FragDate")){

            calendar = Calendar.getInstance();

            Time_h = calendar.get(Calendar.HOUR_OF_DAY);
            Time_m = calendar.get(Calendar.MINUTE);
            TextView_Time_H.setText(Integer.toString(calendar.get(Calendar.HOUR)));
            TextView_Time_M.setText(Integer.toString(Time_m));
        }

/*
        if(getIntentData.getStringExtra("FragScene").equals("FragAll_UpDate")){


        }else if(getIntentData.getStringExtra("FragScene").equals("FragAll_UpDate")){

        }*/

        UpDateNum = getIntentData.getIntExtra("DateNumBer", 0);

        Name = getIntentData.getStringExtra("Name");
        Memo = getIntentData.getStringExtra("Memo");

        Time_h = getIntentData.getIntExtra("Time_h", 0);
        Time_m = getIntentData.getIntExtra("Time_m", 0);

        Important = getIntentData.getBooleanExtra("Important", false);

        sound = getIntentData.getBooleanExtra("Sound", false);
        vibration = getIntentData.getBooleanExtra("Vibration", false);
        popup = getIntentData.getBooleanExtra("popup", false);

        beforehand = getIntentData.getBooleanExtra("Beforehand", false);

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
        Switch_Important.setChecked(Important);

        Switch_sound.setChecked(sound);
        Switch_vibration.setChecked(vibration);
        Switch_popup.setChecked(popup);
        Switch_beforehand.setChecked(beforehand);
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
        allDay.setPopup(popup);

        allDay.setAutoOff_Time(10);
        allDay.setWarning(beforehand);

        switch (DateSet){
            case "Insert":{
                allDayDao.setInsert(allDay); break;
            }
            case "UpDate": {
                //Log.d("==================UpDate", "OK");
                allDay.setId(UpDateNum);
                allDayDao.setUpDate(allDay); break;
            }
        }
    }

    void WeekDate(String DateSet){
        WeekDataBase weekDataBase = Room.databaseBuilder(getApplicationContext(), WeekDataBase.class, "WeekDay_DB")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        weekDayDao = weekDataBase.weekDayDao();

        weekDay = new WeekDay();

        weekDay.setWeek(Week);

        weekDay.setName(Name);
        weekDay.setMemo(Memo);
        weekDay.setTime_h(Time_h);
        weekDay.setTime_m(Time_m);

        weekDay.setImportant(Important);

        weekDay.setSound(sound);
        weekDay.setVibration(vibration);
        weekDay.setPopup(popup);

        weekDay.setAutoOff_Time(10);
        weekDay.setWarning(beforehand);

        //weekDayDao.setInsert(weekDay);

        switch (DateSet){
            case "Insert":{
                weekDayDao.setInsert(weekDay); break;
            }
            case "UpDate": {
                //Log.d("==================UpDate", "OK");
                weekDay.setId(UpDateNum);
                weekDayDao.setUpDate(weekDay); break;
            }
        }
    }

    void DateDate(String DateSet){

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
        dateDay.setPopup(popup);

        dateDay.setAutoOff_Time(10);
        dateDay.setWarning(beforehand);

        switch (DateSet){
            case "Insert":{
                dateDayDao.setInsert(dateDay); break;
            }
            case "UpDate": {
                //Log.d("==================UpDate", "OK");
                dateDay.setId(UpDateNum);
                dateDayDao.setUpDate(dateDay); break;
            }
        }
    }

    public void SettingFinishButton(View view) {
        Name = EditText_Name.getText().toString();
        Memo = EditText_Memo.getText().toString();
        if(!Name.equals("")){

            if(getIntentData.getStringExtra("FragScene").equals("FragAll_UpDate")){
                AllDate("UpDate");
            }else if(getIntentData.getStringExtra("FragScene").equals("FragWeek_UpDate")){
                WeekDate("UpDate");
            }else if(getIntentData.getStringExtra("FragScene").equals("FragDate_UpDate")){
                DateDate("UpDate");
            }

            switch (getIntentData.getStringExtra("FragScene")) {
                case "FragAll": {

                    AllDate("Insert");
                    break;
                }
                case "FragWeek": {

                    WeekDate("Insert");

                    break;
                }
                case "FragDate": {
                    DateDate("Insert");

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
