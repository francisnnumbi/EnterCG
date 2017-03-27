package fnn.smirl.cardgame.object.core.interfacing;
import android.graphics.*;

import fnn.smirl.cardgame.object.core.enums.Intersection;

public interface Rectangle
{
	
 public void setRectangleColor(int color);
 public void setRectanglePaint(Paint paint);
 public void setRectangleWidth(float width);
 public void setRectangleVisible(boolean visible);
 public void setRoundRectangle(boolean round);
 
 public void setRadiusX(float rad);
 public void setRadiusY(float rad);
 public void setRadii(float rad);
 
 public float width();
 public float height();
 public float left();
 public float right();
 
 public PointF getPosition();
 public void setPosition(PointF p);
 public void setPosition(float x, float y);
 public RectF getCoordinate();
 
 public boolean contains(float x, float y);
 public boolean contains(PointF p);
 
 public boolean intersect(Rectangle rect);
 
 public PointF getPointAt(Intersection point);
 
 
}
