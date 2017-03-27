package fnn.smirl.cardgame.component;
import android.graphics.*;
import android.view.*;
import fnn.smirl.cardgame.object.*;
import fnn.smirl.cardgame.util.*;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.widget.ImageButton;
import fnn.smirl.cardgame.R;
import fnn.smirl.cardgame.MainActivity;
import fnn.smirl.cardgame.object.core.enums.AILevel;
import fnn.smirl.cardgame.object.core.enums.TextStyle;
import java.util.HashMap;

public class GameView extends SurfaceView {
 public static final String TAG="GameView.class";
 public Hand deck = new Hand();
 public Hand myHand = new Hand();
 public Hand oppHand = new Hand();
 public Hand oppMoveHand = new Hand();
 public Hand discardPile = new Hand();
 private SurfaceHolder holder;
 private GameLoopThread gameLoopThread;
 public static boolean blink = false;
 public static int blinkRate = 0;
 private boolean choseSuit = false;
 private HashMap<Integer, GameObject> chosenSuit = new HashMap<Integer, GameObject>();
 private int selectedSuit = 0;
 public GameObject prevBtn = null, nextBtn = null;

 private static Resources res = MainActivity.res;
 public static final float CARD_HEIGHT_SCALE = res.getFraction(R.fraction.CARD_HEIGHT_SCALE, 1, 1);
 public static final float CARD_WIDTH_SCALE = res.getFraction(R.fraction.CARD_WIDTH_SCALE, 1, 1);;
 private static final int HAND_SIZE = res.getInteger(R.integer.HAND_SIZE);
 private static final int HAND_START= res.getInteger(R.integer.HAND_START);

 private String turnToPlay = "";
 private boolean startOppMove = true, oppCanDrawCard = false,
 forceOppToDrawCard = false, forceUserToDrawCard = false,
 userCanDrawCard  = false, hasGameEnded = false;
 private float scale;
 private Paint whitePaint;
 private Bitmap bg, cardPack, nextCardBtn, prevCardBtn;
 private Card cardBack1, discardCard;
 private Card _c = null;
 private int screenW, screenH, scaledCardW, scaledCardH,
 movingCardIdx = -1, movingX, movingY,
 validRank = MainActivity.REQUESTER, validSuit = 0;
 private int numberToDraw = 0, numberToDrawUser = 0;
 private AI ai =
 new AI();

 Popper endGameNotif;
 private String endGameMessage = "";

// private int randomBackColumn;
 private Context myContext;

 public GameView(Context context) {
	super(context);
	myContext = context;
	gameLoopThread = new GameLoopThread(this);
	holder = getHolder();
	scale = myContext.getResources().getDisplayMetrics().density;

	holder.addCallback(new SurfaceHolder.Callback(){

		@Override
		public void surfaceCreated(SurfaceHolder p1) {
		 // TODO: Implement this method
		 startLooper();
		}

		@Override
		public void surfaceChanged(SurfaceHolder p1, int p2, int p3, int p4) {
		 // TODO: Implement this method
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder p1) {
		 // TODO: Implement this method
		 killLooper();
		}


	 });
	 
	// setBackgroundResource(R.drawable.flower_menubackground);
 }

 public void startLooper() {
	if (!gameLoopThread.isAlive()) {
	 gameLoopThread.setRunning(true);
	 gameLoopThread.start();
	}
 }

 public void killLooper() {
	boolean retry = true;
	gameLoopThread.setRunning(false);
	while (retry) {
	 try {
		gameLoopThread.join();
		retry = false;
	 }
	 catch (InterruptedException e) {}
	}
 }

 private void init() {
	prepareCards();
	if (!MainActivity.table.myTurn) {
	 turnToPlay = "";
	 makeComputerPlay();
	} else {
	 turnToPlay = "Votre Tour";
	}
 }

//scale to screen resolution
 private Bitmap scaledBmp(Bitmap bmp) {
	float theX = screenW * 2.25f; // bmp.getWidth() * 0.4f;
	return Bitmap
	 .createScaledBitmap(bmp, 
											 (int)theX, 
											 (int)(theX * 0.70f), 
											 false);
 }

 //scale to screen size
 private Bitmap scaleToScreenBmp(Bitmap bmp) {
	return Bitmap
	 .createScaledBitmap(bmp, screenW, screenH, false);
 }
 

// private int randomInt(int floor, int ceilling) {
//	Random rr = new Random();
//	return floor + rr.nextInt(ceilling - floor);
// }

 private void preparePaint() {
	whitePaint = new Paint();
	whitePaint.setAntiAlias(true);
	whitePaint.setColor(res.getColor(R.color.text_color_1));
	whitePaint.setStyle(Paint.Style.STROKE);
	whitePaint.setTextAlign(Paint.Align.LEFT);
	whitePaint.setStyle(Paint.Style.FILL);
	whitePaint.setTextSize(scale * 15);
	whitePaint.setFakeBoldText(true);
 }

