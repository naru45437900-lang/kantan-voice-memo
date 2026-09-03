package jp.kantan.voicememo;

import android.app.*;import android.appwidget.*;import android.content.*;import android.widget.RemoteViews;import java.util.*;
public class MemoWidget extends AppWidgetProvider {
 public void onUpdate(Context c,AppWidgetManager m,int[] ids){for(int id:ids)update(c,m,id);}
 static void update(Context c,AppWidgetManager am,int id){RemoteViews v=new RemoteViews(c.getPackageName(),R.layout.widget);int n=MemoStore.pendingCount(c);v.setTextViewText(R.id.widgetSummary,"未完了 "+n+"件");List<String> ts=MemoStore.times(c);v.setTextViewText(R.id.widgetNext,"通知: "+String.join("・",ts));StringBuilder s=new StringBuilder();for(MemoStore.Memo x:MemoStore.memos(c)){if(!x.done){if(s.length()>0)s.append("\n");s.append("・").append(x.text);if(s.toString().split("\n").length>=3)break;}}v.setTextViewText(R.id.widgetMemos,s.length()==0?"未完了のメモはありません":s.toString());Intent i=new Intent(c,MainActivity.class).putExtra("speak",true);PendingIntent p=PendingIntent.getActivity(c,22,i,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);v.setOnClickPendingIntent(R.id.widgetRecord,p);am.updateAppWidget(id,v);}
 public static void refresh(Context c){AppWidgetManager am=AppWidgetManager.getInstance(c);ComponentName cn=new ComponentName(c,MemoWidget.class);for(int id:am.getAppWidgetIds(cn))update(c,am,id);}
}
