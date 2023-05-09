package com.example.term_tracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationBroadcast extends BroadcastReceiver {

    private static final String TAG = "NotificationBroadcast";

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notify_term_tracker")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(intent.getExtras().getString("title"))
                .setContentText(intent.getExtras().getString("text"))
                .setPriority(NotificationCompat.PRIORITY_LOW);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        Log.d(TAG,"FROM before notificationManager: id~" + MainActivity.id);
        notificationManager.notify(MainActivity.id++, builder.build());
        Log.d(TAG,"FROM after notificationManager: id~" + MainActivity.id);
    }
}
