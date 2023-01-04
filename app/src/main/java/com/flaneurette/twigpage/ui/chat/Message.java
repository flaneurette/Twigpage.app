package com.flaneurette.twigpage.ui.chat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.webkit.JavascriptInterface;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.flaneurette.twigpage.R;

import java.security.SecureRandom;

public class Message {

    public static final String CHANNEL_ID = "Chat";

    Context context;

    public Message(Context c) {
        context = c;
    }

    @JavascriptInterface
    public void MessageShow(String message) {

        NotificationManager notificationManager = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NotificationManager NotificationManager = context.getSystemService(android.app.NotificationManager.class);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Chat";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(message);
            channel.enableLights(true);
            channel.setLightColor(Color.BLUE);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationManager.createNotificationChannel(channel);
        }

        Notification notify = new NotificationCompat.Builder(context.getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("You received a new chat")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        int randomInt = random.nextInt(20);
        // assert notificationManager != null;
        notificationManager.notify(randomInt, notify);
    }
}
