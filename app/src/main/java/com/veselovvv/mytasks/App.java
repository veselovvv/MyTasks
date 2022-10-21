package com.veselovvv.mytasks;

import android.app.Application;
import androidx.room.Room;
import com.veselovvv.mytasks.data.AppDatabase;
import com.veselovvv.mytasks.data.TaskDao;

public class App extends Application {
    private static final String DATABASE_NAME = "app-db-name";

    private static App instance;
    private TaskDao taskDao;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        AppDatabase database =
                Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries()
                .build();
        taskDao = database.taskDao();
    }

    public TaskDao getTaskDao() {
        return taskDao;
    }

    public static App getInstance() {
        return instance;
    }
}
