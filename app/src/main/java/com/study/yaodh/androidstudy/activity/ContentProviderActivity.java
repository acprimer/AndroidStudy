package com.study.yaodh.androidstudy.activity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.provider.ContactsContract.CommonDataKinds.Phone;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.databinding.ActivityContentProviderBinding;

public class ContentProviderActivity extends BaseActivity {
    private ActivityContentProviderBinding binding;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_content_provider;
    }

    @Override
    protected void initContent() {
        super.initContent();
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        binding.contact.setText(getPhoneContacts());
    }

    private String getPhoneContacts() {
        String[] phoneProjection =new String[] {
                Phone.DISPLAY_NAME,
                Phone.NUMBER,
        };
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(Phone.CONTENT_URI,
                phoneProjection, null, null, null);
        StringBuilder builder = new StringBuilder();
        if(cursor != null) {
            while(cursor.moveToNext()) {
                String name = cursor.getString(0);
                String number = cursor.getString(1);
                builder.append(name + "\n" + number + "\n");
            }
            cursor.close();
        }
        return builder.toString();
    }
}