 @Override
 protected void onDraw(Canvas canvas) {
	preparePaint();
	canvas.drawColor(Color.BLACK);
	drawBackground(canvas);
	
	drawAllTextRelated(canvas);
	drawEndGameState(canvas);
	if (!hasGameEnded) {
	 drawOpponentCards(canvas);
	 drawStackCards(canvas);
	 drawDiscardCards(canvas);
	 drawChosenSuit(canvas);
	 drawOpponentPlayedCard(canvas);
	 drawOppFromStack(canvas);
	 drawUserCards(canvas);
	 drawUserFromStack(canvas);
	 drawCardArrows(canvas);
	 drawTextToUser(canvas);
	}
 }
 
 private void drawBackground(Canvas canvas){
	canvas.drawBitmap(bg, null, new Rect(0, 0, screenW, screenH), null);
	
 }

 private void drawEndGameState(Canvas canvas) {
	if (hasGameEnded) {
	 endGameNotif = new Popper(myContext, new PointF(screenW, screenH));
	 endGameNotif.setText("TERMINÉ", endGameMessage, "Touche pour Continuer");
	 //endGameNotif.setTextPaint(whitePaint);
	 endGameNotif.setTextSize(20);
	 endGameNotif.setTextStyle(TextStyle.BOLD_ITALIC);
	 endGameNotif.setTextColor(res.getColor(R.color.text_color_2));
	 endGameNotif.setStrokeColor(res.getColor(R.color.stroke_color_2));
	 endGameNotif.setReferenceText(endGameMessage);
	 endGameNotif.setRectangleColor(res.getColor(R.color.rectangle_color_3));
	 endGameNotif.setRectangleVisible(true);
	 endGameNotif.setRoundRectangle(true);
	 endGameNotif.setRadii(10);
	 endGameNotif.setStrokeVisible(true);
	 endGameNotif.setStrokeWidth(3);
	 endGameNotif.padHorizontal(10);
	 endGameNotif.padVerticle(10);
	 endGameNotif.shadow(res.getColor(R.color.shadow_color_1), 0, new PointF(3, 3));
	 endGameNotif.setLocation((screenW - endGameNotif.getTextWidth()) / 2, ((screenH - endGameNotif.getCoordinate().height()) / 2)) ;
	 endGameNotif.organize();
	 endGameNotif.onDraw(canvas);
	}
 }

 private void drawAllTextRelated(Canvas canvas) {
	drawTexting(res.getString(R.string.a1) + " " +
							Integer.toString(MainActivity.table.oppScore), 5 * scale, 
							5 * scale, canvas, whitePaint);
		String gLevel = "Niveau " + MainActivity.table.level.getLevel();					
	drawTexting(gLevel, (screenW - whitePaint.measureText(gLevel))/ 2, (10 * scale), canvas, whitePaint);

	int statTot = MainActivity.table. statW + MainActivity.table.statL;
	int statP = 0;
	if (statTot != 0)statP = ((MainActivity.table.statW * 100) / statTot);

	String statString = res.getString(R.string.a2) + " " + statP + "%";
	drawTexting(statString, screenW - whitePaint.measureText(statString) - (5 * scale), 
							5 * scale, canvas, whitePaint);	

	String statString2 = "Gagné: " + MainActivity.table.statW;
	drawTexting(statString2, screenW - whitePaint.measureText(statString2) - (80 * scale), 
							35 * scale, canvas, whitePaint);	

	String statString3 = "Perdu: " + MainActivity.table.statL;
	drawTexting(statString3, screenW - whitePaint.measureText(statString3) - (5 * scale), 
							35 * scale, canvas, whitePaint);	

	drawTexting("Mon Score: " +
							Integer.toString(MainActivity.table.myScore), (5 * scale), 
							screenH - whitePaint.getTextSize() - (10 * scale), canvas, whitePaint);

	String limScore = "Score Limite: " + MainActivity.table.max_score ;
	drawTexting(limScore, screenW - whitePaint.measureText(limScore) - (5 * scale), 
							screenH - whitePaint.getTextSize() - (10 * scale), canvas, whitePaint);									

 }

 private void drawOpponentCards(Canvas canvas) {
	for (int i = 0; i < oppHand.size(); i++) {
	 Card cardBack = new Card(myContext, cardPack, new Point(2, 4), new PointF(screenW, screenH));
	 cardBack.setPosition(i * (scale * 10), whitePaint.getTextSize() + (50 * scale));
	 cardBack.onDraw(canvas);

	}

 }

 private void drawStackCards(Canvas canvas) {
	for (int a = 0; a< deck.size(); a++) {
	 if (a != 0) {
		Card b = new Card(myContext, cardPack, new Point(2, 4), new PointF(screenW, screenH));
		b.setPosition(
		 (screenW / 2) - b.getWidth() - ((10 + (a*0.2f)) * scale),
		 ((screenH - b.getHeight() - (a*0.3f)) / 2));
		b.onDraw(canvas);
	 } else {
		cardBack1 = new Card(myContext, cardPack, new Point(2, 4), new PointF(screenW, screenH));
		cardBack1.setPosition(
		 (screenW / 2) - cardBack1.getWidth() - ((10 + (a*0.2f)) * scale),
		 ((screenH - cardBack1.getHeight() - (a*0.3f)) / 2));
		cardBack1.onDraw(canvas);
	 } 
	 }
	}

