/**
 * @author: Ryan Liu, Vismaya Theertha, Daniel Wei
 * @date: 2021-05-12
 * @version: 2.0.1
 * @description: ComputerVsSinglePlayerMode of Boggle
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
import javax.swing.Timer;

public class ComputerVsSinglePlayerMode extends JFrame implements ActionListener // Class definition
{

	String name = MainMenu.Name1;// the name of the user passed from the main menu

	static String pen = "src/static/file_example_WAV_10MG.wav";// music filepath
	int playerNumsTurn = 1;// who's turn it is
	private static final long serialVersionUID = 1L;
	// Initialize all Swing components
	static int gamePointsP1;// player one's points
	static int gamePointsComp;// computer's points
	// JPanel to contain and organize other components
	JPanel toolPanel = new JPanel();
	JPanel ArraytoolPanel = new JPanel();
	JPanel pointsPanel = new JPanel();
	JPanel inputPanel = new JPanel();

	static String Difficultylevel = MainMenu.Difficultylevel;// what difficulty level the user wants to play on, passed from
														// main menu

	// Size of the grid
	final static int ROW = 5,COL = 5;
	ArrayList<String> searchedWords = new ArrayList<String>();// the words that were searched on the grid previously.
	// This list is here to prevent the re-entry of words

	// The object that will contain the elements of searched words
	JList<Object> list;
	// DefaultListModel allows for mutable list
	DefaultListModel<Object> listModel;
	// JScrollPane adds the functionality to scroll
	JScrollPane listScroller;

	// int pointsToWin = 10;
	int pointsToWin = MainMenu.winningpoints;// the points required to win, as set by the user on the main menu

	JLabel grid[][] = new JLabel[ROW][COL];// 2d array of JLabel

	JButton checkButton = new JButton("Check");
	JButton quitButton = new JButton("Quit");// The button to exit the program
	JButton infoButton = new JButton("How to play?");
	JLabel titleLabel = new JLabel("BOGGLE", JLabel.CENTER);
	JButton restartButton = new JButton("Reset"); // JButton for restarting the game
	// Textfield provides a area for the user to enter words
	// JTextField tf = new JTextField("Enter Here, player 1");
	JTextField tf = new JTextField("Enter your word here, " + name);
	// JTextField points1 = new JTextField("points");
	JLabel points1 = new JLabel(MainMenu.Name1 + "'s points : 0", JLabel.CENTER);// player's points displayed on JLabel
	// JTextField points2 = new JTextField("points computer");
	JLabel points2 = new JLabel("AI's points : 0", JLabel.CENTER);// computer's points displayed on JLabel
	JLabel gameProgress = new JLabel("Game Status: in progress");// JLabel to display if the game is in progress or over

	// Creating fonts
	Font font = tf.getFont().deriveFont(Font.PLAIN, 24f);
	Font titleFont = tf.getFont().deriveFont(Font.PLAIN, 30f);
	ArrayList<String> wordsFound = new ArrayList<String>();

	Font font2 = points1.getFont().deriveFont(Font.PLAIN, 20f);// font used for player's points
	Font titleFont2 = points1.getFont().deriveFont(Font.PLAIN, 20f);// font used for player's points

	Font font3 = points2.getFont().deriveFont(Font.PLAIN, 20f);// font used for computer's points
	Font titleFont3 = points2.getFont().deriveFont(Font.PLAIN, 20f);// font used for computer's points

	// Background color for the buttons
	Color green = new Color(204, 255, 204);
	Color blue = new Color(102, 204, 255);
	Color red = new Color(255, 0, 0);

	// 2d array of characters that make up the grid
	static char[][] lettersGrid = new char[ROW][COL];

	// ArrayList
	ArrayList<String> wordlist = new ArrayList<String>(); // provides the ability to check if a word is in wordlist
	GridBagConstraints gbc = new GridBagConstraints();
	//Defines a timer
	//Timer timer = null;
	// Class constructor
	public ComputerVsSinglePlayerMode() throws FileNotFoundException {
		
		
		
		// Tempory variable to the string value of a char in the letterGrid
		String letter;
		// Read and store wordlist for determining if a word exist
		Scanner scanner = new Scanner(new File("src/wordlist.txt"));
		while (scanner.hasNextLine()) {
			wordlist.add(scanner.nextLine());
		}
		//Creates a timer to record the time of the turn
	/*	timer = new Timer(30000, new ActionListener() { // Timer 15 seconds
			// THis defines that action that will be performed every 15 seconds
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "You Took Too Long, Turn Alternates to The Next Player ",
						"You Took Too Long", JOptionPane.WARNING_MESSAGE);
				nextTurn();
			}
		});
		timer.start(); */
		
		
		// Generates and fills lettersGrid with random letters
		generateRandomLetter(lettersGrid);
		wordsFound = checkForEachWord();// method returns a list containing all the words on the particular word list
										// (depending on difficulty) and on the grid

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
		// Creating the layout for panels

		GridLayout Array_Button_Layout = new GridLayout(ROW, COL, 3, 3);

		// Setting the font of the welcome label
		titleLabel.setFont(font);

		// Setting the layout of the ArraytoolPanel
		ArraytoolPanel.setPreferredSize(new Dimension(500, 500));
		ArraytoolPanel.setLayout(Array_Button_Layout);
		ArraytoolPanel.setBackground(Color.LIGHT_GRAY);

		// Adding Action Listener to buttons
		checkButton.addActionListener(this);
		quitButton.addActionListener(this);
		infoButton.addActionListener(this);
		restartButton.addActionListener(this);

		// Setting the properties of the check button
		checkButton.setPreferredSize(new Dimension(150, 30));
		checkButton.setFont(font);

		// Setting the properties of the textfield
		tf.setFont(font);
		tf.setPreferredSize(new Dimension(400, 30));

		// Adding boarders around the input panel
		inputPanel.setBorder(BorderFactory.createEtchedBorder());

		// Setting the size for the tool buttons
		restartButton.setPreferredSize(new Dimension(130, 30));
		infoButton.setPreferredSize(new Dimension(130, 30));
		quitButton.setPreferredSize(new Dimension(100, 30));

		points1.setFont(font2);// set font for player 1's points
		points1.setPreferredSize(new Dimension(200, 30));// set size of textfield

		points2.setFont(font3);// set font for computer's points
		points2.setPreferredSize(new Dimension(300, 30));// set size of textfield

		gameProgress.setFont(font3);// set font for game status
		gameProgress.setPreferredSize(new Dimension(300, 30));// set size of textfield

		titleLabel.setFont(titleFont);

		// Adding the quitButton to the button panel
		toolPanel.add(restartButton);
		toolPanel.add(infoButton);
		toolPanel.add(quitButton);

		pointsPanel.add(points1);
		pointsPanel.add(points2);

		// Adding the textfield and check button to the input panel
		inputPanel.add(tf);
		inputPanel.add(checkButton);// add JButton to check for word to JPanel
		// inputPanel.add(points1);//add JTextField to display user's points to JPanel
		// inputPanel.add(points2);//add JTextField to display computer's points to
		// JPanel
		// inputPanel.add(gameProgress);//add JTextField to display game status to
		// JPanel

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
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(titleLabel, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;

		add(pointsPanel, gbc);

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

		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.CENTER;
		add(gameProgress, gbc);

		setTitle("Boggle Computer Vs 1 Player");// Title of the window
		setSize(1200, 700);// Size of the Window

		// Setting the window to be visible when executed
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// Interaction section
	public void actionPerformed(ActionEvent event) {// Start of actionPerformed method
		boolean recurring;// variable returns whether the word has been entered before during the game
		boolean exist = false;// returns whether the word is found on both the complete wordlist and the grid
		String command = event.getActionCommand();// String value of the command

		
		
		// Changes the background color of every button in the grid to blue
		for (int row = 0; row < ROW; row++) {
			for (int col = 0; col < COL; col++) {
				grid[row][col].setBackground(blue);
			}
		}

		if (command.equals("Check")) {// if the user clicks the checkButton

			// timer.restart();
			if ((gamePointsP1 < pointsToWin && playerNumsTurn == 1) || gamePointsComp < pointsToWin) {// if either of
																										// the users
																										// have points
																										// below the
																										// value
																										// required to
																										// win in the
																										// same round
				String word = tf.getText().toUpperCase();// the word entered by the user converted to upper case to
															// check for it on the grid
				int defaultLengthOfWord = 3;// the length of the word that the user is allowed to input
				if (word.length() < defaultLengthOfWord) {// If the word is too short
					// while (currentwordword.length() < defaultLengthOfWord) {
					JOptionPane.showMessageDialog(null, "This word is too short.", "This word is too short.",
							JOptionPane.WARNING_MESSAGE);// display that it's too short so it is invalid
					tf.setText("Computer's turn");// displau that it is now the computer's turn
					playerNumsTurn = 2;// the player's turn has switched to the computer's

				} // close if (word.length() < defaultLengthOfWord)
				else {// If the word has an acceptable length
					recurring = checkPreviousword(word);// the boolean variable will be set to the value being return by
					// the method that checks whether
					// the word that was entered now, has been entered before
					if (recurring == true) {// If the word has been entered before
						JOptionPane.showMessageDialog(null, "You've entered this word before.",
								"You've entered this word before. ", JOptionPane.WARNING_MESSAGE);// display it has been
																									// entered before so
																									// its not valid
						tf.setText("Computer's turn");// display its the computer's turn
						playerNumsTurn = 2;// switch turn to computer

					} // close if (recurring == true)
					else {// If the word has not been entered before, and has an acceptable length

						ArrayList<Object> pastCor = new ArrayList<Object>();// an array list that will hold the
						// coordinate of the first character of the
						// word entered, on the grid
						// Main loop to check if the word exist on grid

						if (word.length() == 0) {// Checking if there is a input in textfield
							JOptionPane.showMessageDialog(null, "Please Enter A Word", "Warning",
									JOptionPane.WARNING_MESSAGE);
						}

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
											// Calling the method that checks if the word exist
											exist = (findPath(word, 1, i, j, pastCor, lettersGrid, visited));
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

									int len = word.length();// the number of characters is equal to len
									gamePointsP1 = gamePointsP1 + len;// then add the number of characters in word to
																		// the points of player 1
									System.out.println(gamePointsP1);// print points, to debug
									String displayPoints = Integer.toString(gamePointsP1);// the conversion of the int
																							// value to string
									// points1.setText(displayPoints);
									points1.setText(name + "'s points: " + displayPoints);// displaying the string
																							// version of the points on
																							// the points1 textfield

									// Setting the background of the letters that are in the word to green
									for (Object i : pastCor) {
										grid[((int[]) i)[0]][((int[]) i)[1]].setBackground(green);
									}
									// Display that the word exists in wordlist
									JOptionPane.showMessageDialog(null, "Word Found In Wordlist", "Found In Wordlist",
											JOptionPane.INFORMATION_MESSAGE);
									// onList = true;
									// onGrid = true;
									/*
									 * int len = word.length(); gamePointsP1 = gamePointsP1 + len;
									 * System.out.println(gamePointsP1); String displayPoints =
									 * Integer.toString(gamePointsP1); //points1.setText(displayPoints);
									 * points1.setText(name+"'s points: "+displayPoints);
									 */
									// if (gamePointsP1 >= pointsToWin) {
									// points1.setText("YOU WIN! CONGRATS!");
									// }
								} else {
									// Setting the background of the letters that are in the word to red
									for (Object i : pastCor) {
										grid[((int[]) i)[0]][((int[]) i)[1]].setBackground(red);
									}
									// Display that the word does not exist in wordlist
									JOptionPane.showMessageDialog(null, "Word Not Found In Wordlist", "Not Found",
											JOptionPane.INFORMATION_MESSAGE);
									tf.setText("Computer's turn");// display that it is the computer's turn now
									playerNumsTurn = 2;// set that it is the computer's turn
								}

							} else {
								// Display that the word does not exist on the grid
								JOptionPane.showMessageDialog(null, "Word Not Found On Grid", "Not Found",
										JOptionPane.INFORMATION_MESSAGE);
								tf.setText("Computer's turn");// display that it is the computer's turn now
								playerNumsTurn = 2;// set that it is the computer's turn

							}
						}
						//Removing every element in pastCor so that it will not contaminate the next round 
						pastCor.clear();
					}
				}

				String compsWord = "";// the word to be entered by the user for this round

				compsWord = wordsFound.get(0);// compsWord takes the first value on the list
				wordsFound.remove(0);// the first value is removed from the list to avoid repetition
				int length2 = compsWord.length();// the length of the word is assigned to length2

				boolean reccuringForComp = false;// a variable that returns whether the word about to be entered by the
													// computer, has been entered before
				reccuringForComp = checkPreviousword(compsWord);// the method returns a boolean that determines whether
																// or not this word has been entered before or not

				while (reccuringForComp == true) {// if it has been entered before in the game
					compsWord = wordsFound.get(0);// take the new first word on the list and assign it to compsWord
					wordsFound.remove(0);// remove that word from the list
					reccuringForComp = checkPreviousword(compsWord);// again check whether that word has been entered
																	// before
				} // close while (reccuringForComp == true)

				length2 = compsWord.length();// the length of the word is assigned to length2

				// display what word the computer enters and tell the user its their turn now
				tf.setText("The AI has entered the word " + compsWord + ". Your turn " + name);

				ArrayList<Object> path = new ArrayList<Object>();
				// This pass by reference method adds the coordinatres to which the word is
				// found
				returnPath(path, compsWord);

				// Setting the background of the letters that are in the word to green
				for (Object i : path) {
					grid[((int[]) i)[0]][((int[]) i)[1]].setBackground(green);
				}

				gamePointsComp = gamePointsComp + length2;// gamePointsComp has added the length of the word entered now
															// to itself
				System.out.println(gamePointsComp);// print it, to debug
				String displayPoints = Integer.toString(gamePointsComp);// convert the computer's points to string
				points2.setText("AI have " + displayPoints + " points");// display the computer's points as a string

				if (gamePointsComp >= pointsToWin) {// if the computer has points higher than or equal to whats needed
													// to win
					checkButton.setEnabled(false);// disable checkButton
					if (gamePointsP1 < gamePointsComp) {// if the computer has points higher than the user
						points2.setText("AI won with " + gamePointsComp + " points");// display the computer wins
						points1.setText("You lost with " + gamePointsP1 + " points, " + name);// display the user loses
						gameProgress.setText("GAME OVER");// display the game has ended
						tf.setText("The AI has entered the word " + compsWord);// display what word the computer
																				// entered
					} // close if (gamePointsP1 < gamePointsComp)
					else if (gamePointsP1 > gamePointsComp) {// if the player has more points than the computer
						points2.setText("AI lost with " + gamePointsComp + " points");// display the computer loses
						points1.setText("You won with " + gamePointsP1 + " points, " + name);// display that the user
																								// wins
						gameProgress.setText("GAME OVER");// display the game is over
						tf.setText("The AI has entered the word " + compsWord);// display the word entered by the
																				// computer
					} // close else if (gamePointsP1 >gamePointsComp)
					else if (gamePointsP1 == gamePointsComp) {// if the computer and user both have points equal to each
																// other
						points2.setText("IT'S A TIE");// display it's a tie
						points1.setText("IT'S A TIE");// display it's a tie
						gameProgress.setText("GAME OVER");// display the game is over
						tf.setText("The AI has entered the word " + compsWord);// display what word was entered by
																				// the computer
					} // close else if (gamePointsP1 == gamePointsComp)
				} // close if (gamePointsComp >= pointsToWin)
				else if (gamePointsP1 >= pointsToWin && playerNumsTurn == 1) {// If player 1 has points higher than or
																				// equal to whats needed to win, and the
																				// round is over
					checkButton.setEnabled(false);// disable checkButton
					if (gamePointsP1 < gamePointsComp) {// if the computer has points higher than the user
						points2.setText("AI won with " + gamePointsComp + " points");// display the computer wins
						points1.setText("You lost with " + gamePointsP1 + " points, " + name);// display the user loses
						gameProgress.setText("GAME OVER");// display the game has ended
						tf.setText("The AI has entered the word " + compsWord);// display what word the computer
																				// entered
					} // close if (gamePointsP1 < gamePointsComp)
					else if (gamePointsP1 > gamePointsComp) {// if the player has more points than the computer
						points2.setText("AI lost with " + gamePointsComp + " points");// display the computer loses
						points1.setText("You won with " + gamePointsP1 + " points, " + name);// display that the user
																								// wins
						gameProgress.setText("GAME OVER");// display the game is over
						tf.setText("The AI has entered the word " + compsWord);// display the word entered by the
																				// computer
					} // close else if (gamePointsP1 >gamePointsComp)
					else if (gamePointsP1 == gamePointsComp) {// if the computer and user both have points equal to each
																// other
						points2.setText("IT'S A TIE");// display it's a tie
						points1.setText("IT'S A TIE");// display it's a tie
						gameProgress.setText("GAME OVER");// display the game is over
						tf.setText("The AI has entered the word " + compsWord);// display what word was entered by
																				// the computer
					} // close else if (gamePointsP1 == gamePointsComp)
				} // close else if (gamePointsP1 >= pointsToWin && playerNumsTurn == 1)
			} // close if ((gamePointsP1 < pointsToWin && playerNumsTurn == 1) ||
				// gamePointsComp < pointsToWin)
			else {// if the computer or user has won or both have won
				gameProgress.setText("GAME OVER.");// display the game is over
			} // close else statement (if the computer or user has won or both have won)

		} // close if (command.equals("Check"))
		else if (command.equals("How to play?")) {// if the user clicks the infoButton
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
		} else if (event.getSource() == restartButton) {

			int reset = JOptionPane.showConfirmDialog(null, "Do you want to reset this JFrame?", "Warning",
					JOptionPane.CANCEL_OPTION);

			if (reset == 0) {
				try {// open try block
					gamePointsP1 = 0;
					gamePointsComp = 0;
					new ComputerVsSinglePlayerMode();
				} // close try block
				catch (FileNotFoundException e) {// open catch block to handle file not found exception
					// TODO Auto-generated catch block
					e.printStackTrace();// handles exception
				} // close catch
				searchedWords.clear();// the list containing the words that have searched for during the game must be
										// cleared

				checkButton.setEnabled(true);// enable the check Button
				gamePointsP1 = 0;// set the points of the user to 0
				gamePointsComp = 0;// set the computer's points to 0
				tf.setText("Enter your word here, " + name);// reset tf to its original text
				points1.setText(name + "'s points");// reset points1 to its original text
				points2.setText("My (Computer's) points");// reset points2 to its original text
				gameProgress.setText("Game Status: in progress");//// reset gameProgress to its original text
				JOptionPane.showMessageDialog(null, "JFrame reset", "Warning", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	public void nextTurn() {

		playerNumsTurn = 2;
		checkButton.doClick();
		

	}

	// checkPreviousWord compares the word entered by the user currently with all
	// words entered previously during the game
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

	// All words is a method that returns all the words from one of the 3 modified
	// wordlist files.
	// The wordlist file has been modified in a way where one only includes words
	// with 3 characters,
	// for when the player selects easy mode so that the computer is entering
	// shorter words, giving them
	// a greater shot at winning. Another file is used for medium mode so it
	// includes words with 3-5 characters but in descending order.
	// The last file contains all words with a length ranging from 3-9 arranged in
	// descending order,
	// to make it as difficult as possible for the user to win, without conducting
	// the searching process
	// for too long.
	public static ArrayList<String> AllWords(ArrayList<String> words) throws FileNotFoundException {// method header
		File file = new java.io.File("src/static/WordListSorted9chars.txt");// create a file, the file path defaults to
																			// the file with 3-9 character long words
		if (Difficultylevel == "Easy") {// if the user chooses Easy mode on the main menu
			file = new java.io.File("src/static/WordListSorted3chars.txt");// set the file's filepath to the one where
																			// the file contains 3-4 character long
																			// words
		} // close if (Difficultylevel == "Easy")
		else if (Difficultylevel == "Medium") {// if the user chooses medium mode on the main menu
			file = new java.io.File("src/static/WordListSorted5chars.txt");// set the file's filepath to the one where
																			// the file contains 3-5 character long
																			// words
		} // close else if (Difficultylevel == "Medium")
		else if (Difficultylevel == "Hard") {// if the user chooses hard mode on the main menu
			file = new java.io.File("src/static/WordListSorted9chars.txt");// set the file's filepath to the one where
																			// the file contains 3-9 character long
																			// words
		} // close else if (Difficultylevel == "Hard")
		Scanner sc = new Scanner(file);// scan the chosen file

		while (sc.hasNext())// while there's another line
			words.add(sc.nextLine());// add what was scanned to the ArrayList<String>

		sc.close();// close the scanner
		return words;// return all the words from the chosen file
	}// close the method

//This method sequentially searches for each word on the wordlist chosen and returns a list of the words
//on the chosen wordlist that were found on the grid.
	public static ArrayList<String> checkForEachWord() throws FileNotFoundException {// method header
		ArrayList<String> wordsFound = new ArrayList<String>();// declare ArrayList that will contain the words found on
																// the grid
		String currentWordToSearch;// the word on the list being searched
		ArrayList<String> allWords = new ArrayList<String>();// declare ArrayList meant to contain all the words from
																// the chosen wordlist
		allWords = AllWords(allWords);// have AllWords return all the words on the wordlist
		boolean exist = false;// the variable meant to confirm whether the word exists on the grid
		ArrayList<Object> pastCor = new ArrayList<Object>();//
		for (int a = 0; a < allWords.size(); a++) {// for loop used to check for each word on the grid sequentially
			currentWordToSearch = allWords.get(a);// set the current word to search for, to be the element at the ath
													// index in AllWords
			currentWordToSearch = currentWordToSearch.toUpperCase();// convert the wordToSearch to upper case letters to
																	// search them on the grid

			if (currentWordToSearch.length() > 1) {// General case
				// Searches through every occurrence of the first letter of the word in the grid
				for (int i = 0; i < lettersGrid.length; i++) {
					for (int j = 0; j < lettersGrid[i].length; j++) {

						char c = lettersGrid[i][j];
						if (c == currentWordToSearch.charAt(0)) {// If the first letter of the word in the at the grid
							int[] cor = { i, j };
							boolean[][] visited = new boolean[5][5];

							visited[i][j] = true;
							pastCor.add(cor);
							exist = (findPath(currentWordToSearch, 1, i, j, pastCor, lettersGrid, visited));// Calling
							// the
							// method
							// that
							// checks
							// if the word exist
							if (exist) {
								wordsFound.add(currentWordToSearch);
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
						if (c == currentWordToSearch.charAt(0)) {
							exist = true;
							wordsFound.add(currentWordToSearch);

							break;
						}
					}
					if (exist) {
						break;
					}
				}

			}

		}

		System.out.println("On List: " + wordsFound);// debug, and find what values are stored
		return wordsFound;// return all the words in the specific wordlist found in grid
	}// close checkForEachWord method

	// To initialize and run the GUI
	public static void main(String[] args) throws FileNotFoundException {
		new ComputerVsSinglePlayerMode();
	}

	public void returnPath(ArrayList<Object> path, String word) {
		boolean exist = false;
		for (int i = 0; i < lettersGrid.length; i++) {
			for (int j = 0; j < lettersGrid[i].length; j++) {

				char c = lettersGrid[i][j];
				if (c == word.charAt(0)) {// If the first letter of the word in the at the grid
					int[] cor = { i, j };
					boolean[][] visited = new boolean[5][5];

					visited[i][j] = true;
					path.add(cor);
					// Calling the method that checks if the word exist
					exist = (findPath(word, 1, i, j, path, lettersGrid, visited));
					if (exist) {

						break;
					} else {
						path.clear();
					}
				}
			}
			// No need to loop more because the word exists
			if (exist) {

				break;
			}
		}

	}
}
