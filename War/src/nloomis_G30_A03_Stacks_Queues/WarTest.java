package nloomis_G30_A03_Stacks_Queues;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class WarTest {

	@Test
	void testWin() {
		War emuWar = new War();
		emuWar.setP1("Noah");
		emuWar.setP2("Kyle");
		emuWar.start();
		emuWar.setCards(new Card(10, "Diamonds"), new Card(11, "Clubs"));
		emuWar.play();
		assertEquals("Kyle", emuWar.getWinner(), "Test Case testWin: Testing win");
	}

	@Test
	void testGame() {
		War emuWar = new War();
		emuWar.setP1("Noah");
		emuWar.setP2("Kyle");
		emuWar.start();
		emuWar.setCards(new Card(10, "Diamonds"), new Card(11, "Clubs"));
		assertEquals(emuWar.getHand1Size(), 26, "Test Case testGame: Testing default card size for hand1");
		assertEquals(emuWar.getHand2Size(), 26, "Test Case testGame: Testing default card size for hand2");
		emuWar.play();

		assertEquals(emuWar.getHand1Size(), 25,
				"Test Case testGame: Testing default card size for hand1 after losing a card");
		assertEquals(emuWar.getHand2Size(), 27,
				"Test Case testGame: Testing default card size for hand2 after winning a card");

		while (!emuWar.isGameOver()) {
			emuWar.dealRoundCards();
			emuWar.play();
		}

		if (emuWar.getHand1Size() == 0) {
			assertEquals(emuWar.getWinner(), "Kyle");
		} else if (emuWar.getHand2Size() == 0) {
			assertEquals(emuWar.getWinner(), "Noah");
		}

	}

}
