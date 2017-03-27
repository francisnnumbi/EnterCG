package fnn.smirl.cardgame.util;
import android.media.SoundPool;
import android.media.AudioManager;
import android.media.AudioAttributes;
import java.util.ArrayList;
import android.content.Context;
import java.util.HashMap;
import java.util.Iterator;
import android.util.Log;
import java.util.Set;
import android.os.*;
import java.util.jar.Attributes;
import android.app.Activity;

public class GameSounds {
 private SoundPool sp;
 private HashMap<Integer, Integer> soundIds = new HashMap<Integer, Integer>();
 private HashMap<Integer, SoundCard> sounds;
 private Activity ctx;
 boolean ready = false;

 public GameSounds(Activity ctx, HashMap<Integer, SoundCard> sounds) {
	this.ctx = ctx;
	this.sounds = sounds;
	if (Build.VERSION.SDK_INT < 21) {
	 sp = new SoundPool(30, AudioManager.STREAM_MUSIC, 0);
	} else {
	 sp = getSoundPool(30);
	 ctx.setVolumeControlStream(AudioManager.STREAM_MUSIC);
	}
	sp.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener(){

		@Override
		public void onLoadComplete(SoundPool p1, int p2, int p3) {
		 // TODO: Implement this method
		 ready = true;
		}
	 });

	load();
 }

 private AudioAttributes getAudioAttributes() {
	return new AudioAttributes.Builder()
	 .setUsage(AudioAttributes.USAGE_GAME)
	 .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
	 .build();
 }

 private SoundPool getSoundPool(int maxStream) {
	return new SoundPool.Builder()
	 .setMaxStreams(maxStream)
	 .setAudioAttributes(getAudioAttributes())
	 .build();
 }

 public void pause(int playingId) {
	sp.pause(playingId);
 }

 public void stop(int playingId) {
	sp.stop(playingId);
 }

 public int play(int id) {
	SoundCard s = sounds.get(id);
	return sp.play(soundIds.get(id), s.volLeft, s.volRight, 
								 s.priority, s.looping, s.ratePlayback);

 }

 public int play(int id, float vol) {
	SoundCard s = sounds.get(id);
	s.volLeft = vol;
	s.volRight = vol;
	return sp.play(soundIds.get(id), s.volLeft, s.volRight, 
								 s.priority, s.looping, s.ratePlayback);
 }

 public void resume(int playingId) {
	sp.resume(playingId);
 }

 private boolean load() {
	Iterator<Integer> iter = sounds.keySet().iterator();
	try {
	 while (iter.hasNext()) {
		int id = iter.next().intValue();
		SoundCard s = sounds.get(id);
		soundIds.put(id, sp.load(ctx, s.resId, 0));
	 }
	}
	catch (Exception e) {
	 Log.v("GameSounds : ", "fail >> " + e);
	}
	return true;
 }

 public void setVolume(float vol) {
	setVolume(vol, vol);
 }
 public void setVolume(float volLeft, float volRight) {
	Iterator<Integer> iter = sounds.keySet().iterator();
	while (iter.hasNext()) {
	 int id = iter.next().intValue();
	 sounds.get(id).volLeft = volLeft;
	 sounds.get(id).volRight = volRight;
	}
 }

 public boolean isReady() {
	return ready;
 }

 public void release() {
	sp.release();
 }

}
