package com.study.yaodh.androidstudy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.study.yaodh.androidstudy.R;

import java.util.List;

/**
 * Created by yaodh on 16/7/19.
 */
public class ListAdapter extends BaseAdapter {
    private List<String> mList;
    private Context mContext;

    public ListAdapter(Context context, List<String>list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        TextView tvTitle = (TextView) view.findViewById(R.id.title);
        tvTitle.setText(mList.get(position));
        return view;
    }
}
