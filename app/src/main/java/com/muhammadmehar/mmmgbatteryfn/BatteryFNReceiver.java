package com.muhammadmehar.mmmgbatteryfn;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import java.util.Calendar;

/**
 * Created by Muhammad Mehar on 3/20/2018.
 */
public class BatteryFNReceiver  extends BroadcastReceiver {

    private static final int ALARM_CODE = 1001;
    private static PendingIntent pendingIntent;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
            context.startService(new Intent(context, ReceiverRegisterService.class));
        } else if (intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED)
                || intent.getAction().equals("com.muhammadmehar.action.ACTION_CANCEL_ALARM")) {
            setCancelAlarm(context);
            if (isServiceRunning(ReceiverRegisterService.class, context)) {
                context.stopService(new Intent(context, ReceiverRegisterService.class));
            }
            if (isServiceRunning(TTSService.class, context)) {
                context.stopService(new Intent(context, TTSService.class));
            }
            return;
        }
        int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
        if ((status == BatteryManager.BATTERY_STATUS_FULL || level == 100) && plugged != 0 && status == BatteryManager.BATTERY_STATUS_CHARGING) {
            context.startService(new Intent(context, TTSService.class));
            context.startService(new Intent(context, BatteryFNService.class));
            triggerAlarmManager(getTimeInterval("5"), context);
        }
    }

    private static int getTimeInterval(String getInterval) {
        int interval = Integer.parseInt(getInterval);
        return interval * 60;
    }

    private static void triggerAlarmManager(int alarmTriggerTime, Context context){
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context, ALARM_CODE, alarmIntent, 0);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, alarmTriggerTime);
        AlarmManager manger = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manger.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pendingIntent);
    }

    public static void setCancelAlarm(Context context) {
        cancelAlarm(context);
    }

    public static void setTriggerAlarmManager(Context context) {
        triggerAlarmManager(getTimeInterval("5"), context);
    }

    private static void cancelAlarm(Context context){
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancel(BatteryFNService.NOTIFICATION_ID);
    }

    private boolean isServiceRunning(Class<?> serviceClass, Context mContext){
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
