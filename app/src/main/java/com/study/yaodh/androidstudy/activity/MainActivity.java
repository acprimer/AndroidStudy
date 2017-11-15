package com.study.yaodh.androidstudy.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.activity.fb.FBAdActivity;

public class MainActivity extends BaseActivity {
    private Class[] activities = new Class[]{
            TextViewActivity.class,
            NotificationActivity.class,
            RecyclerViewActivity.class,
            DrawerActivity.class,
            ProgressbarActivity.class,
            ListViewLoader.class,
            ShareActivity.class,
            ImageViewActivity.class,
            TextSwitcherActivity.class,
            SwitchButtonActivity.class,
            FastScrollListActivity.class,
            SectionRecyclerViewActivity.class,
            DataBindingActivity.class,
            AnimActivity.class,
            ActionModeActivity.class,
            CoordinatorActivity.class,
            CardActivity.class,
            SearchActivity.class,
            AlertActivity.class,
            ButtonActivity.class,
            BottomSheetActivity.class,
            PermissionActivity.class,
            FileActivity.class,
            DownloadActivity.class,
            DownloadListActivity.class,
            MultiDownloadActivity.class,
            RxAndroidActivity.class,
            FlexboxLayoutActivity.class,
            ContentProviderActivity.class,
            BottomNavActivity.class,
            ToolbarActivity.class,
            TTSActivity.class,
            HandlerActivity.class,
            LoaderActivity.class,
            FBAdActivity.class,
            EventBusActivity.class,
            TouchEventActivity.class,
            BluetoothActivity.class,
            LiulishuoDownloadActivity.class,
            SpinnerActivity.class,
            CalendarActivity.class,
            ViewPagerActivity.class,
            LinearLayoutActivity.class,
            AnimationActivity.class,
            VectorActivity.class
    };
    private String[] titles = new String[]{
            "TextView属性",
            "Notification",
            "RecyclerView",
            "Navigation Drawer",
            "ProgressBar",
            "ListActivity",
            "Share",
            "ImageView",
            "TextSwitcher",
            "SwitchButton",
            "FastScrollList",
            "Section RecyclerView",
            "Data Binding",
            "Animation",
            "ActionMode",
            "CoordinatorLayout",
            "CardView",
            "SearchView",
            "AlertDialog",
            "Button",
            "Bottom Sheet",
            "Permission",
            "File",
            "Download",
            "DownloadList",
            "MultiDownload",
            "RxAndroid",
            "Flexbox Layout",
            "Content Provider",
            "BottomNavigation",
            "Toolbar",
            "TextToSpeech",
            "Handler",
            "Loader",
            "Facebook ad",
            "EventBus",
            "TouchEvent",
            "Bluetooth",
            "Liulishuo Download",
            "Spinner",
            "Calendar",
            "ViewPager",
            "LinearLayout",
            "动画",
            "Vector"
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean showBack() {
        return false;
    }

    @Override
    protected void initContent() {
        ListView listView = (ListView) findViewById(R.id.listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1,
                titles);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, activities[position]);
                startActivity(intent);
            }
        });
    }
}
