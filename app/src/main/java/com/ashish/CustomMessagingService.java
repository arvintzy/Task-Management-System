package com.ashish;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class CustomMessagingService  extends FirebaseMessagingService {
    NotificationManager notificationManager;
    Notification notification;
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if(remoteMessage.getNotification()!=null){
            String title=remoteMessage.getNotification().getTitle();
            String message=remoteMessage.getNotification().getBody();
            generateNotification(title,message);
         //   Toast.makeText(this, "Notification Received", Toast.LENGTH_SHORT).show();

        }
       /* if(remoteMessage.getData().size()>0){
            String statusValue=remoteMessage.getData().get("STATUS");
        }*/

    }
    public void generateNotification(String notititle,String notiMessage){
        Intent intent=new Intent(CustomMessagingService.this,MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(CustomMessagingService.this,0,intent, PendingIntent.FLAG_UPDATE_CURRENT );
        notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        if(Build.VERSION.SDK_INT>=26){
            String channelId="com.ashish";
            String channelName="FCMDemo";
            NotificationChannel notificationChannel=new NotificationChannel(channelId,channelName,NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            assert  notificationManager!=null;
            notificationManager.createNotificationChannel(notificationChannel);
            NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(CustomMessagingService.this,channelId);
            notificationBuilder.setOngoing(true)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setColor(Color.BLUE)
                    .setAutoCancel(true)
                    .setTicker(notiMessage)
                    .setContentTitle(notititle)
                    .setContentText(notiMessage)
                    .setSound(alarmSound)
                    .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS)
                    .setContentIntent(pendingIntent);
                notification=notificationBuilder.build();
        }else {
            Notification.Builder nb=new Notification.Builder(CustomMessagingService.this);
            nb.setSmallIcon(R.mipmap.ic_launcher);
                    nb.setContentTitle(notititle);
                    nb.setContentText(notiMessage);
                    nb.setContentIntent(pendingIntent);
                    nb.setSound(alarmSound);
                    nb.build();

                    notification=nb.getNotification();
                    notification.flags=Notification.FLAG_AUTO_CANCEL;
        }
        if(Build.VERSION.SDK_INT>=26){
            startForeground(0,notification);
        }else {
            notificationManager.notify(0,notification);
        }
    }
}
