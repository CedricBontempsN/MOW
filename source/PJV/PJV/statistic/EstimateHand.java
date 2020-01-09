package statistic;

import java.util.List;

import card.Card;
import core.Hand;
import strategy.StyleGame;

public class EstimateHand {

	private Hand<Card> playerHand;
	
	
	public EstimateHand() {
		playerHand = new Hand();
	}
	
	public void estimateHand(StyleGame sg, List<Card> herd) {
		if (sg offensive) {
			
		}
	}
	
	public Hand getEstimateHand() {
		return playerHand;
	}
	
	public void updateHand(Card c) {
		if (playerHand.contains(c)) {
			evaluateHand();
		}
	}
} 