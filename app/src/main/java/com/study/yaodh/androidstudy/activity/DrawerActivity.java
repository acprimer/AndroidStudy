package com.study.yaodh.androidstudy.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.utils.IntentManager;

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

        initDrawer();
    }

    private void initDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mLeftDrawer = (NavigationView) findViewById(R.id.left_drawer);
        disableNavigationViewScrollbars(mLeftDrawer);
        // 默认选中的菜单项
        mLeftDrawer.setCheckedItem(R.id.checked_id);
        testNavMenu();
        mLeftDrawer.setNavigationItemSelectedListener(this);
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
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        // drawer菜单的switch button
        SwitchCompat switchCompat = (SwitchCompat) mLeftDrawer.getMenu().findItem(R.id.notification).getActionView();
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Snackbar.make(mDrawerLayout, isChecked ? "opened" : "closed", Snackbar.LENGTH_SHORT).show();
            }
        });

        // drawer菜单的消息数提示小红点
        TextView tvMessage = (TextView) mLeftDrawer.getMenu().findItem(R.id.message).getActionView();
        tvMessage.setText("10+");
    }

    private void testNavMenu() {
        Menu menu = mLeftDrawer.getMenu();
        for (int i = 0, size = menu.size(); i < size; i++) {
            System.out.println(menu.getItem(i));
        }
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

    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if(navigationView != null) {
            NavigationMenuView menuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (menuView != null) {
                menuView.setVerticalScrollBarEnabled(false);
            }
        }
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
//        mLeftDrawer.setCheckedItem(item.getItemId());
//        item.setChecked(true);
        switch (item.getItemId()) {
            case R.id.notification:
                Toast.makeText(this, "Notification", Toast.LENGTH_LONG).show();
                break;
            case R.id.offline:
                IntentManager.startListActivity(this);
            default:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;
        }
        return true;
    }
}
