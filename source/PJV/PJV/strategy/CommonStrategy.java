package strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import card.Acrobat;
import card.Card;
import card.CardColor;
import card.Classical;
import card.Deck;
import card.NoValue;
import core.AICard;
import core.AIDeck;
import core.AIManager;
import core.Game;
import core.Herd;
import core.RestDeck;
import core.Round;
import player.AIPlayer;
import player.ObservablePlayer;

public class CommonStrategy {

	private AIPlayer ia;
	private Round round;

	public CommonStrategy(AIPlayer ia) {
		this.ia = ia;
	}

	public Boolean sept(){
		for(AICard c : ia.getPlayableCards()) {
			if(c.getColor().equals(CardColor.S)){
				if(c.getValue() == 7) {
					return true;
				}
			}
		}
		return false;
	}

	public Boolean nine(){
		for(AICard c : ia.getPlayableCards()) {
			if(c.getColor().equals(CardColor.S)){
				if(c.getValue() == 9) {
					return true;
				}
			}
		}
		return false;
	}

	public Boolean resquilleuse(){
		for(AICard c : ia.getPlayableCards()) {
			if(c.getColor().equals(CardColor.S)){
				if(c.getValue() == -1) {
					return true;
				}
			}
		}
		return false;
	}
	
	public AICard play7or9orResq(int number) {
		AICard card = null;
		if(number==7) {
			for(AICard c : ia.getPlayableCards()) {
				if(c.getColor().equals(CardColor.S)){
					if(c.getValue() == 7 ) {
						card = c;
					}
				}
			}
		}
		if(number==9) {
			for(AICard c : ia.getPlayableCards()) {
				if(c.getColor().equals(CardColor.S)){
					if(c.getValue() == 9 ) {
						card = c;
					}
				}
			}
		}
		if(number== -1) {
			for(AICard c : ia.getPlayableCards()) {
				if(c.getColor().equals(CardColor.S)){
					if(c.getValue() == -1) {
						card = c;
					}
				}
			}
		}
		return card;
	}

	public List<AICard> playTightMin(Herd herd) {
		List<AICard> listTight = new ArrayList<AICard>();
		int i = 1;
		while (i < 8) {
			for(AICard c : ia.getPlayableCards()) {
				if(((c.getValue()+(-i)) == herd.getMin().getValue())) {
					listTight.add(c);
				}
			}
			i++;
		}
		return listTight;
	}
	
	public List<AICard> playTightMax(Herd herd) {
		List<AICard> listTight = new ArrayList<AICard>();
		int i = 1;
		while (i < 8) {
			for(AICard c : ia.getPlayableCards()) {
				if(((c.getValue()+i) == herd.getMax().getValue())) {
					listTight.add(c);
				}
			}
			i++;
		}
		return listTight;
	}

	public List<AICard> playLargeMin(Herd herd) {
		List<AICard> listLarge = new ArrayList<AICard>();
		List<AICard> listTight = playTightMin(herd);
		int lengthListTight = listTight.size();
		for (AICard c : listTight) {
			listLarge.set(lengthListTight, c);
			lengthListTight --;
		}
		return listLarge;
	}
	
	public List<AICard> playLargeMax(Herd herd) {
		List<AICard> listLarge = new ArrayList<AICard>();
		List<AICard> listTight = playTightMax(herd);
		int lengthListTight = listTight.size();
		for (AICard c : listTight) {
			listLarge.set(lengthListTight, c);
			lengthListTight --;
		}
		return listLarge;
	}

	public List<AICard> playLessFly(){
		List<AICard> listLessFly = new ArrayList<AICard>();
		for(int i=0;i<=5;i++) {
			if (i != 4) {
				for(AICard c : ia.getPlayableCards()) {
					if(c.getFly()==i) {
						listLessFly.add(c);
					}		
				}
			}
		}
		return listLessFly;
	}

