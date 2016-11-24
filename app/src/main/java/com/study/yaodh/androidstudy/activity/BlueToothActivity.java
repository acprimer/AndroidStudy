package com.study.yaodh.androidstudy.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.databinding.ActivityBlueToothBinding;

import java.util.List;
import java.util.Set;

public class BluetoothActivity extends BaseActivity {
    public static final int REQUEST_ENABLE_BT = 14;
    private ActivityBlueToothBinding binding;
    private BluetoothAdapter mBlueToothAdapter;
    private ArrayAdapter<String> mDeviceAdapter;
    private List<String> mDeviceNames;

    @Override
    protected int getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_blue_tooth);
        return 0;
    }

    @Override
    protected void initContent() {
        mBlueToothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBlueToothAdapter == null) {
            // Device does not support Bluetooth
            finish();
        }
        if(!mBlueToothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }

        mDeviceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, mDeviceNames);
        Set<BluetoothDevice> pairedDevices = mBlueToothAdapter.getBondedDevices();
        for(BluetoothDevice device : pairedDevices) {
            mDeviceAdapter.add(device.getName() + "\n" + device.getAddress());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_ENABLE_BT) {
            if(resultCode == RESULT_OK) {
                Toast.makeText(this, "Enable Bluetooth.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Can't Enable Bluetooth.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
