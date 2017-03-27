package fnn.smirl.cardgame.object.core.enums;
import java.util.HashMap;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;

public enum AILevel {
 ROOKIE(0, 3), BEGINNER(1, 4), INTERMEDIATE(2, 5), ADVANCED(3, 6), PROFESSIONAL(4, 7);
 private int level;
 private int startingHand;
 private static Map<Integer, Integer> map;
 private AILevel(int level, int startingHand){
	this.level = level;
	this.startingHand = startingHand;
 }

 public int getLevel(){
	return level;
 }
 public int getStartingHand(){
	return startingHand;
 }

 public static int getStartingHandFor(int level){
	if(map == null){
	 map = Collections.unmodifiableMap(initMap());
	}
	
	return map.get(level);
 }
 
 public static ArrayList<Integer> getLevels(){
	if(map == null){
	 map = Collections.unmodifiableMap(initMap());
	}
	ArrayList<Integer> li = new ArrayList<Integer>();
	Iterator<Integer> iter = map.keySet().iterator();
	while(iter.hasNext()){
	 li.add(iter.next());
	}
	return li; 
 }

 public static ArrayList<Integer> getValues(){
	if(map == null){
	 map = Collections.unmodifiableMap(initMap());
	}
	ArrayList<Integer> li = new ArrayList<Integer>();
	Iterator<Integer> iter = map.values().iterator();
	while(iter.hasNext()){
	 li.add(iter.next());
	}
	return li; 
 }

 private static Map<Integer, Integer> initMap(){
	Map<Integer, Integer> m = new HashMap<>();
	for(AILevel i : values()){
	 m.put(i.level, i.startingHand);
	}
	return m;
 }
}