	public List<AICard> playMoreFly(){
		List<AICard> listLessFly = new ArrayList<AICard>();
		List<AICard> listMoreFly = playLessFly();
		int lengthListLessFly = listLessFly.size();
		for (AICard c : listLessFly) {
			listMoreFly.set(lengthListLessFly, c);
			lengthListLessFly --;
		}
		return listMoreFly;
	}

	public AICard minimumCard() {
		AICard minimumCard = null;
		for(AICard c : ia.getPlayableCards()) {
			if(c.getValue() < minimumCard.getValue() || minimumCard.equals(null)) {
				minimumCard = c;
			}
		}
		return minimumCard;
	}

	public AICard maximumCard() {
		AICard maximumCard = null;
		for(AICard c : ia.getPlayableCards()) {
			if((c.getValue()) > maximumCard.getValue() || maximumCard.equals(null)) {
				maximumCard = c;
			}
		}
		return maximumCard;
	}

	public List<Card> cardMasterMin() {
		AIDeck deck = new AIDeck();
		Card minimumCard = minimumCard();
		List<Card> listCardMmin = new ArrayList<Card>();
		for(Card c : deck.getCards()) {
			if(minimumCard.getValue() >= c.getValue()) {
				listCardMmin.add(c);
			}
		}
		listCardMmin.add(new Classical(CardColor.S, "0"));
		listCardMmin.add(new Acrobat(CardColor.S, "7")); // a verifier si dans le troupeau on peut poser ces cartes
		listCardMmin.add(new Acrobat(CardColor.S, "9"));
		listCardMmin.add(new NoValue(CardColor.S, "1"));
		listCardMmin.add(new NoValue(CardColor.S, "2"));
		return listCardMmin;
	}

	public List<Card> cardMasterMax() {
		AIDeck deck = new AIDeck();
		Card maximumCard = maximumCard();
		List<Card> listCardMmax = new ArrayList<Card>();
		for(Card c : deck.getCards()) {
			if(maximumCard.getValue() >= c.getValue()) {
				listCardMmax.add(c);
			}
		}
		listCardMmax.add(new Classical(CardColor.S, "16"));
		listCardMmax.add(new Acrobat(CardColor.S, "7")); // a verifier si dans le troupeau on peut poser ces cartes
		listCardMmax.add(new Acrobat(CardColor.S, "9"));
		listCardMmax.add(new NoValue(CardColor.S, "1"));
		listCardMmax.add(new NoValue(CardColor.S, "2"));
		return listCardMmax;
	}

	public List<Card> cardMasterMinJouable(){
		Card minimumCard = minimumCard();
		RestDeck deck = RestDeck.getInstance();
		List<Card> listMasterMinJouable = new ArrayList<Card>();
		List<Card> listCardMmin = cardMasterMin();
		List<Card> listCarteJouer = deck.getCardPlayed();
		for (Card c1 : listCardMmin) {
			for (Card c2 : listCarteJouer) {
				if (!c2.equals(c1) && !c2.equals(minimumCard)) {
					listMasterMinJouable.add(c1);
				}
			}
		}
		return listMasterMinJouable;
	}

	public List<Card> cardMasterMaxJouable(){
		Card maximumCard = maximumCard();
		RestDeck deck = RestDeck.getInstance();
		List<Card> listMasterMaxJouable = new ArrayList<Card>();
		List<Card> listCardMmax = cardMasterMax();
		List<Card> listCarteJouer = deck.getCardPlayed();
		for (Card c1 : listCardMmax) {
			for (Card c2 : listCarteJouer) {
				if (!c2.equals(c1) && !c2.equals(maximumCard)) {
					listMasterMaxJouable.add(c1);
				}
			}
		}
		return listMasterMaxJouable;
	}


