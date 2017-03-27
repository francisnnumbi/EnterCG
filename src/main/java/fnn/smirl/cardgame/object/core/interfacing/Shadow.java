package fnn.smirl.cardgame.object.core.interfacing;
import android.graphics.Color;
import android.graphics.PointF;

public interface Shadow
{
 public void shadowColor(int color);
 public void shadowRadius(float rad);
 public void shadowPoint(PointF point);
 public void shadow(int color, float radius, PointF point);
}
