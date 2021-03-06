package core;

import java.util.ArrayList;
import java.util.Comparator;
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
	
	public int getHerdSize() {
		return herd.size();
	}
	
	public Card getMin() {
		if (!herd.isEmpty()) {
			Card min = herd.get(0);
			for (Card c : herd) {
				if (min.getValue() > c.getValue() && min.getValue() != -1 && c.getValue() != -1) {
					min = c;
				}
			}
			return min;
		}
		else {
			return null;
		}
	}
	
	public Card getMax() {
		if (!herd.isEmpty()) {
			Card max = herd.get(0);
			for (Card c : herd) {
				if (max.getValue() < c.getValue() && max.getValue() != -1 && c.getValue() != -1) {
					max = c;
				}
			}
			return max;
		}
		else {
			return null;
		}
	}
	
	public boolean isPlayable(Card c) {
		System.out.println(herd + " --------- " + c.getValue() + " ==================== " +c.getNumber());
		if (c.getValue() == -1) {
			System.out.println("resquilleuse");
			if (herd.size() > 1) {
				herd.sort(Comparator.comparing(Card::getValue));
				System.out.println(herd);
				for (int i = 1; i < herd.size(); i++) {
					if (Math.abs(herd.get(i - 1).getValue() - herd.get(i).getValue()) > 1) {
						return true;
					}
				}
				return false;
			}
			else {
				System.out.println("non playable *******************");
				return false;
			}
		}
		else if (getMax() == null && getMin() == null) {
			return true;
		}
		
		else if (getMax().getValue() < c.getValue() || getMin().getValue() > c.getValue()) {
			return true;
		}
		else if (c.getNumber().equals("7A") || c.getNumber().equals("9A")) {
			System.out.println("ACROBAT ------- ACROBAT------- ACROBAT");
			for (Card card : herd) {
				if (card.getValue() == c.getValue()) {
					return true;
				}
			}
		}
		return false;
	}
}
