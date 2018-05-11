package com.sava.sm.control;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context,Notifi.class);
        Log.e("SAVA","RECEIVER");
        context.startService(intent1);
    }
}
