package com.study.yaodh.androidstudy.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.databinding.ActivityPermissionBinding;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class PermissionActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks{

    private static final int PERMISSION_REQUEST_CODE = 15;
    private static final int RC_CAMERA_PERM = 123;
    private static final int RC_LOCATION_CONTACTS_PERM = 124;

    private ActivityPermissionBinding binding;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_permission;
    }

    @Override
    protected void initContent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_permission);
    }

    public void getImei(View view) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)) {
                Snackbar.make(binding.getRoot(), "Permission request", Snackbar.LENGTH_SHORT)
                        .setAction("Request", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ActivityCompat.requestPermissions(PermissionActivity.this,
                                        new String[]{Manifest.permission.READ_PHONE_STATE},
                                        PERMISSION_REQUEST_CODE);
                            }
                        })
                        .show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        PERMISSION_REQUEST_CODE);
            }
        } else {
            loadImei();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadImei();
                } else {
                    Snackbar.make(binding.getRoot(), "Permission denied", Snackbar.LENGTH_SHORT)
                            .setAction("reset", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ActivityCompat.requestPermissions(PermissionActivity.this,
                                            new String[]{Manifest.permission.READ_PHONE_STATE},
                                            PERMISSION_REQUEST_CODE);
                                }
                            })
                            .show();
                }
                break;
        }
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void loadImei() {
        TelephonyManager telManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = telManager.getDeviceId();
        binding.setImei(deviceId);
    }

    public void getCamera(View view) {
        getCamera();
    }

    @AfterPermissionGranted(RC_CAMERA_PERM)
    private void getCamera() {
        Log.d(TAG, "getCamera");
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            // Have permission, do the thing!
            Toast.makeText(this, "TODO: Camera things", Toast.LENGTH_LONG).show();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_camera),
                    RC_CAMERA_PERM, Manifest.permission.CAMERA);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());

        // (Optional) Check whether the user denied permissions and checked NEVER ASK AGAIN.
        // This will display a dialog directing them to enable the permission in app settings.
        EasyPermissions.checkDeniedPermissionsNeverAskAgain(this,
                getString(R.string.rationale_ask_again),
                R.string.setting, R.string.cancel, null, perms);
    }
}
