package com.study.yaodh.androidstudy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.utils.IntentManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        ListView listView = (ListView) findViewById(R.id.listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1,
                new String[]{
                        "TextView属性",
                        "Notification",
                        "RecyclerView",
                        "Navigation Drawer",
                        "ProgressBar",
                        "ListActivity",
                        "Share",
                        "TextSwitcher"
                });
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position) {
                    case 0:
                        IntentManager.startTextViewActivity(MainActivity.this);
                        break;
                    case 1:
                        IntentManager.startNotificationActivity(MainActivity.this);
                        break;
                    case 2:
                        IntentManager.startRecyclerViewActivity(MainActivity.this);
                        break;
                    case 3:
                        IntentManager.startDrawerActivity(MainActivity.this);
                        break;
                    case 4:
                        IntentManager.startProgressbarActivity(MainActivity.this);
                        break;
                    case 5:
                        IntentManager.startListActivity(MainActivity.this);
                        break;
                    case 6:
                        IntentManager.startShareActivity(MainActivity.this);
                        break;
                    case 7:
                        Intent intent = new Intent(MainActivity.this, TextSwitcherActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }
}
