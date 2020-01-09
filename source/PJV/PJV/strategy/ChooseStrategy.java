package strategy;

import player.GamePlayers;

public class ChooseStrategy {

	private GamePlayers game;
	
	public Boolean styleNbPlayer() {
		int nbJoueur = game.getListObservablePlayer().size();
		if (nbJoueur == 3) {
			return true; // jouer serr�
		}
		return false; // jouer large
	}
	
}
