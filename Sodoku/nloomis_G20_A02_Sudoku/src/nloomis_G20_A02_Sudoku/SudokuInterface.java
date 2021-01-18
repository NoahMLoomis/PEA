package nloomis_G20_A02_Sudoku;

import java.util.Scanner;

/*
 * Author: Noah Loomis
 * G20_A02
 */

public class SudokuInterface {

	private Scanner input = new Scanner(System.in);
	private String fileName;
	private boolean isNumberThere, isColValid, isRowValid, isSquareValid;
	private String row = "";
	private String col = "";
	private String numberInput = "";
	private boolean allValid = false;
	private SudokuGame game = new SudokuGame();

	public static void main(String[] args) {
		SudokuInterface inter = new SudokuInterface();
		System.out.print("Welcome to Noah Loomis sudoku\nPlease enter the file name for your puzzle: ");

		/*
		 * Reading in file to play from
		 */

		while ((inter.game.validateFileFormat == false)) {
			inter.fileName = inter.input.nextLine();
			if (inter.fileName.equals("")) {
				inter.fileName = "sudoku.txt";
			} // if nothing is entered, default applied
			inter.game.readFile(inter.fileName);
			if ((inter.game.validateFileFormat(inter.fileName)) && inter.game.readFile(inter.fileName)) {
				inter.game.readFile(inter.fileName);
			} // if
			else if (((inter.game.readCode == SudokuGame.NOSUCHELEMENT_ERROR))
					&& (!inter.game.readFile(inter.fileName)))
				System.err.print("Error, does not follow proper format, please re-enter\n");

			else if (((inter.game.readCode == SudokuGame.FILENOTFOUND_ERROR)) && (!inter.game.readFile(inter.fileName)))
				System.err.print("Error, file not found, please re-enter\n");
			else if ((inter.game.readCode == SudokuGame.NUMBERFORMAT_ERROR) && (!inter.game.readFile(inter.fileName)))
				System.err.print("Error, number format error");
		} // while

		System.out.print(inter.fileName + " applied\n");

		inter.displayBoard();

		System.out.print("\nEnter Q at any time to exit the game, S to save the game, or U to undo your last move\n");

		/*
		 * Looping the game until game is Over
		 */

		do {
			if (!(inter.game.isGameOver())) {
				System.out.print("\nPlease enter what square you'd like to change, seperated by a space (row col):");
				inter.row = inter.input.next();

				if ((inter.row.equalsIgnoreCase("Q")) || (inter.col.equalsIgnoreCase("Q"))) {
					System.out.print("\nThanks for playing!");
					inter.game.quitGame();
				} // if Q
				else if ((inter.row.equalsIgnoreCase("S")) || (inter.col.equalsIgnoreCase("S"))) {
					inter.game.saveGame(inter.fileName);
					if (inter.game.saveGame(inter.fileName) == 0)
						System.out.print("Game succesfully saved!");
					else if (inter.game.saveGame(inter.fileName) == SudokuGame.FILENOTFOUND_ERROR) {
						System.err.print("Error, File not found, please re-enter");
					} else if (inter.game.saveGame(inter.fileName) == SudokuGame.FILENOTFOUND_ERROR) {
						System.err.print("Error, file contents not correct, please re-enter");
					}
				} // if S
				else if ((inter.row.equalsIgnoreCase("U")) || (inter.col.equalsIgnoreCase("U"))) {

					if (inter.game.canUndo) {
						System.out.print("Move undone");
						inter.game.undoMove((inter.game.getRow() - 1), (inter.game.getCol() - 1));
						inter.game.canUndo = false;
					} else
						System.err.print("Cannot undo move, you can only undo move once");
					inter.displayBoard();
				} else if (inter.game.validateNumber((inter.row))) {
					inter.game.setRow(inter.row);

					inter.col = inter.input.next();
					if (inter.game.validateNumber(inter.col)) {
						inter.game.setCol(inter.col);
						inter.changeNumber(inter.row, inter.col);
						inter.displayBoard();
					} // inner if
					else {
						System.err.print("\nPlease enter your col as an int between 1-9 inclusively\n");
					} // else
				} else {
					System.err.print("\nPlease enter your row as an int between 1-9 inclusively\n");
				} // else
			} // if !isGameOver()
			else {
				if (inter.allValid) {
					System.out.print("\n\nCongrats, you won!");
				} else if (!inter.allValid)
					System.out.print("\n\nYou lost, better luck next time.");
				inter.game.quitGame();
			} // else isGameOver()
		} while (!(inter.row.equalsIgnoreCase("Q")) || (!(inter.col.equalsIgnoreCase("Q"))));

	}// main

