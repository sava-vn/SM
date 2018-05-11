package com.sava.sm.control;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.sava.sm.GhiChuActivity;
import com.sava.sm.R;

public class Notifi extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("SAVA","SERVICE");
        Log.e("TITLE",intent.getExtras().getString("TITLE"));
        Log.e("CONTENT",intent.getExtras().getString("CONTENT"));
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent1  = new Intent(this.getApplicationContext(), GhiChuActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,intent.getExtras().getInt("PENDINGID"),intent1,0);
        Notification notification  = new Notification.Builder(this)
                .setContentTitle(intent.getExtras().getString("TITLE"))
                .setContentText(intent.getExtras().getString("CONTENT"))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(R.drawable.clock)
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .build();
        notificationManager.notify(0,notification);
        return START_NOT_STICKY;
    }
}
