package core;

import card.Card;
import player.ObservablePlayer;

public class EventCardPlayed {

	private Card card; 
	private ObservablePlayer player;
	
	public EventCardPlayed(String cardId, ObservablePlayer player) {
		card = Card.convertStringToCard(cardId);
		this.player = player;
	}
	
	public Card getCard() {
		return card;
	}
	
	public ObservablePlayer getPlayer() {
		return player;
	}
	
}
