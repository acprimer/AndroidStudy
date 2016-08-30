package com.study.yaodh.androidstudy.activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.SparseArray;
import android.view.View;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.adapter.CardAdapter;
import com.study.yaodh.androidstudy.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CardActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private CardAdapter mAdapter;
    private List<String> list;
    private SparseArray<Boolean> marked;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_card;
    }

    @Override
    protected void initContent() {
        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        marked = new SparseArray<>();
        String[] array = getResources().getStringArray(R.array.fruit_array);
        list.addAll(Arrays.asList(array));
        recyclerView.setAdapter(mAdapter = new CardAdapter(this, list, marked));

        Drawable drawable = getResources().getDrawable(R.drawable.ic_delete_black_24dp);
        final Bitmap bitmap = Utils.getBitmapFromDrawable(drawable);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                for (int i = 0; i < marked.size(); i++) {
                    mAdapter.notifyItemRemoved(marked.keyAt(i));
                    marked.remove(marked.keyAt(i));
                }
            }
        });

        ItemTouchHelper.Callback mCallback = new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(list, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(list, i, i - 1);
                    }
                }
                mAdapter.notifyItemMoved(fromPosition, toPosition);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                marked.put(position, true);
                mAdapter.notifyItemChanged(position);
//                list.remove(position);
//                mAdapter.notifyItemRemoved(position);
            }

            @Override
            public void onChildDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                final int position = viewHolder.getAdapterPosition();
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    View itemView = viewHolder.itemView;
                    Paint p = new Paint();
                    p.setColor(getResources().getColor(R.color.colorAccent));
                    if(dX > 0) {
                        // swiped right: --->
                        float right = Math.min(itemView.getLeft() + dX, itemView.getRight());
                        canvas.drawRect(itemView.getLeft(), itemView.getTop(), right, itemView.getBottom(), p);
                        int size = bitmap.getHeight();
                        int padding = (itemView.getHeight() - size) / 2;
                        canvas.drawBitmap(bitmap, itemView.getLeft() + padding, itemView.getTop() + padding, p);
//                        if(itemView.getLeft() + dX > itemView.getRight()) {
//                            LinearLayout layout = (LinearLayout) LayoutInflater.from(CardActivity.this).inflate(R.layout.card_swiped_item, (ViewGroup) itemView.getParent(), false);
//                            layout.measure(itemView.getWidth(), itemView.getHeight());
//                            layout.layout(itemView.getLeft(), itemView.getTop(), itemView.getRight(), itemView.getBottom());
//                            canvas.translate(itemView.getLeft(), itemView.getTop());
//                            layout.draw(canvas);
//                            TextView tvUndo = (TextView) layout.findViewById(R.id.undo);
//                            tvUndo.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    mAdapter.notifyItemChanged(position);
//                                }
//                            });
//                        } else {
//                            int size = bitmap.getHeight();
//                            int padding = (itemView.getHeight() - size) / 2;
//                            canvas.drawBitmap(bitmap, itemView.getLeft() + padding, itemView.getTop() + padding, p);
//                        }
                    } else {
                        // swiped left: <---
                        float left = Math.max(itemView.getRight() + dX, itemView.getLeft());
                        canvas.drawRect(left, itemView.getTop(), itemView.getRight(), itemView.getBottom(), p);
                        int size = bitmap.getHeight();
                        int padding = (itemView.getHeight() - size) / 2;
                        canvas.drawBitmap(bitmap, itemView.getRight() - size - padding, itemView.getTop() + padding, p);
                    }
                }
                super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

}
