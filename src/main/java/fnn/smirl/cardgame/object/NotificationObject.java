package fnn.smirl.cardgame.object;
import android.graphics.*;
import fnn.smirl.cardgame.object.core.enums.*;
import fnn.smirl.cardgame.object.core.interfacing.*;

import android.content.Context;
import fnn.smirl.cardgame.object.core.abstracting.GObject;

public class NotificationObject extends GObject 
implements Alignment, Padding, Stroke, Text, Rectangle, Shadow
{

 private Paint paint, paint2, paint3;
 private float scale, radX = 0, radY = 0;
 private RectF tempRect, rect;
 private float padLeft = 0, padTop = 0, padRight = 0, padBottom = 0;
 private boolean rectVisible = false, isRoundRectangle = false,
 strokeVisible = false;
 private float parentX = 0, parentY = 0;
 private String message = "", baseText = "";
 private int shadowColor = Color.GREEN;
 private float shadowRadius = 0;
 private PointF shadowPoint = new PointF(0, 0);

 public NotificationObject(Context ctx, PointF parentSize) {
	super(ctx, parentSize);
	scale = ctx.getResources().getDisplayMetrics().density;
	rect = new RectF(0, 0, 0, 0);
	tempRect = new RectF(0, 0, 0, 0);
	paint = new Paint();
	paint2 = new Paint();
	paint3 = new Paint();
	paint2.setAntiAlias(true);
	paint2.setStyle(Paint.Style.FILL);
	paint.setStyle(Paint.Style.FILL);
	paint3.setAntiAlias(true);
	paint3.setStyle(Paint.Style.STROKE);
	paint.setAntiAlias(true);
	paint.setFakeBoldText(true);
	paint.setTextSize(20 * scale);
	
 }

 @Override
 public void setText(String msg) {
	message = msg;
	tempRect.set(tempRect.left, tempRect.top, tempRect.left + paint.measureText(message),
							 tempRect.top + paint.getTextSize());

 }

 @Override
 public void setReferenceText(String bt) {
	baseText = bt;
 }

 @Override
 public void setTextColor(int color) {
	paint.setColor(color);

 }
 @Override
 public void setStrokeColor(int color) {
	paint3.setColor(color);
 }

 @Override
 public void setRectangleColor(int color) {
	paint2.setColor(color);
 }
 
 @Override
 public void setRectangleWidth(float width) {
	// not applicable here
 }

 @Override
 public void setTextSize(float size) {
	paint.setTextSize(size * scale);
 }

 @Override
 public void setRectangleVisible(boolean visible) {
	rectVisible = visible;
 }

 /**
	* should be called after setMessage() and setPosition()
	* but before onDraw().
	*/
 public void organize() {
	float f = (paint.measureText(baseText) - paint.measureText(message)) / 2;
	tempRect = new RectF(tempRect.left, tempRect.top,
											 tempRect.left + paint.measureText(message), tempRect.top + paint.getTextSize());
	rect = new RectF(tempRect.left - f - padLeft,
									 tempRect.top - padTop, tempRect.right + f + scale + padRight,
									 tempRect.bottom + (paint.getFontMetrics().descent * scale) + padBottom);
 }

 public void setLocation(float x, float y) {
	tempRect.offsetTo(x, y);
 }

 @Override
 public void setTextPaint(Paint paint) {
	this.paint = paint;
 }

 @Override
 public void setRectanglePaint(Paint paint) {
	this.paint2 = paint;
 }
 @Override
 public void setStrokePaint(Paint paint) {
	this.paint3 = paint;
 }
 @Override
 public void setStrokeWidth(float sWidth) {
	this.paint3.setStrokeWidth(sWidth);
 }

 @Override
 public void setTextStyle(TextStyle style) {
	switch (style) {
	 case NORMAL:
		paint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
		break;
	 case ITALIC:
		paint.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
		break;
	 case BOLD:
		paint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
		break;
	 case BOLD_ITALIC:
		paint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
		break;
	}
 }

 @Override
 public void setRoundRectangle(boolean round) {
	isRoundRectangle = round;
 }
 @Override
 public void setStrokeVisible(boolean stroke) {
	strokeVisible = stroke;
 }

 @Override
 public void setRadii(float radius) {
	radX = radius;
	radY = radius;
 }

 @Override
 public void setRadiusX(float radius) {
	radX = radius;
 }


 @Override
 public void setRadiusY(float radius) {
	radY = radius;
 }


 /**
	* x and y range from 0 and 1
	*/
 @Override
 public void setPosition(float x, float y) {
	// TODO: Implement this method
	if (x > 1) x = 1;
	if (x < 0) x = 0;
	if (y > 1) y = 1;
	if (y < 0) y = 0;
	tempRect.offsetTo(parentSize.x * x, parentSize.y * y);

 }

 @Override
 public void setPosition(PointF p) {
	// TODO: Implement this method
	setPosition(p.x, p.y);
 }

 @Override
 public float width() {
	// TODO: Implement this method
	return rect.width();
 }
 
 @Override
 public float right() {
	// TODO: Implement this method
	return rect.right;
 }
 
 @Override
 public float left() {
	// TODO: Implement this method
	return rect.left;
 }

 @Override
 public float getTextWidth() {
	return paint.measureText(message);
 }

 @Override
 public float getTextWidth(String text) {
	return paint.measureText(text);
 }

 @Override
 public float height() {
	// TODO: Implement this method
	return rect.height();
 }

 @Override
 public RectF getCoordinate() {
	// TODO: Implement this method
	return rect;
 }

 @Override
 public PointF getPosition() {
	// TODO: Implement this method
	return new PointF(rect.left, rect.top);
 }

 @Override
 public boolean contains(float x, float y) {
	// TODO: Implement this method
	return rect.contains(x, y);
 }

 @Override
 public boolean contains(PointF p) {
	// TODO: Implement this method
	return rect.contains(p.x, p.y);
 }

 @Override
 public boolean intersect(Rectangle recta) {
	// TODO: Implement this method
	return rect.intersect(recta.getCoordinate());
 }

 @Override
 public PointF getPointAt(Intersection point) {
	// TODO: Implement this metho
	organize();
	
	switch (point) {
	 case TOP_LEFT:
		return new PointF(rect.left, rect.top); 
	 case TOP_RIGHT:
		return new PointF(rect.right, rect.top);
	 case BOTTOM_LEFT:
		return new PointF(rect.left, rect.bottom) ;
	 case BOTTOM_RIGHT:
		return new PointF(rect.right, rect.bottom);
	 case CENTER_LEFT:
		return new PointF(rect.left, rect.centerY());
	 case CENTER_TOP:
		return new PointF(rect.centerX(), rect.top);
	 case CENTER_RIGHT:
		return new PointF(rect.right, rect.centerY());
	 case CENTER_BOTTOM:
		return new PointF(rect.centerX(), rect.bottom);

	 default:
		return new PointF(rect.left, rect.top);
	}
 }

 @Override
 public void align(Compas align) {
	// TODO: Implement this method
	switch (align) {
	 case LEFT:
		tempRect.offsetTo(parentX, tempRect.top);
		break;
	 case TOP:
		tempRect.offsetTo(tempRect.left, parentY);
		break;
	 case RIGHT:
		tempRect.offsetTo(parentSize.x - tempRect.width(), tempRect.top);
		break;
	 case BOTTOM:
		tempRect.offsetTo(tempRect.left, parentSize.y - tempRect.height());
		break;
	 case CENTER:
		tempRect.offsetTo((parentSize.x - tempRect.width()) / 2, (parentSize.y - tempRect.height()) / 2);
		break;

	}
 }


 @Override
 public void clearAlign() {
	// TODO: Implement this method
	tempRect.offsetTo(0, 0);
 }


 @Override
 public void pad(SimpleCompas position, float distance) {
	// TODO: Implement this method
	distance *= scale;
	switch (position) {
	 case LEFT:
		padLeft = distance;
		break;
	 case TOP:
		padTop = distance;
		break;
	 case RIGHT:
		padRight = distance;
		break;
	 case BOTTOM:
		padBottom = distance;
		break;
	}
 }

 @Override
 public void padHorizontal(float distance) {
	// TODO: Implement this method
	distance *= scale;
	padLeft = distance;
	padRight = distance;
 }

 @Override
 public void padVerticle(float distance) {
	// TODO: Implement this method
	distance *= scale;
	padTop = distance;
	padBottom = distance;
 }

 @Override
 public void pad(float distance) {
	// TODO: Implement this method
	distance *= scale;
	padLeft = distance;
	padTop = distance;
	padRight = distance;
	padBottom = distance;
 }

 @Override
 public void clearPad() {
	// TODO: Implement this method
	padLeft = 0;
	padTop = 0;
	padRight = 0;
	padBottom = 0;
 }
 
 @Override
 public void shadowColor(int color) {
	// TODO: Implement this method
	this.shadowColor = color;
 }

 @Override
 public void shadowRadius(float rad) {
	// TODO: Implement this method
	this.shadowRadius = rad;
 }

 @Override
 public void shadowPoint(PointF point) {
	// TODO: Implement this method
	this.shadowPoint = point;
 }

 @Override
 public void shadow(int color, float radius, PointF point) {
	// TODO: Implement this method
	this.shadowColor = color;
	this.shadowRadius = radius;
	this.shadowPoint = point;
 }

 @Override
 public void onDraw(Canvas canvas) {
	// TODO: Implement this method
	paint.setShadowLayer(shadowRadius, shadowPoint.x, shadowPoint.y, shadowColor);
	if (rectVisible) {
	 if (isRoundRectangle) {
		canvas.drawRoundRect(rect, radX, radY, paint2);
	 } else {
		canvas.drawRect(rect, paint2);
	 }
	}
	if (strokeVisible) {
	 if (isRoundRectangle) {
		canvas.drawRoundRect(rect, radX, radY, paint3);
	 } else {
		canvas.drawRect(rect, paint3);
	 }
	}
	canvas.drawText(message, tempRect.left, (tempRect.top + paint.getTextSize()), paint);
 }

}
