/**
 * @author: Ryan Liu, Vismaya Theertha, Daniel Wei
 * @date: 2021-05-12
 * @version: 2.0.1
 * @description: SinglePlayerMode of Boggle
 */


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class SinglePlayerMode extends JFrame implements ActionListener // Class definition
{

	String name = MainMenu.Name1;// Stores Name of User

	private static final long serialVersionUID = 1L;//
	// Initialize all Swing components
	static int gamePoints;// points of the player
	// JPanel to contain and organize other components
	JPanel toolPanel = new JPanel();
	JPanel ArraytoolPanel = new JPanel();
	JPanel inputPanel = new JPanel();

	// Size of the grid
	final int ROW = 5;
	final int COL = 5;

	ArrayList<String> searchedWords = new ArrayList<String>();// the words that were searched on the grid previously.
																// This list is here to prevent the re-entry of words

	// The object that will contain the elements of searched words
	JList<Object> list;
	// DefaultListModel allows for mutable list
	DefaultListModel<Object> listModel;
	// JScrollPane adds the functionality to scroll
	JScrollPane listScroller;

	int pointsToWin = MainMenu.winningpoints;// points needed to win, the value is passed from the main menu (what is
												// entered by the user)

	// Records the number of rounds
	int rounds = 0;
	JLabel grid[][] = new JLabel[ROW][COL];// 2d array of grid

	JButton checkButton = new JButton("Check");
	JButton quitButton = new JButton("Quit");// The button to exit the program
	JButton infoButton = new JButton("How to play?");
	JButton restartButton = new JButton("Reset");// the button to reset the process to play the game again
	JButton shakeButton = new JButton("Shake");

	JLabel titleLabel = new JLabel("BOGGLE", JLabel.CENTER);
	// JTextField points = new JTextField("points");
	JLabel points = new JLabel(name + "'s Points : 0", JLabel.CENTER);// the textfield to display the user's points

	// Textfield provides a area for the user to enter words
	JTextField tf = new JTextField("Enter Here");

	// Creating fonts
	Font font = tf.getFont().deriveFont(Font.PLAIN, 24f);
	Font titleFont = tf.getFont().deriveFont(Font.PLAIN, 30f);
	Font font2 = tf.getFont().deriveFont(Font.PLAIN, 20f);// the font to use on the text in the textfield points
	Font titleFont2 = tf.getFont().deriveFont(Font.PLAIN, 20f);// the font to use in tf

	// Background color for the buttons
	Color green = new Color(204, 255, 204);
	Color blue = new Color(102, 204, 255);
	Color red = new Color(255, 0, 0);

	// GridBagConstraints used to control the placement of components in the
	// GridBagLayout
	GridBagConstraints gbc = new GridBagConstraints();

	// 2d array of characters that make up the grid
	char[][] lettersGrid = new char[ROW][COL];

	// ArrayList
	ArrayList<String> wordlist = new ArrayList<String>(); // provides the ability to check if a word is in wordlist

	// Class constructor
	public SinglePlayerMode() throws FileNotFoundException {
		// Temporary variable to the string value of a char in the letterGrid
		String letter;
		// Read and store wordlist for determining if a word exist
		Scanner scanner = new Scanner(new File("src/wordlist.txt"));
		while (scanner.hasNextLine()) {
			wordlist.add(scanner.nextLine());
		}

		// Instantiating DefaultListModel that allows for mutable JList
		listModel = new DefaultListModel<Object>();
		// Instantiating the JList with the listModel
		list = new JList<Object>(listModel);
		// Setting the properties of the list
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setVisibleRowCount(-1);
		// Instantiating the listScroller with the JList
		listScroller = new JScrollPane(list);
		// Setting the size of the JScrollPane
		listScroller.setPreferredSize(new Dimension(250, 300));

		// Generates and fills lettersGrid with random letters
		generateRandomLetter(lettersGrid);

		// Create and match each label with a letter in the lettersGrid
		for (int row = 0; row < ROW; row++) {
			for (int col = 0; col < COL; col++) {
				// Get and parse the char from the lettersGrid to a string
				letter = Character.toString(lettersGrid[row][col]);

				// For each grid, a JLabel is created with value of the char and allighmnet at
				// CENTER
				grid[row][col] = new JLabel(letter, JLabel.CENTER);
				// Setting the size of each JLabel
				grid[row][col].setPreferredSize(new Dimension(40, 40));
				// Setting the background to opaque so that the background color can be seen
				grid[row][col].setOpaque(true);

			}
		}

		// Setting the layout of the GUI
		setLayout(new GridBagLayout());
		GridLayout Array_Letters_Layout = new GridLayout(ROW, COL, 3, 3);

		// Setting the font of the welcome label
		titleLabel.setFont(font);

		// Setting the layout of the ArraytoolPanel
		ArraytoolPanel.setPreferredSize(new Dimension(500, 500));
		ArraytoolPanel.setLayout(Array_Letters_Layout);
		ArraytoolPanel.setBackground(Color.LIGHT_GRAY);

		// Adding Action Listener to buttons
		checkButton.addActionListener(this);
		quitButton.addActionListener(this);
		infoButton.addActionListener(this);
		restartButton.addActionListener(this);
		shakeButton.addActionListener(this);

		// Setting the properties of the check button
		checkButton.setPreferredSize(new Dimension(150, 30));
		checkButton.setFont(font);

		// Setting the properties of the textfield
		tf.setFont(font);
		tf.setPreferredSize(new Dimension(300, 30));

		// Setting the properties of the infoButton and quitButton
		restartButton.setPreferredSize(new Dimension(130, 30));
		infoButton.setPreferredSize(new Dimension(130, 30));
		quitButton.setPreferredSize(new Dimension(100, 30));

		points.setFont(font2);// setting the font of points
		points.setPreferredSize(new Dimension(400, 40));// setting the size of the textfielf

		titleLabel.setFont(titleFont);

		// Adding the quitButton and Instructions button to the button panel
		toolPanel.add(restartButton);// add restart button to the button panel
		toolPanel.add(infoButton);
		toolPanel.add(quitButton);

		// Adding the textfield and check button to the input panel
		inputPanel.add(tf);
		inputPanel.add(checkButton);// add the textfield points to the JPanel inputPanel
		inputPanel.setBorder(BorderFactory.createEtchedBorder());
		// Add array of buttons to the ArraytoolPanel and setting the background color
		// of each to blue
		for (int row = 0; row < ROW; row++) {
			for (int col = 0; col < COL; col++) {
				// Adding the JLabel to to the JPanel
				ArraytoolPanel.add(grid[row][col]);
				// Setting the background color of the JLabel
				grid[row][col].setBackground(blue);
				// Setting the fonts of the JLabel
				grid[row][col].setFont(font);

			}
		}

		// Adding each component to this JFrame
		gbc.anchor = GridBagConstraints.LINE_START;
		
		add(titleLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		add(points, gbc);

		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.gridx = 0;
		gbc.gridy = 1;
		add(ArraytoolPanel, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.NORTH;
		add(inputPanel, gbc);

		gbc.anchor = GridBagConstraints.CENTER;
		add(listScroller, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.LINE_START;
		add(toolPanel, gbc);

		setTitle("Boggle Single Player Mode");// Title of the window
		setSize(1200, 700);// Size of the Window

		// Setting the window to be visible when executed
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	// Interaction section
	public void actionPerformed(ActionEvent event) {// beginning of action performed method
		boolean recurring;// a boolean variable to confirm whether a word has been entered before during
							// the game
							// the wordlist
		boolean exist = false;// boolean variable to confirm whether the word being searched for is found on
								// the grid
		String command = event.getActionCommand();// String value of the command

		// Changes the background color of every button in the grid to blue
		for (int row = 0; row < ROW; row++) {
			for (int col = 0; col < COL; col++) {
				grid[row][col].setBackground(blue);
			}
		}

		if (command.equals("Check")) {// If the user clicks the checkButton
			rounds++;
			if (gamePoints < pointsToWin) {// if the gamePoints is less than whats necessary to win
				String word = tf.getText().toUpperCase();// the word entered by the user should be converted to all caps
															// to search on the grid
				int defaultLengthOfWord = 3;// the length of the word that the user is allowed to input
				if (word.length() < defaultLengthOfWord) {// If the word is too short

					JOptionPane.showMessageDialog(null, "This word is too short. Try again.",
							"This word is too short. Try again.", JOptionPane.WARNING_MESSAGE);// DIsplay that it is too
																								// short

				} // close if (word.length() < defaultLengthOfWord)
				else {// If the word has an acceptable length
					recurring = checkPreviousword(word);// the boolean variable will be set to the value being return by
														// the method that checks whether
					// the word that was entered now, has been entered before
					if (recurring == true) {// If the word has been entered before
						JOptionPane.showMessageDialog(null, "You've entered this word before. Try again.",
								"You've entered this word before. Try again.", JOptionPane.WARNING_MESSAGE);// Display
																											// that the
																											// word has
																											// been
																											// entered
																											// before

					} else {// If the word has not been entered before, and has an acceptable length

						ArrayList<Object> pastCor = new ArrayList<Object>();// an array list that will hold the
																			// coordinate of the first character of the
																			// word entered, on the grid
						// Main loop to check if the word exist on grid

						if (word.length() == 0) {// Checking if there is a input in textfield
							JOptionPane.showMessageDialog(null, "Please Enter A Word", "Warning",
									JOptionPane.WARNING_MESSAGE);// if a word was not entered when the checkbutton was
																	// pressed
						} // close if (word.length() == 0)

						else {// When there is a word in textfield

							if (word.length() > 1) {// General case

								// Searches through every occurrence of the first letter of the word in the grid
								for (int i = 0; i < lettersGrid.length; i++) {
									for (int j = 0; j < lettersGrid[i].length; j++) {

										char c = lettersGrid[i][j];
										if (c == word.charAt(0)) {// If the first letter of the word in the at the grid
											int[] cor = { i, j };
											boolean[][] visited = new boolean[5][5];

											visited[i][j] = true;
											pastCor.add(cor);
											exist = (findPath(word, 1, i, j, pastCor, lettersGrid, visited));// Calling
																												// the
																												// method
																												// that
											// checks
											// if the word exist
											if (exist) {

												break;
											} else {
												pastCor.clear();
											}
										}
									}
									// No need to loop more because the word exists
									if (exist) {

										break;
									}
								}
							}

							else {// Edge case : when the length of the word is 0

								for (int i = 0; i < lettersGrid.length; i++) {
									for (int j = 0; j < lettersGrid[i].length; j++) {

										char c = lettersGrid[i][j];
										if (c == word.charAt(0)) {

											exist = true;

											break;
										}
									}
									if (exist) {
										break;
									}
								}

							}

							// Displaying the result of the user input
							if (exist) {

								// Display that the word exists on grid
								JOptionPane.showMessageDialog(null, "Word Found on Grid", "Found on Grid",
										JOptionPane.INFORMATION_MESSAGE);

								// Check if the word is in wordlist
								if (wordlist.contains(word.toLowerCase())) {// words in wordlist are lowercase
									// Change the background color of the letters on the grid

									int len = word.length();// len set to the length of the word
									gamePoints = gamePoints + len;// gamePoints has the len added to it, so that the
																	// user receives a point for each character in the
																	// valid word entered
									System.out.println(gamePoints);// print to ensure it is the correct amount
									String displayPoints = Integer.toString(gamePoints);// convert the int value to
																						// string so that it could be
																						// displayed on screen
									// points.setText(displayPoints);
									points.setText(name + "'s Points : " + displayPoints);// display the points of the
																							// user on the points
																							// textfield

									for (Object i : pastCor) {
										grid[((int[]) i)[0]][((int[]) i)[1]].setBackground(green);
									}
									// Display that the word exists in wordlist
									JOptionPane.showMessageDialog(null, "Word Found In Wordlist", "Found In Wordlist",
											JOptionPane.INFORMATION_MESSAGE);

									if (gamePoints >= pointsToWin) {// if the user now has points higher than or equal
																	// to whats necessary

										points.setText(
												"You win with " + displayPoints + " points, " + name + ". Congrats!");// Display
																														// the
																														// user
																														// wins
									} // close if (gamePoints >= pointsToWin)
								} // close if (wordlist.contains(word.toLowerCase()))
								else {// if the wordlist does not contain the word on the grid that is being searched

									for (Object i : pastCor) {
										grid[((int[]) i)[0]][((int[]) i)[1]].setBackground(red);
									}
									// Display that the word does not exist in wordlist
									JOptionPane.showMessageDialog(null, "Word Not Found In Wordlist", "Not Found",
											JOptionPane.INFORMATION_MESSAGE);

								} // close inner else statement

							}
							// close if (exist)
							else {
								// if it doesn't exist on the grid
								// Display that the word does not exist on the grid
								JOptionPane.showMessageDialog(null, "Word Not Found On Grid", "Not Found",
										JOptionPane.INFORMATION_MESSAGE);

							}
							// close inner else statement
						}
						// close if statement for when there is a word in the textfield
						pastCor.clear();
					}
					// close else statement for when word has not been entered before
				}
				// close else statement for when the word has an acceptable length
			}
			// close if gamePoints is less than pointsToWin
			else {
				// else if gamePoints is higher than or equal to pointsToWin
				tf.setText("GAME OVER.");// let the user know the game is over
			} // close else statement

			if (rounds == 2) {

				shakeButton.setPreferredSize(new Dimension(100, 30));
				remove(toolPanel);
				toolPanel.add(shakeButton);
				add(toolPanel, gbc);
				revalidate();
				repaint();
			}

		} else if (command.equals("How to play?")) {// if the user clicks the infoButton
			// Display instructions
			JTextArea msg = new JTextArea(
					"This video game is a replica of the board game Boggle. There are 25 dice with a letter on each side and they each have been rolled once to select the "
							+ "letter that will be displayed in each cell of this 5*5 grid. Your job is to identify English words in the grid. Before doing this, ensure that you have "
							+ "entered your name in the text field labelled with ‘Name’ beside it. The English word is valid if the contiguous letters in words are adjacent diagonally,"
							+ "horizontally, or vertically. That being said, if a letter in a cell is meant to be a part of what you think is a valid word, then that letter in that cell "
							+ "can only be used once to build the word. It is however okay to use the same letter more than once for it to be considered a valid word. The word has to have "
							+ "a set length for it to be considered valid. You can set the required length to any size larger than 3 characters or equal to 3 characters. 3 characters would "
							+ "be the default required length. For each valid word you enter, you get 1 point for each character within that word. "
							+ "To win you must get a set amount of points. You may decide to change this amount, but the default number of points needed to win is 50. You may also choose to play against another person, against the "
							+ "computer, or to play against another person and computer at the same time. When playing against others, whether that be a computer, person or both, when a "
							+ "player makes it to or past 50 points, the game does not automatically stop there, but rather allows for the players to finish the entire round, so that each "
							+ "player had a fair chance at winning. When you, the user or users, has/have passed their chances twice in total, you will be given the option to shake up the board."
							+ "If you’d like to restart the game, please click the button labelled ‘Restart’. If you’d like to quit the game, please click the button ‘Quit’. HAVE FUN!");
			msg.setRows(10);// set the rows of the text
			msg.setColumns(50);// set the columns of the text
			msg.setLineWrap(true);// wraps the text in JTextArea
			msg.setWrapStyleWord(true);// wraps the text in JTextArea

			JScrollPane scrollPane = new JScrollPane(msg);// msg set to be displayed on the JScrollPane
			// optionpane.getFrame().setSize()
			JOptionPane.showMessageDialog(null, scrollPane);// display scrollPane
		} // close if (command.equals("How to play?"))
		else if (command.equals("Quit")) {

			int quit = JOptionPane.showConfirmDialog(null, "Do You Want To Quit?", "Warning",
					JOptionPane.CANCEL_OPTION);
			if (quit == 0) {
				dispose();
			}
		} else if (command.equals("Reset")) {

			int reset = JOptionPane.showConfirmDialog(null, "Do you want to reset this JFrame?", "Warning",
					JOptionPane.CANCEL_OPTION);

			if (reset == 0) {

				dispose();
				try {
					gamePoints = 0;
					new SinglePlayerMode();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else if (command.equals("Shake")) {

			shakeGrid(lettersGrid);
		}

	}// close actionPerformed method
//checkPreviousWord compare the word entered by the user currently with all words entered previously during the game

	// Generate a new set of random letters and sets the new letters to the 2d-array
	// of JLabel
	public void shakeGrid(char[][] lettersGrid) {
		String letter;
		// Generates a new set of random letters
		generateRandomLetter(lettersGrid);

		// Sets the new letters to the 2d-array of JLabel
		for (int row = 0; row < ROW; row++) {
			for (int col = 0; col < COL; col++) {
				letter = Character.toString(lettersGrid[row][col]);
				grid[row][col].setText(letter);

			}
		}

	}

	public boolean checkPreviousword(String currentword) {// method header

		boolean recurring = false;// set reccuring to false, the variable that confirms whether it has been
									// entered before
		boolean found = false;// if the word being searched now matches another

		if (searchedWords.size() == 0) {// if no other words have been entered
			searchedWords.add(currentword);// add the first word entered to the words searched
			listModel.addElement(currentword);
			recurring = false;// the word does not match any other because there's nothing to match it with
		} // close if (searchedWords.size() == 0)
		else {// if (searchedWords.size() != 0)
			for (int m = 0; m < searchedWords.size(); m++) {// for loop that increments by +1, for the size of
															// searchedWords
				// to see compare each element

				if ((searchedWords.get(m).equals(currentword))) {// if the current element of the list with the index of
																	// the variable in the for loop, is equal to the
																	// current word being searched
					tf.setText("");// textfield where word is entered would contain no text
					recurring = true;// a match exists
					found = true;// a match has been found
					break;// break out of the for loop

				} // close if ((searchedWords.get(m).equals(currentword)))

			} // close for (int m = 0; m < searchedWords.size(); m++)
			if (!found) {// if the current word being search does not match previous entries

				searchedWords.add(currentword);// add it to the list of words that have been searched
				listModel.addElement(currentword);
				recurring = false;// confirm a match for the word entered has not been found on the list
			} // close if (!found)
		} // close if the searched word size is greater than 0

		// Changing the data in the JList

		return recurring;// return whether the word has been entered before or not
	}// close checkPreviousword method

	// Path finding method
	/**
	 * 
	 * @param word        -String The word that we want to see if exists on the grid
	 * @param inx         -int The current index of the letter in the word that we
	 *                    want to check
	 * @param x           -int The index of the current row
	 * @param y           -int The index of the current column
	 * @param pastCor     -ArrayList This stores the path that the algorithm took
	 * @param lettersGrid -2D-Array of char The letters on the grid
	 * @param visited     -2D-Array of boolean, the index of each element in the
	 *                    array represents
	 * @return if the each letter in the word is found, then the method would return
	 *         true
	 */
	public static boolean findPath(String word, int inx, int x, int y, ArrayList<Object> pastCor, char[][] lettersGrid,
			boolean[][] visited) {

		boolean exists = false;
		int[] cor = new int[2];

		// Terminating Conditions : When the algorithm checks the last letter in the
		// word
		if (inx == (word.length() - 1)) {

			// There are 8 try/catch to test the 8 adjacent letters in the grid - they are
			// identical

			try {
				cor[0] = x - 1;
				cor[1] = y;
				// Checks if the adjacent letters in the grid is the last letter in the word and
				// if the letter is visited before
				if ((word.charAt(word.length() - 1) == lettersGrid[x - 1][y]) && !visited[x - 1][y]) {
					visited[x - 1][y] = true;
					// The coordinates of the last letters is added to pastCor
					pastCor.add(cor);

					// This terminates the recursion -> the top of the stack returns true
					return true;
				}

				// Catches when the coordiantes of the adjacent letters is not in the grid
			} catch (IndexOutOfBoundsException e) {

				// System.out.println("Top Out of Bound");

			}

			// Check top left
			try {
				cor[0] = x - 1;
				cor[1] = y - 1;
				if ((word.charAt(word.length() - 1) == lettersGrid[x - 1][y - 1]) && !visited[x - 1][y - 1]) {
					visited[x - 1][y - 1] = true;
					pastCor.add(cor);
					return true;
				}

			} catch (IndexOutOfBoundsException e) {

				// System.out.println("Top Left Out of Bound");

			}
			// Check top right
			try {
				cor[0] = x - 1;
				cor[1] = y + 1;
				if ((word.charAt(word.length() - 1) == lettersGrid[x - 1][y + 1]) && !visited[x - 1][y + 1]) {
					visited[x - 1][y + 1] = true;
					pastCor.add(cor);
					return true;
				}

			} catch (IndexOutOfBoundsException e) {

				// System.out.println("Top Right Out of Bound");

			}
			// Check left
			try {
				cor[0] = x;
				cor[1] = y - 1;
				if ((word.charAt(word.length() - 1) == lettersGrid[x][y - 1]) && !visited[x][y - 1]) {
					visited[x][y - 1] = true;
					pastCor.add(cor);
					return true;
				}

			} catch (IndexOutOfBoundsException e) {

				//// System.out.println("Left Out of Bound");

			}
			// Check right
			try {
				cor[0] = x;
				cor[1] = y + 1;
				if ((word.charAt(word.length() - 1) == lettersGrid[x][y + 1]) && !(visited[x][y + 1])) {
					visited[x][y + 1] = true;
					pastCor.add(cor);
					return true;
				}

			} catch (IndexOutOfBoundsException e) {

				// System.out.println("Right Out of Bound");

			}
			// Check bottom
			try {
				cor[0] = x + 1;
				cor[1] = y;
				if ((word.charAt(word.length() - 1) == lettersGrid[x + 1][y]) && !(visited[x + 1][y])) {
					visited[x + 1][y] = true;
					pastCor.add(cor);
					return true;
				}

			} catch (IndexOutOfBoundsException e) {

				// System.out.println("Bottom Out of Bound");

			}
			// Check bottom left
			try {
				cor[0] = x + 1;
				cor[1] = y - 1;

				if ((word.charAt(inx) == lettersGrid[x + 1][y - 1]) && !(visited[x + 1][y - 1])) {
					visited[x + 1][y - 1] = true;
					pastCor.add(cor);
					return true;
				}

			} catch (IndexOutOfBoundsException e) {

				// System.out.println("Bottom Left Out of Bound");

			}
			// Check bottom right
			try {
				cor[0] = x + 1;
				cor[1] = y + 1;
				if ((word.charAt(inx) == lettersGrid[x + 1][y + 1]) && !(visited[x + 1][y + 1])) {
					visited[x + 1][y + 1] = true;
					pastCor.add(cor);
					return true;
				}

			} catch (IndexOutOfBoundsException e) {

				// System.out.println("Bottom Right Out of Bound");

			}

		}

		// Recursive calls
		else {

			// There are 8 try/catch to test the 8 adjacent letters in the grid - they are
			// identical

			// Check top
			try {
				// A new copy(shallow copy) of the pastCor
				ArrayList<Object> tempPastCor = new ArrayList<Object>(pastCor);

				// Create a new 2d-array that matches the size of visited
				boolean[][] tempVis = new boolean[visited.length][visited[0].length];

				// Copying the value from visited to tempVis
				for (int i = 0; i < visited.length; i++) {
					for (int j = 0; j < visited[0].length; j++) {
						tempVis[i][j] = visited[i][j];
					}
				}
				cor[0] = x - 1;
				cor[1] = y;

				if ((word.charAt(inx) == lettersGrid[x - 1][y]) && !(visited[x - 1][y])) {
					tempVis[x - 1][y] = true;
					// the coordinates that we checked is added to tempPastCor
					tempPastCor.add(cor);

					// tempPastCor and tempVis are passed because findPath is pass by referance that
					// can change the value of PastCor and visited
					exists = findPath(word, inx + 1, x - 1, y, tempPastCor, lettersGrid, tempVis);

					// If the algorithm successfully finds the rest of the letters in the grid
					if (exists) {
						// Removing all elements in pastCor
						pastCor.clear();

						// Adding the elements in tempPastCor(after storing the coordinates with
						// findPath ) to pastCor
						// Since this is pass by reference, the value in the original pastCor is also
						// changed
						for (Object i : tempPastCor) {
							pastCor.add(i);
						}

						// Return true to the recursive call/base call before
						return true;
					}

				}

			} catch (IndexOutOfBoundsException e) {

				// System.out.println("Top Out of Bound");
			}

			// Check top left
			try {
				ArrayList<Object> tempPastCor = new ArrayList<Object>(pastCor);
				boolean[][] tempVis = new boolean[visited.length][visited[0].length];

				for (int i = 0; i < visited.length; i++) {
					for (int j = 0; j < visited[0].length; j++) {
						tempVis[i][j] = visited[i][j];
					}
				}
				cor[0] = x - 1;
				cor[1] = y - 1;
				if ((word.charAt(inx) == lettersGrid[x - 1][y - 1]) && !(visited[x - 1][y - 1])) {
					tempVis[x - 1][y - 1] = true;
					tempPastCor.add(cor);
					exists = findPath(word, inx + 1, x - 1, y - 1, tempPastCor, lettersGrid, tempVis);
					if (exists) {
						pastCor.clear();

						for (Object i : tempPastCor) {
							pastCor.add(i);
						}
						return true;
					}
				}

			} catch (IndexOutOfBoundsException e) {

				// System.out.println("Top Left Out of Bound");

			}
			// Check top right
			try {
				ArrayList<Object> tempPastCor = new ArrayList<Object>(pastCor);
				boolean[][] tempVis = new boolean[visited.length][visited[0].length];

				for (int i = 0; i < visited.length; i++) {
					for (int j = 0; j < visited[0].length; j++) {
						tempVis[i][j] = visited[i][j];
					}
				}
				cor[0] = x - 1;
				cor[1] = y + 1;
				if ((word.charAt(inx) == lettersGrid[x - 1][y + 1]) && !(visited[x - 1][y + 1])) {
					tempVis[x - 1][y + 1] = true;
					tempPastCor.add(cor);
					exists = findPath(word, inx + 1, x - 1, y + 1, tempPastCor, lettersGrid, tempVis);
					if (exists) {
						pastCor.clear();

						for (Object i : tempPastCor) {
							pastCor.add(i);
						}
						return true;
					}

				}

			} catch (IndexOutOfBoundsException e) {

				// System.out.println("Top Right Out of Bound");

			}
			// Check left
			try {
				ArrayList<Object> tempPastCor = new ArrayList<Object>(pastCor);
				boolean[][] tempVis = new boolean[visited.length][visited[0].length];

				for (int i = 0; i < visited.length; i++) {
					for (int j = 0; j < visited[0].length; j++) {
						tempVis[i][j] = visited[i][j];
					}
				}
				cor[0] = x;
				cor[1] = y - 1;
				if ((word.charAt(inx) == lettersGrid[x][y - 1]) && !visited[x][y - 1]) {
					tempVis[x][y - 1] = true;
					tempPastCor.add(cor);
					exists = findPath(word, inx + 1, x, y - 1, tempPastCor, lettersGrid, tempVis);
					if (exists) {
						pastCor.clear();

						for (Object i : tempPastCor) {
							pastCor.add(i);
						}
						return true;
					}

				}

			} catch (IndexOutOfBoundsException e) {
				// System.out.println("Left Out of Bound");

			}

			// Check right

			try {
				ArrayList<Object> tempPastCor = new ArrayList<Object>(pastCor);
				boolean[][] tempVis = new boolean[visited.length][visited[0].length];

				for (int i = 0; i < visited.length; i++) {
					for (int j = 0; j < visited[0].length; j++) {
						tempVis[i][j] = visited[i][j];
					}
				}
				cor[0] = x;
				cor[1] = y + 1;
				if ((word.charAt(inx) == lettersGrid[x][y + 1]) && !(visited[x][y + 1])) {
					tempVis[x][y + 1] = true;
					tempPastCor.add(cor);
					exists = findPath(word, inx + 1, x, y + 1, tempPastCor, lettersGrid, tempVis);

					if (exists) {
						pastCor.clear();

						for (Object i : tempPastCor) {
							pastCor.add(i);
						}
						return true;
					}

				}

			} catch (IndexOutOfBoundsException e) {
				// System.out.println("Right Out of Bound");

			}

			// Check bottom
			try {
				ArrayList<Object> tempPastCor = new ArrayList<Object>(pastCor);
				boolean[][] tempVis = new boolean[visited.length][visited[0].length];

				for (int i = 0; i < visited.length; i++) {
					for (int j = 0; j < visited[0].length; j++) {
						tempVis[i][j] = visited[i][j];
					}
				}
				cor[0] = x + 1;
				cor[1] = y;
				if ((word.charAt(inx) == lettersGrid[x + 1][y]) && !(visited[x + 1][y])) {
					tempVis[x + 1][y] = true;
					tempPastCor.add(cor);
					exists = findPath(word, inx + 1, x + 1, y, tempPastCor, lettersGrid, tempVis);
					if (exists) {
						pastCor.clear();

						for (Object i : tempPastCor) {
							pastCor.add(i);
						}
						return true;
					}

				}

			} catch (IndexOutOfBoundsException e) {

				// System.out.println("Bottom Out of Bound");

			}
			// Check bottom left
			try {
				ArrayList<Object> tempPastCor = new ArrayList<Object>(pastCor);
				boolean[][] tempVis = new boolean[visited.length][visited[0].length];

				for (int i = 0; i < visited.length; i++) {
					for (int j = 0; j < visited[0].length; j++) {
						tempVis[i][j] = visited[i][j];
					}
				}
				cor[0] = x + 1;
				cor[1] = y - 1;
				if ((word.charAt(inx) == lettersGrid[x + 1][y - 1]) && !(visited[x + 1][y - 1])) {
					tempVis[x + 1][y - 1] = true;
					tempPastCor.add(cor);
					exists = findPath(word, inx + 1, x + 1, y - 1, tempPastCor, lettersGrid, tempVis);
					if (exists) {
						pastCor.clear();

						for (Object i : tempPastCor) {
							pastCor.add(i);
						}
						return true;
					}

				}

			} catch (IndexOutOfBoundsException e) {

				// System.out.println("Bottom Left Out of Bound");

			}

			// Check bottom right
			try {
				ArrayList<Object> tempPastCor = new ArrayList<Object>(pastCor);
				boolean[][] tempVis = new boolean[visited.length][visited[0].length];

				for (int i = 0; i < visited.length; i++) {
					for (int j = 0; j < visited[0].length; j++) {
						tempVis[i][j] = visited[i][j];
					}
				}
				cor[0] = x + 1;
				cor[1] = y + 1;
				if ((word.charAt(inx) == lettersGrid[x + 1][y + 1]) && !(visited[x + 1][y + 1])) {
					tempVis[x + 1][y + 1] = true;
					tempPastCor.add(cor);
					exists = findPath(word, inx + 1, x + 1, y + 1, tempPastCor, lettersGrid, tempVis);
					if (exists) {
						pastCor.clear();

						for (Object i : tempPastCor) {
							pastCor.add(i);
						}
						return true;
					}

				}
			} catch (IndexOutOfBoundsException e) {
				// System.out.println("Bottom Right Out of Bound");

			}
		}
		// return false when the algorithm fails to find a adjacent letter that matches
		// the letter in the word
		return false;
	}

	/**
	 * Word Generation Methods: generateRandomLetter Generates a random list of
	 * letters
	 * 
	 * @param lettersGrid The 2d-array of char that is going to be filled with words
	 */
	public static void generateRandomLetter(char[][] lettersGrid) {
		int randomDice, randomLetter;

		// Used to determine which dices are left from the 25 dices
		ArrayList<Integer> dicesLeft = new ArrayList<Integer>();

		// Contains the orientations of each dice
		String[] dices = { "AAAFRS", "AAEEEE", "AAFIRS", "ADENNN", "AEEEEM", "AEEGMU", "AEGMNN", "AFIRSY", "BJKQXZ",
				"CCNSTW", "CEIILT", "CEILPT", "CEIPST", "DDLNOR", "DHHLOR", "DHHNOT", "DHLNOR", "EIIITT", "EMOTTT",
				"ENSSSU", "FIPRSY", "GORRVW", "HIPRRY", "NOOTUW", "OOOTTU"

		};

		// Creating a 1d array with the size of the total amount of letters
		char[] letters = new char[lettersGrid[0].length * lettersGrid.length];
		// the total amount of letters in the grid

		// Adding the index of each dice to dicesLeft
		for (int i = 0; i < letters.length; i++) {
			dicesLeft.add(i);
		}

		// This takes an iterative approach -> determine the letter on the dice, one
		// dice at a time
		for (int i = 0; i < letters.length; i++) {
			// Gives a random index with-in dicesLeft
			randomDice = (int) ((Math.random() * (dicesLeft.size())));

			// The value at that index is removed
			dicesLeft.remove(randomDice);
			// randomLetter is the random index of a letter with-in a dice
			randomLetter = (int) ((Math.random() * (5)));
			// the letter is assigned to the randomLetter index of a dice at randomDice
			// index
			letters[i] = dices[randomDice].charAt(randomLetter);
		}

		// Matching the 1d array of letters to the 2-d grid
		for (int i = 0; i < lettersGrid.length; i++) {
			for (int j = 0; j < lettersGrid[0].length; j++) {
				lettersGrid[i][j] = letters[i * (lettersGrid.length - 1) + j];

			}
		}
	}

}
