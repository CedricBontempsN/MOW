package strategy;

import java.util.List;

import card.Card;
import core.Hand;

public abstract class StyleGame {
	
	// --- Start Attributes
	private int priority; // It's the preference of the AI ex : AI prefer drop yellow card but it play also offensive
	// To improve priority, use an enumeration
	// --- End Attributes
	
	// --- Start Methods
	public void setPriority(int priority) { // permettra de choisir manuellement la priorité pour la premiere version
		this.priority = priority;
	}
	
	public abstract List<Card> choosePannelCard(Hand hand);

	// --- End Methods
}
