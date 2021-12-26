package com.veselovvv.mytasks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
import com.veselovvv.mytasks.Task;
import com.veselovvv.mytasks.App;

public class MainViewModel extends ViewModel {

    private LiveData<List<Task>> taskLiveData = App.getInstance().getTaskDao().getAllLiveData();

    public LiveData<List<Task>> getTaskLiveData() {
        return taskLiveData;
    }
}
