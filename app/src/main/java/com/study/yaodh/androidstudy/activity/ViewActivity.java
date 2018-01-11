package com.study.yaodh.androidstudy.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.study.yaodh.androidstudy.R;
import com.study.yaodh.androidstudy.view.ListWrapperLayout;

public class ViewActivity extends AppCompatActivity {
    private ImageView ivBlur;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        ListWrapperLayout listview = findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.item_wrapper_list,
                R.id.text,
                new String[] {"A", "B"});
        listview.setAdapter(adapter);
//
//        toolbar = findViewById(R.id.toolbar);
//        ivBlur = findViewById(R.id.blur_image);
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.navigation_header);
//        ivBlur.setImageBitmap(blur(this, bitmap, 25));
//
//        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
//            @Override
//            public void onGenerated(Palette palette) {
//                Palette.Swatch vibrant = palette.getVibrantSwatch();
//                //根据调色板Palette获取到图片中的颜色设置到toolbar和tab中背景，标题等，使整个UI界面颜色统一
//                toolbar.setBackgroundColor(vibrant.getRgb());
////                toolbar_tab.setSelectedTabIndicatorColor(colorBurn(vibrant.getRgb()));
////                toolbar.setBackgroundColor(vibrant.getRgb());
////
////                if (android.os.Build.VERSION.SDK_INT >= 21) {
////                    Window window = getWindow();
////                    window.setStatusBarColor(colorBurn(vibrant.getRgb()));
////                    window.setNavigationBarColor(colorBurn(vibrant.getRgb()));
////                }
//            }
//        });
    }

    private static Bitmap blur(Context context, Bitmap source, int radius) {
        Bitmap target = source;
        RenderScript rs = RenderScript.create(context);
        final Allocation input = Allocation.createFromBitmap(rs, target);
        final Allocation output = Allocation.createTyped(rs, input.getType());
        ScriptIntrinsicBlur intrinsicBlur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        intrinsicBlur.setInput(input);
        intrinsicBlur.setRadius(radius);
        intrinsicBlur.forEach(output);
        output.copyTo(target);
        rs.destroy();
        return target;
    }
}
