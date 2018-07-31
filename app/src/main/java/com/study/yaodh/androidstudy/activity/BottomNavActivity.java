package com.study.yaodh.androidstudy.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.Toast;

import com.study.yaodh.androidstudy.R;

public class BottomNavActivity extends BaseActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bottom_nav;
    }

    @Override
    protected void initContent() {
        super.initContent();
        bottomNavigationView = findViewById(R.id.navigation);
//        disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Toast.makeText(BottomNavActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

//    public static void disableShiftMode(BottomNavigationView view) {
//        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
//        try {
//            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
//            shiftingMode.setAccessible(true);
//            shiftingMode.setBoolean(menuView, false);
//            shiftingMode.setAccessible(false);
//            for (int i = 0; i < menuView.getChildCount(); i++) {
//                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
//                //noinspection RestrictedApi
//                item.setShiftingMode(false);
//                // set once again checked value, so view will be updated
//                //noinspection RestrictedApi
//                item.setChecked(item.getItemData().isChecked());
//            }
//        } catch (NoSuchFieldException e) {
//            Log.e("BNVHelper", "Unable to get shift mode field", e);
//        } catch (IllegalAccessException e) {
//            Log.e("BNVHelper", "Unable to change value of shift mode", e);
//        }
//    }
}
