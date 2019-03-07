package com.business_idea.business_ideas_app.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.DashPathEffect;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.business_idea.business_ideas_app.DashboardActivity;
import com.business_idea.business_ideas_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

//import android.support.v7.app.NotificationCompat;

public class FCMService extends FirebaseMessagingService {
    private static final String TAG = "FirebaseMsgService";
    FirebaseAuth auth;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        /* There are two types of messages data messages and notification messages. Data messages are handled here in onMessageReceived whether the app is in the foreground or background. Data messages are the type traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app is in the foreground. When the app is in the background an automatically generated notification is displayed. */
auth=FirebaseAuth.getInstance();
        String notificationTitle = null, notificationBody = null;
        String dataTitle = null, dataMessage = null;

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData().get("message"));
            dataTitle = remoteMessage.getData().get("title");
            dataMessage = remoteMessage.getData().get("message");
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            notificationTitle = remoteMessage.getNotification().getTitle();
            notificationBody = remoteMessage.getNotification().getBody();
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
       // if(!dataMessage.equals(auth.getUid())) {
            sendNotification(notificationTitle, notificationBody, dataTitle, dataMessage);
       // }
    }

    /**
     //     * Create and show a simple notification containing the received FCM message.
     //     */
    private void sendNotification(String notificationTitle, String notificationBody, String dataTitle, String dataMessage) {
   Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra("title", dataTitle);
        intent.putExtra("message", dataMessage);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent,PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =  new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(notificationTitle)
                .setContentText(notificationBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 , notificationBuilder.build());

/*


        NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify=new Notification.Builder
                (getApplicationContext()).setContentTitle("Notify").setContentText("helooooo")
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentTitle("Information").setSmallIcon(R.drawable.ic_launcher_foreground).build();
        notify.contentIntent=  PendingIntent.getActivity(this, 0,
                new Intent(this, DashboardActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        notif.notify(1, notify);



        Toast.makeText(FCMService.this,"New value added",Toast.LENGTH_SHORT).show();
        */
    }
}
