package com.jiujiu.mytodoapp.Utils;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.jiujiu.mytodoapp.ui.statistic.StatisticViewModel;
import com.jiujiu.mytodoapp.ui.taskDetail.TaskDetailViewModel;
import com.jiujiu.mytodoapp.ui.tasks.TasksViewModel;
import com.jiujiu.mytodoapp.ui.addeditTask.AddEditTaskViewModel;

public class ViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {

    private Application application;

    public ViewModelFactory(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(TasksViewModel.class)){
            return (T)new TasksViewModel(this.application);
        }else if(modelClass.isAssignableFrom(AddEditTaskViewModel.class)){
            return (T) new AddEditTaskViewModel(this.application);
        }else if(modelClass.isAssignableFrom(TaskDetailViewModel.class)){
            return (T) new TaskDetailViewModel(this.application);
        }else  if(modelClass.isAssignableFrom(StatisticViewModel.class)){
            return (T) new StatisticViewModel(this.application);
        }
        throw  new IllegalArgumentException("Unknow viewmodel class" + modelClass.getName());
    }
}
