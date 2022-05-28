package com.example.perfecttime.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.perfecttime.ListDataAdapter;
import com.example.perfecttime.ListItemDara;
import com.example.perfecttime.R;
import com.example.perfecttime.RoomDataBase.WeekDataBase.WeekDataBase;
import com.example.perfecttime.RoomDataBase.WeekDataBase.WeekDay;
import com.example.perfecttime.RoomDataBase.WeekDataBase.WeekDayDao;
import com.example.perfecttime.ScheduleAdd;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class FragWeek extends Fragment {
    private View view;

    static WeekDayDao weekDayDao;

    static List<WeekDay> dayList;

    protected TextView TextView_Sun, TextView_Mon, TextView_Tue, TextView_Wed, TextView_Thu, TextView_Fri, TextView_Sat;
    LinearLayout LinearLayout_TimerAddButton;

    private static ArrayList<ListItemDara> arrayList;
    private static ListDataAdapter listAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    static int deleteId;

    void ID_Setting(){
        LinearLayout_TimerAddButton = view.findViewById(R.id.LinearLayout_TimerAddButton);

        recyclerView = view.findViewById(R.id.recyclerview);

        TextView_Sun = view.findViewById(R.id.TextView_Sun);
        TextView_Mon = view.findViewById(R.id.TextView_Mon);
        TextView_Tue = view.findViewById(R.id.TextView_Tue);
        TextView_Wed = view.findViewById(R.id.TextView_Wed);
        TextView_Thu = view.findViewById(R.id.TextView_Thu);
        TextView_Fri = view.findViewById(R.id.TextView_Fri);
        TextView_Sat = view.findViewById(R.id.TextView_Sat);

    }

    public static FragWeek newInstance(){
        FragWeek fragAll = new FragWeek();
        return fragAll;
    }

    @Override
    public void onStart() {
        super.onStart();

        WeekDataBase weekDataBase = Room.databaseBuilder(view.getContext(), WeekDataBase.class, "WeekDay_DB")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        weekDayDao = weekDataBase.weekDayDao();

        ListShow();
    }

    void ListShow(){
        arrayList.clear();
        dayList = weekDayDao.getWeekDay();
        String WeekDay[] = {"일","월", "화", "수", "목", "금", "토"};
        for(int i = 1; i < dayList.size(); i++){

            if(dayList.get(i).getWeek() == WeekButton.day || true){

                String Name = dayList.get(i).getName();
                String Memo = dayList.get(i).getMemo();
                int Time_h = dayList.get(i).getTime_h();
                int Time_m = dayList.get(i).getTime_m();
                boolean Important = dayList.get(i).isImportant();
                boolean AmPm = true;
                if(Time_h > 12){
                    Time_h -= 12;
                    AmPm = false;
                }


                ListItemDara dara = new ListItemDara(Name ,Memo, Time_h, Time_m, AmPm, Important, WeekDay[dayList.get(i).getWeek()] + "요일");
                arrayList.add(dara);

            }

        }
        listAdapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_weekday,container, false);
        ID_Setting();

        Calendar calendar = Calendar.getInstance();
        WeekButton.day = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        WeekButton weekButton = new WeekButton(view);

        TextView_Sun.setBackgroundResource(R.drawable.week_button_color_false);
        TextView_Mon.setBackgroundResource(R.drawable.week_button_color_false);
        TextView_Tue.setBackgroundResource(R.drawable.week_button_color_false);
        TextView_Wed.setBackgroundResource(R.drawable.week_button_color_false);
        TextView_Thu.setBackgroundResource(R.drawable.week_button_color_false);
        TextView_Fri.setBackgroundResource(R.drawable.week_button_color_false);
        TextView_Sat.setBackgroundResource(R.drawable.week_button_color_false);

        switch (WeekButton.day){
            case 0: TextView_Sun.setBackgroundResource(R.drawable.week_button_color_true); break;
            case 1: TextView_Mon.setBackgroundResource(R.drawable.week_button_color_true); break;
            case 2: TextView_Tue.setBackgroundResource(R.drawable.week_button_color_true); break;
            case 3: TextView_Wed.setBackgroundResource(R.drawable.week_button_color_true); break;
            case 4: TextView_Thu.setBackgroundResource(R.drawable.week_button_color_true); break;
            case 5: TextView_Fri.setBackgroundResource(R.drawable.week_button_color_true); break;
            case 6: TextView_Sat.setBackgroundResource(R.drawable.week_button_color_true); break;
        }

        linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        listAdapter = new ListDataAdapter(arrayList);
        recyclerView.setAdapter(listAdapter);

        LinearLayout_TimerAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), ScheduleAdd.class);
                intent.putExtra("FragScene", "FragWeek");
                intent.putExtra("Day", "월");
                intent.putExtra("Week", WeekButton.day);
                startActivity(intent);
            }
        });

        return view;
    }

    public boolean test(int DeleteId) {

        this.deleteId = DeleteId;

        return true;

    }
}

class WeekButton extends FragWeek{

    static int day = 0;


    public WeekButton(View view){

        TextView_Sun = view.findViewById(R.id.TextView_Sun);
        TextView_Mon = view.findViewById(R.id.TextView_Mon);
        TextView_Tue = view.findViewById(R.id.TextView_Tue);
        TextView_Wed = view.findViewById(R.id.TextView_Wed);
        TextView_Thu = view.findViewById(R.id.TextView_Thu);
        TextView_Fri = view.findViewById(R.id.TextView_Fri);
        TextView_Sat = view.findViewById(R.id.TextView_Sat);

        TextView_Sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                day = 0;
                AllReset();
            }
        });
        TextView_Mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                day = 1;
                AllReset();
            }
        });
        TextView_Tue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                day = 2;
                AllReset();
            }
        });
        TextView_Wed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                day = 3;
                AllReset();
            }
        });
        TextView_Thu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                day = 4;
                AllReset();
            }
        });
        TextView_Fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                day = 5;
                AllReset();
            }
        });
        TextView_Sat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                day = 6;
                AllReset();
            }
        });

    }

    void AllReset(){
        TextView_Sun.setBackgroundResource(R.drawable.week_button_color_false);
        TextView_Mon.setBackgroundResource(R.drawable.week_button_color_false);
        TextView_Tue.setBackgroundResource(R.drawable.week_button_color_false);
        TextView_Wed.setBackgroundResource(R.drawable.week_button_color_false);
        TextView_Thu.setBackgroundResource(R.drawable.week_button_color_false);
        TextView_Fri.setBackgroundResource(R.drawable.week_button_color_false);
        TextView_Sat.setBackgroundResource(R.drawable.week_button_color_false);

        switch (WeekButton.day){
            case 0: TextView_Sun.setBackgroundResource(R.drawable.week_button_color_true); break;
            case 1: TextView_Mon.setBackgroundResource(R.drawable.week_button_color_true); break;
            case 2: TextView_Tue.setBackgroundResource(R.drawable.week_button_color_true); break;
            case 3: TextView_Wed.setBackgroundResource(R.drawable.week_button_color_true); break;
            case 4: TextView_Thu.setBackgroundResource(R.drawable.week_button_color_true); break;
            case 5: TextView_Fri.setBackgroundResource(R.drawable.week_button_color_true); break;
            case 6: TextView_Sat.setBackgroundResource(R.drawable.week_button_color_true); break;
        }

        FragWeek fragWeek = new FragWeek();

        fragWeek.ListShow();

    }


    protected int getData(){
        return day;
    }
}
