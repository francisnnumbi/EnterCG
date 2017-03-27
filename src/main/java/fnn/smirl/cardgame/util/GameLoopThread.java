package fnn.smirl.cardgame.util;
import android.graphics.Canvas;
import android.os.Looper;
import fnn.smirl.cardgame.component.GameView;

public class GameLoopThread extends Thread 
{
 static final long FPS = 10;
 private GameView view;
 private boolean running = false;

 public GameLoopThread(GameView view) {
	this.view = view;
 }

 public void setRunning(boolean run) {
	running = run;
 }

 @Override
 public void run() {
	long ticksPS = 100 / FPS;
	long startTime;
	long sleepTime;
	Looper.prepare();
	while (running) {
	 Canvas c = null;
	 startTime = System.currentTimeMillis();
	 try {
		c = view.getHolder().lockCanvas();
		synchronized (view.getHolder()) {
		 view.onDraw(c);
		}
	 }
	 finally {
		if (c != null) {
		 view.getHolder().unlockCanvasAndPost(c);
		 Looper.myLooper().quit();
		}
	 }
	 sleepTime = ticksPS - (System.currentTimeMillis()) - startTime;
	 try {
		if (sleepTime > 0) {
		 sleep(sleepTime);
		} else {
		 sleep(1);
		}
	 }
	 catch (Exception e) {}
	}
	Looper.loop();
 }
}