	/*
	 * Displaying board and adding formatting
	 */

	public void displayBoard() {
		int lineCount1 = 0;
		int lineCount2 = 0;
		System.out.print("\n");
		for (int row = 0; row < game.getBoard().length; ++row) {
			for (int col = 0; col < game.getBoard().length; ++col) {
				if ((lineCount2 == 3) || (lineCount2 == 6)) {
					System.out.print("| ");
				} // if
				lineCount2++;
				System.out.print(game.getBoard()[row][col] + " ");
			} // inner
			System.out.print("\n");
			lineCount2 = 0;
			lineCount1++;
			if ((lineCount1 == 3)) {
				System.out.print("----------------------");
				lineCount1 = 0;
			} // if
		} // outer
	} // displayBoard

	/*
	 * Changing number, checking that coordinates were entered correctly, checking
	 * that making that move is legal
	 */

	public void changeNumber(String row, String col) {

		do {
			game.canUndo = true;
			if (((game.getBoard()[Integer.parseInt(row) - 1][Integer.parseInt(col) - 1]).equalsIgnoreCase("*"))
					|| (game.getBoard()[Integer.parseInt(row) - 1][Integer.parseInt(col) - 1]
							.equalsIgnoreCase("\n*"))) {
				System.out.print("\nWhat number would you like to change it to?:");
				isNumberThere = false;
				numberInput = input.next();

				if ((numberInput.equalsIgnoreCase("Q")) || (numberInput.equalsIgnoreCase("Q"))) {
					System.out.print("\nThanks for playing!");
					game.quitGame();
				} // if Q
				else if ((numberInput.equalsIgnoreCase("S")) || (numberInput.equalsIgnoreCase("S"))) {
					game.saveGame(fileName);
					if (game.saveGame(fileName) == 0)
						System.out.print("Game succesfully saved!");
					else if (game.saveGame(fileName) == SudokuGame.FILENOTFOUND_ERROR) {
						System.err.print("Error, File not found, please re-enter");
					} else if (game.saveGame(fileName) == SudokuGame.FILENOTFOUND_ERROR) {
						System.err.print("Error, file contents not correct, please re-enter");
					}
				} // if S
				else {
					if (!(game.validateNumber(numberInput))) {
						System.err.print("Error, must be a number 1-9 inclusive");
					}

					else if (!game.validateRow(game.getRow() - 1, numberInput)) {
						System.err.print("There is already a " + numberInput + " in that row, please re-enter\n");
						isRowValid = false;
						allValid = false;
					}

					else if (!(game.validateCol(game.getCol() - 1, numberInput))) {
						System.err.print("There is already a " + numberInput + " in that col, please re-enter\n");
						isColValid = false;
						allValid = false;
					} else if (!(game.validateSquare((game.getRow() - 1), (game.getCol() - 1), numberInput))) {
						System.err.print("There is already a " + numberInput + " in that square, please re-enter\n");
						allValid = false;
						isSquareValid = false;
					} else {
						isRowValid = true;
						isColValid = true;
						isSquareValid = true;
						allValid = true;
						if (Integer.parseInt(col) == 1)
							game.getBoard()[Integer.parseInt(row) - 1][Integer.parseInt(col) - 1] = "\n" + numberInput;
						else
							game.getBoard()[Integer.parseInt(row) - 1][Integer.parseInt(col) - 1] = numberInput;
					} // else
				} // else
			} else if ((Character.isDigit(row.charAt(0))) || (Character.isDigit(col.charAt(0)))) {
				System.err.print("\nNumber already there, number has not been entered\n");
				isNumberThere = true;
			} // else if
		} while ((!isNumberThere) && (!(game.validateNumber(row)) && (!(game.validateNumber(col))))
				&& (!(game.validateNumber(numberInput))) && (!(isColValid)) && (!(isRowValid)) && (!(isSquareValid)));

	}// changeNumber
}// sudokuInterface
