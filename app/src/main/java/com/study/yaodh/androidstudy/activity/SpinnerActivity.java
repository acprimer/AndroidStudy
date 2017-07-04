package com.study.yaodh.androidstudy.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.databinding.ActivitySpinnerBinding;

public class SpinnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySpinnerBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_spinner);

        String[] fruits = getResources().getStringArray(R.array.fruit_array);

        binding.simpleSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, fruits));

        binding.customSpinner.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.item, fruits));
//        binding.customSpinner.setPopupBackgroundResource(R.drawable.ic_directory_bg);
    }
}
