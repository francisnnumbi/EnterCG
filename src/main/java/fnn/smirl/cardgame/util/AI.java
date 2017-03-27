package fnn.smirl.cardgame.util;

import fnn.smirl.cardgame.R;
import fnn.smirl.cardgame.MainActivity;
import fnn.smirl.cardgame.object.Hand;
import fnn.smirl.cardgame.object.core.enums.AILevel;
import java.util.Arrays;

public class AI {

 public int makePlay(Hand hand, int suit, int rank, AILevel level) {

	// for beginner
	if (level == AILevel.ROOKIE) {
	 return level0(hand, rank, suit);
	} else
	if (level == AILevel.BEGINNER) {
	 return level1(hand, rank, suit);
	} else
	if (level == AILevel.INTERMEDIATE) {
	 return level2(hand, rank, suit);
	} else
	if (level == AILevel.ADVANCED) {
	 return level3(hand, rank, suit);
	} else
	if (level == AILevel.PROFESSIONAL) {
	 return level1(hand, rank, suit);
	}

	return 0;
 }

 // play ROOKIE
 private int level0(Hand hand, int rank, int suit) {
	int play = 0;
	for (int i = 0; i < hand.size(); i++) {
	 int _tempId = hand.getCardProperty(Hand.CARD.ID, i);
	 int tempRank = hand.getCardProperty(Hand.CARD.RANK, i);
	 int tempSuit = hand.getCardProperty(Hand.CARD.SUIT, i);

	 //- ignore JOKER, REQUESTER, DRAW_FOUR, DRAW_TWO from the list;
	 if (tempRank != MainActivity.DRAW_TWO && tempRank != MainActivity.DRAW_FOUR
			 && tempRank != MainActivity.DRAW_FIVE_1 && tempRank != MainActivity.DRAW_FIVE_2
			 && tempRank != MainActivity.REQUESTER) {
		//- check if the played card is JOKER in order to play any card which comes first in the list, no need to match;
		if (rank == MainActivity.DRAW_FIVE_1) {
		 play = _tempId;
		}
		if (rank == MainActivity.DRAW_FIVE_2) {
		 play = _tempId;
		}

		if (play == 0) {
		 // - check if played card is REQUESTER in order to match only the suit that was requested;
		 if (rank == MainActivity.REQUESTER) {
			if (suit == tempSuit) {
			 play = _tempId;
			}
		 } else if (suit == tempSuit || rank == tempRank) {
			play = _tempId; //- match either the suit or the rank;
		 }
		}
	 }
	}

	//- check to see if AI has a DRAW_TWO and play it;
	if (play == 0) {
	 for (int i = 0; i < hand.size(); i++) {
		int tempId = hand.getCardProperty(Hand.CARD.ID, i);
		int tempRank = hand.getCardProperty(Hand.CARD.RANK, i);
		int tempSuit = hand.getCardProperty(Hand.CARD.SUIT, i);
		if (tempRank == MainActivity.DRAW_TWO && (rank == tempRank || suit == tempSuit)) {
		 play = tempId;
		}
	 }
	}

	//- check to see if AI has a DRAW_FOUR and play it;
	if (play == 0) {
	 for (int i = 0; i < hand.size(); i++) {
		int tempId = hand.getCardProperty(Hand.CARD.ID, i);
		int tempRank = hand.getCardProperty(Hand.CARD.RANK, i);
		int tempSuit = hand.getCardProperty(Hand.CARD.SUIT, i);
		if (tempRank == MainActivity.DRAW_FOUR && (rank == tempRank || suit == tempSuit)) {
		 play = tempId;
		}
	 }
	}

	//- check to see if AI has a REQUESTER and play it;
	if (play == 0) {
	 for (int i = 0; i < hand.size(); i++) {
		int tempId = hand.getCardProperty(Hand.CARD.ID, i);
		if (isRequester(tempId)) {
		 play = tempId;
		}
	 }
	}

	//- check to see if AI has a JOKER and play it;
	if (play == 0) {
	 for (int i = 0; i < hand.size(); i++) {
		int tempId = hand.getCardProperty(Hand.CARD.ID, i);
		if (isJoker(tempId)) {
		 play = tempId;
		}
	 }
	}

	return play;
 }

