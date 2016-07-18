package com.study.yaodh.androidstudy.activity;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.adapter.ListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ActionModeActivity extends BaseActivity {
    private TextView tvPaste;
    private ListView listView;
    private ListAdapter mAdapter;
    private List<String> list;
    private ActionMode mActionMode;
    private ActionMode.Callback textCallback;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_action_mode;
    }

    @Override
    protected void initContent() {
        initCallback();
        tvPaste = (TextView) findViewById(R.id.text);
        tvPaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mActionMode != null) {
                    return;
                }
                mActionMode = startActionMode(textCallback);
                mActionMode.setTitle(tvPaste.getText());
                mActionMode.setSubtitle("Saved");
                tvPaste.setSelected(true);
            }
        });

        listView = (ListView) findViewById(android.R.id.list);
        list = new ArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        listView.setAdapter(mAdapter = new ListAdapter(this, list));
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                // mode应该是个全局的单例模式
                mode.setTitle(String.valueOf(listView.getCheckedItemCount()));
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.menu_delete, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.all:
                        boolean all = listView.getCheckedItemCount() != mAdapter.getCount();
                        if(all) {
                            for (int i = mAdapter.getCount() - 1; i >= 0; i--) {
                                listView.setItemChecked(i, true);
                            }
                        } else {
                            listView.clearChoices();
                        }
                        mode.setTitle(String.valueOf(listView.getCheckedItemCount()));
                        mAdapter.notifyDataSetChanged();
                        return true;
                    case R.id.delete:
                        for (int i = mAdapter.getCount() - 1; i >= 0; i--) {
                            if(listView.isItemChecked(i)) {
                                list.remove(i);
                            }
                        }
                        mode.finish(); // Action picked, so close the CAB
                        mAdapter.notifyDataSetChanged();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
    }

    private void initCallback() {
        textCallback = new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.menu_context, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.all:
                        Toast.makeText(ActionModeActivity.this, "Select all", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.copy:
                        Toast.makeText(ActionModeActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                mActionMode = null;
            }
        };
    }
}
