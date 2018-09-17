package com.study.yaodh.androidstudy.activity;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.databinding.ActivityBottomSheetBinding;

import java.util.ArrayList;
import java.util.List;

public class BottomSheetActivity extends BaseActivity {
    private ActivityBottomSheetBinding binding;
    private List<String> data;
    private BottomSheetDialog dialog;

    @Override
    protected int getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bottom_sheet);
        return 0;
    }

    @Override
    protected void initContent() {
        getData();
    }

    public void showBottomSheetDialog(View view) {
        if (dialog == null) {
//            dialog = new BottomSheetDialog(this, R.style.MyBottomSheetDialog);
            dialog = new BottomSheetDialog(this);
            View layout = LayoutInflater.from(this).inflate(R.layout.bottom_dialog, null);
//        BottomSheetBehavior behavior = BottomSheetBehavior.from(layout);
//        behavior.setPeekHeight(100);
            RecyclerView listView = layout.findViewById(R.id.list);
            listView.setLayoutManager(new LinearLayoutManager(this));
            listView.setAdapter(new ItemAdapter());
            dialog.setContentView(layout);
        }
        dialog.show();
    }

    private void getData() {
        data = new ArrayList<>();
        data.add("A");
        data.add("B");
        data.add("C");
        data.add("D");
        data.add("E");
        data.add("A");
        data.add("B");
        data.add("C");
        data.add("D");
        data.add("E");
        data.add("A");
        data.add("B");
        data.add("C");
        data.add("D");
        data.add("E");
        data.add("A");
        data.add("B");
        data.add("C");
        data.add("D");
        data.add("E");
        data.add("E");
        data.add("X");
        data.add("Y");
        data.add("Z");
    }

    class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ItemViewHolder(getLayoutInflater().inflate(R.layout.layout_item, null));
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int position) {
            itemViewHolder.tv.setText(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView tv;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.text);
        }
    }
}
