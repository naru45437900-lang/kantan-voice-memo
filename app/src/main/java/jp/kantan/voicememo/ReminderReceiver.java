package jp.kantan.voicememo;

import android.app.*;import android.content.*;import android.graphics.Color;import androidx.core.app.NotificationCompat;
public class ReminderReceiver extends android.content.BroadcastReceiver {
 public void onReceive(Context c,Intent i){String ch="memo_reminders";NotificationManager nm=(NotificationManager)c.getSystemService(Context.NOTIFICATION_SERVICE);NotificationChannel nc=new NotificationChannel(ch,"ボイスメモのお知らせ",NotificationManager.IMPORTANCE_HIGH);nc.enableVibration(true);nc.setLightColor(Color.GREEN);nm.createNotificationChannel(nc);Intent open=new Intent(c,MainActivity.class);PendingIntent p=PendingIntent.getActivity(c,0,open,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);int n=MemoStore.pendingToday(c);Notification x=new NotificationCompat.Builder(c,ch).setSmallIcon(android.R.drawable.ic_btn_speak_now).setContentTitle("メモを確認する時間です").setContentText(n==0?"新しいメモを録音できます":"未完了のメモが "+n+"件あります").setPriority(NotificationCompat.PRIORITY_HIGH).setDefaults(Notification.DEFAULT_ALL).setAutoCancel(true).setContentIntent(p).build();nm.notify(1001,x);ReminderScheduler.scheduleAll(c);}
}
