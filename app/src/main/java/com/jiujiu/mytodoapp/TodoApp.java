package com.jiujiu.mytodoapp;

import android.app.Application;

import com.jiujiu.mytodoapp.Utils.AppExecutor;
import com.jiujiu.mytodoapp.db.DataRepository;
import com.jiujiu.mytodoapp.db.TruthDataSource;
import com.jiujiu.mytodoapp.db.TodoDatabase;

public class TodoApp extends Application {


    private AppExecutor mExecutor;
    private TodoDatabase mDatabase;
    private TruthDataSource mLocalDataSource;

    @Override
    public void onCreate() {
        super.onCreate();
        mExecutor = new AppExecutor();
        mDatabase = TodoDatabase.getInstance(this);
        mLocalDataSource = TruthDataSource.getInstance(mDatabase.taskDao(), mExecutor);
    }


    public DataRepository getDataRepository(){
        return DataRepository.getInstance(mLocalDataSource);
    }
}
