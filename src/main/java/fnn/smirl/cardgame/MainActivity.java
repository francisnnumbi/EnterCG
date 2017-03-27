package fnn.smirl.cardgame;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.view.Window;
import android.view.WindowManager;
import fnn.smirl.cardgame.R;
import fnn.smirl.cardgame.component.TitleView;
import fnn.smirl.cardgame.object.TableGame;
import fnn.smirl.cardgame.util.GameSounds;
import fnn.smirl.cardgame.util.SoundCard;
import fnn.smirl.simple.Serializer;
import java.io.File;
import java.util.HashMap;

public class MainActivity extends Activity {
 public static Context ctx;
 public static GameSounds gameSounds;
 static Serializer serializer = new Serializer();
 static File sdcard = Environment.getExternalStorageDirectory();
 static File dir = new File(sdcard.getAbsolutePath() + "/enter_cg/db");
 static File filename = new File(dir, "/table.json");
 public static TableGame table = new TableGame();
 public static HashMap<Integer, Integer> soundMap = new HashMap<Integer, Integer>();
 
 public static final int REQUESTER = 8,
 STOPPER = 1,
 DRAW_TWO = 2,
 DRAW_FOUR = 10,
 DRAW_FIVE_1 = 14, // Joker
 DRAW_FIVE_2 = 15; // Joker
 
 public static Resources res;
 
 @Override
 protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	ctx = getApplicationContext();
	res = getResources();
	prepareSounds();
	
	TitleView tView = new TitleView(this);
	tView.setKeepScreenOn(true);
	
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	getWindow().setFlags(
	 WindowManager.LayoutParams.FLAG_FULLSCREEN,
	 WindowManager.LayoutParams.FLAG_FULLSCREEN);

	setContentView(tView);
	
	if (!dir.exists())dir.mkdirs();
	if(!filename.exists())store();
	retrieve();
	if(table == null){
	 table = new TableGame();
	store();
	}
	
 }

 @Override
 protected void onStart() {
	// TODO: Implement this method
	super.onStart();
	//Toaster.toast(ctx, "prepared sound ready : " + gameSounds.isReady(), false);
	play(0, table.musicSound);
	//Toaster.toast(ctx, "playing sound 0", false);
 }
 

 
 private void prepareSounds(){
	if(gameSounds == null){
	// Toaster.toast(ctx, "gamesound is null", false);
	HashMap<Integer, SoundCard> als = new HashMap<Integer, SoundCard>();
	SoundCard sound0 = new SoundCard(R.raw.bg0, 0.3f, 0.3f, 0, -1, 1.0f);
	SoundCard sound1 = new SoundCard(R.raw.bg1,  0.3f, 0.3f, 0, -1, 1.0f);
	SoundCard sound2 = new SoundCard(R.raw.bg2,  0.3f, 0.3f, 0, -1, 1.0f);
	SoundCard sound3 = new SoundCard(R.raw.bg3,  0.3f, 0.3f, 0, -1, 1.0f);
	SoundCard sound4 = new SoundCard(R.raw.clic1,  0.3f, 0.3f, 1, 0, 1.0f);
	SoundCard sound5 = new SoundCard(R.raw.clic0,  0.3f, 0.3f, 1, 0, 1.0f);
	
	als.put(0, sound0);
	als.put(1, sound1);
	als.put(2, sound2);
	als.put(3, sound3);
	als.put(4, sound4);
	als.put(5, sound5);
	gameSounds = new GameSounds(this, als);
	}
 }

 public static void update(){
	store();
 }
 private static void store() {
	serializer.serialize(filename.getAbsolutePath(), table, TableGame.class);

 }

 private static void retrieve() {
	table = serializer.deserialize(filename.getAbsolutePath(), TableGame.class);
 }

 @Override
 public void onBackPressed() {
	// TODO: Implement this method
	super.onBackPressed();
	gameSounds.release();
 }

 @Override
 protected void onDestroy() {
	// TODO: Implement this method
	super.onDestroy();
	stop(0);
 }
 
 

 @Override
 protected void onResume() {
	// TODO: Implement this method
	super.onResume();
	MainActivity.play(4, MainActivity.table.clickSound);
 }
 
 public static void play(int id, float vol){
	if(gameSounds.isReady()){
	if(soundMap.containsKey(id)){
	 gameSounds.resume(soundMap.get(id));
	}else{
	 soundMap.put(id, gameSounds.play(id, vol));
	}
	}
 }
 
 public static void pause(int id){
	if(soundMap.containsKey(id)){
	 gameSounds.pause(soundMap.get(id));
	}
 }
 
 public static void stop(int id){
	if(soundMap.containsKey(id)){
	 gameSounds.stop(soundMap.get(id));
	 soundMap.remove(id);
	}
 }
 
}
