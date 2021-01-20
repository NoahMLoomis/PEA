package nloomis_G30_A03_Stacks_Queues;

import java.util.Scanner;

public class WarGame {
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		char command=0;
		War emuWar = new War();
		System.out.println("Welcome to the game of war. \r\n" + 
				"The object of the game is to force the other player to run out of cards.\r\n" + 
				"All the cards are dealt at the beginning of the game.\r\n" + 
				"Each play both players lay the top card of their pile face up. The player with the highest rank card, puts both cards on the bottom of his pile. \r\n" + 
				"If both cards have the same rank, each player plays three cards face down and plays another round.\r\n" + 
				"The winner of the tie-breaking round gets all the played cards (the cards in the tie, the six face down and the two in the tie-breaking play.)\r\n" + 
				"");
		
		emuWar.start();
		emuWar.setP1("Player 1");
		emuWar.setP2("Player 2");
		System.out.println("Both hands have been dealt");
		System.out.println(emuWar.getP1() + " has "+ emuWar.getHand1Size() + " cards to start");
		System.out.println(emuWar.getP2() + " has "+ emuWar.getHand2Size() + " cards to start\n");
		
		while (Character.toUpperCase(command) !='Q' && !emuWar.isGameOver()){
			
			emuWar.dealRoundCards();
			emuWar.play();
			
			System.out.println(emuWar.getP1() + " plays: " + emuWar.getCard1() + ". " + emuWar.getP2() + " plays: " + emuWar.getCard2());
			System.out.println("Winner is " + emuWar.getWinner());
			System.out.println(emuWar.getP1() + " has "+ emuWar.getHand1Size() + " cards left");
			System.out.println(emuWar.getP2() + " has "+ emuWar.getHand2Size() + " cards left");
			System.out.println("Press any key and enter to continue, or Q to quit");
			command = input.next().charAt(0);
		}
		System.out.println("Thanks for playing!\n" + emuWar.gameOverMsg());

	}

}
