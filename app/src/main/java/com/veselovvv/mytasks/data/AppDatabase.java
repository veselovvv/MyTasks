package com.veselovvv.mytasks.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.veselovvv.mytasks.Task;

@Database(entities = {Task.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
}
