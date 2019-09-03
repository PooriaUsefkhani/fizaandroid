package com.dehqanis.messageforwarder;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.provider.Telephony;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class RegisterService extends Service {
    private NewSMSReciever reciever = null;
    public RegisterService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        String CHANNEL_ID = "my_channel_01";
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Messages")
                    .setContentText("listening").build();

            startForeground(1, notification);
        }
        Log.d("SMSSTUFF","Registering reciever");

        IntentFilter intentFilter = new IntentFilter();

        // Add network connectivity change action.
        intentFilter.addAction(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);

        // Set broadcast receiver priority.
        intentFilter.setPriority(1000);

        // Create a network change broadcast receiver.
        reciever = new NewSMSReciever();

        // Register the broadcast receiver with the intent filter object.
        registerReceiver(reciever, intentFilter);

        Log.d("SMSSTUFF","Registered reciever");
    }

    @Override
    public void onDestroy() {
    }
}
