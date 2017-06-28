package kr.co.hiowner.jnauction.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import kr.co.hiowner.jnauction.MainActivity;
import kr.co.hiowner.jnauction.NewMainActivity;
import kr.co.hiowner.jnauction.R;

/**
 * Created by user on 2016-08-09.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    int mIntId = 0;
    Context mContext;

    @Override
    public void onMessageReceived(RemoteMessage message) {

        mContext = MyFirebaseMessagingService.this;

        Log.d("value", "PUSH 가 들어옴 - " + message.toString());
        String from = message.getFrom();
        Map<String, String> data = message.getData();
        Log.d("value", "data - " + data.toString());
        String title = data.get("title");
        String msg = data.get("message");
        String URL = data.get("url");

        sendNotification(title, msg, URL);
    }

//    private void handleMessage(Context context, RemoteMessage message) {
//
//        String from = message.getFrom();
//        Map<String, String> data = message.getData();
//        String tickerText = data.get("tickerText");
//        String title = data.get("title");
//        String msg = data.get("msg");
//        String URL = data.get("URL");
//
//        // 화면 깨우기
//        PushWakeLock.acquireCpuWakeLock(context);
//
//        Intent i = new Intent(context, ShowMsg.class);
//        Bundle b = new Bundle();
//        b.putString("title", title);
//        b.putString("msg", msg);
//        b.putString("url", URL);
//
//        i.putExtras(b);
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(i);
//    }




    /**
     * 실제 디바에스에 GCM으로부터 받은 메세지를 알려주는 함수이다. 디바이스 Notification Center에 나타난다.
     * @param title
     * @param message
     */
    private void sendNotification(String title, String message, String url) {
        Intent intent = new Intent(this, NewMainActivity.class);
        intent.putExtra("url", url);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentTitle(title)
                .setContentText(message)
//                .setPriority(Notification.PRIORITY_MAX)
                .setAutoCancel(true)
                .setColor(Color.TRANSPARENT)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(mIntId /* ID of notification */, notificationBuilder.build());
    }

}
