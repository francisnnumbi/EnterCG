package fnn.smirl.cardgame.object;
import android.graphics.*;

import android.content.Context;
import fnn.smirl.cardgame.R;
import fnn.smirl.cardgame.MainActivity;
import fnn.smirl.cardgame.object.core.abstracting.GObject;
import fnn.smirl.cardgame.object.core.interfacing.GCard;
import fnn.smirl.cardgame.util.CardMap;

public class Card extends GObject implements GCard {

 private float width, height;
 private int id = 0, suit=0, rank=0, scoreValue = 0;
 private Bitmap bmp;
 private Rect src;
 private RectF rect;
 private float x=0, y=0;

 public Card(Context ctx, Bitmap bmp, Point pos, PointF parent) {
	super(ctx, parent);
	float scale = ctx.getResources().getDisplayMetrics().density;
	this.suit = (pos.y + 1) * 100;
	this.rank = pos.x + 1;
	if(pos.y > 3){
	 rank += 13;
	}
	this.id = suit + rank;
	scoreValue = CardMap.getValueFor(rank);

	this.width = (bmp.getWidth() / MainActivity.res.getInteger(R.integer.CARD_COLUMNS));
	this.height = (bmp.getHeight() / MainActivity.res.getInteger(R.integer.CARD_ROWS));
	this.bmp = bmp;
	rect = new RectF((int)x, (int)y, (int)(x + width), (int)(y + height));
	int srcX = (int)((pos.x * width) +(scale * pos.x* 0.5f));
	int srcY = (int)((pos.y * height) +(scale * pos.y * 0.5f));
	//int destW = (int)parentSize.x / MainActivity.res.getInteger(R.integer.CARD_COLUMNS);
	//int destH= (int)parentSize.y / MainActivity.res.getInteger(R.integer.CARD_ROWS);
	src = new Rect(srcX, srcY, (int)(srcX + width), (int)(srcY + height));
 }

 public int getId() {
	return id;
 }
 public void setPosition(float x, float y) {
	this.x = x;
	this.y = y;
	rect.offsetTo((int)x, (int)y);
 }
 
 public void moveBy(PointF p){
	moveBy(p.x, p.y);
 }
 
 public void moveBy(float X, float Y) {
	x = x + X;
	y = y + Y;
	rect.offsetTo((int)x, (int)y);
 }

 public float getWidth() {
	return width;
 }
 public float getHeight() {
	return height;
 }
 
 public PointF getCenter(){
	return new PointF(rect.centerX(), rect.centerY());
 }

 public RectF getCoordinate() {
	return rect;
 }

 public Rect getSrcCoordinate() {
	return src;
 }

 public void  onDraw(Canvas canvas) {
	canvas.drawBitmap(bmp, src, rect, null);
 }

 public int getSuit() {
	return suit;
 }

 public int getRank() {
	return rank;
 }

 public int getScoreValue() {
	return scoreValue;
 }

 public boolean contains(float x, float y) {
	return rect.contains((int)x, (int)y);
 }

 public boolean intersect(GObject gobject) {
	return RectF.intersects(rect, ((Card)gobject).rect);
 }

 public boolean isMoveValid(int _rank, int _suit, boolean choseSuit) {
	boolean b = false;
	if (this.rank == MainActivity.REQUESTER) {
	 b = true;
	} else if(this.rank == MainActivity.DRAW_FIVE_1 ||
	this.rank == MainActivity.DRAW_FIVE_2){
	 b = true;
	}else if(_rank == MainActivity.DRAW_FIVE_1 ||
	 _rank == MainActivity.DRAW_FIVE_2){
	 b = true;
	}
	else if (!choseSuit) {
	 if (this.rank == _rank) b = true;
	 else if (this.suit == _suit) b = true;
	} else if (choseSuit) {
	 if (this.suit == _suit) b = true;
	}
	return b;
 }

}
