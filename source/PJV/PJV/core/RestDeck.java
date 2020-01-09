package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import card.Card;
import card.Deck;

public class RestDeck implements Observer{
		
	// --- Start Singleton
	private static RestDeck rd;

	private RestDeck() {}
	
	public static RestDeck getInstance() {
		if (rd == null) {
			rd = new RestDeck();
		}
		return rd;
	}
	// --- End Singleton
	
	private AIDeck deckCard = new AIDeck();
	private List<Card> cardPlayed = new ArrayList<>();
	
	public void startRound(List<Card> list) {
		deckCard.resetDeck();
		deckCard.remove(list); 
		cardPlayed.clear();
	}

	public boolean checkCard(Card card){ // permettra de vérifier que telle ou telle carte a deja été jouée pour faire choix stratégique
		return deckCard.isInDeck(card);
	}
	
	public List<Card> getCards(){
		return deckCard.getCards();
	}
	
	public int getDeckSize() {
		return deckCard.nbCardInDeck();
	}
	
	public List<Card> getCardPlayed(){
		return cardPlayed;
	}
	
	@Override
	public void update(Observable o, Object element) {
		if (element instanceof EventCardPlayed) {
			EventCardPlayed evCardPlayed = (EventCardPlayed) element;
			deckCard.remove(evCardPlayed.getCard());
			cardPlayed.add(evCardPlayed.getCard());
		}
	}
	
}
