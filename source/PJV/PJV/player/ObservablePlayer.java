package player;

import java.util.Observable;

import core.EventCardPlayed;
import core.RestDeck;

public class ObservablePlayer extends Observable{
	
	private Player player;
	private PlayerInformation pInfo;
	private RestDeck rd = RestDeck.getInstance();
	
	public ObservablePlayer(Player p) {
		player = p;
		pInfo = new PlayerInformation();
		addObserver(pInfo);
		addObserver(rd);
	}
	
	public void playerPlayed(String cardId) {
		setChanged();
		notifyObservers(new EventCardPlayed(cardId, this));
	}
	
	public Player getPlayer() {
		return player;
	}
}
