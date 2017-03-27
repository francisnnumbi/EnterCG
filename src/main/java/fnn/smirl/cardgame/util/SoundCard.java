package fnn.smirl.cardgame.util;

public class SoundCard {
 public int 
 resId = 0,
 looping = 0, // 0 = no loop, -1 = loop
 priority = 0; // 0 = low
 
 public float
 ratePlayback = 1.0f, // between 0.5 and 2.0, 1.0 is normal
 volLeft = 0, // between 0.0 and 1.0
 volRight = 0; // between 0.0 and 1.0
 
 public int playingId = 0;
 
 public SoundCard(int resId, float volLeft, float volRight, int priority, int looping, float ratePlayback) {
	this.volLeft = volLeft;
	this.volRight = volRight;
	this.resId = resId;
	this.looping = looping;
	this.priority = priority;
	this.ratePlayback = ratePlayback;
 }
}
