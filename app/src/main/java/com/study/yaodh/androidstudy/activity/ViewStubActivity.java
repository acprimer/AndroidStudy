package com.study.yaodh.androidstudy.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.study.yaodh.androidstudy.R;

public class ViewStubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_stub);

        ViewStub viewStub1 = findViewById(R.id.view_stub_1);
        ViewStub viewStub2 = findViewById(R.id.view_stub_2);

        View view = viewStub1.inflate();
//        viewStub1.inflate();

        LinearLayout layout = findViewById(R.id.id_1);
        TextView tv = findViewById(R.id.tv_1);
        tv.setText("good");

        System.out.println("view " + view);
        System.out.println("textview " + tv);
        System.out.println("layout " + layout);


        viewStub1 = null;
//        View view1 = viewStub1.inflate();
//        TextView tv1 = (TextView) view1;
//        ((TextView) view1).setText("good");
//        TextView tv1 = view1.findViewById(R.id.view_stub_1);
    }
}
