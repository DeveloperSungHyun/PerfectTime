package com.example.perfecttime.RoomDataBase.AllDay_DataBase;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AllDayDao {

    @Insert
    void setInsert(AllDay allDay);

    @Update
    void setUpDate(AllDay allDay);

    @Delete
    void setDelete(AllDay allDay);

    @Query("SELECT * FROM AllDay ORDER BY Time_h, Time_m, Name")
    List<AllDay> getAllDay();
}
