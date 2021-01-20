package nloomis_G30_A03_Stacks_Queues;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

/*TODO:
-BUG WITH TIE, losing some cards

*/
public class War {
	private Deck myDeck;
	private String p1;
	private String p2;
	private String winner;
	private Card card1;
	private Card card2;
	private Queue<Card> hand1;
	private Queue<Card> hand2;
	private Stack<Card> kitty;

	public War() {
		this.winner = "";
		myDeck = new Deck();
		myDeck.shuffle();
		kitty = new Stack<>();
		hand1 = new ArrayDeque<>();
		hand2 = new ArrayDeque<>();
	}

	public void start() {
		for (int i = 0; i < 26; i++) {
			hand1.add(myDeck.deal());
			hand2.add(myDeck.deal());
		}
	}

	public void setCards(Card _card1, Card _card2) {
		this.card1 = _card1;
		this.card2 = _card2;

	}

	public void dealRoundCards() {
		if (hand1.size() > 0 || hand2.size() > 0) {
			setCards(getHand1Card(), getHand2Card());
		}
	}

	public void play() {

		System.out.println("Hand 1 is " + hand1.size());
		System.out.println("Hand 2 is " + hand2.size());

		if (!isGameOver()) {
			if (card1.getRank() < card2.getRank()) {
				hand2.offer(hand1.poll());
				hand2.offer(hand2.poll());

				while (!kitty.empty()) {
					hand2.offer(kitty.pop());
				}
				setWinner(p2);
			} else if (card1.getRank() > card2.getRank()) {
				hand1.offer(hand2.poll());
				hand1.offer(hand1.poll());

				while (!kitty.empty()) {
					hand1.offer(kitty.pop());
				}
				setWinner(p1);
			} else if (card1.getRank() == card2.getRank()) {
				resolveWar();
			}

		}
	}

	public boolean isGameOver() {
		if (hand1.peek() == null || hand2.peek() == null)
			return true;
		else
			return false;
	}

	public String gameOverMsg() {
		if (!isGameOver()) {
			return "Game is not over";
		} else {
			if (getHand1Size() < getHand2Size())
				return getP2() + " won!";
			else if (getHand1Size() > getHand2Size())
				return getP1() + " won!";
			else
				return "Tie!";
		}
	}

	public void resolveWar() {
		for (int i = 0; i < 3; i++) {
			if (!isGameOver()) {
				kitty.push(hand1.poll());
				kitty.push(hand2.poll());
			} else {
				if (getHand1Size() < getHand2Size())
					setWinner(getP2());
				else if (getHand1Size() > getHand2Size())
					setWinner(getP1());
			}
		}
		dealRoundCards();
		play();
	}

	public Card getHand1Card() {
		return hand1.peek();
	}

	public Card getHand2Card() {
		return hand2.peek();
	}

	public String getP2() {
		return p2;
	}

	public void setP2(String p2) {
		this.p2 = p2;
	}

	public String getP1() {
		return p1;
	}

	public void setP1(String p1) {
		this.p1 = p1;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

	public int getHand1Size() {
		return hand1.size();
	}

	public int getHand2Size() {
		return hand2.size();
	}

	public String getCard1() {
		return card1.toString();
	}

	public String getCard2() {
		return card2.toString();
	}
}
