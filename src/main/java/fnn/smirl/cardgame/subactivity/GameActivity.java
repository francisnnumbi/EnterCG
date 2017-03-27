package fnn.smirl.cardgame.subactivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import fnn.smirl.cardgame.MainActivity;
import fnn.smirl.cardgame.component.GameView;

public class GameActivity extends Activity
{

 GameView gView;
 @Override
 public void onCreate(Bundle savedInstanceState) {
	// TODO: Implement this method
	super.onCreate(savedInstanceState);
	gView = new GameView(this);
	gView.setKeepScreenOn(true);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	getWindow().setFlags(
	 WindowManager.LayoutParams.FLAG_FULLSCREEN,
	 WindowManager.LayoutParams.FLAG_FULLSCREEN);
	setContentView(gView);
	
 }

 @Override
 protected void onStart() {
	// TODO: Implement this method
	super.onStart();
	MainActivity.play(1, MainActivity.table.musicSound);
 }
 
 @Override
 protected void onResume() {
	// TODO: Implement this method
	super.onResume();
	//MainActivity.play(1, MainActivity.table.musicSound);
 }
 

 @Override
 public void onBackPressed() {
	// TODO: Implement this method
	super.onBackPressed();
	MainActivity.pause(1);
	gView.killLooper();
 }
 
 
}
