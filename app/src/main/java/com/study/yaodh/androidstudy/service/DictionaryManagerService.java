package com.study.yaodh.androidstudy.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.study.yaodh.androidstudy.aidl.IDictionaryManager;

import java.util.HashMap;
import java.util.Map;

public class DictionaryManagerService extends Service {
    public static final String TAG = "aidl";
    private Map<String, String> map = new HashMap<>();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new IDictionaryManager.Stub() {

            @Override
            public void add(String source, String translation) throws RemoteException {
                map.put(source, translation);
                Log.d(TAG, "add: " + source);
            }

            @Override
            public String query(String source) throws RemoteException {
                return map.get(source);
            }
        };
    }
}
