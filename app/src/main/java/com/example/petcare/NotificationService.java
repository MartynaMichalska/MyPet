package com.example.petcare;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;


public class NotificationService extends BroadcastReceiver {
    public NotificationManager myNotificationManager;
    public static final int NOTIFICATION_ID = 1;
    @Override
    public void onReceive(Context context, Intent intent) {

        myNotificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new NotificationChannel( "channelID", "NOTIFICATION_CHANNEL_NAME" , importance) ;
            myNotificationManager.createNotificationChannel(notificationChannel) ;
        }
        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);CharSequence NotificationTicket = "USB Connected!";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context , "channelID" ) ;
        builder.setContentTitle("Powiadomienie");
        builder.setContentText("Tresc");
        builder.setContentIntent(contentIntent);
        myNotificationManager.notify(NOTIFICATION_ID, builder.build());

    }
}
