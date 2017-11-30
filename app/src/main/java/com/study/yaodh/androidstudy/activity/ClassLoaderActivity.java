package com.study.yaodh.androidstudy.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.study.yaodh.androidstudy.R;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class ClassLoaderActivity extends AppCompatActivity {
    private static final String TAG = "ClassLoaderActivity";
    private TextView tVText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_loader);
        tVText = findViewById(R.id.text);
    }

    public void loadDex(View view) {
        File jarFile = new File(Environment.getExternalStorageDirectory(), "hello_dex.jar");
        if (!jarFile.exists()) {
            Log.e(TAG, "loadDex: hello_dex.jar not found");
        }

        DexClassLoader dexClassLoader =  new DexClassLoader(
                jarFile.getAbsolutePath(),
                getExternalCacheDir().getAbsolutePath(),
                null, getClassLoader());
        try {
            Class<?> clazz = dexClassLoader.loadClass("com.yaodh.HelloAndroid");
            Method method = clazz.getMethod("say");
//            ISayHello helloAndroid = (ISayHello) clazz.newInstance();
            tVText.setText((CharSequence) method.invoke(clazz.newInstance()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
