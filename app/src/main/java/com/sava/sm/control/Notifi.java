package com.sava.sm.control;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
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
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent1  = new Intent(this.getApplicationContext(), GhiChuActivity.class);
        intent1.putExtra("CURRENT",1);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,intent.getExtras().getInt("PENDINGID"),intent1,0);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            String CHANEL_ID ="SAVA";
            NotificationChannel channel = new NotificationChannel(CHANEL_ID,"Follower",NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
            Notification notification = new Notification.Builder(this,CHANEL_ID)
                    .setSmallIcon(R.drawable.clock)
                    .setContentTitle(intent.getExtras().getString("TITLE"))
                    .setContentText(intent.getExtras().getString("CONTENT"))
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setChannelId(CHANEL_ID)
                    .setStyle(new Notification.BigPictureStyle())
                    .setVisibility(Notification.VISIBILITY_PUBLIC)
                    .build();
            notificationManager.notify((int) System.currentTimeMillis(),notification);
        }else{
            Notification notification  = new Notification.Builder(this)
                    .setSmallIcon(R.drawable.clock)
                    .setContentTitle(intent.getExtras().getString("TITLE"))
                    .setContentText(intent.getExtras().getString("CONTENT"))
                    .setContentIntent(pendingIntent)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setAutoCancel(true)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setVisibility(Notification.VISIBILITY_PUBLIC)
                    .build();
            notificationManager.notify((int) System.currentTimeMillis(),notification);
        }
        return START_NOT_STICKY;
    }
}
