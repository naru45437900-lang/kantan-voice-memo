package jp.kantan.voicememo;

import android.content.Context;
import org.json.*;
import java.text.SimpleDateFormat;
import java.util.*;

public final class MemoStore {
  private static final String PREF="voice_memos", MEMOS="memos", TIMES="times";
  public static class Memo { public String text, day; public boolean done; Memo(String t,String d,boolean x){text=t;day=d;done=x;} }
  public static List<Memo> memos(Context c){
    List<Memo> out=new ArrayList<>(); try { JSONArray a=new JSONArray(c.getSharedPreferences(PREF,0).getString(MEMOS,"[]"));
      for(int i=0;i<a.length();i++){JSONObject o=a.getJSONObject(i);out.add(new Memo(o.getString("text"),o.getString("day"),o.optBoolean("done")));}
    } catch(Exception ignored){} return out;
  }
  public static void saveMemos(Context c,List<Memo> ms){JSONArray a=new JSONArray(); try{for(Memo m:ms){JSONObject o=new JSONObject();o.put("text",m.text);o.put("day",m.day);o.put("done",m.done);a.put(o);}}catch(Exception ignored){} c.getSharedPreferences(PREF,0).edit().putString(MEMOS,a.toString()).apply(); MemoWidget.refresh(c);}
  public static void add(Context c,String text){List<Memo> ms=memos(c);ms.add(0,new Memo(text,today(),false));saveMemos(c,ms);}
  public static String today(){return new SimpleDateFormat("yyyy-MM-dd",Locale.JAPAN).format(new Date());}
  public static List<String> times(Context c){String raw=c.getSharedPreferences(PREF,0).getString(TIMES,"09:00,12:00,15:00");if(raw.isEmpty())return new ArrayList<>();return new ArrayList<>(Arrays.asList(raw.split(",")));}
  public static void saveTimes(Context c,List<String> ts){Collections.sort(ts);c.getSharedPreferences(PREF,0).edit().putString(TIMES,String.join(",",ts)).apply(); ReminderScheduler.scheduleAll(c);MemoWidget.refresh(c);}
  public static int pendingToday(Context c){int n=0;for(Memo m:memos(c))if(m.day.equals(today())&&!m.done)n++;return n;}
}
