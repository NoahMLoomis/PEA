package nloomis_G20_A02_Sudoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/*
 * Author: Noah Loomis
 * G20_A02
 */

public class SudokuGame {

	private String board[][] = new String[9][9];
	public boolean validateFileFormat = false;
	private String singleData;
	private String singleLine;
	public boolean canUndo;
	private int row, col, saveCode;
	public int validateCode, readCode;
	private boolean validNumber;
	public static final int FILENOTFOUND_ERROR = -1;
	public static final int IO_ERROR = -2;
	public static final int NUMBERFORMAT_ERROR = -3;
	public static final int NOSUCHELEMENT_ERROR = -4;

	public void setRow(String r) {
		row = Integer.parseInt(r);
	}// setRow()

	public int getRow() {
		return row;
	}// getRow()

	public void setCol(String c) {
		col = Integer.parseInt(c);
	}// setCol()

	public int getCol() {
		return col;
	}// getCol()

	/*
	 * Reading file with a scanner, assigning values to board[][]
	 */

	public boolean readFile(String fileName) {
		readCode = 0;
		try {
			File sudokuFile = new File(fileName);
			Scanner fileReader = new Scanner(sudokuFile);
			fileReader.useDelimiter("~");

			while (fileReader.hasNext()) {
				for (int row = 0; row < board.length; ++row) {
					for (int col = 0; col < board[row].length; ++col) {
						singleData = fileReader.next();
						board[row][col] = singleData;
						validateFileFormat = true;
					} // inner
				} // outer
			} // while

			fileReader.close();
		} // try
		catch (NumberFormatException e) {
			readCode = NUMBERFORMAT_ERROR;
			validateFileFormat = false;
		} catch (NoSuchElementException e) {
			readCode = NOSUCHELEMENT_ERROR;
			validateFileFormat = false;
		} catch (FileNotFoundException e) {
			readCode = FILENOTFOUND_ERROR;
			validateFileFormat = false;
		} catch (IOException e) {
			readCode = IO_ERROR;
			validateFileFormat = false;
		}
		return validateFileFormat;
	}// readFile

	/*
	 * Validating the file, ensuring that it's in the proper format
	 */

	public boolean validateFileFormat(String fileName) {
		try {
			File sudoku = new File(fileName);
			Scanner validateReader = new Scanner(sudoku);
			while (validateReader.hasNextLine()) {

				singleLine = validateReader.nextLine();

				for (int i = 0; i < singleLine.length(); i += 2) {
					if ((Character.isDigit(singleLine.charAt(i)))
							|| ((Character.toString(singleLine.charAt(i)).equals("*")))) {
						return true;
					} // if
				} // for
				for (int i = 1; i < singleLine.length(); i += 2) {
					if (Character.toString(singleLine.charAt(i)).equalsIgnoreCase("~")) {
						return true;
					} // if

				} // for

			} // while
			validateCode = 0;
			validateReader.close();
			return false;
		} // try
		catch (NoSuchElementException e) {
			validateCode = NOSUCHELEMENT_ERROR;
			return false;
		} catch (FileNotFoundException e) {
			validateCode = FILENOTFOUND_ERROR;
			// System.err.print("\nError file not found, please re-enter");
			return false;
		} catch (IOException e) {
			validateCode = IO_ERROR;
			// System.err.print("\nError with file contents IOExeption");
			return false;
		}
	} // validateFileFormat

	/*
	 * Saving the game by overriding the file
	 */

	public String[][] getBoard() {
		return board;
	} // getBoard()

	public int saveGame(String fileName) {
		try {
			File sudokuFile = new File(fileName);
			FileWriter writer = new FileWriter(sudokuFile);

			for (int row = 0; row < board.length; ++row) {
				for (int col = 0; col < board[row].length; ++col) {
					writer.write(board[row][col] + "~");
				} // inner
			} // outer
			saveCode = 0;
			writer.close();
		} // try

		catch (FileNotFoundException e) {
			saveCode = FILENOTFOUND_ERROR;
		} catch (IOException e) {
			saveCode = IO_ERROR;
		}
		return saveCode;

	}// saveGame()

	public void quitGame() {
		System.exit(1);
	}// quitGame()

	public void undoMove(int lastRow, int lastCol) {
		if (!(board[lastRow][lastCol].equalsIgnoreCase("\n*")) || !(board[lastRow][lastCol].equalsIgnoreCase("*")))
			if (lastCol == 0)
				board[lastRow][lastCol] = "\n*";
			else
				board[lastRow][lastCol] = "*";
	}// undoMove()

	public boolean validateCol(int col, String num) {
		for (int i = 0; i < board[col].length; ++i) {
			if ((board[i][col].equalsIgnoreCase(num)) || (board[i][col].equalsIgnoreCase("\n" + num))) {
				return false;
			}
		} // for
		return true;
	}// validateCol()

