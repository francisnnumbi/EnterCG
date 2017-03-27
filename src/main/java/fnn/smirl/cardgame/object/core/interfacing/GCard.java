package fnn.smirl.cardgame.object.core.interfacing;

public interface GCard
{
 public int getId();
 public int getSuit();
 public int getRank();
 public int getScoreValue();
 
 public boolean isMoveValid(int rank, int suit, boolean choseSuit);
 
}
