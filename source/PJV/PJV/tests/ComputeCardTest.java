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
import statistic.ComputeCard;

public class ComputeCardTest {

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
	void testGetExtremum() {
		assertEquals(ComputeCard.getExtremum(cards), 3/cards.size()); // Because we have 3 cards upper or equals 12 or lower or equals 4
	}	
	
	@Test
	void testGetMainColorStat() {
		assertEquals(ComputeCard.getMainColorStat(cards), 5f/cards.size()); // Because the most color are Orange and we have 5 cards
	}
	
	@Test
	void testGetMainColor() {
		assertEquals(ComputeCard.getMainColor(cards), CardColor.O); // Because we have 5 orange cards and only 3 green cards
	}

}
