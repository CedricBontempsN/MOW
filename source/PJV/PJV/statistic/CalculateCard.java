package statistic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import card.Card;
import core.AICard;
import core.RestDeck;
import core.Round;
import player.ObservablePlayer;
import player.Player;

public final class CalculateCard {
	
	// --- Start Attributes
	// --- Start Indicate Value
	public static final int CARD_SUITE = 50;
	public static int NUMBER_PLAYER = 150;
	// --- End Indicate Value
	// --- End Attributes
	private CalculateCard() {}
	
	public static List<AICard> getBestCard(Round r, List<AICard> playableCards/*, List<ObservablePlayer> obsPlayers*/){
		specialPlayable(r, playableCards);
//		otherPlayersInfluence(obsPlayers);
		return playableCards;
	}
	
	private static void specialPlayable(Round round, List<AICard> cards) {
		// If we haven't acrobats, we don't want play a 7 or 9
		if (!cards.contains(Card.convertStringToCard("SA"))) {
			for (AICard c : cards) {
				if (c.getNumber().equals(Card.convertStringToCard("V.7").getNumber()) ||
						c.getNumber().equals(Card.convertStringToCard("V.9").getNumber())) {
					c.setValue(Integer.MIN_VALUE);
				}
			}
		}
		// If we have an acrobat, we want directly played their
		else {
			for (AICard c : cards) {
				if (c.getNumber().equals(Card.convertStringToCard("SA").getNumber())) {
					c.setValue(Integer.MAX_VALUE);
				}
			}
		}
		// If we haven't resquilleuse, we try to play a card at border of herd
		if (!cards.contains(Card.convertStringToCard("SR"))) {
			for (AICard c : cards) {
				if (c.getNumber().equals(round.getMin()) ||
						c.getNumber().equals(round.getMax())) {
					c.updateValue(CARD_SUITE);
				}
			}
		}		
	}
	
	public static int isMaster(Player p) {
		List<Card> resetCard = sortBy("asc", RestDeck.getInstance().getCards());
		if (round.getMin() > resetCard.get(0) && round.getMax() < resetCard.get(resetCard.size())) {
			return 1;
		}
		return 0;
	}
	
	
	public static List<Card> sortBy(String type, List<Card> cards) {
		switch(type) {
			case "asc":
				cards.sort(Comparator.comparing(Card::getNumber));
				break;
			case "desc":
				List<Card> copyCards = new ArrayList<>(cards);
				cards.clear();
				copyCards.sort(Comparator.comparing(Card::getNumber));
				for(int i = copyCards.size() - 1; i >= 0; i--) {
					cards.add(copyCards.get(i));
				}
			default:
				break;
		}
		return cards;
	}
	
	private static void otherPlayersInfluence(List<ObservablePlayer> obsPlayers) {
		
	}
}
