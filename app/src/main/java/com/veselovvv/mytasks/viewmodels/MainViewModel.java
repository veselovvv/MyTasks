package com.veselovvv.mytasks.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.veselovvv.mytasks.App;
import com.veselovvv.mytasks.models.Task;
import java.util.List;

public class MainViewModel extends ViewModel {

    private LiveData<List<Task>> taskLiveData = App.getInstance().getTaskDao().getAllLiveData();

    public LiveData<List<Task>> getTaskLiveData() { return taskLiveData; }
}
