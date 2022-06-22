package com.example.perfecttime.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.perfecttime.ListDataAdapter;
import com.example.perfecttime.ListItemDara;
import com.example.perfecttime.R;
import com.example.perfecttime.RoomDataBase.AllDay_DataBase.AllDay;
import com.example.perfecttime.RoomDataBase.AllDay_DataBase.AllDayDao;
import com.example.perfecttime.RoomDataBase.AllDay_DataBase.AllDayDataBase;
import com.example.perfecttime.ScheduleAdd;

import java.util.ArrayList;
import java.util.List;

public class FragAll extends Fragment {
    private View view;
    AllDayDao allDayDao;

    List<AllDay> dayList;

    private ArrayList<ListItemDara> arrayList;
    private ListDataAdapter listAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    LinearLayout LinearLayout_TimerAddButton;

    static int deleteId;

    public static FragAll newInstance(){
        FragAll fragAll = new FragAll();
        return fragAll;
    }

    @Override
    public void onStart() {
        super.onStart();


        AllDayDataBase allDayDataBase =
                Room.databaseBuilder(view.getContext(), AllDayDataBase.class, "AllDay_DB")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        allDayDao = allDayDataBase.allDayDao();

        arrayList.clear();
        dayList = allDayDao.getAllDay();
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

            ListItemDara dara = new ListItemDara(Name ,Memo, Time_h, Time_m, AmPm, Important, sound, vibration, popup, "매일", 0);
            arrayList.add(dara);

        }

        listAdapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_allday,container, false);

        LinearLayout_TimerAddButton = view.findViewById(R.id.LinearLayout_TimerAddButton);

        recyclerView = view.findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        listAdapter = new ListDataAdapter(arrayList);
        recyclerView.setAdapter(listAdapter);


        LinearLayout_TimerAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ScheduleAdd.class);
                intent.putExtra("FragScene", "FragAll");
                intent.putExtra("Day", "매일");
                startActivity(intent);
            }
        });

        LinearLayout_TimerAddButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                return false;
            }
        });

        return view;
    }


    public boolean test(int DeleteId){

        this.deleteId = DeleteId;

        return true;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }
}