	public double probaMasterMin(Game game) {
		AIManager manager = AIManager.getInstance();
		List<ObservablePlayer> obsPlayers = manager.getPlayers();
		int nbCarteJoueur =  5 * obsPlayers.size();
		int nbCartePioche = game.getDeckSize();
		int nbTotalEnJeu = nbCarteJoueur + nbCartePioche;
		List<Card> listMasterMinJouable = cardMasterMinJouable();
		if (nbCartePioche == 0 && listMasterMinJouable.size() != 0) { // sur de ne pas etre master
			return 0;
		}
		return (nbCartePioche/nbTotalEnJeu) * listMasterMinJouable.size();
	}

	public double probaMasterMax(Game game) {	
		AIManager manager = AIManager.getInstance();
		List<ObservablePlayer> obsPlayers = manager.getPlayers();
		int nbCarteJoueur =  5 * obsPlayers.size();
		int nbCartePioche = game.getDeckSize(); 
		int nbTotalEnJeu = nbCarteJoueur + nbCartePioche;
		List<Card> listMasterMaxJouable = cardMasterMaxJouable();
		if (nbCartePioche == 0 && listMasterMaxJouable.size() != 0) { // sur de ne pas etre master
			return 0;
		}
		return (nbCartePioche/nbTotalEnJeu) * listMasterMaxJouable.size();
	}

	public Boolean masterMax(Game game) {
		if(probaMasterMax(game) >= 9/10) {
			return true;
		}
		return false;
	}
	
	public Boolean masterMin(Game game) {
		if(probaMasterMin(game) >= 9/10) {
			return true;
		}
		return false;
	}
	public void getValeurLessMouche() {
		AIManager manager = AIManager.getInstance();
		List<ObservablePlayer> obsPlayers = manager.getPlayers();
		int nbJoueur = obsPlayers.size();
		List<AICard> listLessFly = playLessFly();
		int valeurPrec = 1000;
		if(nbJoueur == 3) {
			int i = listLessFly.size();
			for(AICard c : listLessFly) {
				if( valeurPrec != c.getFly()) {
					i --;
				}
				c.updateAIValue(i);
			}
		}
		else if(nbJoueur == 4) {
			int i = listLessFly.size()+1;
			for(AICard c : listLessFly) {
				if( valeurPrec != c.getFly()) {
					i --;
				}
				c.updateAIValue(i);
			}
		}
		else { // 5 joueurs
			int i = listLessFly.size()+2;
			for(AICard c : listLessFly) {
				if( valeurPrec != c.getFly()) {
					i --;
				}
				c.updateAIValue(i);
			}
		}
	}

	public void getValeurMoreMouche() {
		AIManager manager = AIManager.getInstance();
		List<ObservablePlayer> obsPlayers = manager.getPlayers();
		int nbJoueur = obsPlayers.size();
		List<AICard> listMoreFly = playMoreFly();
		int valeurPrec = 1000;
		if(nbJoueur == 3) {
			int i = listMoreFly.size();
			for(AICard c : listMoreFly) {
				if( valeurPrec != c.getFly()) {
					i --;
				}
				c.updateAIValue(i);
			}
		}
		else if(nbJoueur == 4) {
			int i = listMoreFly.size()+1;
			for(AICard c : listMoreFly) {
				if( valeurPrec != c.getFly()) {
					i --;
				}
				c.updateAIValue(i);
			}
		}
		else { // 5 joueurs
			int i = listMoreFly.size()+2;
			for(AICard c : listMoreFly) {
				if( valeurPrec != c.getFly()) {
					i --;
				}
				c.updateAIValue(i);
			}
		}
	}

	public void getValeurPlayTightMin(Herd herd) {
		AIManager manager = AIManager.getInstance();
		List<ObservablePlayer> obsPlayers = manager.getPlayers();
		int nbJoueur = obsPlayers.size();
		List<AICard> listTight = playTightMin(herd);
		int valeurPrec = 1000;
		if(nbJoueur == 3) {
			int i = listTight.size()+2;
			for(AICard c : listTight) {
				if( valeurPrec != c.getValue()) {
					i --;
				}
				c.updateAIValue(i);
			}
		}
		else if(nbJoueur == 4) {
			int i = listTight.size()+1;
			for(AICard c : listTight) {
				if( valeurPrec != c.getValue()) {
					i --;
				}
				c.updateAIValue(i);
			}
		}
		else { // 5 joueurs
			int i = listTight.size();
			for(AICard c : listTight) {
				if( valeurPrec != c.getValue()) {
					i --;
				}
				c.updateAIValue(i);
			}
		}
	}
	
