package jp.kantan.voicememo;

import android.app.*; import android.content.*; import java.util.*;
public final class ReminderScheduler {
 public static void scheduleAll(Context c){AlarmManager am=(AlarmManager)c.getSystemService(Context.ALARM_SERVICE);for(int id=0;id<48;id++)am.cancel(pi(c,id));int id=0;for(String t:MemoStore.times(c)){String[] p=t.split(":");Calendar cal=Calendar.getInstance();cal.set(Calendar.HOUR_OF_DAY,Integer.parseInt(p[0]));cal.set(Calendar.MINUTE,Integer.parseInt(p[1]));cal.set(Calendar.SECOND,0);if(cal.getTimeInMillis()<=System.currentTimeMillis())cal.add(Calendar.DAY_OF_YEAR,1);Intent i=new Intent(c,ReminderReceiver.class).putExtra("time",t);PendingIntent x=PendingIntent.getBroadcast(c,id++,i,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);try{am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),x);}catch(SecurityException e){am.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),x);}}}
 private static PendingIntent pi(Context c,int id){return PendingIntent.getBroadcast(c,id,new Intent(c,ReminderReceiver.class),PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);}
}