 // play BEGINNER
 private int level1(Hand hand, int rank, int suit) {
	int play = 0;
	for (int i = 0; i < hand.size(); i++) {
	 int _tempId = hand.getCardProperty(Hand.CARD.ID, i);
	 int tempRank = hand.getCardProperty(Hand.CARD.RANK, i);
	 int tempSuit = hand.getCardProperty(Hand.CARD.SUIT, i);

	 //- ignore JOKER, REQUESTER from the list;
	 if (tempRank != MainActivity.DRAW_FIVE_1 && tempRank != MainActivity.DRAW_FIVE_2
			 && tempRank != MainActivity.REQUESTER) {
		//- check if the played card is JOKER in order to play any card which comes first in the list, no need to match;
		if (rank == MainActivity.DRAW_FIVE_1) {
		 play = _tempId;
		}
		if (rank == MainActivity.DRAW_FIVE_2) {
		 play = _tempId;
		}

		if (play == 0) {
		 // - check if played card is REQUESTER in order to match only the suit that was requested;
		 if (rank == MainActivity.REQUESTER) {
			if (suit == tempSuit) {
			 play = _tempId;
			}
		 } else if (suit == tempSuit || rank == tempRank) {
			play = _tempId; //- match either the suit or the rank;
		 }
		}
	 }
	}

	//- check to see if AI has a REQUESTER and play it;
	if (play == 0) {
	 for (int i = 0; i < hand.size(); i++) {
		int tempId = hand.getCardProperty(Hand.CARD.ID, i);
		if (isRequester(tempId)) {
		 play = tempId;
		}
	 }
	}

	//- check to see if AI has a JOKER and play it;
	if (play == 0) {
	 for (int i = 0; i < hand.size(); i++) {
		int tempId = hand.getCardProperty(Hand.CARD.ID, i);
		if (isJoker(tempId)) {
		 play = tempId;
		}
	 }
	}
	return play;
 }


 // play INTERMEDIATE
 private int level2(Hand hand, int rank, int suit) {
	int play = 0;
	for (int i = 0; i < hand.size(); i++) {
	 int _tempId = hand.getCardProperty(Hand.CARD.ID, i);
	 int tempRank = hand.getCardProperty(Hand.CARD.RANK, i);
	 int tempSuit = hand.getCardProperty(Hand.CARD.SUIT, i);

	 //- ignore JOKER, REQUESTER from the list;
	 if (tempRank != MainActivity.DRAW_FIVE_1 && tempRank != MainActivity.DRAW_FIVE_2
			 && tempRank != MainActivity.REQUESTER) {
		//- check if the played card is JOKER in order to play any card which comes first in the list, no need to match;
		if (rank == MainActivity.DRAW_FIVE_1) {
		 play = _tempId;
		}
		if (rank == MainActivity.DRAW_FIVE_2) {
		 play = _tempId;
		}

		if (play == 0) {
		 // - check if played card is REQUESTER in order to match only the suit that was requested;
		 if (rank == MainActivity.REQUESTER) {
			if (suit == tempSuit) {
			 play = _tempId;
			}
		 } else if (suit == tempSuit || rank == tempRank) {
			play = _tempId; //- match either the suit or the rank;
		 }
		}
	 }
	}

	//- check to see if AI has a JOKER and play it;
	if (play == 0) {
	 for (int i = 0; i < hand.size(); i++) {
		int tempId = hand.getCardProperty(Hand.CARD.ID, i);
		if (isJoker(tempId)) {
		 play = tempId;
		}
	 }
	}

	//- check to see if AI has a REQUESTER and play it;
	if (play == 0) {
	 for (int i = 0; i < hand.size(); i++) {
		int tempId = hand.getCardProperty(Hand.CARD.ID, i);
		if (isRequester(tempId)) {
		 play = tempId;
		}
	 }
	}

	return play;
 }



