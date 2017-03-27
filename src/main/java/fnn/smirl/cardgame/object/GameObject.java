package fnn.smirl.cardgame.object;
import android.graphics.*;

import fnn.smirl.cardgame.object.core.abstracting.GObject;

public class GameObject extends GObject{

 
 private Bitmap bmp;
 private RectF rect;
 private PointF position;

 public GameObject(Bitmap bmp) {
	super(null, null);
	this.bmp = bmp;
	rect = new RectF();
	position = new PointF(0, 0);
	rect.set(0, 0, bmp.getWidth(), bmp.getHeight());
 }

 public void setPosition(float x, float y) {
	this.rect.offsetTo((int)x, (int)y);
 }

 public float getWidth() {
	return bmp.getWidth();
 }
 public float getHeight() {
	return bmp.getHeight();
 }

 public RectF getCoordinate() {
	return rect;
 }
 public PointF getPosition() {
	position.set(rect.left, rect.top);
	return position;
 }

 public boolean contains(float x, float y) {
	return rect.contains((int)x, (int)y);
 }
 
 public void  onDraw(Canvas canvas) {
	canvas.drawBitmap(bmp, null, rect, null);
 }

}
