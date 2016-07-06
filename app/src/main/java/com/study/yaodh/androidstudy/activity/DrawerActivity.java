package com.study.yaodh.androidstudy.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.study.yaodh.androidstudy.R;

/**
 * Created by yaodh on 2016/6/27.
 */
public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout mDrawerLayout;
    private NavigationView mLeftDrawer;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        initToolbar();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mLeftDrawer = (NavigationView) findViewById(R.id.left_drawer);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mLeftDrawer.setNavigationItemSelectedListener(this);
//        String s = "";
//        for (int i = 0; i < mLeftDrawer.getMenu().getItem(1).getSubMenu().getItem(0).; i++) {
//            s += mLeftDrawer.getMenu().getItem(i).getTitle() + " | ";
//        }
//        Toast.makeText(DrawerActivity.this, "Menu " + s, Toast.LENGTH_LONG).show();
        SwitchCompat switchCompat = (SwitchCompat) mLeftDrawer.getMenu().getItem(1).getSubMenu().getItem(0).getActionView();
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(DrawerActivity.this, "Notification " + isChecked, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notification:
                Toast.makeText(this, "Notification", Toast.LENGTH_LONG).show();
                break;
            default:
                item.setChecked(true);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;
        }
        return true;
    }
}