 // play ADVANCED
 private int level3(Hand hand, int rank, int suit) {
	int play = 0;

	// check for DRAW_FOUR
	for (int i = 0; i < hand.size(); i++) {
	 int _tempId = hand.getCardProperty(Hand.CARD.ID, i);
	 int tempRank = hand.getCardProperty(Hand.CARD.RANK, i);
	 int tempSuit = hand.getCardProperty(Hand.CARD.SUIT, i);
	 //- consider only DRAW_FOUR from the list;
	 if (tempRank == MainActivity.DRAW_FOUR) {
		//- check if the played card is JOKER in order to play any card which comes first in the list, no need to match;
		if (rank == MainActivity.DRAW_FIVE_1 || rank == MainActivity.DRAW_FIVE_2) {
		 play = _tempId;
		}	
		if (play == 0) {
		 // - check if played card is REQUESTER in order to match only the suit that was requested;
		 if (rank == MainActivity.REQUESTER) {
			if (suit == tempSuit) {
			 play = _tempId;
			}
		 } else if (suit == tempSuit || rank == tempRank) {
			play = _tempId; //- match either the suit or the rank;
		 }
		}
	 }
	}

	if (play == 0) {
	 // check for DRAW_TWO
	 for (int i = 0; i < hand.size(); i++) {
		int _tempId = hand.getCardProperty(Hand.CARD.ID, i);
		int tempRank = hand.getCardProperty(Hand.CARD.RANK, i);
		int tempSuit = hand.getCardProperty(Hand.CARD.SUIT, i);
		//- consider only DRAW_TWO from the list;
		if (tempRank == MainActivity.DRAW_TWO) {
		 //- check if the played card is JOKER in order to play any card which comes first in the list, no need to match;
		 if (rank == MainActivity.DRAW_FIVE_1 || rank == MainActivity.DRAW_FIVE_2) {
			play = _tempId;
		 }	
		 if (play == 0) {
			// - check if played card is REQUESTER in order to match only the suit that was requested;
			if (rank == MainActivity.REQUESTER) {
			 if (suit == tempSuit) {
				play = _tempId;
			 }
			} else if (suit == tempSuit || rank == tempRank) {
			 play = _tempId; //- match either the suit or the rank;
			}
		 }
		}
	 }
	}

	if (play == 0) {
	 for (int i = 0; i < hand.size(); i++) {
		int _tempId = hand.getCardProperty(Hand.CARD.ID, i);
		int tempRank = hand.getCardProperty(Hand.CARD.RANK, i);
		int tempSuit = hand.getCardProperty(Hand.CARD.SUIT, i);

		//- ignore JOKER, REQUESTER from the list;
		if (tempRank != MainActivity.DRAW_FIVE_1 && tempRank != MainActivity.DRAW_FIVE_2
				&& tempRank != MainActivity.REQUESTER) {
		 //- check if the played card is JOKER in order to play any card which comes first in the list, no need to match;
		 if (rank == MainActivity.DRAW_FIVE_1) {
			play = _tempId;
		 }
		 if (rank == MainActivity.DRAW_FIVE_2) {
			play = _tempId;
		 }

		 if (play == 0) {
			// - check if played card is REQUESTER in order to match only the suit that was requested;
			if (rank == MainActivity.REQUESTER) {
			 if (suit == tempSuit) {
				play = _tempId;
			 }
			} else if (suit == tempSuit || rank == tempRank) {
			 play = _tempId; //- match either the suit or the rank;
			}
		 }
		}
	 }
	}
	
	//- check to see if AI has a JOKER and play it;
	if (play == 0) {
	 for (int i = 0; i < hand.size(); i++) {
		int tempId = hand.getCardProperty(Hand.CARD.ID, i);
		if (isJoker(tempId)) {
		 play = tempId;
		}
	 }
	}

	//- check to see if AI has a REQUESTER and play it;
	if (play == 0) {
	 for (int i = 0; i < hand.size(); i++) {
		int tempId = hand.getCardProperty(Hand.CARD.ID, i);
		if (isRequester(tempId)) {
		 play = tempId;
		}
	 }
	}

	return play;
 }



 public int chooseSuit(Hand hand) {
	int suit = MainActivity.res.getInteger(R.integer.HEART), 
	 numDiamonds = 0, 
	 numClubs = 0,
	 numHearts = 0,
	 numSpades = 0;

	for (int i = 0; i < hand.size(); i++) {
	 int tempRank = hand.getCardProperty(Hand.CARD.RANK, i);
	 int tempSuit = hand.getCardProperty(Hand.CARD.SUIT, i);
	 if (tempRank != MainActivity.REQUESTER) {
		if (tempSuit == MainActivity.res.getInteger(R.integer.DIAMOND))numDiamonds++;
		else if (tempSuit == MainActivity.res.getInteger(R.integer.CLUB))numClubs++;
		else if (tempSuit == MainActivity.res.getInteger(R.integer.HEART))numHearts++;
		else if (tempSuit == MainActivity.res.getInteger(R.integer.SPADE))numSpades++;
	 }
	}
	int q = getLargest(numDiamonds, numClubs, numHearts, numSpades);

	if (q == numHearts)	suit = MainActivity.res.getInteger(R.integer.HEART);		
	else if (q == numDiamonds)	suit = MainActivity.res.getInteger(R.integer.DIAMOND); 
	else if (q == numClubs)	suit = MainActivity.res.getInteger(R.integer.CLUB);	
	else if (q == numSpades)	suit = MainActivity.res.getInteger(R.integer.SPADE);
	return suit;
 }

 private int getLargest(int ... ps) {
	Arrays.sort(ps);                   
	return ps[ps.length - 1];            
 }  

 private boolean isRequester(int tempId) {
	if (tempId == MainActivity.res.getInteger(R.integer.REQ_HEART) ||
			tempId == MainActivity.res.getInteger(R.integer.REQ_DIAMOND) || 
			tempId == MainActivity.res.getInteger(R.integer.REQ_CLUB) ||
			tempId == MainActivity.res.getInteger(R.integer.REQ_SPADE)) {
	 return true;
	}
	return false;
 }

 private boolean isJoker(int tempId) {
	if (tempId == MainActivity.res.getInteger(R.integer.JOKER_BLACK) ||
			tempId == MainActivity.res.getInteger(R.integer.JOKER_COLOR)) {
	 return true;
	}
	return false;
 }
}                      
