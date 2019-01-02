package com.study.yaodh.androidstudy.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.activity.fb.FBAdActivity;
import com.study.yaodh.androidstudy.aidl.IDictionaryManager;
import com.study.yaodh.androidstudy.service.DictionaryManagerService;

public class MainActivity extends BaseActivity {
    private Class[] activities = new Class[]{
            TextViewActivity.class,
            AutofitTextActivity.class,
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
            HandlerThreadActivity.class,
            LoaderActivity.class,
            FBAdActivity.class,
            EventBusActivity.class,
            TouchEventActivity.class,
            BluetoothActivity.class,
            LiulishuoDownloadActivity.class,
            SpinnerActivity.class,
            CalendarActivity.class,
            ViewPagerActivity.class,
            ListViewActivity.class,
            ViewPagerActivity.class,
            LinearLayoutActivity.class,
            AnimationActivity.class,
            VectorActivity.class,
            ClassLoaderActivity.class,
            ViewActivity.class,
            ShapeActivity.class,
            DialogActivity.class,
            ShadowActivity.class,
            ViewStubActivity.class,
            ATaskActivity.class,
            MotionEventActivity.class,
            SparseArrayActivity.class,
            TabActivity.class,
            TranslucentActivity.class,
            SharedElementTransitionActivity.class,
            BitmapActivity.class,
            PicassoActivity.class
    };
    private String[] titles = new String[]{
            "TextView属性",
            "自适应文本",
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
            "HandlerThread",
            "Loader",
            "Facebook ad",
            "EventBus",
            "TouchEvent",
            "Bluetooth",
            "Liulishuo Download",
            "Spinner",
            "Calendar",
            "ViewPager",
            "ListView",
            "ViewPager",
            "LinearLayout",
            "动画",
            "Vector",
            "ClassLoader",
            "自定义View",
            "Shape",
            "Dialog",
            "阴影",
            "ViewStub",
            "SingleTask",
            "触摸反馈",
            "SparseArray",
            "Tab",
            "Translucent",
            "共享元素",
            "Bitmap",
            "Picasso"
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

//        startAndBindService();
    }

    private IDictionaryManager iDictionaryManager;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iDictionaryManager = IDictionaryManager.Stub.asInterface(service);
            Log.d(TAG, "onServiceConnected: 连接成功");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: 连接失败");
        }
    };
    private void startAndBindService() {
        Intent service = new Intent(this, DictionaryManagerService.class);
        bindService(service, serviceConnection, Context.BIND_AUTO_CREATE);
    }
}
