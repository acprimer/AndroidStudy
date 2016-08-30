package com.study.yaodh.androidstudy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.study.yaodh.androidstudy.R;

import java.util.List;

/**
 * Created by yaodh on 2016/7/19.
 */
public class CardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private List<String> titles;
    private SparseArray<Boolean> mMarked;
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_SWIPED = 1;

    public CardAdapter(Context mContext, List<String> titles, SparseArray<Boolean> map) {
        this.mContext = mContext;
        this.titles = titles;
        mMarked = map;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_NORMAL:
                view = LayoutInflater.from(mContext).inflate(R.layout.card_item, parent, false);
                return new CardViewHold(view);
            case TYPE_SWIPED:
                view = LayoutInflater.from(mContext).inflate(R.layout.card_swiped_item, parent, false);
                return new CardSwipedViewHold(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case TYPE_NORMAL:
                CardViewHold cardViewHold = (CardViewHold) holder;
                cardViewHold.tvTitle.setText(titles.get(position));
                break;
            case TYPE_SWIPED:
                CardSwipedViewHold cardSwipedViewHold = (CardSwipedViewHold) holder;
                cardSwipedViewHold.tvUndo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMarked.remove(position);
                        notifyItemChanged(position);
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return titles == null ? 0 : titles.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mMarked.get(position) == null ? TYPE_NORMAL : TYPE_SWIPED;
    }

    static class CardViewHold extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        public CardViewHold(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.title);
        }
    }

    static class CardSwipedViewHold extends RecyclerView.ViewHolder {
        private TextView tvUndo;
        public CardSwipedViewHold(View itemView) {
            super(itemView);
            tvUndo = (TextView) itemView.findViewById(R.id.undo);
        }
    }
}
