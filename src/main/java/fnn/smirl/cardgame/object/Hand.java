package fnn.smirl.cardgame.object;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Random;
import java.util.List;
import java.util.Iterator;

public class Hand {
 public enum CARD {
	RANK, SUIT, ID, SCORE;
 }

 public enum Rotate {
	LEFT, RIGHT;
 }

 private int movingCardIndex = -1;
 private LinkedList<Card> list = new LinkedList<Card>();

 public Hand() {}
 public Hand(LinkedList<Card> cards) {
	list.addAll(cards);
 }

 public int size() {
	//removeNulls();
	return list.size();
 }

 public void clear() {
	list.clear();
 }

 public boolean isEmpty() {
	return list.isEmpty();
 }

 public void shuffle() {
	removeNulls();
	Collections.shuffle(list, new Random());
 }

 public void rotate(Hand.Rotate direction) {
	if (direction == Hand.Rotate.LEFT) {
	 Collections.rotate(list, -1);
	} else if (direction == Hand.Rotate.RIGHT) {
	 Collections.rotate(list, 1);
	}
 }

 public void push(Card newCard) {
	list.push(newCard);
 }

 // get the first card and remove it from the pile
 public Card poll() {
	return list.poll();
 }
 public Card pollLast() {
	return list.pollLast();
 }

 // get first card without removing it from the pile
 public Card peek() {
	return list.peek();
 }

 public Card get(int location) {
	return list.get(location);
 }

 public Card getMovingCard() {
	return list.get(movingCardIndex);
 }

 public Card pass(int location) {
	Card c = list.get(location);
	list.remove(location);
	return c;
 }

 public void passMovingCardTo(Hand thisHand) {
	thisHand.pickCardFrom(this, movingCardIndex);
 }

 public boolean isMovingCardValid(int validRank, int validSuit, boolean choseSuit) {
	Card c = list.get(movingCardIndex);
	if (c == null) return false;
	else return c.isMoveValid(validRank, validSuit, choseSuit);
 }

 public List<Card> view() {
	return list;
 }

 public void passAllTo(Hand thisHand) {
	while (!list.isEmpty()) {
	 thisHand.pickTopCardFrom(this);
	}
 }

 public void setMovingCardIndex(int idx) {
	movingCardIndex = idx;
 }

 public int getMovingCardProperty(Hand.CARD property) {
	switch (property) {
	 case RANK:
		return list.get(movingCardIndex).getRank();
	 case SUIT:
		return list.get(movingCardIndex).getSuit();
	 case ID:
		return list.get(movingCardIndex).getId();
	 case SCORE:
		return list.get(movingCardIndex).getScoreValue();
	 default:
		return list.get(movingCardIndex).getId();
	}
 }

 public int getCardProperty(Hand.CARD property, int location) {
	switch (property) {
	 case RANK:
		return list.get(location).getRank();
	 case SUIT:
		return list.get(location).getSuit();
	 case ID:
		return list.get(location).getId();
	 case SCORE:
		return list.get(location).getScoreValue();
	 default:
		return list.get(location).getId();
	}
 }


 public boolean movingCardIntersect(Card thisCard) {
	return list.get(movingCardIndex).intersect(thisCard);
 }

 public void pickTopCardFrom(Hand hand) {
	Card c = hand.poll();
	if (c != null) list.push(c);
	removeNulls();
 }

 public void pickBottomCardFrom(Hand hand) {
	Card c = hand.pollLast();
	if (c != null) list.push(c);
	removeNulls();
 }


 public void pickCardFrom(Hand hand, int location) {
	Card c = hand.pass(location);
	if (c != null) list.push(c);
	removeNulls();
 }

 private void removeNulls() {
	if(!list.isEmpty()){
	Iterator<Card> iter = list.iterator();
	while (iter.hasNext()) {
	 if (iter.next() == null) iter.remove();
	}
	}
 }

 public int remainingCardsScoreValue() {
	int i = 0;
	if (!list.isEmpty()) {
	 for (Card c : list) {
		i += c.getScoreValue();
	 }
	}
	return i;
 }
}
