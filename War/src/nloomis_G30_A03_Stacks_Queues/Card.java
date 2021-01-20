package nloomis_G30_A03_Stacks_Queues;

public class Card {
	private int rank;
	private String suit;
	public String[] allSuits = new String[] { "Clubs", "Spades", "Diamonds", "Hearts" };
	public int[] allRanks = new int[] { 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 };
	public String rankStr;

	public Card() {
		this.rankStr = "";
		this.rank = 0;
		this.suit = "";

	}

	public Card(int _rank, String _suit) {
		setRank(_rank);
		setSuit(_suit);
	}

	public String toString() {
		return this.rankStr + " of " + this.suit;
	}

	public int getRank() {
		return this.rank;
	}

	public void setRank(int _rank) {
		if (_rank <= 14 || _rank >= 1) {
			this.rank = _rank;
			if (this.rank == 11) {
				rankStr = "Jack";
			} else if (this.rank == 12) {
				rankStr = "Queen";
			} else if (this.rank == 13) {
				rankStr = "King";
			} else if (this.rank == 14) {
				rankStr = "Ace";
			} else {
				rankStr = this.rank + "";
			}
		} else
			throw new IllegalArgumentException();
	}

	public String getSuit() {
		return suit;
	}

	public void setSuit(String suit) {
		boolean isSuitValid = false;

		for (String s : allSuits) {
			if (s.equalsIgnoreCase(suit))
				isSuitValid = true;
		}

		if (isSuitValid)
			this.suit = suit;
		else
			throw new IllegalArgumentException();
	}

}