 private void drawDiscardCards(Canvas canvas) {
	discardCard = discardPile.peek();
	//Log.v(TAG, "138 >> discardCard is null = " + (discardCard == null));
	for (int a = 0; a < discardPile.size(); a++) {
	 if(a != discardPile.size() - 1){
	 Card b = discardPile.get(a);                   
	 b.setPosition(
		(screenW / 2) + ((10 - (a*0.2f)) * scale),
		((screenH - b.getHeight()) / 2) - (a*0.3f));
	 b.onDraw(canvas);
	}
	else{
	discardCard.setPosition(
	 (screenW / 2) + ((10 - (a*0.2f))* scale),
	 ((screenH - discardCard.getHeight())/ 2) - (a*0.3f));
	discardCard.onDraw(canvas);
 }
 }
 }

 private void drawChosenSuit(Canvas canvas) {
	if (selectedSuit > 0) {
	 GameObject go = chosenSuit.get(selectedSuit);
	 go.setPosition(discardCard.getCoordinate().centerX() - (go.getWidth() / 2),
									discardCard.getCoordinate().top - (go.getHeight() + (5 * scale)));
	 go.onDraw(canvas);
	}
 }

 private void drawTextToUser(Canvas canvas) {
	String ct = "- " + oppHand.size() + " -";
	NotificationObject nbOp = new NotificationObject(myContext, new PointF(screenW, screenH));
	nbOp.setText(ct);
	nbOp.setTextPaint(whitePaint);
	nbOp.setStrokeColor(res.getColor(R.color.stroke_color_2));
	nbOp.setReferenceText(ct);
	nbOp.setStrokeVisible(true);
	nbOp.setStrokeWidth(1);
	nbOp.pad(1);
	nbOp.shadow(res.getColor(R.color.shadow_color_1), 2, new PointF(3, 3));
	nbOp.setLocation((25 * scale), (whitePaint.getTextSize() * 2) + (10 * scale));
	nbOp.organize();
	nbOp.onDraw(canvas);

	String ut = "- " + myHand.size() + " -";
	NotificationObject nbMe = new NotificationObject(myContext, new PointF(screenW, screenH));
	nbMe.setText(ut);
	nbMe.setReferenceText(ut);
	nbMe.setTextPaint(whitePaint);
	nbMe.setStrokeColor(res.getColor(R.color.stroke_color_2));
	nbMe.setStrokeVisible(true);
	nbMe.setStrokeWidth(1);
	nbMe.pad(1);
	nbMe.shadow(res.getColor(R.color.shadow_color_1), 2, new PointF(3, 3));
	nbMe.setLocation((screenW - whitePaint.measureText(ut)) / 2, 
									 screenH - discardCard.getCoordinate().height() - (whitePaint.getTextSize() * 2) - (40 * scale));
	nbMe.organize();
	nbMe.onDraw(canvas);
 }

 private void drawTexting(String txt, float _x, float _y, Canvas canvas, Paint paint) {
	NotificationObject nobj = new NotificationObject(myContext, new PointF(screenW, screenH));
	nobj.setText(txt);
	nobj.setReferenceText(txt);
	nobj.setTextPaint(paint);
	nobj.setRectangleColor(res.getColor(R.color.rectangle_color_1));
	nobj.setStrokeColor(res.getColor(R.color.stroke_color_1));
	nobj.setStrokeWidth(2);
	nobj.setTextStyle(TextStyle.ITALIC);
	nobj.setStrokeVisible(true);
	nobj.setRectangleVisible(true);
	nobj.setRoundRectangle(true);
	nobj.padVerticle(2);
	nobj.padHorizontal(3);
	nobj.setRadii(10);
	nobj.shadow(res.getColor(R.color.shadow_color_1), 2, new PointF(3, 3));
	nobj.setLocation(_x, _y);
	nobj.organize();
	nobj.onDraw(canvas);
 }

 private void drawCardArrows(Canvas canvas) {
	if (myHand.size() > HAND_SIZE) {
	 nextBtn = new GameObject(nextCardBtn);
	 nextBtn.setPosition(screenW - nextBtn.getWidth() - (10 * scale),
											 screenH - nextBtn.getHeight() - discardCard.getCoordinate().height() - (60 * scale));
	 nextBtn.onDraw(canvas);

	 prevBtn = new GameObject(prevCardBtn);
	 prevBtn.setPosition((10 * scale), screenH - prevBtn.getHeight() - discardCard.getCoordinate().height() - (60 * scale));
	 prevBtn.onDraw(canvas);
	}
 }

