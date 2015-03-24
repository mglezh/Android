package com.mglezh.intentapplication.BroadCastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

public class TelephonyReceiver extends BroadcastReceiver {
    public TelephonyReceiver() {
    }
    private final String RECEIVER = "RECEIVER";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(RECEIVER, "TelephonyReceiver onReceiver()");
        Log.d(RECEIVER, intent.getAction());
        switch ( intent.getAction()) {
            case Intent.ACTION_AIRPLANE_MODE_CHANGED:
                break;
            case ConnectivityManager.CONNECTIVITY_ACTION:
                break;
        }
    }
}
