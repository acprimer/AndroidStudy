package com.study.yaodh.androidstudy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.study.yaodh.androidstudy.R;

import java.util.List;

/**
 * Created by yaodh on 2016/7/19.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHold>{
    private Context mContext;
    private List<String> titles;

    public CardAdapter(Context mContext, List<String> titles) {
        this.mContext = mContext;
        this.titles = titles;
    }

    @Override
    public CardViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_item, parent, false);
        return new CardViewHold(view);
    }

    @Override
    public void onBindViewHolder(CardViewHold holder, int position) {
        holder.tvTitle.setText(titles.get(position));
    }

    @Override
    public int getItemCount() {
        return titles == null ? 0 : titles.size();
    }

    static class CardViewHold extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        public CardViewHold(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
