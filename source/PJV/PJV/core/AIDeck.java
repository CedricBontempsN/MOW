package core;

import java.util.ArrayList;
import java.util.List;

import card.Card;
import card.Deck;

// Add different function in addition at traditionnal deck
public class AIDeck extends Deck{
	
	public void remove(List<Card> cards) {
		for (Card c : cards) {
			this.cards.remove(c);
		}
	}
	
	public void remove(Card c) {
		this.cards.remove(c);
	}
	
	public boolean isInDeck(Card card) {
		return cards.contains(card);
	}

	public List<Card> getCards() {
		return cards;
	}
	
	// TODO factoriser les deux methodes suivantes avec un nouveau parametre
	public List<Card> cardInferior(Card currentCard){
		List<Card> cards = new ArrayList<>();
		for (Card c : cards) {
			if (Integer.parseInt(c.getNumber()) < Integer.parseInt(currentCard.getNumber())) {
				cards.add(c);
			}
		}
		return cards;
	}
	
	public List<Card> cardSperior(Card currentCard){
		List<Card> cards = new ArrayList<>();
		for (Card c : cards) {
			if (Integer.parseInt(c.getNumber()) >Integer.parseInt(currentCard.getNumber())) {
				cards.add(c);
			}
		}
		return cards;
	}
}
