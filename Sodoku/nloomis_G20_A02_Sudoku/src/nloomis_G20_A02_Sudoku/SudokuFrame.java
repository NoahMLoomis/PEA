package nloomis_G20_A02_Sudoku;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/*
 * Author: Noah Loomis
 * G20_A02
 */

public class SudokuFrame extends JFrame implements ActionListener {

	private JPanel sudokuPanel, displayPanel, keypadPanel;
	private JButton sudokuBtn[][] = new JButton[9][9];
	private JButton keypadBtn[][] = new JButton[3][3];
	private SudokuGame sudokuBoard = new SudokuGame();
	private JTextField fldWhatFile;
	private JMenuBar menuBar = new JMenuBar();
	private JMenu fileMenu = new JMenu("File");
	private JMenuItem helpMenuItem = new JMenuItem("Help");
	private JMenuItem saveMenuItem = new JMenuItem("Save game");
	private JMenuItem undoMenuItem = new JMenuItem("Undo last move");
	private JMenuItem quitMenuItem = new JMenuItem("Quit Game");
	private JMenuItem aboutMenuItem = new JMenuItem("About");
	private JButton btnDisplay;
	private JLabel lblWhatFile;
	private String fileNameDisplay;
	private boolean validEntry;
	private boolean allValid = false;
	private String tempKey;
	private String changeNumber;
	private int whatRow, whatCol;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SudokuFrame frame = new SudokuFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SudokuFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 700);
		sudokuPanel = new JPanel();
		setContentPane(sudokuPanel);
		displayPanel = new JPanel();
		displayPanel.setBounds(6, 144, 500, 500);
		displayPanel.setLayout(new GridLayout(9, 9));
		this.setTitle("sudoku");
		sudokuPanel.setLayout(null);
		this.getContentPane().add(displayPanel);

		setJMenuBar(menuBar);
		menuBar.add(fileMenu);
		fileMenu.add(saveMenuItem);
		fileMenu.add(undoMenuItem);
		fileMenu.add(helpMenuItem);
		fileMenu.add(aboutMenuItem);
		fileMenu.add(quitMenuItem);
		fileMenu.addActionListener(this);
		helpMenuItem.addActionListener(this);
		saveMenuItem.addActionListener(this);
		undoMenuItem.addActionListener(this);
		quitMenuItem.addActionListener(this);
		aboutMenuItem.addActionListener(this);

		saveMenuItem.setEnabled(false);
		undoMenuItem.setEnabled(false);

		lblWhatFile = new JLabel("What file would you like to open?");
		lblWhatFile.setBounds(25, 108, 219, 16);
		sudokuPanel.add(lblWhatFile);

		fldWhatFile = new JTextField();
		fldWhatFile.setBounds(243, 103, 239, 26);
		sudokuPanel.add(fldWhatFile);
		fldWhatFile.setColumns(10);

		btnDisplay = new JButton("Display ");
		btnDisplay.setBounds(484, 103, 117, 29);
		sudokuPanel.add(btnDisplay);
		btnDisplay.addActionListener(this);

		keypadPanel = new JPanel();
		keypadPanel.setBounds(599, 191, 291, 331);
		sudokuPanel.add(keypadPanel);
		keypadPanel.setLayout(new GridLayout(3, 3));
		initializeKeypad();
		this.getContentPane().add(keypadPanel);
	} // SudokuFrame()

	/*
	 * Create and set values of keypad, attach actionListener to each
	 */

	public void initializeKeypad() {
		int count = 1;
		for (int row = 0; row < keypadBtn.length; ++row)
			for (int col = 0; col < keypadBtn[row].length; ++col) {
				keypadBtn[row][col] = new JButton(Integer.toString(count));
				keypadBtn[row][col].setEnabled(false);
				keypadBtn[row][col].addActionListener(this);
				keypadBtn[row][col].setFont(new Font("board", Font.ROMAN_BASELINE, 24));
				keypadPanel.add(keypadBtn[row][col]);
				count++;
			} // for
	} // initializeKeypad

	/*
	 * Create set values for board, attach actionListener to each
	 */

	public void initializeBoard() {
		for (int row = 0; row < sudokuBtn.length; ++row)
			for (int col = 0; col < sudokuBtn[row].length; ++col) {
				sudokuBtn[row][col] = new JButton(sudokuBoard.getBoard()[row][col]);
				sudokuBtn[row][col].addActionListener(this);
				sudokuBtn[row][col].setFont(new Font("board", Font.ROMAN_BASELINE, 24));
				if ((sudokuBtn[row][col].getText().equalsIgnoreCase("*"))
						|| (sudokuBtn[row][col].getText().equalsIgnoreCase("\n*")))
					sudokuBtn[row][col].setText("");
				displayPanel.add(sudokuBtn[row][col]);
			} // for
		fldWhatFile.setVisible(false);
		lblWhatFile.setVisible(false);
		btnDisplay.setVisible(false);

	} // initializeBoard()

	/*
	 * Validating the file format, and reading the file, displays the appropriate
	 * message.
	 */

	public boolean setBoard() {
		boolean chkBoardFormat = true;

		fileNameDisplay = fldWhatFile.getText();
		if ((fileNameDisplay.equals(""))) {
			JOptionPane.showMessageDialog(this, "Default of sudoku.txt applied");
			fileNameDisplay = "sudoku.txt";
		} // if nothing entered, default of sodou.txt applied
		sudokuBoard.readFile(fileNameDisplay);
		if ((sudokuBoard.readCode == SudokuGame.NOSUCHELEMENT_ERROR) && (!(sudokuBoard.readFile(fileNameDisplay)))) {
			JOptionPane.showMessageDialog(this, "Error, file does not follow the proper format");
			chkBoardFormat = false;
		} else if ((sudokuBoard.readCode == SudokuGame.FILENOTFOUND_ERROR)
				&& (!sudokuBoard.validateFileFormat(fileNameDisplay))) {
			JOptionPane.showMessageDialog(this, "Error, file not found exeption");
			chkBoardFormat = false;
		} else if ((sudokuBoard.readCode == SudokuGame.IO_ERROR)
				&& (!sudokuBoard.validateFileFormat(fileNameDisplay))) {
			JOptionPane.showMessageDialog(this, "Error, IO exeption");
			chkBoardFormat = false;
		} else {
			sudokuBoard.readFile(fileNameDisplay);
		} // if not exceptions and file is valid, readFile()
		return chkBoardFormat;
	}// btnDisplay_actionPerformed()

	public void saveGame_actionPerformed() {
		if (sudokuBoard.saveGame(fileNameDisplay) == 0) {
			sudokuBoard.saveGame(fileNameDisplay);
			JOptionPane.showMessageDialog(this, "Game succesfully saved");
		} else if (sudokuBoard.saveGame(fileNameDisplay) == SudokuGame.FILENOTFOUND_ERROR)
			JOptionPane.showMessageDialog(this, "Game not saved, file not found");
		else if (sudokuBoard.saveGame(fileNameDisplay) == SudokuGame.IO_ERROR)
			JOptionPane.showMessageDialog(this, "Game not saved, IOExeption occured");
	}// saveGame_actionPerformed()

	public void undoMove_actionPerformed() {
		if (sudokuBoard.canUndo) {
			sudokuBoard.undoMove(whatRow, whatCol);
			sudokuBtn[whatRow][whatCol].setText(sudokuBoard.getBoard()[whatRow][whatCol]);
			sudokuBtn[whatRow][whatCol].setText("");
			JOptionPane.showMessageDialog(this, "Move undone");
			sudokuBoard.canUndo = false;
		} // if
		else
			JOptionPane.showMessageDialog(this, "You can't do 2 undo's in a row", "Error", 2);

	}// undoMove_actionPerformed()

	public void quitGame_actionPerformed() {
		JOptionPane.showMessageDialog(this, "Thank you for playing!");
		sudokuBoard.quitGame();

	} // quitGame_actionPerformed()

	public void about_actionPerformed() {
		JOptionPane.showMessageDialog(this, "sudoku Game\nBy Noah Loomis\n1st year, class of 2022\nHeritage College ",
				"About", 1);
	} // about_actionPerformed()

	public void help_actionPerformed() {
		JOptionPane.showMessageDialog(this,
				"To play, click on the square that you want to enter a number in\n\nThen, click on the keypad to choose which number you'd like to enter\n\nEach row, column, and square can contain each number exactly once",
				"Help", 1);
	} // help_actionPerformed()

	public void setTempKey(int row, int col) {
		tempKey = (keypadBtn[row][col].getText());
		undoMenuItem.setEnabled(true);
	} // keypadNum_actionPerformed()

	public String getTempKey() {
		return tempKey;
	} // getTempKey()

	/*
	 * Check that square does not have a value in it.
	 */

	public void setChangeNumber(int row, int col) {
		undoMenuItem.setEnabled(false);
		if (sudokuBtn[row][col].getText().equalsIgnoreCase("")) {
			validEntry = true;
			sudokuBoard.setRow(row + "");
			sudokuBoard.setCol(col + "");
			whatRow = row;
			whatCol = col;
		} else {
			validEntry = false;
			JOptionPane.showMessageDialog(this,
					"You can only change a square that does not have a value already in it");
		} // else
	} // setChangeNumber(int row, int col)

	public int getChangeNumber() {

		return Integer.parseInt(changeNumber);
	} // setChangeNumber

	public void enableKeypad() {
		for (int row = 0; row < keypadBtn.length; ++row)
			for (int col = 0; col < keypadBtn[row].length; ++col) {
				keypadBtn[row][col].setEnabled(true);
			} // for
	} // enableKeypad()

	public void disableKeypad() {
		for (int row = 0; row < keypadBtn.length; ++row)
			for (int col = 0; col < keypadBtn[row].length; ++col) {
				keypadBtn[row][col].setEnabled(false);
			} // for
	} // disableKeypad()

	/*
	 * Checks if the game is over, and whether player has won or lost, displays
	 * appropriate message.
	 */

	public void chkGameOver() {
		if (sudokuBoard.isGameOver()) {
			for (int r = 0; r < sudokuBtn.length; ++r) {
				for (int c = 0; c < sudokuBtn.length; ++c)
					sudokuBtn[r][c].setEnabled(false);
			} // for
			if (allValid) {
				JOptionPane.showMessageDialog(this, "Congrats, you've won!!! Thanks for playing");
				sudokuBoard.quitGame();
			} // if
			else if (!allValid)
				JOptionPane.showMessageDialog(this, "You lost, better luck next time.");
			sudokuBoard.quitGame();
		} // if isGameOver()

	}// chkGameOver()

	/*
	 * Verifies that the number the user inputs is not in the same row, column, or
	 * square, if it is, displays appropriate message, otherwise enter the value
	 */

	public void changeNumber(int row, int col) {
		sudokuBoard.canUndo = true;
		if (!(sudokuBoard.validateRow(sudokuBoard.getRow(), getTempKey())))
			JOptionPane.showMessageDialog(this, "Already a " + getTempKey() + " in that row");

		else if (!(sudokuBoard.validateCol(sudokuBoard.getCol(), getTempKey())))
			JOptionPane.showMessageDialog(this, "Already a " + getTempKey() + " in that col");

		else if (!(sudokuBoard.validateSquare(sudokuBoard.getRow(), sudokuBoard.getCol(), getTempKey())))
			JOptionPane.showMessageDialog(this, "Already a " + getTempKey() + " in that square");
		else {
			allValid = true;
			if (col == 0)
				sudokuBoard.getBoard()[row][col] = "\n" + getTempKey();
			else
				sudokuBoard.getBoard()[row][col] = getTempKey();
			sudokuBtn[row][col].setText(sudokuBoard.getBoard()[row][col]);
		}
	}// changeNumber

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == saveMenuItem)
			saveGame_actionPerformed();

		else if (e.getSource() == undoMenuItem)
			undoMove_actionPerformed();

		else if (e.getSource() == quitMenuItem)
			quitGame_actionPerformed();

		else if (e.getSource() == aboutMenuItem)
			about_actionPerformed();

		else if (e.getSource() == btnDisplay) {
			if (setBoard())
				initializeBoard();
			chkGameOver();
		} // if

		else if (e.getSource() == helpMenuItem)
			help_actionPerformed();

		for (int row = 0; row < sudokuBoard.getBoard().length; ++row)
			for (int col = 0; col < sudokuBoard.getBoard()[row].length; ++col) {
				if (e.getSource() == sudokuBtn[row][col]) {
					setChangeNumber(row, col);
					if (validEntry)
						enableKeypad();
				} // if
			} // for

		for (int row = 0; row < keypadBtn.length; ++row)
			for (int col = 0; col < keypadBtn[row].length; ++col) {
				if (e.getSource() == keypadBtn[row][col]) {
					setTempKey(row, col);
					changeNumber(whatRow, whatCol);
					disableKeypad();
					saveMenuItem.setEnabled(true);
					undoMenuItem.setEnabled(true);
					chkGameOver();
				} // if
			} // for
	}// actionPerformed(ActionEvent e)

} // SudokuFrame
