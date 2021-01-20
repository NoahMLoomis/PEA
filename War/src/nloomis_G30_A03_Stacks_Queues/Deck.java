package nloomis_G30_A03_Stacks_Queues;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class Deck {
	private Queue<Card> allCards;
	private String[] allSuits = new String[] { "Clubs", "Spades", "Diamonds", "Hearts" };
	private int[] allRanks = new int[] { 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 };

	public Deck() {
		allCards = new LinkedList<>();
		for (int i = 0; i < allSuits.length; i++) {
			for (int j = 0; j < allRanks.length; j++) {
				allCards.add(new Card(allRanks[j], allSuits[i]));
			}
		}
	}

	public Queue<Card> getAllCards() {
		return allCards;

	}

	public int size() {
		return allCards.size();
	}

	public Card deal() {
		return allCards.poll();
	}

	public void shuffle() {
		Collections.shuffle((List<Card>) allCards, new Random());
	}
}
