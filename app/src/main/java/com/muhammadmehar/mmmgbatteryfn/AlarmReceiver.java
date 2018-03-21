package com.muhammadmehar.mmmgbatteryfn;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Muhammad Mehar on 3/21/2018.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent( context, TTSService.class));
        BatteryFNReceiver.setTriggerAlarmManager(context);
    }
}
