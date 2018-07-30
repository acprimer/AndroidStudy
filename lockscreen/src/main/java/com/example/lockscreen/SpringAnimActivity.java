package com.example.lockscreen;

import android.os.Bundle;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;

public class SpringAnimActivity extends AppCompatActivity {
    private TextView textView;
    private TextView textView2;
    private SpringAnimation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spring_anim);

        textView = findViewById(R.id.text);
        textView2 = findViewById(R.id.text2);

        initAnimation();
    }

    private void initAnimation() {
        animation = new SpringAnimation(textView, SpringAnimation.Y);
        SpringForce force = new SpringForce(0);
        force.setStiffness(100f);
        force.setDampingRatio(0.5f);
//        animation.setStartVelocity(0f);
        animation.setSpring(force);
    }

    public void startAnim(View view) {
        animation.start();
        showSpring(textView2);
    }

    public void showSpring(final View view) {

        final int top = view.getTop();
        /*if(true)
            return ;*/

        // Create a system to run the physics loop for a set of springs.
        SpringSystem springSystem = SpringSystem.create();

        // Add a spring to the system.
        Spring spring = springSystem.createSpring();

        // Add a listener to observe the motion of the spring.
        spring.addListener(new SimpleSpringListener() {

            @Override
            public void onSpringUpdate(Spring spring) {
                // You can observe the updates in the spring
                // state by asking its current value in onSpringUpdate.
//                Log.d(TAG, "onSpringUpdate : currentValue = " + spring.getCurrentValue());
                float value = (float) spring.getCurrentValue();
                float scale = 1f - (value * 0.5f);
                value = Math.abs(value);
                if (top > 0) {
                    view.setTranslationY(value);
                } else {
                    view.setTranslationY(-value);
                }
            }

            @Override
            public void onSpringActivate(Spring spring) {
                super.onSpringActivate(spring);
            }

        });

        spring.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(4, 2));
//        SpringConfig.fromBouncinessAndSpeed()
        spring.setCurrentValue(top);
        // Set the spring in motion; moving from 0 to 1
        spring.setEndValue(0);
        view.offsetTopAndBottom(-top);
    }

    public void handle(View view) {
        Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
    }
}
