package com.example.perfecttime.RoomDataBase.AllDay_DataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {AllDay.class}, version = 2)
public abstract class AllDayDataBase extends RoomDatabase {

    public abstract AllDayDao allDayDao();
}