 private void drawUserCards(Canvas canvas) {
	int g = myHand.size() < HAND_SIZE ? myHand.size() : HAND_SIZE;
	for (int i = 0; i < g; i++) {
	 if (i == movingCardIdx) {
		Card c = myHand.getMovingCard();
		c.setPosition(movingX, movingY);
		c.onDraw(canvas);
	 } else if (i < HAND_SIZE) {
		if (i < myHand.size()) {
		 Card c =myHand.get(i) ;
		 //Log.v(TAG, "162 >> myHand card is null = " + (c == null));
		 c.setPosition(i * (c.getWidth() - (10 * scale)),
									 screenH - c.getHeight() - whitePaint.getTextSize() - (30 * scale));
		 c.onDraw(canvas);
		}
	 }
	}
 }

 // animate opponent played card
 private void drawOpponentPlayedCard(Canvas canvas) {
	if (!oppMoveHand.isEmpty()) {
	 Card cardy = oppMoveHand.peek();
	 if (startOppMove) {
		cardy.setPosition(1 * (scale * 10), whitePaint.getTextSize() + (50 * scale));
		startOppMove = false;
	 }
	 PointF pf = new PointF((screenW / 2) + (20 * scale) - cardy.getCoordinate().left, (screenH / 2) - cardy.getCoordinate().top);
	 // move
	 cardy.moveBy(cardMovingScale(pf));
	 cardy.onDraw(canvas);

	 if (discardCard.intersect(cardy)) {
		discardPile.pickTopCardFrom(oppMoveHand);
		evaluateOppMove();
		startOppMove = true;
	 }
	}
 }

 private PointF cardMovingScale(PointF dest) {
	float _x = 0, _y = 0;  
	if (dest.x < 0 && dest.y < 0) {
	 float A = dest.x * (-1);
	 float B = dest.y * (-1);
	 if (A > B) {
		_x = A / B * (-1);
		_y = -1;
	 } else if (A < B) {
		_y = B / A * (-1);
		_x = -1;
	 } else {
		_x = -1;
		_y = -1;
	 }
	} else {
	 if (dest.x > dest.y) {
		_x   = dest.x / dest . y;
		_y = 1;
	 } else if (dest.x < dest.y) {
		_x   = 1;                       
		_y = dest.y / dest.x;
	 } else {
		_x   =    1; _y = 1;
	 }
	}
	return new PointF(_x * scale * 10, _y * scale * 10);
 }

 // animate the card user draws from stack
 private void drawUserFromStack(Canvas canvas) {
	// simple user draw
	if (userCanDrawCard) {
	 if (_c == null) {
		_c = new Card(myContext, cardPack, new Point(2, 4), new PointF(screenW, screenH));
		_c.setPosition((screenW / 2) - _c.getWidth() - (10 * scale),
									 (screenH - _c.getHeight()) / 2);
	 }
	 PointF pf = new PointF((2 * scale) - _c.getCoordinate().left, 
													screenH - _c.getHeight()	-	whitePaint.getTextSize() - (30 * scale) - _c.getCoordinate().top);
	 PointF pp = new PointF(pf.x * (-1), pf.y * (-1));
	 _c.moveBy(cardMovingScale(pp));
	 _c.onDraw(canvas);
	 if (_c.contains((2 * scale * 10) + (_c.getWidth() / 2), screenH - (_c.getHeight() / 2)	-	whitePaint.getTextSize() - (1 * scale))) {
		drawCard(myHand);
		MainActivity.table.myTurn = false;
		turnToPlay = "";
		_c = null;
		userCanDrawCard = false;
		MainActivity.update();
		makeComputerPlay();
	 }
	}

	// ff
	if (forceUserToDrawCard) {
	 if (_c == null) {
		_c = new Card(myContext, cardPack, new Point(2, 4), new PointF(screenW, screenH));
		_c.setPosition((screenW / 2) - _c.getWidth() - (10 * scale),
									 (screenH - _c.getHeight()) / 2);
	 }
	 PointF pf = new PointF((2 * scale) - _c.getCoordinate().left, 
													screenH - _c.getHeight()	-	whitePaint.getTextSize() - (30 * scale) - _c.getCoordinate().top);
	 PointF pp = new PointF(pf.x * (-1), pf.y * (-1));
	 _c.moveBy(cardMovingScale(pp));
	 _c.onDraw(canvas);
	 if (_c.contains((2 * scale * 10) + (_c.getWidth() / 2), screenH - (_c.getHeight() / 2)	-	whitePaint.getTextSize() - (1 * scale))) {
		drawCard(myHand);
		turnToPlay = "";
		MainActivity.update();
		_c = null;
		numberToDrawUser--;
		if (numberToDrawUser < 1) {
		 forceUserToDrawCard = false;
		 MainActivity.table.myTurn = false;
		 makeComputerPlay();
		}
	 }
	}
 }

