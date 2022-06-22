package com.example.perfecttime.RoomDataBase.WeekDataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {WeekDay.class}, version = 4)
public abstract class WeekDataBase extends RoomDatabase {

    public abstract WeekDayDao weekDayDao();
}
