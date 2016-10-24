package com.jezik.remindme;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

public class ReminderService extends IntentService {

    private final String GROUP_KEY = "reminder notification";

    public ReminderService() {
        super("ReminderService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        String text = intent.getStringExtra("content");

        SharedPreferences sPref = getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        int notificationId = sPref.getInt("notificationNumber", 0);

        Intent resultIntent = new Intent(this, MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.information)
                .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.ic_launcher))
                .setContentTitle(intent.getStringExtra("header"))
                .setContentText(text)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(text))
                .setGroup(GROUP_KEY)
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setVibrate(new long[] {1000, 1000})
                .setContentIntent(resultPendingIntent);

        manager.notify(notificationId, mBuilder.build());

        SharedPreferences.Editor editor = sPref.edit();
        notificationId++;
        editor.putInt("notificationNumber", notificationId);
        editor.apply();
    }
}
