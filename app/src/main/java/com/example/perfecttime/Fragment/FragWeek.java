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

    LinearLayout LinearLayout_TimerAddButton;

    private static ArrayList<ListItemDara> arrayList;
    private static ListDataAdapter listAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    static int deleteId;

    void ID_Setting(){
        LinearLayout_TimerAddButton = view.findViewById(R.id.LinearLayout_TimerAddButton);

        recyclerView = view.findViewById(R.id.recyclerview);

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
        for(int i = 0; i < dayList.size(); i++){

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

            boolean sound = dayList.get(i).isSound();
            boolean vibration = dayList.get(i).isVibration();
            boolean popup = dayList.get(i).isPopup();

            if(i == 0 || dayList.get(i).getWeek() != dayList.get(i - 1).getWeek()){
                ListItemDara dara = new ListItemDara(null ,Memo, Time_h, Time_m, AmPm, Important, sound, vibration, popup, WeekDay[dayList.get(i).getWeek()] + "요일", 1);
                arrayList.add(dara);
            }
            ListItemDara dara = new ListItemDara(Name ,Memo, Time_h, Time_m, AmPm, Important, sound, vibration, popup, WeekDay[dayList.get(i).getWeek()] + "요일", 0);
            arrayList.add(dara);

        }
        listAdapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_weekday,container, false);
        ID_Setting();

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