 // animate the card opp draws from stack
 private void drawOppFromStack(Canvas canvas) {
	// simple opp draw
	if (oppCanDrawCard) {
	 if (_c == null) {
		_c = new Card(myContext, cardPack, new Point(2, 4), new PointF(screenW, screenH));
		_c.setPosition((screenW / 2) - _c.getWidth() - (10 * scale),
									 (screenH - _c.getHeight()) / 2);
	 }
	 PointF pf = new PointF((2 * scale * 10) - _c.getCoordinate().left, 
													whitePaint.getTextSize() + (50 * scale) - _c.getCoordinate().top);
	 _c.moveBy(cardMovingScale(pf));
	 _c.onDraw(canvas);
	 if (_c.contains((2 * scale * 10) + 10, whitePaint.getTextSize() + (60 * scale))) {
		drawCard(oppHand);
		MainActivity.table.myTurn = true;
		turnToPlay = "Votre Tour";
		MainActivity.update();
		_c = null;
		oppCanDrawCard = false;
	 }
	}

	// force opp to draw
	if (forceOppToDrawCard) {
	 if (_c == null) {
		_c = new Card(myContext, cardPack, new Point(2, 4), new PointF(screenW, screenH));
		_c.setPosition((screenW / 2) - _c.getWidth() - (10 * scale),
									 (screenH - _c.getHeight()) / 2);
	 }
	 PointF pf = new PointF((2 * scale * 10) - _c.getCoordinate().left, 
													whitePaint.getTextSize() + (50 * scale) - _c.getCoordinate().top);
	 _c.moveBy(cardMovingScale(pf));
	 _c.onDraw(canvas);
	 if (_c.contains((2 * scale * 10) + 10, whitePaint.getTextSize() + (60 * scale))) {
		drawCard(oppHand);
		MainActivity.update();
		_c = null;
		numberToDraw--;
		if (numberToDraw < 1) {
		 forceOppToDrawCard = false;
		}
	 }
	}
 }

 private void prepareCards() {
	
	cardPack = BitmapFactory.decodeResource(res, R.drawable.complete_card_deck1);
	nextCardBtn = BitmapFactory.decodeResource(res, R.drawable.arrow_next);
	prevCardBtn = BitmapFactory.decodeResource(res, R.drawable.arrow_prev);

	initChosableSuits();
	initCards();
	dealCards();
	discardPile.clear();
	drawCard(discardPile);
	//MainActivity.update();
	choseSuit = false;
	selectedSuit = 0;
	validSuit = discardPile.getCardProperty(Hand.CARD.SUIT, 0);
	validRank = discardPile.getCardProperty(Hand.CARD.RANK, 0);
	MainActivity.update();
 }

 @Override
 protected void onSizeChanged(int w, int h, int oldw, int oldh) {
	// TODO: Implement this method
	super.onSizeChanged(w, h, oldw, oldh);
	screenW = w; screenH = h;
	scaledCardW = (int)(screenW / CARD_WIDTH_SCALE);
	scaledCardH = (int) (scaledCardW * CARD_HEIGHT_SCALE);
	bg = BitmapFactory.decodeResource(res, R.drawable.flower_menubackground);
	bg = scaleToScreenBmp(bg);
	init();
 }



