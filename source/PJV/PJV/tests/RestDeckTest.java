package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import card.Card;
import card.CardColor;
import card.Classical;
import core.AIDeck;
import core.RestDeck;

public class RestDeckTest {

	private RestDeck rd;
	List<Card> cards = new ArrayList<>();
	
	@BeforeEach
	void setUp() throws Exception {
		rd = RestDeck.getInstance();
		cards.add(new Classical(CardColor.V, "2"));
		cards.add(new Classical(CardColor.S, "7"));
		cards.add(new Classical(CardColor.O, "4"));
		cards.add(new Classical(CardColor.O, "5"));
		cards.add(new Classical(CardColor.R, "7"));
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testStartRound() {
		List<Card> iaHand = cards;
		rd.startRound(iaHand);
		AIDeck d = new AIDeck();
		d.remove(cards);
		assertEquals(d.getCards(), rd.getCards());
	}
}
