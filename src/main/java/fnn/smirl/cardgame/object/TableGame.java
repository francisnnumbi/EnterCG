package fnn.smirl.cardgame.object;
import fnn.smirl.cardgame.object.core.enums.AILevel;
import fnn.smirl.cardgame.util.Statistique;
import java.util.ArrayList;

public class TableGame {
 public ArrayList<Statistique> stats;
 public boolean myTurn = false;
 public AILevel level;
 public float clickSound =0.3f, musicSound = 0.15f;
 public int max_score = 300, statW = 0, statL = 0;
 public int oppScore = 0, myScore = 0, scoreThisEnd = 0;

 public TableGame(){
	init();
 }
 
 private void init(){
  myTurn = false;
	stats = new ArrayList<Statistique>();
	level = AILevel.ROOKIE;
	max_score = 300; statW = 0; statL = 0;
	oppScore = 0; myScore = 0; scoreThisEnd = 0;
 }
 
 public Statistique bestWin(){
	Statistique ss = stats.get(0);
	for(Statistique s : stats){
	 if(ss.diff < s.diff)ss = s;
	}
	return ss;
 }
 
 public Statistique worstLoss(){
	Statistique ss = stats.get(0);
	for(Statistique s : stats){
	 if(ss.diff > s.diff)ss = s;
	}
	return ss;
 }
 
 public void reset() {
	init();
 }
}
