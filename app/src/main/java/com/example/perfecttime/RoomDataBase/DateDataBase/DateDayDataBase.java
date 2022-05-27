package com.example.perfecttime.RoomDataBase.DateDataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {DateDay.class}, version = 1)
public abstract class DateDayDataBase extends RoomDatabase {

    public abstract DateDayDao dateDayDao();

}
