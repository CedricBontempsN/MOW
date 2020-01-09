package statistic;

import java.util.HashMap;
import java.util.List;

import card.Card;
import card.CardColor;
import strategy.Defensive;
import strategy.Offensive;
import strategy.StyleGame;

public final class ComputeCard {
	
	private ComputeCard() {}
	
	private static final int EXTREM_MIN = 4;
	private static final int EXTREM_MAX = 12;
	
	public static StyleGame getMainStrategy(List<Card> cards) {
		StyleGame sg = null;
		CardColor colorStat = getMainColor(cards);
		double extremumStat = getExtremum(cards);
		double centerStat = 1 - extremumStat;
		// TODO PRENDRE EN COMPTE LE NOMBRE DE JOUEUR DANS LA PARTE 
		if (extremumStat > 0.5) {
			sg = new Offensive();
		}
		else {
			sg = new Defensive();
		}
		return sg;
	}

	public static int getExtremum(List<Card> cards) {
		int extrem = 0;
		for (Card c : cards) {
			if (Integer.parseInt(c.getNumber()) <= EXTREM_MIN || Integer.parseInt(c.getNumber()) >= EXTREM_MAX) {
				extrem++;
			}
		}
		return extrem/cards.size();
	}

	public static double getMainColorStat(List<Card> cards) {
		double proba;
		double green = 0, yellow = 0, orange = 0, red = 0, special = 0;
		for (Card c : cards) {
			CardColor color = c.getColor();
			switch(color) {
			case V:
				green++;
				break;
			case J:
				yellow++;
				break;
			case O:
				orange++;
				break;
			case R:
				red++;
				break;
			case S:
				special++;
				break;
			default:
				break;
			}
		}
		try {
			proba = max(green, yellow, orange, red, special)/cards.size();
		} catch (Exception e) {
			proba = 0;
		}
		return proba;
	}
	
	public static CardColor getMainColor(List<Card> cards) {
		double proba = getMainColorStat(cards);
		HashMap<CardColor, Double> cardsColor = getColor(cards);
		if (proba == cardsColor.get(CardColor.V)/cards.size()) {
			return CardColor.V;
		}
		else if (proba == cardsColor.get(CardColor.J)/cards.size()) {
			return CardColor.J;
		}
		else if (proba == cardsColor.get(CardColor.O)/cards.size()) {
			return CardColor.O;
		}
		else if (proba == cardsColor.get(CardColor.R)/cards.size()) {
			return CardColor.R;
		}
		else if (proba == cardsColor.get(CardColor.S)/cards.size()) {
			return CardColor.S;
		}
		return null;
	}
	
	
	private static HashMap<CardColor, Double> getColor(List<Card> cards) {
		HashMap<CardColor, Double> cardsColor = new HashMap<>();
		double green = 0, yellow = 0, orange = 0, red = 0, special = 0;
		for (Card c : cards) {
			CardColor color = c.getColor();
			switch(color) {
			case V:
				green++;
				break;
			case J:
				yellow++;
				break;
			case O:
				orange++;
				break;
			case R:
				red++;
				break;
			case S:
				special++;
				break;
			default:
				break;
			}
		}
		cardsColor.put(CardColor.V, green);
		cardsColor.put(CardColor.J, yellow);
		cardsColor.put(CardColor.O, orange);
		cardsColor.put(CardColor.R, red);
		cardsColor.put(CardColor.S, special);
		return cardsColor;
	}

	public static double max(double... cmp) throws Exception {
		if (cmp.length == 0) {
			throw new Exception("No int to compare");
		}
		double max = cmp[0];
		for(int i = 0; i < cmp.length; i++) {
			max = Math.max(max, cmp[i]);
		}
		return max;
	}
}
