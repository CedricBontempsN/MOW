package player;

import java.util.ArrayList;
import java.util.List;

public class GamePlayers {
	
	private int currentPlayer = 0;
	private boolean direction = false;
	private List<ObservablePlayer> obsPlayers;
	
	public GamePlayers(List<Player> players) {
		obsPlayers = new ArrayList<>();
		for (Player p : players) {
			obsPlayers.add(new ObservablePlayer(p));
		}
	}
	
	public ObservablePlayer getNextPlayer() {
		if (!direction) {
			return obsPlayers.get(++currentPlayer%obsPlayers.size());
		}
		else {
			return obsPlayers.get(--currentPlayer%obsPlayers.size());
		}
	}
	
	public ObservablePlayer getPlayer(int i) {
		return obsPlayers.get(i);
	}
	
	public List<ObservablePlayer> getListObservablePlayer(){
		return obsPlayers;
	}
	
	public void playerPlayed(String playerId, String cardId) {
		for (ObservablePlayer p : obsPlayers) {
			if (p.getPlayer().getName().equals(playerId)) {
				p.playerPlayed(cardId);
			}
		}
	}

	public void endRound() {
		
	}
}
