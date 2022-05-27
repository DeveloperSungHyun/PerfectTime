package com.example.perfecttime.RoomDataBase.DateDataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DateDayDao {
    @Insert
    void setInsert(DateDay dateDay);

    @Update
    void setUpDate(DateDay dateDay);

    @Delete
    void setDelete(DateDay dateDay);

    @Query("SELECT * FROM DateDay ORDER BY Time_h, Time_m, Name")
    List<DateDay> getDateDay();
}
