package com.study.yaodh.androidstudy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;

import com.study.yaodh.androidstudy.R;

import java.util.HashMap;

import hugo.weaving.DebugLog;

public class SparseArrayActivity extends AppCompatActivity {

    private static final int COUNT = 10_000;
    private int[] keys = new int[COUNT];
    private Object[] values = new Object[COUNT];
    private HashMap<Integer, Object> hashMap = new HashMap<>();
    private SparseArray<Object> sparseArray = new SparseArray<>();

    {
        for (int i = 0; i < keys.length; i++) {
            keys[i] = i;
            values[i] = String.valueOf(i);

            hashMap.put(keys[i], new Object());
            sparseArray.put(keys[i], new Object());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sparse_array);

        testHashMap();
        testSparseArray();
    }

    @DebugLog
    private void testHashMap() {
//        for (int i = 0; i < keys.length; i++) {
//            hashMap.put(keys[i], values[i]);
//        }
//        for (int i = 0; i < keys.length; i++) {
//            hashMap.get(i);
//        }
        for (HashMap.Entry entry : hashMap.entrySet()) {
            int key = (int) entry.getKey();
            Object value = entry.getValue();
        }
    }

    @DebugLog
    private void testSparseArray() {
//        for (int i = 0; i < keys.length; i++) {
//            sparseArray.put(keys[i], values[i]);
//        }
//        for (int i = 0; i < keys.length; i++) {
//            sparseArray.get(i);
//        }
        sparseArray.delete(1);
        for (int i = 0; i < sparseArray.size(); i++) {
            int key = sparseArray.keyAt(i);
            Object value = sparseArray.valueAt(i);
        }
    }
}
