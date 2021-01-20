package nloomis_G30_A03_Stacks_Queues;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CardTest {

	@Test
	void test1() {
		Card card1= new Card(10, "Clubs");
		assertEquals("10 of Clubs", card1.toString(), "Test Case 1: With valid rank and suit");
		
		try {
			//valid and invalid suits and ranks
			Card card2 = new Card(10, "Turkey");
			Card card3 = new Card(0, "Diamonds");
			Card card4 = new Card(0, "Turkey");
			fail("IllegalArgumentException not caught on suit or rank");
		}
		catch (IllegalArgumentException e) {
			assertTrue(true, "IllegalArgumentException caught");
		}
	}
	

}
