package fnn.smirl.cardgame.component;

import android.graphics.*;
import fnn.smirl.cardgame.subactivity.*;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.MotionEvent;
import android.view.View;
import fnn.smirl.cardgame.R;
import fnn.smirl.cardgame.MainActivity;
import fnn.smirl.cardgame.object.NotificationObject;
import fnn.smirl.cardgame.object.core.enums.TextStyle;
import fnn.smirl.cardgame.util.Toaster;


public class TitleView extends View {

 private Context context;
 NotificationObject btnPlay, btnStat, btnOption, btnHelp;
 private Bitmap titleGraphic;
 private boolean playButtonPressed, statButtonPressed, optionButtonPressed, helpButtonPressed;
 private int screenW, screenH;
 Resources res = MainActivity.res;

 public TitleView(Context context) {
	super(context);
	this.context = context; 
 }

 @Override
 protected void onSizeChanged(int w, int h, int oldw, int oldh) {
	// TODO: Implement this method
	super.onSizeChanged(w, h, oldw, oldh);
	screenW = w; screenH = h;
	init();
 }
 
 private void init(){
	setBackgroundColor(Color.GRAY);
	titleGraphic = BitmapFactory.
	 decodeResource(res, R.drawable.cards_logo);
	btnPlay =
	 new NotificationObject(context, new PointF(screenW, screenH));		
	btnStat=
	 new NotificationObject(context, new PointF(screenW, screenH)); 
	btnOption =
	 new NotificationObject(context, new PointF(screenW, screenH)); 
	btnHelp =
	 new NotificationObject(context, new PointF(screenW, screenH)); 
	
 }

 @Override
 protected void onDraw(Canvas canvas) {
	canvas.drawBitmap(titleGraphic, 
										(screenW - titleGraphic.getWidth()) * 0.5f, screenH * 0.1f, null);
	String baseText = "i love android";
	// Play button	
	drawBtn(btnPlay, "Jouer", 0.5f, 0.55f, canvas, baseText);
	// Stat button
	drawBtn(btnStat, "Statistique", 0.5f, 0.65f, canvas, baseText);
	// Option button
	drawBtn(btnOption, "Configurer", 0.5f, 0.75f, canvas, baseText);
	// help button
	drawBtn(btnHelp, "Aide", 0.5f, 0.85f, canvas, baseText);

	
 }

 private void drawBtn(NotificationObject btn, String txt, float x, float y, Canvas canvas, String baseText) {
	btn.setText(txt);
	btn.setReferenceText(baseText);
	btn.padHorizontal(5);
	btn.padVerticle(2);
	btn.setTextSize(35);
	btn.setLocation((screenW - btn.getTextWidth()) * x, screenH * y);
	btn.setTextColor(res.getColor(R.color.text_color_2));
	btn.setTextStyle(TextStyle.BOLD_ITALIC);
	btn.setRectangleColor(res.getColor(R.color.rectangle_color_2));
	btn.setRectangleVisible(true);
	btn.setRoundRectangle(true);
	btn.shadow(res.getColor(R.color.shadow_color_1), 3, new PointF(3, 3));
	btn.setRadii(30);
	btn.setStrokeColor(res.getColor(R.color.stroke_color_2));
	btn.setStrokeWidth(6);
	btn.setStrokeVisible(true);

	btn.organize();
	btn.onDraw(canvas);
 }

 @Override
 public boolean onTouchEvent(MotionEvent event) {
	int eventaction = event.getAction();
	int X = (int)event.getX();
	int Y = (int)event.getY();
	switch (eventaction) {
	 case MotionEvent.ACTION_DOWN:
		playButtonPressed = btnPlay.contains(X, Y);
		statButtonPressed = btnStat.contains(X, Y);
		optionButtonPressed = btnOption.contains(X, Y);
		helpButtonPressed = btnHelp.contains(X, Y);
		break;
	 case MotionEvent.ACTION_MOVE:

		break;
	 case MotionEvent.ACTION_UP:
		if (playButtonPressed) {
		 Intent gameIntent = 
			new Intent(context, GameActivity.class);
		 context.startActivity(gameIntent);
		}
		playButtonPressed = false;
		
		if (statButtonPressed) {
	 Intent statsIntent = 
		new Intent(context, StatsActivity.class);
	 context.startActivity(statsIntent);
		// Toaster.toast(context, "Très bientôt", false);
		}
		statButtonPressed = false;
		
		if (helpButtonPressed) {
//		 Intent gameIntent = 
//			new Intent(context, GameActivity.class);
//		 context.startActivity(gameIntent);
		 Toaster.toast(context, "Très bientôt", false);
		}
		helpButtonPressed = false;

		if (optionButtonPressed) {
		 Intent optionIntent = 
			new Intent(context, OptionActivity.class);
		 context.startActivity(optionIntent);
		}
		optionButtonPressed = false;
		break;
	}
	invalidate();

	return true;
 }
}
