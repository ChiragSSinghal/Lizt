package net.atmode.lizt.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import net.atmode.lizt.dao.LiztDao;
import net.atmode.lizt.entity.Lizt;

@Database(entities = {Lizt.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class LiztDatabase extends RoomDatabase {
    public abstract LiztDao liztDao();

    private static volatile LiztDatabase INSTANCE;

    private static final String DATABASE_NAME = "list_database";

    public static LiztDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LiztDatabase.class) {
                if (INSTANCE == null) {
                    //create database
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LiztDatabase.class,DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
