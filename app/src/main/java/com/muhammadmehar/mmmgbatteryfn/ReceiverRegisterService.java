package com.muhammadmehar.mmmgbatteryfn;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Muhammad Mehar on 3/21/2018.
 */
public class ReceiverRegisterService extends Service {

    private BatteryFNReceiver mReceiver;

    @Override
    public void onCreate() {
        mReceiver = new BatteryFNReceiver();
        registerReceiver(mReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
