package com.jiujiu.mytodoapp.ui.statistic;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jiujiu.mytodoapp.R;
import com.jiujiu.mytodoapp.TodoApp;
import com.jiujiu.mytodoapp.db.DataRepository;

public class StatisticViewModel extends AndroidViewModel {
    private static final String TAG = "StatisticViewModel";

    private DataRepository mRepository;
    private LiveData<Integer> numberOfCompleted;
    private LiveData<Integer> numberOfActive;
    private MediatorLiveData<Boolean> empty = new MediatorLiveData<>();
    private LiveData<String> activieStatistic;
    private LiveData<String> completedStatistic;


    public StatisticViewModel(@NonNull Application application) {
        super(application);
        mRepository = ((TodoApp) application).getDataRepository();

        numberOfCompleted = mRepository.getNumberOfCompleted();
        numberOfActive = mRepository.getNumberOfActive();

        empty.addSource(numberOfActive, integer -> empty.setValue(isTaskEmpty()));
        empty.addSource(numberOfCompleted, integer -> empty.setValue(isTaskEmpty()));

        activieStatistic = Transformations.map(numberOfActive, number -> String.format("%s: %d", application.getResources().getString(R.string.string_task_active), number));
        completedStatistic = Transformations.map(numberOfCompleted, number -> String.format("%s: %d", application.getResources().getString(R.string.string_task_completed), number));

//        activieStatistic.addSource(numberOfActive, number -> String.format("%s: %d", application.getResources().getString(R.string.string_task_active), number));
//        completedStatistic.addSource(numberOfCompleted, number -> String.format("%s: %d", application.getResources().getString(R.string.string_task_completed), number));
    }

    private boolean isTaskEmpty() {
        Integer active = numberOfActive.getValue();
        Integer completed = numberOfCompleted.getValue();
        boolean result = active != null && completed != null && active == 0 && completed == 0;
        return result;
    }

    public LiveData<Boolean> isEmpty() {
        return empty;
    }

    public LiveData<String> getActiveStatistic() {
        return activieStatistic;
    }

    public LiveData<String> getCompletedStatistic() {
        return completedStatistic;
    }

}
