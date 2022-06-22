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
import com.example.perfecttime.RoomDataBase.DateDataBase.DateDay;
import com.example.perfecttime.RoomDataBase.DateDataBase.DateDayDao;
import com.example.perfecttime.RoomDataBase.DateDataBase.DateDayDataBase;
import com.example.perfecttime.ScheduleAdd;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FragDate extends Fragment {
    private View view;

    DateDayDao dateDayDao;

    public List<DateDay> dateDayList;


    LinearLayout LinearLayout_TimerAddButton;

    private ArrayList<ListItemDara> arrayList;
    private ListDataAdapter listAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;


    static int deleteId;

    int INT_Year, INT_Month, INT_Date;
    int INT_Date_ch;
    int Min_Year, Min_Month;

    public static FragDate newInstance(){
        FragDate fragDate = new FragDate();
        return fragDate;
    }

    @Override
    public void onStart() {
        super.onStart();


        DateDayDataBase dateDayDataBase = Room.databaseBuilder(view.getContext(), DateDayDataBase.class, "DateDayData_DB")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        dateDayDao = dateDayDataBase.dateDayDao();

        dateDayList = dateDayDao.getDateDay();
        ListShow();

    }
    String Title_Day;
    void ListShow(){
        arrayList.clear();

        for(int i = 0; i < dateDayList.size(); i++){

            String Name = dateDayList.get(i).getName();
            String Memo = dateDayList.get(i).getMemo();
            int Time_h = dateDayList.get(i).getTime_h();
            int Time_m = dateDayList.get(i).getTime_m();
            boolean Important = dateDayList.get(i).isImportant();

            boolean AmPm = true;
            if(Time_h > 12){
                Time_h -= 12;
                AmPm = false;
            }

            boolean sound = dateDayList.get(i).isSound();
            boolean vibration = dateDayList.get(i).isVibration();
            boolean popup = dateDayList.get(i).isPopup();

            if((i == 0) || (!(dateDayList.get(i).getDate_y() != dateDayList.get(i - 1).getDate_y() &&
                    dateDayList.get(i).getDate_m() != dateDayList.get(i - 1).getDate_m()) &&
                    dateDayList.get(i).getDate_d() != dateDayList.get(i - 1).getDate_d())){

                Title_Day = dateDayList.get(i).getDate_y() + "년 " +
                        dateDayList.get(i).getDate_m() + "월 " +
                        dateDayList.get(i).getDate_d() + "일";

                ListItemDara dara = new ListItemDara(null ,Memo, Time_h, Time_m, AmPm, Important, sound, vibration, popup, Title_Day, 1);
                arrayList.add(dara);

            }

            ListItemDara dara = new ListItemDara(Name ,Memo, Time_h, Time_m, AmPm, Important, sound, vibration, popup, Title_Day, 0);
            arrayList.add(dara);


        }


        listAdapter.notifyDataSetChanged();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_dateday,container, false);

        recyclerView = view.findViewById(R.id.recyclerview);

        LinearLayout_TimerAddButton = view.findViewById(R.id.LinearLayout_TimerAddButton);

        linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        listAdapter = new ListDataAdapter(arrayList);
        recyclerView.setAdapter(listAdapter);

        Calendar calendar = Calendar.getInstance();
        Min_Year = INT_Year = calendar.get(Calendar.YEAR);
        Min_Month = INT_Month = calendar.get(Calendar.MONTH);
        INT_Date_ch = INT_Date = calendar.get(Calendar.DATE);


        LinearLayout_TimerAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ScheduleAdd.class);
                intent.putExtra("FragScene", "FragDate");

                startActivity(intent);
            }
        });


        return view;
    }


    public boolean test(int DeleteId){

        this.deleteId = DeleteId;

        return true;

    }
}