	public boolean validateRow(int row, String num) {
		for (int i = 0; i < board[row].length; ++i) {
			if ((board[row][i].equalsIgnoreCase(num)) || (board[row][i].equalsIgnoreCase("\n" + num))) {
				return false;
			}
		} // for
		return true;
	}// validate row

	/*
	 * Loops through the square in question, if the value already exists in the
	 * square, return false, otherwise return true
	 */

	public boolean validateSquare(int ro, int co, String whatNum) {

		/*
		 * Loops through first 3 squares
		 */
		if ((ro >= 0 && ro < 3)) {
			for (int row = 0; row < 3; ++row) {
				if (co >= 0 && co < 3) {
					for (int col = 0; col < 3; ++col) {
						if (((board[row][col]).equalsIgnoreCase(whatNum))
								|| (board[row][col].equalsIgnoreCase("\n" + whatNum)))
							return false;
					} // inner
				} // if
				if (co >= 3 && co < 6) {
					for (int col = 3; col < 6; ++col) {
						if (((board[row][col]).equalsIgnoreCase(whatNum))
								|| (board[row][col].equalsIgnoreCase("\n" + whatNum)))
							return false;

					} // inner
				} // if

				if (co >= 6 && co < 9) {
					for (int col = 6; col < 9; ++col) {
						if (((board[row][col]).equalsIgnoreCase(whatNum))
								|| (board[row][col].equalsIgnoreCase("\n" + whatNum)))
							return false;
					} // inner
				} // if

			} // outer
			return true;
		} // if getRow>0 && getRow<=3

		/*
		 * Loops through second 3 squares
		 */

		else if (ro >= 3 && ro < 6) {
			for (int row = 3; row < 6; ++row) {
				if (co >= 0 && co < 3) {
					for (int col = 0; col < 3; ++col) {
						if ((board[row][col]).equalsIgnoreCase(whatNum)
								|| (board[row][col].equalsIgnoreCase("\n" + whatNum)))
							return false;
					} // inner
				} // if
				if (co >= 3 && co < 6) {
					for (int col = 3; col < 6; ++col) {
						if ((board[row][col]).equalsIgnoreCase(whatNum)
								|| (board[row][col].equalsIgnoreCase("\n" + whatNum)))
							return false;
					} // inner
				} // if

				if (co >= 6 && co < 9) {
					for (int col = 6; col < 9; ++col) {
						if ((board[row][col]).equalsIgnoreCase(whatNum)
								|| (board[row][col].equalsIgnoreCase("\n" + whatNum)))
							return false;
					} // inner
				} // if
			} // outer
			return true;
		} // if (ro>3 && ro<=6)

		/*
		 * Loops through last 3 squares
		 */

		else if (ro >= 6 && ro < 9) {
			for (int row = 6; row < 9; ++row) {
				if (co >= 0 && co < 3) {
					for (int col = 0; col < 3; ++col) {
						if ((board[row][col]).equalsIgnoreCase(whatNum)
								|| (board[row][col].equalsIgnoreCase("\n" + whatNum)))
							return false;
					} // inner
				} // if
				if (co >= 3 && co < 6) {
					for (int col = 3; col < 6; ++col) {
						if ((board[row][col]).equalsIgnoreCase(whatNum)
								|| (board[row][col].equalsIgnoreCase("\n" + whatNum)))
							return false;
					} // inner
				} // if
				if (co >= 6 && co < 9) {
					for (int col = 6; col < 9; ++col) {
						if ((board[row][col]).equalsIgnoreCase(whatNum)
								|| (board[row][col].equalsIgnoreCase("\n" + whatNum)))
							return false;
					} // inner
				} // if
			} // outer
			return true;
		} // if (ro>3 && ro<=6)
		else
			return false;
	} // validateSquare(int ro, int co, String whatNum)

	/*
	 * Sees if there's coordinates left that haven't been entered
	 */

	public boolean isGameOver() {
		for (int row = 0; row < board.length; ++row) {
			for (int col = 0; col < board[row].length; ++col) {
				if ((board[row][col]).equalsIgnoreCase("*") || (board[row][col]).equalsIgnoreCase("\n*"))
					return false;
			} // inner
		} // outer
		return true;
	} // isGameOver()

	/*
	 * Validating that the number entered is a int 1-9 inclusive
	 */

	public boolean validateNumber(String num) {
		if (num.length() != 1)
			validNumber = false;
		else if (Character.isLetter(num.charAt(0)) || (Character.isLetter(num.charAt(0))))
			validNumber = false;
		else if (((Integer.parseInt(num) > 9) || ((Integer.parseInt(num) < 1)))) {
			validNumber = false;
		} else
			validNumber = true;
		return validNumber;
	}// validateNumber
}// sudokuGame
