package com.jiujiu.mytodoapp.ui.statistic;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jiujiu.mytodoapp.R;
import com.jiujiu.mytodoapp.Utils.ViewModelFactory;
import com.jiujiu.mytodoapp.ui.MainActivity;

public class StatisticActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        setupToolbar();
        setupUIComponent();
        setupFragment();
    }


    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionbar.setTitle(getResources().getString(R.string.statistics_title));
    }

    public static StatisticViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelProvider.Factory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(StatisticViewModel.class);
    }


    private void setupFragment() {
        StatisticFragment fragment = StatisticFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_container, fragment, null).commit();
    }

    private void setupUIComponent() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.list_navigation_menu_item:
                    Intent intent = new Intent(StatisticActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
                case R.id.statistics_navigation_menu_item:
                    break;
            }
            item.setChecked(true);
            mDrawerLayout.closeDrawers();
            return true;
        });
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
