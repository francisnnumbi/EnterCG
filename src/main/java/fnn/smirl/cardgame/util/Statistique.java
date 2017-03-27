package fnn.smirl.cardgame.util;

public class Statistique implements Comparable<Statistique>
{
 public final int id;
 public final int user;
 public final int comp;
 public final int diff;
 public final float percent;
 public final boolean win;

 public Statistique(int id, int user, int comp) {
	this.id = id;
	this.user = user;
	this.comp = comp;
	this.diff = user - comp;
	this.percent = (1.0f*diff)/(1.0f*user);
	this.win = diff > 0 ? true : false;
 }
 
 
 @Override
 public int compareTo(Statistique p1) {
	// TODO: Implement this method
	return Integer.compare(id, p1.id);
 }

 @Override
 public boolean equals(Object o) {
	// TODO: Implement this method
	return Integer.compare(id, ((Statistique)o).id) == 0;
 }
}
