package player;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import card.Card;
import core.EventCardPlayed;
import statistic.ComputeCard;
import statistic.EstimateHand;
import strategy.StyleGame;


public class PlayerInformation implements Observer{

	private List<Card> dropCards;
	private StyleGame mainSg;				// --- Global Strategie of player
	private StyleGame currentPossibleSg; 	// --- Turn Strategie of player
	private EstimateHand estimateHand;
		
	public PlayerInformation() {
		estimateHand = new EstimateHand();
	}
	
	@Override
	public void update(Observable o, Object element) {
		if (element instanceof EventCardPlayed) {
			EventCardPlayed evCardPlayed = (EventCardPlayed) element;
			dropCards.add(evCardPlayed.getCard());
			mainSg = ComputeCard.getMainStrategy(dropCards);
//			currentPossibleSg = ComputeCard.getCurrentStrategy();
		}
	}
	
}
