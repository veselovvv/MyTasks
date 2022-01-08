package com.veselovvv.mytasks;

import android.app.Application;
import androidx.room.Room;
import com.veselovvv.mytasks.data.AppDatabase;
import com.veselovvv.mytasks.data.TaskDao;

public class App extends Application {

    private AppDatabase database;
    private TaskDao taskDao;
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        database = Room.databaseBuilder(
            getApplicationContext(),
            AppDatabase.class, "app-db-name"
        ).allowMainThreadQueries()
        .build();

        taskDao = database.taskDao();
    }

    public AppDatabase getDatabase() { return database; }

    public void setDatabase(AppDatabase database) { this.database = database; }

    public TaskDao getTaskDao() { return taskDao; }

    public void setTaskDao(TaskDao taskDao) { this.taskDao = taskDao; }

    public static App getInstance() { return instance; }
}