 @Override
 public boolean onTouchEvent(MotionEvent event) {
	// TODO: Implement this method
	int eventaction = event.getAction();
	int X = (int)event.getX();
	int Y = (int)event.getY();

	switch (eventaction) {
	 case MotionEvent.BUTTON_BACK:
		updateScore();
		killLooper();
		break;
	 case MotionEvent.ACTION_DOWN:
		if (MainActivity.table.myTurn) {
		 int g = myHand.size() < HAND_SIZE ? myHand.size() : HAND_SIZE;
		 for (int i = 0; i < g; i++) {
			if (myHand.get(i).contains(X, Y)) {
			 movingCardIdx = i;
			 myHand.setMovingCardIndex(movingCardIdx);
			 updateSelectedCardPosition(X, Y);
			 MainActivity.play(5, MainActivity.table.clickSound);
			}
		 }
		}

		if (hasGameEnded) {
		 if (endGameNotif.contains(X, Y)) {
			MainActivity.pause(2);
			MainActivity.play(1, MainActivity.table.musicSound);

			if (MainActivity.table.oppScore >= MainActivity.table.max_score || MainActivity.table.myScore >= MainActivity.table.max_score) {
			 Statistique staty = new Statistique(
				MainActivity.table.stats.size() + 1,
				MainActivity.table.myScore,
				MainActivity.table.oppScore);
			 MainActivity.table.stats.add(staty);
			 MainActivity.table.myScore = 0; MainActivity.table.oppScore = 0;
			 MainActivity.update();
			} 
			initNewHand();

			hasGameEnded = false;
		 }
		}
		break;

	 case MotionEvent.ACTION_MOVE:
		updateSelectedCardPosition(X, Y);
		//		if (movingCardIdx > -1) {
		//		}
		break;

	 case MotionEvent.ACTION_UP:

		try {
		 if (movingCardIdx > -1 && myHand.movingCardIntersect(discardCard) && 
				 (myHand.isMovingCardValid(validRank, validSuit, choseSuit))) {
			MainActivity.play(4, MainActivity.table.clickSound);
			validRank = myHand.getMovingCardProperty(Hand.CARD.RANK);
			validSuit = myHand.getMovingCardProperty(Hand.CARD.SUIT);
			myHand.passMovingCardTo(discardPile);
			choseSuit = false;
			selectedSuit = 0;
			if (myHand.isEmpty()) {
			 endHand();
			} else {
			 switch (validRank) {
				case MainActivity.REQUESTER:
				 showChooseSuitDialog();
				 break;
				case MainActivity.STOPPER:
				 turnToPlay = "Votre Tour";
				 MainActivity.table. myTurn = true;
				 break;
				case MainActivity.DRAW_TWO:
				 // drawCard(oppHand, 2);
				 numberToDraw += 2;
				 forceOppToDrawCard = true;
				 turnToPlay = "Votre Tour";
				 MainActivity.table.myTurn = true;
				 break;
				case MainActivity.DRAW_FOUR:
				 //drawCard(oppHand, 4);
				 numberToDraw += 4;
				 forceOppToDrawCard = true;
				 turnToPlay = "Votre Tour";
				 MainActivity.table.myTurn = true;
				 break;
				case MainActivity.DRAW_FIVE_1:
				 // drawCard(oppHand, 5);
				 numberToDraw += 5;
				 forceOppToDrawCard = true;
				 turnToPlay = "Votre Tour";
				 MainActivity.table.myTurn = true;
				 break;
				case MainActivity.DRAW_FIVE_2:
				 //drawCard(oppHand, 5);
				 numberToDraw += 5;
				 forceOppToDrawCard = true;
				 turnToPlay = "Votre Tour";
				 MainActivity.table.myTurn = true;
				 break;
				default:
				 MainActivity.table.myTurn = false;
				 turnToPlay = "";
				 makeComputerPlay();
			 }

			}
		 }

		 if (movingCardIdx == -1 && MainActivity.table.myTurn &&
				 cardBack1.contains(X, Y)) {
			if (checkForValidDraw()) {
			 MainActivity.play(5, MainActivity.table.clickSound);
			 userCanDrawCard = true;
			} else {
			 Toaster.toast(myContext, res.getString(R.string.move_is_valid), true);
			}
		 } 

		 if (myHand.size() > HAND_SIZE && nextBtn.contains(X, Y)) {
			myHand.rotate(Hand.Rotate.RIGHT);
		 }
		 if (myHand.size() > HAND_SIZE && prevBtn.contains(X, Y)) {
			myHand.rotate(Hand.Rotate.LEFT);
		 }

		 movingCardIdx = -1;
		 myHand.setMovingCardIndex(movingCardIdx);
		}
		catch (Exception e) {
		 Log.d(TAG, "my hand: -> " + e.toString());
		 e.printStackTrace();
		}
		break;
	}
	MainActivity.update();

	return true;
 }

 private void updateSelectedCardPosition(int X, int Y) {
	movingX = X - (int)(30 * scale); 
	movingY = Y - (int)(70 * scale);
 }

 private void initCards() {
	cardPack = scaledBmp(cardPack);
	deck.clear();
	for (int i = 0; i < res.getInteger(R.integer.CARD_ROWS) - 1; i++) {
	 for (int j = 0; j < res.getInteger(R.integer.CARD_COLUMNS); j++) {
		Point p = new Point(j, i);
		Card tempCard = new Card(myContext, cardPack, p, new PointF(screenW, screenH));
		deck.push(tempCard);
	 }
	}

	Card tempCard = new Card(myContext, cardPack, new Point(0, 4), new PointF(screenW, screenH));
	deck.push(tempCard);
	Card tempCard1 = new Card(myContext, cardPack, new Point(1, 4), new PointF(screenW, screenH));
	deck.push(tempCard1);

 }

 private void initChosableSuits() {
	Bitmap b_heart = BitmapFactory.decodeResource(res, R.drawable.deck_heart);
	Bitmap b_diamond= BitmapFactory.decodeResource(res, R.drawable.deck_diamond);
	Bitmap b_club = BitmapFactory.decodeResource(res, R.drawable.deck_club);
	Bitmap b_spade = BitmapFactory.decodeResource(res, R.drawable.deck_spade);
	GameObject g_h = new GameObject(b_heart);
	GameObject g_d = new GameObject(b_diamond);
	GameObject g_c = new GameObject(b_club);
	GameObject g_s = new GameObject(b_spade);
	chosenSuit.put(res.getInteger(R.integer.HEART), g_h);
	chosenSuit.put(res.getInteger(R.integer.DIAMOND), g_d);
	chosenSuit.put(res.getInteger(R.integer.CLUB), g_c);
	chosenSuit.put(res.getInteger(R.integer.SPADE), g_s);
 }

 private void drawCard(Hand handTodraw) {
	try {

	 handTodraw.pickTopCardFrom(deck);
	 if (deck.isEmpty()) {
		while (discardPile.size() > 1) {
		 deck.pickBottomCardFrom(discardPile);
		}
		deck.shuffle();
	 }
	}
	catch (Exception e) {
	 Log.d(TAG, "drawCard():-> " + e.toString());
	 e.printStackTrace();
	}

 }

