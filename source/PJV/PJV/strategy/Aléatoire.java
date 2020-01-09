package strategy;

import java.util.List;
import java.util.Random;

import card.Card;
import core.Hand;
import player.AIPlayer;

public class Aléatoire extends StyleGame {
	
	private AIPlayer ia;
	
	@Override
	public List<Card> choosePannelCard(Hand hand) {
		return null;
	}
	
	public Card chooseCardAleatoire() {
		//List<Card> cartesJouables = cards.Cartesjouables();// connaitre les cartes jouable dans notre situation
		int nbCartesJouables = ia.getPlayableCards().size();// calculer nb carte en main jouable
		Random r = new Random();
		int valeur =r.nextInt(nbCartesJouables); // choisir aléatoire entre 0 et nb
		Card carteAJouer = ia.getPlayableCards().get(valeur);
		return carteAJouer;// return la carte choisi aléatoirement
	}
}