	public void getValeurPlayTightMax(Herd herd) {
		AIManager manager = AIManager.getInstance();
		List<ObservablePlayer> obsPlayers = manager.getPlayers();
		int nbJoueur = obsPlayers.size();
		List<AICard> listTight = playTightMax(herd);
		int valeurPrec = 1000;
		if(nbJoueur == 3) {
			int i = listTight.size()+2;
			for(AICard c : listTight) {
				if( valeurPrec != c.getValue()) {
					i --;
				}
				c.updateAIValue(i);
			}
		}
		else if(nbJoueur == 4) {
			int i = listTight.size()+1;
			for(AICard c : listTight) {
				if( valeurPrec != c.getValue()) {
					i --;
				}
				c.updateAIValue(i);
			}
		}
		else { // 5 joueurs
			int i = listTight.size();
			for(AICard c : listTight) {
				if( valeurPrec != c.getValue()) {
					i --;
				}
				c.updateAIValue(i);
			}
		}
	}

	public void getValeurPlayLargeMin(Herd herd) {
		AIManager manager = AIManager.getInstance();
		List<ObservablePlayer> obsPlayers = manager.getPlayers();
		int nbJoueur = obsPlayers.size();
		List<AICard> listLarge = playLargeMin(herd);
		int valeurPrec = 1000;
		if(nbJoueur == 3) {
			int i = listLarge.size()+2;
			for(AICard c : listLarge) {
				if( valeurPrec != c.getValue()) {
					i --;
				}
				c.updateAIValue(i);
			}
		}
		else if(nbJoueur == 4) {
			int i = listLarge.size()+1;
			for(AICard c : listLarge) {
				if( valeurPrec != c.getValue()) {
					i --;
				}
				c.updateAIValue(i);
			}
		}
		else { // 5 joueurs
			int i = listLarge.size();
			for(AICard c : listLarge) {
				if( valeurPrec != c.getValue()) {
					i --;
				}
				c.updateAIValue(i);
			}
		}
	}
	
	public void getValeurPlayLargeMax(Herd herd) {
		AIManager manager = AIManager.getInstance();
		List<ObservablePlayer> obsPlayers = manager.getPlayers();
		int nbJoueur = obsPlayers.size();
		List<AICard> listLarge = playLargeMax(herd);
		int valeurPrec = 1000;
		if(nbJoueur == 3) {
			int i = listLarge.size()+2;
			for(AICard c : listLarge) {
				if( valeurPrec != c.getValue()) {
					i --;
				}
				c.updateAIValue(i);
			}
		}
		else if(nbJoueur == 4) {
			int i = listLarge.size()+1;
			for(AICard c : listLarge) {
				if( valeurPrec != c.getValue()) {
					i --;
				}
				c.updateAIValue(i);
			}
		}
		else { // 5 joueurs
			int i = listLarge.size();
			for(AICard c : listLarge) {
				if( valeurPrec != c.getValue()) {
					i --;
				}
				c.updateAIValue(i);
			}
		}
	}

	public AICard bestCard() {
		double bestValue = 0;
		AICard bestCard = chooseCardAleatoire();
		for(AICard c : ia.getPlayableCards()) {
			if (c.getAIValue() > bestValue) {
				bestValue = c.getAIValue();
				bestCard = c;
			}
		}
		return bestCard;
	}
	
