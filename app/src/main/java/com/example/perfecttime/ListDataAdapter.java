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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {//리스트 시간순 배열은 여기서

        fragAll = new FragAll();
        fragWeek = new FragWeek();
        fragDate = new FragDate();

        if(holder instanceof CustomViewHolder){
//            ((CustomViewHolder) holder).
            ListItemView(((CustomViewHolder) holder), position);
        }else{
            ((CustomViewHolder_label) holder).TextView_Day.setText("일");
        }



    }

    void ListItemView(CustomViewHolder holder, int position){
        String Time_Str;
        Time_Str = listItemDara.get(position).getTime_h() + " : " + listItemDara.get(position).getTime_m();
        holder.Name.setText(listItemDara.get(position).getName());
        holder.Memo.setText(listItemDara.get(position).getMemo());
        holder.Time.setText(Time_Str);

        holder.TextView_Day.setText(listItemDara.get(position).getDay());

        if(listItemDara.get(position).isAmPm()) //true = 오전 | false = 오후
            holder.AmPm.setText("오전");
        else
            holder.AmPm.setText("오후");

        if(listItemDara.get(position).isImportant())
            holder.important.setImageResource(R.drawable.star_true);
        else
            holder.important.setImageResource(R.drawable.star_false);

        holder.itemView.setTag(position);

        holder.RelativeLayout_BackGround.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = MainActivity.viewPager.getCurrentItem();

                ItemPosition = holder.getAdapterPosition();
                AllDayList = allDayDao.getAllDay();
                Toast.makeText(view.getContext(), "test" + ItemPosition, Toast.LENGTH_SHORT).show();

//                fragAll.TimeDateReSet(view.getContext());
                Intent intent = new Intent(view.getContext(), ScheduleAdd.class);
                intent.putExtra("FragScene", "FragAll_UpDate");
                intent.putExtra("Day", "매일");

                intent.putExtra("DateNumBer", AllDayList.get(ItemPosition).getId());

                intent.putExtra("Name", allDayDao.getAllDay().get(ItemPosition).getName());
                intent.putExtra("Memo", allDayDao.getAllDay().get(ItemPosition).getMemo());
                intent.putExtra("Time_h", allDayDao.getAllDay().get(ItemPosition).getTime_h());
                intent.putExtra("Time_m", allDayDao.getAllDay().get(ItemPosition).getTime_m());
                intent.putExtra("Important", allDayDao.getAllDay().get(ItemPosition).isImportant());

                intent.putExtra("Sound", allDayDao.getAllDay().get(ItemPosition).isSound());
                intent.putExtra("Vibration", allDayDao.getAllDay().get(ItemPosition).isVibration());
                intent.putExtra("Notification", allDayDao.getAllDay().get(ItemPosition).isNotification());

                intent.putExtra("AutoOff_Time", allDayDao.getAllDay().get(ItemPosition).getAutoOff_Time());
                intent.putExtra("Warning", allDayDao.getAllDay().get(ItemPosition).isWarning());
                intent.putExtra("Holiday", allDayDao.getAllDay().get(ItemPosition).isHoliday());

                view.getContext().startActivity(intent);
//                fragAll.startActivity(intent);

            }
        });

        holder.RelativeLayout_BackGround.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ItemPosition = holder.getAdapterPosition();

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
                Log.d("====================", Integer.toString(Week_count));
                remove(ItemPosition, Week_count);

                return true;
            }
        });
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

            DB_Delete.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch (ViewPageNumber){
                        case 1:{
                            if(fragAll.test(position)) {
                                listItemDara.remove(position);

                                notifyItemRemoved(position);//초기화

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

                                notifyItemRemoved(position);//초기화

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

                                notifyItemRemoved(position);//초기화

                                dateDayList = dateDayDao.getDateDay();

                                DateDay dateDay = new DateDay();
                                dateDay.setId(dateDayList.get(position).getId());
                                //dateDay.setId(dateDayList.get(position).getId());
                                dateDayDao.setDelete(dateDay);
                            }
                            break;
                        }
                    }
                }
            });
            DB_Delete.setNegativeButton("취소", new DialogInterface.OnClickListener() {
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

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            RelativeLayout_BackGround = itemView.findViewById(R.id.RelativeLayout_BackGround);

            Name = itemView.findViewById(R.id.TextView_Name);
            Memo = itemView.findViewById(R.id.TextView_Memo);
            Time = itemView.findViewById(R.id.TextView_Time);
            AmPm = itemView.findViewById(R.id.TextView_Time_AmPm);
            important = itemView.findViewById(R.id.ImageView_important);
            TextView_Day = itemView.findViewById(R.id.TextView_Day);
        }
    }

    public class CustomViewHolder_label extends RecyclerView.ViewHolder {
        TextView TextView_Day;
        public CustomViewHolder_label(View itemView){
            super(itemView);

            TextView_Day = itemView.findViewById(R.id.TextView_Day);

        }
    }

}
