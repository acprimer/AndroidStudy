package com.study.yaodh.androidstudy.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.ironz.unsafe.UnsafeAndroid;
import com.study.yaodh.androidstudy.R;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;

public class BitmapActivity extends AppCompatActivity {
    private static UnsafeAndroid unsafe = new UnsafeAndroid();

    public static long addressOf(Object o) {
        Object[] array = new Object[] { o };
        long baseOffset = unsafe.arrayBaseOffset(Object[].class);
        int addressSize = unsafe.addressSize();
        long objectAddress;
        switch (addressSize) {
            case 4:
                objectAddress = unsafe.getInt(array, baseOffset);
                break;
            case 8:
                objectAddress = unsafe.getLong(array, baseOffset);
                break;
            default:
                throw new Error("unsupported address size: " + addressSize);
        }
        return (objectAddress);
    }

    public static long sizeOf(Object o) {
        HashSet<Field> fields = new HashSet<Field>();
        Class c = o.getClass();
        while (c != Object.class) {
            for (Field f : c.getDeclaredFields()) {
                if ((f.getModifiers() & Modifier.STATIC) == 0) {
                    fields.add(f);
                }
            }
            c = c.getSuperclass();
        }

        // get offset
        long maxSize = 0;
        for (Field f : fields) {
            long offset = unsafe.objectFieldOffset(f);
            if (offset > maxSize) {
                maxSize = offset;
            }
        }

        return ((maxSize/8) + 1) * 8;   // padding
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);


        ImageView iv = findViewById(R.id.image);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dragon);
        iv.setImageBitmap(bitmap);
        System.out.println("bitmap: 0x" + Integer.toHexString(bitmap.hashCode()));
        System.out.println("bitmap: 0x" + Long.toHexString(addressOf(bitmap)));
        System.out.println("bitmap sizeOf: " + sizeOf(bitmap));
        System.out.println("bitmap size: " + bitmap.getByteCount());
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        System.out.println("densityDpi: " + metrics.densityDpi);
        Integer a = new Integer(3);
        System.out.println("Integer sizeOf: " + sizeOf(a));
    }
}
