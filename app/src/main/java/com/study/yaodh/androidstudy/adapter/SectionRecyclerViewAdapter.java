package com.study.yaodh.androidstudy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.model.ListItem;

import java.util.List;

/**
 * Created by yaodh on 2016/7/6.
 */
public class SectionRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private List<ListItem> mList;

    public SectionRecyclerViewAdapter(Context context, List<ListItem> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == ListItem.TYPE_HEADER) {
            View header = LayoutInflater.from(mContext).inflate(R.layout.list_header, parent, false);
            return new HeaderViewHolder(header);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
            return new SimpleViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ListItem.TYPE_HEADER:
                HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
                headerViewHolder.tvTitle.setText(mList.get(position).getTitle());
                break;
            case ListItem.TYPE_ITEM:
                SimpleViewHolder simpleViewHolder = (SimpleViewHolder) holder;
                simpleViewHolder.tvTitle.setText(mList.get(position).getTitle());
                simpleViewHolder.tvContent.setText(mList.get(position).getContent());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.title);
        }
    }

    static class SimpleViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvContent;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.title);
            tvContent = (TextView) itemView.findViewById(R.id.content);
        }
    }
}
