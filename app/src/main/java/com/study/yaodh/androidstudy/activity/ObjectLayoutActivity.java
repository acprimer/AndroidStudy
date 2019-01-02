package com.study.yaodh.androidstudy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ironz.unsafe.UnsafeAndroid;
import com.study.yaodh.androidstudy.R;

public class ObjectLayoutActivity extends AppCompatActivity {
    private UnsafeAndroid unsafe = new UnsafeAndroid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_layout);

        TestA obj = new TestA();
        print(obj);
    }

    private void print(TestA obj) {
        for (int i = 0; i < 12; i++) {
            System.out.printf("0x%08x\n", unsafe.getInt(obj, 4L * i));
        }
    }

    class TestA {
        int x = 0x77;
        long y = 0x88;
        byte b = 0x1B;
        boolean bl = true;
        char ch = 0x78;
        int[] arr = new int[]{0x01, 0x02, 0x03, 0x04};
    }
}
