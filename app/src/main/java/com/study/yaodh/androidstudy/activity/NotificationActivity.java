package com.study.yaodh.androidstudy.activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import com.study.yaodh.androidstudy.R;

/**
 * Created by yaodh on 2016/6/16.
 */
public class NotificationActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notification;
    }

    @Override
    protected void initContent() {
        Button button = (Button) findViewById(R.id.notification);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.notification:
                sendNotification();
                break;
        }
    }

    private void sendNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        int icon = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? R.drawable.ic_notification : R.drawable.ic_logo;
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo))
                .setSmallIcon(icon)
//                .setColor(getResources().getColor(R.color.colorAccent))ic_logo.png
                .setContentTitle("Title")
                .setContentText("Message")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
