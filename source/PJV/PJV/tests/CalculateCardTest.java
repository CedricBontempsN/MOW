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
import statistic.CalculateCard;

class CalculateCardTest {

	List<Card> cards = new ArrayList<>();
	
	@BeforeEach
	void setUp() throws Exception {
		cards.add(new Classical(CardColor.V, "2"));
		cards.add(new Classical(CardColor.O, "3"));
		cards.add(new Classical(CardColor.O, "4"));
		cards.add(new Classical(CardColor.O, "5"));
		cards.add(new Classical(CardColor.V, "6"));
		cards.add(new Classical(CardColor.V, "7"));
		cards.add(new Classical(CardColor.O, "8"));
		cards.add(new Classical(CardColor.O, "9"));
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testSortBy() {
		List<Card> oCards = new ArrayList<>();
		oCards.add(new Classical(CardColor.V, "2"));
		oCards.add(new Classical(CardColor.V, "6"));
		oCards.add(new Classical(CardColor.O, "3"));
		oCards.add(new Classical(CardColor.O, "4"));
		oCards.add(new Classical(CardColor.O, "5"));
		oCards.add(new Classical(CardColor.O, "8"));
		oCards.add(new Classical(CardColor.V, "7"));
		oCards.add(new Classical(CardColor.O, "9"));
		assertEquals(CalculateCard.sortBy("asc", oCards).toString(), cards.toString());
		// ---
		oCards.clear();
		oCards.add(new Classical(CardColor.V, "9"));
		oCards.add(new Classical(CardColor.V, "8"));
		oCards.add(new Classical(CardColor.O, "7"));
		oCards.add(new Classical(CardColor.O, "6"));
		oCards.add(new Classical(CardColor.O, "5"));
		oCards.add(new Classical(CardColor.O, "4"));
		oCards.add(new Classical(CardColor.V, "3"));
		oCards.add(new Classical(CardColor.O, "2"));
		assertEquals(CalculateCard.sortBy("desc", cards).toString(), oCards.toString());
	}
}
