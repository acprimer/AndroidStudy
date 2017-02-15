package com.study.yaodh.androidstudy.activity;

import com.study.yaodh.androidstudy.R;

public class BottomNavActivity extends BaseActivity {
//    private BottomNavigationView bottomNavigationView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bottom_nav;
    }

    @Override
    protected void initContent() {
        super.initContent();
//        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Toast.makeText(BottomNavActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });
    }
}
