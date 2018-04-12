package com.jiujiu.mytodoapp.ui.addeditTask;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.jiujiu.mytodoapp.R;
import com.jiujiu.mytodoapp.Utils.ViewModelFactory;

public class AddEditTaskActivity extends AppCompatActivity {

    private AddEditTaskViewModel mViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        setupToolBar();
        setupFragment();
        setupViewModel();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            mViewModel.callSaveTaskEvent();
        });

    }

    private void setupViewModel() {
        mViewModel = obtainViewModel(this);
        //mViewModel.getSaveTaskEvent().observe(this,o -> mViewModel.saveTask());
    }

    public static AddEditTaskViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelProvider.Factory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(AddEditTaskViewModel.class);
    }


    private void setupFragment() {
        AddeditTaskFragment fragment = (AddeditTaskFragment) getSupportFragmentManager().findFragmentById(R.id.content_container);
        if(fragment == null){
             fragment = AddeditTaskFragment.newInstance();
             Bundle bundle = new Bundle();
             bundle.putString(AddeditTaskFragment.ARGUMENT_ADD_EDIT_TASK_ID,getIntent().getStringExtra(AddeditTaskFragment.ARGUMENT_ADD_EDIT_TASK_ID));
             fragment.setArguments(bundle);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.content_container, fragment, null).commit();
    }


    private void setupToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