 private void dealCards() {
	deck.shuffle();
	myHand.clear();
	oppHand.clear();
	int starter = HAND_START;
	if(MainActivity.table.level != null){
	starter = AILevel.getStartingHandFor(MainActivity.table.level.getLevel());
	}
	for (int i = 0; i < starter; i++) {
	 drawCard(myHand);
	 drawCard(oppHand);
	}

 }

 private void showChooseSuitDialog() {
	MainActivity.pause(1);
	MainActivity.play(2, MainActivity.table.musicSound);
	final Dialog chooseSuitDialog =
	 new Dialog(myContext);
	chooseSuitDialog.requestWindowFeature(
	 Window.FEATURE_NO_TITLE);
	chooseSuitDialog.setContentView(R.layout.choose_suit_dialog);
	ImageButton d_heart = (ImageButton)chooseSuitDialog.findViewById(R.id.d_heart);
	ImageButton d_diamond = (ImageButton)chooseSuitDialog.findViewById(R.id.d_diamond);
	ImageButton d_club = (ImageButton)chooseSuitDialog.findViewById(R.id.d_club);
	ImageButton d_spade = (ImageButton)chooseSuitDialog.findViewById(R.id.d_spade);

	d_heart.setOnClickListener(new OnClickListener(){
		@Override
		public void onClick(View p1) {
		 // TODO: Implement this method
		 validSuit = res.getInteger(R.integer.HEART);
		 choseSuit = true;
		 chooseSuitDialog.dismiss();
		 selectedSuit = validSuit;
		 // Toaster.toast(myContext, "Vous avez choisi " + suitText + " = " + validSuit, true);
		 MainActivity.table.myTurn = false;
		 turnToPlay = "";
		 MainActivity.play(1, MainActivity.table.musicSound);
		 MainActivity.pause(2);
		 makeComputerPlay();
		}
	 });

	d_diamond.setOnClickListener(new OnClickListener(){
		@Override
		public void onClick(View p1) {
		 // TODO: Implement this method
		 validSuit = res.getInteger(R.integer.DIAMOND);
		 choseSuit = true;
		 chooseSuitDialog.dismiss();
		 selectedSuit = validSuit;
		 // Toaster.toast(myContext, "Vous avez choisi " + suitText + " = " + validSuit, true);
		 MainActivity.table.myTurn = false;
		 turnToPlay = "";
		 MainActivity.play(1, MainActivity.table.musicSound);
		 MainActivity.pause(2);
		 makeComputerPlay();
		}
	 });

	d_club.setOnClickListener(new OnClickListener(){
		@Override
		public void onClick(View p1) {
		 // TODO: Implement this method
		 validSuit = res.getInteger(R.integer.CLUB);
		 choseSuit = true;
		 chooseSuitDialog.dismiss();
		 selectedSuit = validSuit;
		 // Toaster.toast(myContext, "Vous avez choisi " + suitText + " = " + validSuit, true);
		 MainActivity.table.myTurn = false;
		 turnToPlay = "";
		 MainActivity.play(1, MainActivity.table.musicSound);
		 MainActivity.pause(2);
		 makeComputerPlay();
		}
	 });

	d_spade.setOnClickListener(new OnClickListener(){
		@Override
		public void onClick(View p1) {
		 // TODO: Implement this method
		 validSuit = res.getInteger(R.integer.SPADE);
		 choseSuit = true;
		 chooseSuitDialog.dismiss();
		 selectedSuit = validSuit;
		 // Toaster.toast(myContext, "Vous avez choisi " + suitText + " = " + validSuit, true);
		 MainActivity.table.myTurn = false;
		 turnToPlay = "";
		 MainActivity.play(1, MainActivity.table.musicSound);
		 MainActivity.pause(2);
		 makeComputerPlay();
		}
	 });
	chooseSuitDialog.setCanceledOnTouchOutside(false);
	chooseSuitDialog.setCancelable(false);
	chooseSuitDialog.show();
 }

 private boolean checkForValidDraw() {
	boolean canDraw = true;
	for (int i = 0; i < myHand.size(); i++) {
	 int tempId = myHand.getCardProperty(Hand.CARD.ID, i);
	 int tempRank = myHand.getCardProperty(Hand.CARD.RANK, i);
	 int tempSuit = myHand.getCardProperty(Hand.CARD.SUIT, i);
	 if (validSuit == tempSuit || validRank == tempRank ||
			 tempId == res.getInteger(R.integer.REQ_HEART) ||
			 tempId == res.getInteger(R.integer.REQ_DIAMOND) || 
			 tempId == res.getInteger(R.integer.REQ_CLUB) ||
			 tempId == res.getInteger(R.integer.REQ_SPADE) || 
			 tempId == res.getInteger(R.integer.JOKER_BLACK) ||
			 tempId == res.getInteger(R.integer.JOKER_COLOR)) {
		canDraw = false;
	 }
	}
	return canDraw;
 }

