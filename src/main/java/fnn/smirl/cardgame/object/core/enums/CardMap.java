package fnn.smirl.cardgame.object.core.enums;
import java.util.EnumMap;
import java.util.HashMap;

public final enum CardMap {
 
	ONE(1, 1), TWO(2, 2), THREE(3, 3), FOUR(4, 4), FIVE(5, 5),
	SIX(6, 6), SEVEN(7, 7), EIGHT(8, 25), NINE(9, 9), TEN(10, 10),
	ELEVEN(11, 10), TWELVE(12, 10), THIRTEEN(13, 10), 
	FORTEEN(14, 50), FIFTEEN(15, 50);
	private int f;
	private int v;
	private static HashMap<Integer, Integer> map;
	
	private CardMap(int face, int value){
	 this.f = face;
	 this.v = value;
	}
	
	public int getFace(){
	 return f;
	}
	public int getValue(){
	 return v;
	}
	
	public static int getValueFor(int face){
	 if(map == null){
		initMap();
	 }
	 if(map.containsKey(face)){
		return map.get(face);
	 }
	 return 0;
	} 
	
private static void initMap(){
 map = new HashMap<Integer, Integer>();
 for(CardMap m : CardMap.values()){
	map.put(m.f, m.v);
 }
}
 }
