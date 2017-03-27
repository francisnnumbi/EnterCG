package fnn.smirl.cardgame.object.core.interfacing;
import fnn.smirl.cardgame.object.core.enums.SimpleCompas;

public interface Padding
{
 public void pad(SimpleCompas position, float away);
 public void pad(float away);
 public void padHorizontal(float away);
 public void padVerticle(float away);
 public void clearPad();
}
