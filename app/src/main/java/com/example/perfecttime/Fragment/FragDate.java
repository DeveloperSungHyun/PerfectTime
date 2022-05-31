package com.example.perfecttime.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.perfecttime.Calender.CalenderAdapter;
import com.example.perfecttime.Calender.CalenderDayDateList;
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

    List<Integer> DayItem;

    Dialog SetDate;
    CalenderAdapter calenderAdapter;

    LinearLayout LinearLayout_TimerAddButton;

    private ArrayList<ListItemDara> arrayList;
    private ListDataAdapter listAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    TextView TextView_SetDate;
    TextView TextView_Day_show;
    ImageView ImageView_Button_Left, ImageView_Button_Right;


    static int deleteId;

    public GridView GridView_Calender;


    int DateMaxDay[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    String grid[] = new String[37];
    int ArrDay;
    int DayOfTheWeek;
    int MaxDayNon;

    int View_Day;

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

        TextView_Day_show.setText(INT_Year + "년 " + (INT_Month + 1) + "월 " + INT_Date_ch + "일");

        DateDayDataBase dateDayDataBase = Room.databaseBuilder(view.getContext(), DateDayDataBase.class, "DateDayData_DB")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        dateDayDao = dateDayDataBase.dateDayDao();

        dateDayList = dateDayDao.getDateDay();
        ListShow();

        Test();
        CalendarCalculation(INT_Year, INT_Month, INT_Date);
    }

    void ListShow(){
        arrayList.clear();

        for(int i = 0; i < dateDayList.size(); i++){
            if(INT_Year == dateDayList.get(i).getDate_y() && INT_Month == dateDayList.get(i).getDate_m()
                    && INT_Date_ch == dateDayList.get(i).getDate_d()){

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

                ListItemDara dara = new ListItemDara(Name ,Memo, Time_h, Time_m, AmPm, Important, "1월 1일", 1);
                arrayList.add(dara);
            }

        }


        listAdapter.notifyDataSetChanged();
    }

    void Test(){
        DayItem.clear();
        for(int i = 0; i < dateDayList.size(); i++){
            if(INT_Year == dateDayList.get(i).getDate_y() && INT_Month == dateDayList.get(i).getDate_m()){
                Log.d("=====================", dateDayList.get(i).getDate_d() + "asd");
                DayItem.add(dateDayList.get(i).getDate_d());

            }

        }

        CalenderDayDateList calenderDayDateList  = new CalenderDayDateList();
        calenderDayDateList.setDayList(DayItem);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_dateday,container, false);


        recyclerView = view.findViewById(R.id.recyclerview);
        GridView_Calender = view.findViewById(R.id.GridView_Calender);
        TextView_SetDate = view.findViewById(R.id.TextView_SetDate);

        TextView_Day_show = view.findViewById(R.id.TextView_Day_show);

        ImageView_Button_Left = view.findViewById(R.id.ImageView_Button_Left);
        ImageView_Button_Right = view.findViewById(R.id.ImageView_Button_Right);
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

        View_Day = INT_Date;

        DayItem = new ArrayList<>();

        ImageView_Button_Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Min_Year < INT_Year || (Min_Year == INT_Year && Min_Month < INT_Month)){
                    if(INT_Month > 0){
                        INT_Month--;
                    }else{
                        INT_Month = 11;
                        INT_Year--;
                    }
                }else{
                    Toast.makeText(view.getContext(), "지나간 달 입니다.", Toast.LENGTH_SHORT).show();
                }

                Test();

                CalendarCalculation(INT_Year, INT_Month, INT_Date);


            }
        });
        ImageView_Button_Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(INT_Month < 11){
                    INT_Month++;
                }else{
                    INT_Month = 0;
                    INT_Year++;
                }

                Test();

                CalendarCalculation(INT_Year, INT_Month, INT_Date);


            }
        });


        GridView_Calender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if((DayOfTheWeek + INT_Date <= i + 1 && DateMaxDay[INT_Month] + DayOfTheWeek >= i + 1)
                || !(Min_Year == INT_Year && INT_Month == Min_Month)) {
                    View_Day = i;

                    INT_Date_ch = i - DayOfTheWeek + 1;

                    ListShow();
                    TextView_Day_show.setText(INT_Year + "년 " + (INT_Month + 1) + "월 " + INT_Date_ch + "일");
                }

            }
        });

        LinearLayout_TimerAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ScheduleAdd.class);
                intent.putExtra("FragScene", "FragDate");
                intent.putExtra("Date_y", INT_Year);
                intent.putExtra("Date_m", INT_Month);
                intent.putExtra("Date_d", INT_Date_ch);
                startActivity(intent);
            }
        });


        return view;
    }

    void CalendarCalculation(int Year, int Month, int Date){
        for(int ArrDel = 0; ArrDel < grid.length; ArrDel++) grid[ArrDel] = "";
//        MaxDayNon = DateMaxDay[Month + NewMonth] + DayOfTheWeek;

        ArrDay = ((Year - 1) * 365) + (int)((Year - 1) / 4);
        for(int i = 0; i < Month; i++) ArrDay += DateMaxDay[i];
        if(Month > 1 && Year % 4 == 0) ArrDay += 1;
        DayOfTheWeek = (ArrDay) % 7;

        for(int i = 1; i <= 37; i++){
            if(DayOfTheWeek < i && DateMaxDay[Month] >= i - DayOfTheWeek){
                grid[i - 1] = Integer.toString(i - DayOfTheWeek);
            }
        }

        if(!(Min_Year == Year && Min_Month == Month)){
            Date = 0;
        }
        calenderAdapter = new CalenderAdapter(view.getContext(), grid, DayOfTheWeek, Date, MaxDayNon, View_Day);
        GridView_Calender.setAdapter(calenderAdapter);

        TextView_SetDate.setText(Year + "년 " + (Month + 1) + "월 ");

    }

    public boolean test(int DeleteId){

        this.deleteId = DeleteId;

        return true;

    }
}
