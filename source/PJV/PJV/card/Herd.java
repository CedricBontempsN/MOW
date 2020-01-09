package card;

import java.util.ArrayList;
import java.util.List;

import card.Card;

public class Herd {

	private List<Card> herd;
	
	public Herd() {
		herd = new ArrayList<>();
	}
	
	public void addCard(Card c) {
		herd.add(c);
	}
	
	public int getFlies() {
		int flies = 0;
		for (Card c : herd) {
			flies += c.getFly();
		}
		return flies;
	}
	
	public List<Card> getHerd(){
		return herd;
	}
}
