package com.sava.sm.control;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context,Notifi.class);
        intent1.putExtra("TITLE",intent.getExtras().getString("TITLE"));
        intent1.putExtra("CONTENT",intent.getExtras().getString("CONTENT"));
        intent1.putExtra("PENDINGID",intent.getExtras().getInt("PENDINGID"));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent1);
        } else {
            context.startService(intent1);
        }
    }
}
