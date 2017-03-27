package fnn.smirl.cardgame.object.core.interfacing;
import android.graphics.Paint;
import fnn.smirl.cardgame.object.core.enums.TextStyle;

public interface Text
{
 public void setText(String text);
 public void setReferenceText(String text);
 public void setTextSize(float size);
 public void setTextPaint(Paint paint);
 public void setTextStyle(TextStyle style);
 public void setTextColor(int color);
 
 public float getTextWidth();
 public float getTextWidth(String text);
}
