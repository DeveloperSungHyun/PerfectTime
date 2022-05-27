package com.example.perfecttime.Calender;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.perfecttime.R;

import java.util.List;

public class CalenderAdapter extends BaseAdapter {

    CalenderDayDateList calenderDayDateList;
    List<Integer> DayList;

    public TextView textView, TextView_val;
    RelativeLayout RelativeLayout_Back;

    Context context;
    LayoutInflater inflater;
    String[] arrNum;
    int Date;
    int DayOfTheWeek;
    int MaxDayNon;
    int View_Day;


    public CalenderAdapter(Context context, String[] arrNum, int Date, int DayOfTheWeek, int MaxDayNon, int View_Day) {
        this.context = context;
        this.arrNum = arrNum;
        this.Date = Date;
        this.DayOfTheWeek = DayOfTheWeek;
        this.MaxDayNon = MaxDayNon;
        this.View_Day = View_Day;

        calenderDayDateList = new CalenderDayDateList();
        DayList = calenderDayDateList.getDayList();
    }

    @Override
    public int getCount() {
        return arrNum.length;
    }

    @Override
    public Object getItem(int i) {
        return arrNum[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(view == null){
            view = inflater.inflate(R.layout.calender_cell, null);
        }

        textView = view.findViewById(R.id.TextView_Number);
        TextView_val = view.findViewById(R.id.TextView_val);
        RelativeLayout_Back = view.findViewById(R.id.RelativeLayout_Back);

        textView.setText(arrNum[i]);

        //textView.setBackgroundColor(0xFFFFFFFF);
        //RelativeLayout_Back.setBackgroundColor(0xFFFFFFFF);
        TextView_val.setVisibility(View.GONE);

        if(i % 7 == 0) textView.setTextColor(0xFFFF0000);
        if((i - 6) % 7 == 0) textView.setTextColor(0xFF0000FF);
        if(Date + DayOfTheWeek - 1 > i){
            textView.setTextColor(0x20000000);
            RelativeLayout_Back.setBackgroundColor(0xFFFFFFFF);
            if(i % 7 == 0) textView.setTextColor(0x60FF0000);
            if((i - 6) % 7 == 0) textView.setTextColor(0x600000FF);
        }
        if(arrNum[i].equals("")) RelativeLayout_Back.setBackgroundColor(0xFFFFFFFF);


        int count = 0;
        for(int a = 0; a < DayList.size(); a++){
            if(DayList.get(a) == i - Date + 1){
                count++;
                TextView_val.setVisibility(View.VISIBLE);
                TextView_val.setText(Integer.toString(count));
            }
        }
//        DayList.clear();

        return view;
    }

}