 private void makeComputerPlay() {
	int tempPlay = 0;
	tempPlay = ai.makePlay(oppHand,
																		 validSuit, validRank, AILevel.ADVANCED);
	if (tempPlay == 0) {
	 //animate drawing card by computer
	 oppCanDrawCard = true;

	} else {
	 if (tempPlay == res.getInteger(R.integer.REQ_HEART) ||
			 tempPlay == res.getInteger(R.integer.REQ_DIAMOND) || 
			 tempPlay == res.getInteger(R.integer.REQ_CLUB) ||
			 tempPlay == res.getInteger(R.integer.REQ_SPADE)) {
		validRank = MainActivity.REQUESTER;
		validSuit = 
		 ai.chooseSuit(oppHand);
		choseSuit = true;
		selectedSuit = validSuit;
		turnToPlay = "Votre Tour";
		// Toaster.toast(myContext, "Ordinateur a choisi " + suitText + " = " + validSuit, true);
	 } else {
		validSuit = Math.round((tempPlay / 100) * 100);
		validRank = tempPlay - validSuit;
		choseSuit = false;
		selectedSuit = 0;
	 }

	 // computer plays its card
	 // i will have to figure how to animate computer move --------------------
	 for (int i = 0; i < oppHand.size(); i++) {
		if (tempPlay == oppHand.getCardProperty(Hand.CARD.ID, i)) {
		 oppMoveHand.pickCardFrom(oppHand, i);
		}
	 }
	}
 }

 private void evaluateOppMove() {
	// if it was the last card, ga   me ends
	if (oppHand.isEmpty()) {
	 // play sound
	 endHand();
	} else {

	 // otherwise see the outcome
	 switch (validRank) {
		case MainActivity.STOPPER:
		 // play sound
		 turnToPlay = "";
		 makeComputerPlay();
		 break;
		case MainActivity.DRAW_TWO:
		 // play sound
		 //drawCard(myHand, 2);
		 forceUserToDrawCard = true;
		 numberToDrawUser += 2;
		 turnToPlay = "";
		 //makeComputerPlay();
		 break;
		case MainActivity.DRAW_FOUR:
		 // play sound
		 //drawCard(myHand, 4);
		 forceUserToDrawCard = true;
		 numberToDrawUser += 4;
		 turnToPlay = "";
		 //makeComputerPlay();
		 break;
		case MainActivity.DRAW_FIVE_1:
		 //Toaster.toast(myContext, "Joker black", false);
		 //drawCard(myHand, 5);
		 forceUserToDrawCard = true;
		 numberToDrawUser += 5;
		 turnToPlay = "";
		 //makeComputerPlay();
		 break;
		case MainActivity.DRAW_FIVE_2:
		 //Toaster.toast(myContext, "Joker color", false);
		 //drawCard(myHand, 5);
		 forceUserToDrawCard = true;
		 numberToDrawUser += 5;
		 turnToPlay = "";
		 //makeComputerPlay();
		 break;
	 }
	 MainActivity.table.myTurn = true;
	 turnToPlay = "Votre Tour";
	 MainActivity.update();
	}
 }

 private void updateScore() {
	MainActivity.table.scoreThisEnd = 0;
	int t_val =0;
	t_val = myHand.remainingCardsScoreValue();
	if (t_val > 0) {
	 MainActivity.table.oppScore += t_val;
	 MainActivity.table.scoreThisEnd += t_val;
	}
	t_val = 0;
	t_val = oppHand.remainingCardsScoreValue();
	if (t_val > 0) {
	 MainActivity.table. myScore += t_val;
	 MainActivity.table. scoreThisEnd += t_val;
	}
	MainActivity.update();
 } 

 private void endHand() {
	MainActivity.pause(1);
	MainActivity.play(2, MainActivity.table.musicSound);
	updateScore();
	if (myHand.isEmpty()) {
	 if (MainActivity.table.myScore >= MainActivity.table.max_score) {
		// play sound
		endGameMessage = String.format(res.getString(R.string.you_reach_max), MainActivity.table.myScore);
		++MainActivity.table.statW;
	 } else {
		// play sound
		endGameMessage = String.format(res.getString(R.string.you_finish_hand), MainActivity.table.scoreThisEnd);

	 }
	} else  if (oppHand.isEmpty()) {
	 if (MainActivity.table.oppScore >= MainActivity.table.max_score) {
		// play sound
		endGameMessage = String.format(res.getString(R.string.computer_reach_max), MainActivity.table.oppScore);
		++MainActivity.table.statL;
	 } else {
		// play sound
		endGameMessage = String.format(res.getString(R.string.computer_finish_hand), MainActivity.table.scoreThisEnd);

	 }
	}
	MainActivity.update();

	hasGameEnded = true;
 }

 private void initNewHand() {
	MainActivity.table.scoreThisEnd = 0;
	if (myHand.isEmpty()) {
	 MainActivity.table.myTurn = true;
	} else if (oppHand.isEmpty()) {
	 MainActivity.table.myTurn = false;
	}
	MainActivity.update();
	choseSuit = false;
	selectedSuit = 0;
	//randomBackColumn = randomInt(3, 12);
	prepareCards();
	if (!MainActivity.table.myTurn) {
	 turnToPlay = "";
	 makeComputerPlay();
	} else {
	 turnToPlay = "Votre Tour";
	}
 }

}
