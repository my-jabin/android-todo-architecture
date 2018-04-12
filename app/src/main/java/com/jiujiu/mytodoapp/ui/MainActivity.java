package com.jiujiu.mytodoapp.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jiujiu.mytodoapp.R;
import com.jiujiu.mytodoapp.ui.statistic.StatisticActivity;
import com.jiujiu.mytodoapp.ui.taskDetail.TaskDetailActivity;
import com.jiujiu.mytodoapp.ui.taskDetail.TaskDetailFragment;
import com.jiujiu.mytodoapp.ui.tasks.TasksViewModel;
import com.jiujiu.mytodoapp.Utils.ViewModelFactory;
import com.jiujiu.mytodoapp.ui.addeditTask.AddEditTaskActivity;
import com.jiujiu.mytodoapp.ui.tasks.TasksFragment;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private TasksViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolBar();
        setupUIComponent();
        setupFragment();
        setupViewModel();
    }

    private void setupViewModel() {
        ViewModelProvider.Factory factory = ViewModelFactory.getInstance(getApplication());
        mViewModel =  ViewModelProviders.of(this,factory).get(TasksViewModel.class);
        mViewModel.getNewTaskEvent().observe(this,aVoid -> {
            Intent intent = new Intent(this, AddEditTaskActivity.class);
            startActivity(intent);
        });
        mViewModel.getOpenTaskEvent().observe(this,id -> {
            Intent intent = new Intent(this, TaskDetailActivity.class);
            intent.putExtra(TaskDetailFragment.TASKDETAILID,id);
            startActivity(intent);
        });
    }

    private void setupFragment() {
       TasksFragment fragment =  TasksFragment.newInstance();
       getSupportFragmentManager().beginTransaction().replace(R.id.content_container,fragment,null).commit();
    }

    private void setupUIComponent() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.list_navigation_menu_item:
                    break;
                case R.id.statistics_navigation_menu_item:
                    Intent intent = new Intent(MainActivity.this, StatisticActivity.class);
                    startActivity(intent);
                    break;
            }
            item.setChecked(true);
            mDrawerLayout.closeDrawers();
            return true;
        });
       FloatingActionButton fab =  findViewById(R.id.fab);
       fab.setOnClickListener(v -> mViewModel.addNewTask());
    }

    private void setupToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionbar.setTitle(getResources().getString(R.string.todos));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
