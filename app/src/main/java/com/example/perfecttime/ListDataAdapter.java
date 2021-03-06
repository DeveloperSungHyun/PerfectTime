package com.example.perfecttime;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.perfecttime.Fragment.FragAll;
import com.example.perfecttime.Fragment.FragDate;
import com.example.perfecttime.Fragment.FragWeek;
import com.example.perfecttime.RoomDataBase.AllDay_DataBase.AllDay;
import com.example.perfecttime.RoomDataBase.AllDay_DataBase.AllDayDao;
import com.example.perfecttime.RoomDataBase.AllDay_DataBase.AllDayDataBase;
import com.example.perfecttime.RoomDataBase.DateDataBase.DateDay;
import com.example.perfecttime.RoomDataBase.DateDataBase.DateDayDao;
import com.example.perfecttime.RoomDataBase.DateDataBase.DateDayDataBase;
import com.example.perfecttime.RoomDataBase.WeekDataBase.WeekDataBase;
import com.example.perfecttime.RoomDataBase.WeekDataBase.WeekDay;
import com.example.perfecttime.RoomDataBase.WeekDataBase.WeekDayDao;

import java.util.ArrayList;
import java.util.List;

public class ListDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Intent intent;
    View view;

    AllDayDao allDayDao;
    WeekDayDao weekDayDao;
    DateDayDao dateDayDao;

    List<AllDay> AllDayList;
    List<WeekDay> WeekDayList;
    List<DateDay> dateDayList;

    FragAll fragAll;
    FragWeek fragWeek;
    FragDate fragDate;

    private ArrayList<ListItemDara> listItemDara;

    public int DeleteId;

    public int ItemPosition;

    public ListDataAdapter(ArrayList<ListItemDara>listItemDara) {
        this.listItemDara = listItemDara;
    }

    void DataBase_setting(){
        AllDayDataBase allDayDataBase = Room.databaseBuilder(view.getContext(), AllDayDataBase.class, "AllDay_DB")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        allDayDao = allDayDataBase.allDayDao();

        WeekDataBase weekDataBase = Room.databaseBuilder(view.getContext(), WeekDataBase.class, "WeekDay_DB")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        weekDayDao = weekDataBase.weekDayDao();

        DateDayDataBase dateDayDataBase = Room.databaseBuilder(view.getContext(), DateDayDataBase.class, "DateDayData_DB")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        dateDayDao = dateDayDataBase.dateDayDao();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(viewType == ViewType.MAIN_View){
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_list_item_1, parent, false);
            view = inflater.inflate(R.layout.recyclerview_list_item_1, parent, false);
            DataBase_setting();
            return new CustomViewHolder(view);
        }else {
            view = inflater.inflate(R.layout.recyclerview_list_item_2, parent, false);
            //DataBase_setting();
            return new CustomViewHolder_label(view);
        }


    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {//????????? ????????? ????????? ?????????

        fragAll = new FragAll();
        fragWeek = new FragWeek();
        fragDate = new FragDate();

        if(holder instanceof CustomViewHolder){
//            ((CustomViewHolder) holder).
            ListItemView(((CustomViewHolder) holder), position);
        }else{
            divisionLine( ((CustomViewHolder_label) holder), position);
        }
        //RelativeLayout_divisionLine


    }

    void divisionLine(CustomViewHolder_label holder, int position){
        holder.TextView_Day.setText(listItemDara.get(position).getDay());
        if(listItemDara.get(position).getDay().equals("?????????")){
            holder.TextView_Day.setTextColor(0xB30000FF);
        }else if(listItemDara.get(position).getDay().equals("?????????")){
            holder.TextView_Day.setTextColor(0xB3FF0000);
        }else{
            holder.TextView_Day.setTextColor(0xFF000000);
        }

        holder.RelativeLayout_divisionLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ScheduleAdd.class);

                intent.putExtra("FragScene", "FragWeek");//Week

                int Int_Day = 0;
                switch (listItemDara.get(position).getDay()){
                    case "?????????": Int_Day = 0;break;
                    case "?????????": Int_Day = 1;break;
                    case "?????????": Int_Day = 2;break;
                    case "?????????": Int_Day = 3;break;
                    case "?????????": Int_Day = 4;break;
                    case "?????????": Int_Day = 5;break;
                    case "?????????": Int_Day = 6;break;
                }
                intent.putExtra("Week", Int_Day);

                view.getContext().startActivity(intent);
            }
        });
    }

    void ListItemView(CustomViewHolder holder, int position){
        String Time_Str;
        Time_Str = listItemDara.get(position).getTime_h() + " : " + listItemDara.get(position).getTime_m();
        holder.Name.setText(listItemDara.get(position).getName());
        holder.Memo.setText(listItemDara.get(position).getMemo());
        holder.Time.setText(Time_Str);

        holder.TextView_Day.setText("");//listItemDara.get(position).getDay()

        if(listItemDara.get(position).isAmPm()) //true = ?????? | false = ??????
            holder.AmPm.setText("??????");
        else
            holder.AmPm.setText("??????");

        if(listItemDara.get(position).isImportant())
            holder.important.setImageResource(R.drawable.star_true);
        else
            holder.important.setImageResource(R.drawable.star_false);

        holder.itemView.setTag(position);

        holder.RelativeLayout_BackGround.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //int i = MainActivity.viewPager.getCurrentItem();
                int i = MainActivity.viewPager.getCurrentItem();
                ItemPosition = holder.getAdapterPosition();

                switch (i){

                    case 1:{

                        AllDayList = allDayDao.getAllDay();
                        Toast.makeText(view.getContext(), "test" + ItemPosition, Toast.LENGTH_SHORT).show();

//                fragAll.TimeDateReSet(view.getContext());
                        intent = new Intent(view.getContext(), ScheduleAdd.class);
                        intent.putExtra("FragScene", "FragAll_UpDate");
                        intent.putExtra("Day", "??????");

                        intent.putExtra("DateNumBer", AllDayList.get(ItemPosition).getId());

                        intent.putExtra("Name", allDayDao.getAllDay().get(ItemPosition).getName());
                        intent.putExtra("Memo", allDayDao.getAllDay().get(ItemPosition).getMemo());
                        intent.putExtra("Time_h", allDayDao.getAllDay().get(ItemPosition).getTime_h());
                        intent.putExtra("Time_m", allDayDao.getAllDay().get(ItemPosition).getTime_m());
                        intent.putExtra("Important", allDayDao.getAllDay().get(ItemPosition).isImportant());

                        intent.putExtra("Sound", allDayDao.getAllDay().get(ItemPosition).isSound());
                        intent.putExtra("Vibration", allDayDao.getAllDay().get(ItemPosition).isVibration());
                        intent.putExtra("popup", allDayDao.getAllDay().get(ItemPosition).isPopup());

                        //intent.putExtra("AutoOff_Time", allDayDao.getAllDay().get(ItemPosition).getAutoOff_Time());
                        intent.putExtra("Warning", allDayDao.getAllDay().get(ItemPosition).isWarning());

                        break;
                    }

                    case 2:{

                        WeekDayList = weekDayDao.getWeekDay();
                        Toast.makeText(view.getContext(), "test" + ItemPosition, Toast.LENGTH_SHORT).show();

//                fragAll.TimeDateReSet(view.getContext());
                        intent = new Intent(view.getContext(), ScheduleAdd.class);
                        intent.putExtra("FragScene", "FragWeek_UpDate");

                        int li_count = li_count_count(ItemPosition);
                        intent.putExtra("DateNumBer", WeekDayList.get(ItemPosition - li_count).getId());

                        intent.putExtra("Week", weekDayDao.getWeekDay().get(ItemPosition - li_count).getWeek());

                        intent.putExtra("Name", weekDayDao.getWeekDay().get(ItemPosition - li_count).getName());
                        intent.putExtra("Memo", weekDayDao.getWeekDay().get(ItemPosition - li_count).getMemo());
                        intent.putExtra("Time_h", weekDayDao.getWeekDay().get(ItemPosition - li_count).getTime_h());
                        intent.putExtra("Time_m", weekDayDao.getWeekDay().get(ItemPosition - li_count).getTime_m());


                        intent.putExtra("Important", weekDayDao.getWeekDay().get(ItemPosition - li_count).isImportant());

                        intent.putExtra("Sound", weekDayDao.getWeekDay().get(ItemPosition - li_count).isSound());
                        intent.putExtra("Vibration", weekDayDao.getWeekDay().get(ItemPosition - li_count).isVibration());
                        intent.putExtra("popup", weekDayDao.getWeekDay().get(ItemPosition - li_count).isPopup());

                        //intent.putExtra("AutoOff_Time", weekDayDao.getWeekDay().get(ItemPosition - li_count).getAutoOff_Time());
                        intent.putExtra("Beforehand", weekDayDao.getWeekDay().get(ItemPosition - li_count).isWarning());
                        break;
                    }
                    case 3:{

                        dateDayList = dateDayDao.getDateDay();
                        //Toast.makeText(view.getContext(), "test" + ItemPosition, Toast.LENGTH_SHORT).show();

                        int li_count = li_count_count(ItemPosition);



//                fragAll.TimeDateReSet(view.getContext());
                        intent = new Intent(view.getContext(), ScheduleAdd.class);
                        intent.putExtra("FragScene", "FragDate_UpDate");

                        intent.putExtra("DateNumBer", dateDayList.get(ItemPosition - li_count).getId());

                        intent.putExtra("Date_y", dateDayDao.getDateDay().get(ItemPosition - li_count).getDate_y());
                        intent.putExtra("Date_m", dateDayDao.getDateDay().get(ItemPosition - li_count).getDate_m());
                        intent.putExtra("Date_d", dateDayDao.getDateDay().get(ItemPosition - li_count).getDate_d());

                        intent.putExtra("Name", dateDayDao.getDateDay().get(ItemPosition - li_count).getName());
                        intent.putExtra("Memo", dateDayDao.getDateDay().get(ItemPosition - li_count).getMemo());
                        intent.putExtra("Time_h", dateDayDao.getDateDay().get(ItemPosition - li_count).getTime_h());
                        intent.putExtra("Time_m", dateDayDao.getDateDay().get(ItemPosition - li_count).getTime_m());
                        intent.putExtra("Important", dateDayDao.getDateDay().get(ItemPosition - li_count).isImportant());

                        intent.putExtra("Sound", dateDayDao.getDateDay().get(ItemPosition - li_count).isSound());
                        intent.putExtra("Vibration", dateDayDao.getDateDay().get(ItemPosition - li_count).isVibration());
                        intent.putExtra("popup", dateDayDao.getDateDay().get(ItemPosition - li_count).isPopup());

                        //intent.putExtra("AutoOff_Time", dateDayDao.getDateDay().get(ItemPosition - li_count).getAutoOff_Time());
                        intent.putExtra("Beforehand", dateDayDao.getDateDay().get(ItemPosition - li_count).isWarning());

                        break;
                    }
                }

                view.getContext().startActivity(intent);
//                fragAll.startActivity(intent);

            }
        });

        holder.ImageView_sound.setVisibility(View.GONE);
        holder.ImageView_vibration.setVisibility(View.GONE);
        holder.ImageView_popup.setVisibility(View.GONE);

        if(listItemDara.get(position).isSound()){
            holder.ImageView_sound.setVisibility(View.VISIBLE);
        }
        if(listItemDara.get(position).isVibration()){
            holder.ImageView_vibration.setVisibility(View.VISIBLE);
        }
        if(listItemDara.get(position).isPopup()){
            holder.ImageView_popup.setVisibility(View.VISIBLE);
        }

        holder.RelativeLayout_BackGround.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ItemPosition = holder.getAdapterPosition();

                int li_count = li_count_count(ItemPosition);
                Log.d("====================", Integer.toString(li_count));

                remove(ItemPosition, li_count);


                return true;
            }
        });

    }

    int li_count_count(int ItemPosition){
        listItemDara.get(ItemPosition).getDay();
        //listItemDara.get(ItemPosition).getDay()
        int Week_count = 1;
        String test_ser = listItemDara.get(0).getDay();
        for(int i = 0; i < ItemPosition; i++){
            if(!test_ser.equals(listItemDara.get(i).getDay())){
                test_ser = listItemDara.get(i).getDay();
                Week_count++;
            }
        }

        return Week_count;
    }

    @Override
    public int getItemCount() {
        return (null != listItemDara ? listItemDara.size() : 0);
    }

    @Override
    public int getItemViewType(int position) {
        return listItemDara.get(position).getViewtype();
    }

    public void remove(int position, int Week_count){
        try {

            int ViewPageNumber = MainActivity.viewPager.getCurrentItem();

            AlertDialog.Builder DB_Delete = new AlertDialog.Builder(view.getContext());
            DB_Delete.setTitle(listItemDara.get(position).getName());
            DB_Delete.setMessage(listItemDara.get(position).getMemo());

            DB_Delete.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch (ViewPageNumber){
                        case 1:{
                            if(fragAll.test(position)) {
                                listItemDara.remove(position);

                                notifyItemRemoved(position);//?????????

                                AllDayList = allDayDao.getAllDay();

                                AllDay allDay = new AllDay();

                                allDay.setId(AllDayList.get(position).getId());
                                allDayDao.setDelete(allDay);

                            }
                            break;
                        }
                        case 2:{
                            if(fragWeek.test(position)) {
                                listItemDara.remove(position);

                                notifyItemRemoved(position);//?????????

                                WeekDayList = weekDayDao.getWeekDay();

                                WeekDay weekDay = new WeekDay();

                                weekDay.setId(WeekDayList.get(position - Week_count).getId());//
                                weekDayDao.setDelete(weekDay);

                            }
                            break;
                        }
                        case 3:{
                            if(fragDate.test(position)){
                                listItemDara.remove(position);

                                notifyItemRemoved(position);//?????????

                                dateDayList = dateDayDao.getDateDay();

                                DateDay dateDay = new DateDay();
                                dateDay.setId(dateDayList.get(position - Week_count).getId());
                                //dateDay.setId(dateDayList.get(position).getId());
                                dateDayDao.setDelete(dateDay);
                            }
                            break;
                        }
                    }
                }
            });
            DB_Delete.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            DB_Delete.show();
        }catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout RelativeLayout_BackGround;

        TextView Name;
        TextView Memo;
        TextView Time;
        TextView AmPm;
        ImageView important;
        TextView TextView_Day;

        ImageView ImageView_sound, ImageView_vibration, ImageView_popup;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            RelativeLayout_BackGround = itemView.findViewById(R.id.RelativeLayout_BackGround);

            Name = itemView.findViewById(R.id.TextView_Name);
            Memo = itemView.findViewById(R.id.TextView_Memo);
            Time = itemView.findViewById(R.id.TextView_Time);
            AmPm = itemView.findViewById(R.id.TextView_Time_AmPm);
            important = itemView.findViewById(R.id.ImageView_important);
            TextView_Day = itemView.findViewById(R.id.TextView_Day);

            ImageView_sound = itemView.findViewById(R.id.ImageView_sound);
            ImageView_vibration = itemView.findViewById(R.id.ImageView_vibration);
            ImageView_popup = itemView.findViewById(R.id.ImageView_popup);
        }
    }

    public class CustomViewHolder_label extends RecyclerView.ViewHolder {
        TextView TextView_Day;
        RelativeLayout RelativeLayout_divisionLine;
        public CustomViewHolder_label(View itemView){
            super(itemView);

            RelativeLayout_divisionLine = itemView.findViewById(R.id.RelativeLayout_divisionLine);
            TextView_Day = itemView.findViewById(R.id.TextView_Day);


        }
    }

}
