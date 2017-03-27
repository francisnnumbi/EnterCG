package fnn.smirl.cardgame.util;
import android.content.*;
import android.widget.*;

public class Toaster
{
 public static void toast(Context ctx, String msg, boolean durationLong){
	if(durationLong)
	Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
	else
	 Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
	 
 }
}
