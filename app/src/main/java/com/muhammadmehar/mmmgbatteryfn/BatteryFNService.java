package com.muhammadmehar.mmmgbatteryfn;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Muhammad Mehar on 3/21/2018.
 */
public class BatteryFNService extends IntentService {

    public static final int NOTIFICATION_ID = 786;

    public BatteryFNService(){
        super("BatteryFNService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        sendNotification();
    }

    private void sendNotification() {
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(getBaseContext(), BatteryFNReceiver.class);
        intent.setAction("com.muhammadmehar.action.ACTION_CANCEL_ALARM");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentTitle("3MGBatteryFN")
                .setContentText(getString(R.string.battery_full))
                .setSmallIcon(R.drawable.ic_notification)
                .addAction(0,"Stop",pendingIntent)
                .setAutoCancel(false);
        notificationManager.notify(NOTIFICATION_ID, nBuilder.build());
    }
}
