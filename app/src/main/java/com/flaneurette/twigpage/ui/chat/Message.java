package com.flaneurette.twigpage.ui.chat;

import static com.flaneurette.twigpage.MainActivity.CHANNEL_ID;
import static java.lang.Boolean.TRUE;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.webkit.JavascriptInterface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.flaneurette.twigpage.MainActivity;
import com.flaneurette.twigpage.R;

import java.security.SecureRandom;


public class Message extends AppCompatActivity {

    private NotificationManager notificationManager;
    Context context;

    public Message(Context c) {
        context = c;
    }

    @JavascriptInterface
    public void MessageShow(String message) {

        NotificationManager notificationManager =
                context.getSystemService(NotificationManager.class);

        if(notificationManager.areNotificationsEnabled() == TRUE) {

            Notification notify = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.notification_icon)
                    .setContentTitle("You received a new chat")
                    .setContentText(message)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .build();

            SecureRandom random = new SecureRandom();
            byte bytes[] = new byte[20];
            random.nextBytes(bytes);
            int randomInt = random.nextInt(20);

            notificationManager.notify(randomInt, notify);
        }
    }
}