	public AICard chooseCardAleatoire(Herd h) {
		List<AICard> playablesCard = ia.getPlayableCards(h);
		int nbCartesJouables = playablesCard.size();// calculer nb carte en main jouable
		System.out.println("CARD nb--- > " + nbCartesJouables);
		if (nbCartesJouables == 0) {
			return null;
		}
		
		Random r = new Random();
		int valeur = r.nextInt(nbCartesJouables); // choisir aléatoire entre 0 et nb
		System.out.println(valeur + " ------------- " + nbCartesJouables);
		
		AICard carteAJouer = playablesCard.get(valeur);
		return carteAJouer;// return la carte choisi aléatoirement
	}

	public AICard startCard() {
		AIManager manager = AIManager.getInstance();
		List<ObservablePlayer> obsPlayers = manager.getPlayers();
		int nbJoueur = obsPlayers.size();
		List<AICard> cartes = ia.getPlayableCards();
		int nbMouche = 0;
		AICard startCard = null;
		int i = 0;
		for (AICard c : cartes) {
			if(nbJoueur == 3) {
				if(c.getValue() == 8-i || c.getValue() == 8+i) {
					nbMouche = c.getFly();
					return c;
				}
			}
			else if(nbJoueur == 4) {
				if(c.getValue() == 7-i || c.getValue() == 9+i) {
					nbMouche = c.getFly();
					return c;
				}
			}
			else if (nbJoueur == 5){
				if(c.getValue() == 5-i || c.getValue() == 11+i || c.getValue() == 5+i || c.getValue() == 11-i ) {
					nbMouche = c.getFly();
					return c;
				}
			}
			i++;
		}
		startCard = chooseCardAleatoire();
		return startCard;		
	}

	public int sizeMin(Herd herd) {
		return herd.getMin().getValue(); 
	}

	public int sizeMax(Herd herd) {
		return 16 - herd.getMax().getValue(); 
	}

	public boolean minOrMax(Herd herd) {
		if (sizeMin(herd) - sizeMax(herd) > 0) {
			return true; // le coté min est plus grand
		}
		else {
			return false; // le coté max est plus grand
		}
	}


	public Card playTheCard(Herd herd, Game game) {
//		List<AICard> masterCards = new ArrayList<>();
//		AICard carteAJouer = chooseCardAleatoire(); // mettre carte aléatoire
		return chooseCardAleatoire(herd);
//		if (herd.equals(null)){ // si le troupeau est null
//			return startCard();
//		}
//		else if (sept().equals(true)) {
//			return play7or9orResq(7);
//		}
//		else if (nine().equals(true)) {
//			return play7or9orResq(9);
//		}
//		else if (game.getDeckSize() < 5) {
//			for (AICard c : ia.getPlayableCards())
//				if (c.getValue() == -1) {
//					return c;
//				}
//		}
//		else if (game.getDeckSize() <1) {
//			for (AICard c: ia.getPlayableCards()) {
//				if (c.getValue() == 0 || c.getValue() == 16 || c.getValue() == -1) {
//					return c;
//				}
//			}
//		}
//		else if (minOrMax(herd) ) { // si le coté min du troupeau est plus grand
//			if(masterMin(game)) {
//				masterCards.add(minimumCard());
//				ia.getPlayableCards().remove(minimumCard());
//			}
//			getValeurMoreMouche();
//			getValeurPlayTightMin(herd);
//			if (bestCard() != null) {
//				return bestCard();
//			}
//		}
//		else if (! minOrMax(herd)) { // coté max plus grand
//			if(masterMax(game)) {
//				masterCards.add(maximumCard());
//				ia.getPlayableCards().remove(maximumCard());
//			}
//			getValeurMoreMouche();
//			getValeurPlayTightMax(herd);
//			if (bestCard() != null) {
//				return bestCard();
//			}
//		}
//		else if(resquilleuse().equals(true)) {
//			return play7or9orResq(-1);
//		}
//		
//		else if (herd.getFlies() < 5) {
//			return null;
//		}
//		
//		else if(masterCards.size() != 0) {
//				return masterCards.get(0);
//		}
//		return carteAJouer;
	}
}