package fnn.smirl.cardgame.object.core.abstracting;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.content.Context;

public abstract class GObject {
 public Context context;
 public PointF parentSize;

 public GObject(Context context, PointF parentSize) {
	this.context = context;
	this.parentSize = parentSize;
 }

 public abstract void  onDraw(Canvas canvas);
}
