package com.example.perfecttime.RoomDataBase.WeekDataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WeekDayDao {

    @Insert
    void setInsert(WeekDay weekDay);

    @Update
    void setUpDate(WeekDay weekDay);

    @Delete
    void setDelete(WeekDay weekDay);

    @Query("SELECT * FROM WeekDay ORDER BY Week, Time_h, Time_m, Name")
    List<WeekDay> getWeekDay();
}
