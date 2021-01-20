package nloomis_G30_A03_Stacks_Queues;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.jupiter.api.Test;

class DeckTest {

	@Test
	void sizeTest() {
		Deck myDeck = new Deck();
		assertEquals(52, myDeck.size(), "Test Case 1: Size of deck with actual size");
		assertFalse(myDeck.size() == 51, "Test Case 1: Size of deck with lower boundry");
		assertFalse(myDeck.size() == 53, "Test Case 1: Size of deck with lower boundry");
	}

	@Test
	void suitsRanksTest() {
		Deck myDeck = new Deck();
		boolean hasD = false;
		boolean hasC = false;
		boolean hasS = false;
		boolean hasH = false;

		Queue<Card> dQ = new LinkedList<>();
		Queue<Card> cQ = new LinkedList<>();
		Queue<Card> sQ = new LinkedList<>();
		Queue<Card> hQ = new LinkedList<>();

		for (int i = 0; i < 52; i++) {
			Card newCard = myDeck.getAllCards().poll();

			if (newCard.getSuit().equalsIgnoreCase("Diamonds")) {
				dQ.add(newCard);
				hasD = true;
			} else if (newCard.getSuit().equalsIgnoreCase("Clubs")) {
				cQ.add(newCard);
				hasC = true;
			} else if (newCard.getSuit().equalsIgnoreCase("Spades")) {
				sQ.add(newCard);
				hasS = true;
			} else if (newCard.getSuit().equalsIgnoreCase("Hearts")) {
				hQ.add(newCard);
				hasH = true;
			}

		}
		// Checking that all suits are in the deck
		if (!hasD || !hasC || !hasS || !hasH) {
			fail("Missing a suit");
		} else {
			assertTrue(true, "Contains all suits");
		}

		// Testing that the size of all suits is 13
		if (dQ.size() != 13 || cQ.size() != 13 || sQ.size() != 13 || hQ.size() != 13) {
			fail("Deck missing cards of a suit");
		} else {
			// Testing that each suit contains cards 2-14
			for (int i = 2; i < dQ.size() + 1; i++) {
				if (dQ.poll().getRank() != i) {
					fail("Missing the correct cards");
				}
			}

			for (int i = 2; i < cQ.size() + 1; i++) {
				if (cQ.poll().getRank() != i) {
					fail("Missing the correct cards");
				}
			}

			for (int i = 2; i < sQ.size() + 1; i++) {
				if (sQ.poll().getRank() != i) {
					fail("Missing the correct cards");
				}
			}

			for (int i = 2; i < hQ.size() + 1; i++) {
				if (hQ.poll().getRank() != i) {
					fail("Missing the correct cards");
				}
			}

		}

	}

	@Test
	void shuffleTest() {
		Deck unshuffled = new Deck();
		Deck shuffled = new Deck();
		Deck shuffled2 = new Deck();
		shuffled.shuffle();

		for (int i = 0; i < unshuffled.size(); i++) {
			if ((unshuffled.getAllCards().poll() == shuffled.getAllCards().poll()) || (unshuffled == shuffled)) {
				fail("Cards in the same position at " + i);
			} else
				assertTrue(true, "Cards not in the same position");
		}

		// Testing the size of the deck after the shuffle
		assertEquals(52, shuffled2.size(), "Test Case shuffleTest: Size of deck with actual size");
		assertFalse(shuffled.size() == 51, "Test Case shuffleTest: of deck with lower boundry");
		assertFalse(shuffled.size() == 53, "Test Case shuffleTest: of deck with lower boundry");

	}

	@Test
	void dealTest() {
		Deck myDeck = new Deck();
		Card peek = myDeck.getAllCards().peek();
		Card dealtCard = myDeck.deal();

		if (!(dealtCard instanceof Card) || (dealtCard == null)) {
			fail("dealtCard is not a Card");
		}

		assertEquals(peek, dealtCard, "Test case dealTest, testing that the card dealt is from the top of the queue");
	}

}
