package com.dehqanis.messageforwarder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

public class AutoStart extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction()))
        {
            Log.d("SMSSTUFF","BOOTED");

            Intent backgroundService = new Intent(context, RegisterService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(backgroundService);

            } else {
                context.startService(backgroundService);
            }
//            startServiceByAlarm(context);
        }
    }
    /* Create an repeat Alarm that will invoke the background service for each execution time.
     * The interval time can be specified by your self.  */
    private void startServiceByAlarm(Context context)
    {
        // Get alarm manager.
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        // Create intent to invoke the background service.
        Intent intent = new Intent(context, RegisterService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        long startTime = System.currentTimeMillis();
        long intervalTime = 60*1000;

        String message = "Start service use repeat alarm. ";
        Log.d("SMSSTUFF",message);
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();

        // Create repeat alarm.
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startTime, intervalTime, pendingIntent);
        Log.d("SMSSTUFF","set alarm");

    }
}
