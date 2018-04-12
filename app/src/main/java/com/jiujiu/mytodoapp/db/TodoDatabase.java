package com.jiujiu.mytodoapp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.jiujiu.mytodoapp.db.model.Task;

@Database(entities = Task.class, version = 1)
public abstract class TodoDatabase extends RoomDatabase {

    private static TodoDatabase mInstance;
    private final static String DATABASE = "tasks.db";

    public abstract TaskDao taskDao();

    public static TodoDatabase getInstance(Context context) {
        synchronized (TodoDatabase.class) {
            if (mInstance == null) {
        mInstance = Room.databaseBuilder(context, TodoDatabase.class, DATABASE).build();
            }
        }
        return mInstance;
    }

}
