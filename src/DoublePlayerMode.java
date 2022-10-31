/**
 * @author: Ryan Liu, Vismaya Theertha, Daniel Wei
 * @date: 2021-05-12
 * @version: 2.0.0
 * @description: DoublePlayerMode of Boggle
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

public class DoublePlayerMode extends JFrame implements ActionListener // Class definition
{
	static String pen = "src/static/file_example_WAV_10MG.wav";// music filepath

	int playerNumsTurn = 1;// which player's turn it is
	private static final long serialVersionUID = 1L;
	// Initialize all Swing components
	static int gamePointsP1;// player 1's points
	static int gamePointsP2;// player 2's points
	// JPanel to contain and organize other components
	JPanel toolPanel = new JPanel();
	JPanel ArraytoolPanel = new JPanel();
	JPanel pointsPanel = new JPanel();
	JPanel inputPanel = new JPanel();

	// Size of the grid
	final int ROW = 5;
	final int COL = 5;
	ArrayList<String> searchedWords = new ArrayList<String>();// the words that were searched on the grid previously.
																// This list is here to prevent the reentry of words
	//Records the number of rounds
	int rounds=0;
	// The object that will contain the elements of searched words
	JList<Object> list;
	// DefaultListModel allows for mutable list
	DefaultListModel<Object> listModel;
	// JScrollPane adds the functionality to scroll
	JScrollPane listScroller;

	// int pointsToWin = 10;
	int pointsToWin = MainMenu.winningpoints;// the points needed to win, a value being passed from the main menu, since
												// it is the user's choice
	

	JLabel grid[][] = new JLabel[ROW][COL];// 2d array of JLabel
	
	
	// Buttons
	JButton checkButton = new JButton("Check");
	JButton quitButton = new JButton("Quit");// The button to exit the program
	JButton restartButton = new JButton("Reset");
	JButton infoButton = new JButton("How to play?");
	JLabel titleLabel = new JLabel("BOGGLE", JLabel.CENTER);
	JButton shakeButton = new JButton("Shake");

	JTextField tf = new JTextField("Enter your word, " + MainMenu.Name1);// tells player 1 to enter their word to search
	JLabel points1 = new JLabel(MainMenu.Name1 + "'s points : 0", JLabel.CENTER);
	JLabel points2 = new JLabel(MainMenu.Name2 + "'s points : 0", JLabel.CENTER);// displays points of player 2

	// Creating fonts
	Font font = tf.getFont().deriveFont(Font.PLAIN, 24f);
	Font titleFont = tf.getFont().deriveFont(Font.PLAIN, 30f);

	Font font2 = points1.getFont().deriveFont(Font.PLAIN, 20f);// font used for points1 textfield
	Font titleFont2 = points1.getFont().deriveFont(Font.PLAIN, 20f);// font used points1 textfield

	Font font3 = points2.getFont().deriveFont(Font.PLAIN, 20f);// font used for points2 textfield
	Font titleFont3 = points2.getFont().deriveFont(Font.PLAIN, 20f);// font used for points2 textfield

	// Background color for the buttons
	Color green = new Color(204, 255, 204);
	Color blue = new Color(102, 204, 255);
	Color red = new Color(255, 0, 0);
	
	//GridBagConstraints used to control the placement of components in the GridBagLayout
	GridBagConstraints gbc = new GridBagConstraints();

	// 2d array of characters that make up the grid
	char[][] lettersGrid = new char[ROW][COL];

	// ArrayList
	ArrayList<String> wordlist = new ArrayList<String>(); // provides the ability to check if a word is in wordlist
	//Defines a timer
	// Timer timer = null;

	// Class constructor
	public DoublePlayerMode() throws FileNotFoundException {
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

		// Tempory variable to the string value of a char in the letterGrid
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

		// Create and match each button with a letter in the lettersGrid
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

		// Creating the layout of the GUI
		setLayout(new GridBagLayout());
		GridLayout Array_Button_Layout = new GridLayout(ROW, COL, 3, 3);

		// Setting the font of the welcome label
		titleLabel.setFont(font);

		// Setting the layout of the ArraytoolPanel
		ArraytoolPanel.setPreferredSize(new Dimension(500, 500));
		ArraytoolPanel.setLayout(Array_Button_Layout);
		ArraytoolPanel.setBackground(Color.LIGHT_GRAY);

		// Adding Action Listener to buttons
		checkButton.addActionListener(this);// add ActionListener to checkButton
		quitButton.addActionListener(this);// add ActionListener to quitButton
		infoButton.addActionListener(this);// add ActionListener to infoButton
		restartButton.addActionListener(this);// add ActionListener to restartButton
		shakeButton.addActionListener(this);// add ActionListener to shakeButton

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
		points2.setPreferredSize(new Dimension(200, 30));// set size of textfield

		titleLabel.setFont(titleFont);

		// Adding the quitButton to the button panel
		toolPanel.add(restartButton);
		toolPanel.add(infoButton);
		toolPanel.add(quitButton);

		pointsPanel.add(points1);
		pointsPanel.add(points2);

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
		add(listScroller,gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.LINE_START;
		add(toolPanel, gbc);

		setTitle("Boggle Double Player Mode");// Title of the window
		setSize(900, 800);// Size of the Window

		setSize(1200, 700);// Size of the Window

		// Setting the window to be visible when executed
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// Interaction section
	public void actionPerformed(ActionEvent event) {// Start of actionPerformed method
		// a boolean variable to confirm whether a word has been entered before during
		// the game
		boolean recurring;
		// boolean variable to confirm whether the word being searched for is found on
		// the grid
		boolean exist = false;

		String command = event.getActionCommand();// String value of the command

		// Changes the background color of every button in the grid to blue
		for (int row = 0; row < ROW; row++) {
			for (int col = 0; col < COL; col++) {
				grid[row][col].setBackground(blue);
			}
		}

		if (command.equals("Check")) {// if the user clicks the checkButton
			// Restarts the timer for the other player
			// timer.restart();
			rounds++;

			// if either of the users have points below the value required to win in the
			// same round
			if ((gamePointsP1 < pointsToWin && playerNumsTurn == 1) || gamePointsP2 < pointsToWin) {

				// the word to search for points entered by the user, converted upper case to
				// search the grid
				String word = tf.getText().toUpperCase();
				int defaultLengthOfWord = 3;// the length of the word that the user is allowed to input
				if (word.length() < defaultLengthOfWord) {// if the length of the word is not long enough to be valid

					JOptionPane.showMessageDialog(null, "This word is too short. ",
							"This word is too short. Invalid entry", JOptionPane.WARNING_MESSAGE);// display it is too
																									// short
					if (playerNumsTurn == 1) {// if player 1 made this mistake

						tf.setText("Your turn " + MainMenu.Name2);// tell users it is now player 2's turn
						playerNumsTurn = 2;// set that it is player 2's turn
					} // close if (playerNumsTurn == 1)
					else if (playerNumsTurn == 2) {// if player 2 made this mistake
						// tf.setText("Your turn player 1");
						tf.setText("Your turn " + MainMenu.Name1);// tell users its player 1's turn now
						playerNumsTurn = 1;// set that it is player 1's turn
					} // close else if (playerNumsTurn == 2)
					if (gamePointsP2 >= pointsToWin) {// if player two has points higher than or equal to the points
														// needed to win
						checkButton.setEnabled(false);// disable the checkButton
						if (gamePointsP1 < gamePointsP2) {// if player2 has higher points than player 1
							points2.setText("You won with " + gamePointsP2 + " points, " + MainMenu.Name2);// display
																											// player
																											// two on
																											// player
																											// 2's
																											// textfield
							points1.setText("You lost with " + gamePointsP1 + " points, " + MainMenu.Name1);// display
																											// play 1
																											// lost on
																											// player
																											// 1's
																											// textfield
							tf.setText("GAME OVER");// the textfield to enter words displays the game is over
						} // close if (gamePointsP1 < gamePointsP2)
						else if (gamePointsP1 > gamePointsP2) {// if player 1 has higher points than player two
							// display that player two has lost on their points textfield
							points2.setText("You lost with " + gamePointsP2 + " points, " + MainMenu.Name2);
							// display that player two has won on their points textfield
							points1.setText("You won with " + gamePointsP1 + " points, " + MainMenu.Name1);
							tf.setText("GAME OVER");// the textfield to enter words displays the game is over
						} // close else if (gamePointsP1 >gamePointsP2)
						else if (gamePointsP1 == gamePointsP2) {// if the player 1 and 2's points are the same
							points2.setText("IT'S A TIE");// display it is a tie on player 2's points textfield
							points1.setText("IT'S A TIE");// display it is a tie on player 1's points textfield
							tf.setText("GAME OVER");// the textfield to enter words displays the game is over
						} // close else if (gamePointsP1 == gamePointsP2)
					} // close if (gamePointsP2 >= pointsToWin)
					else if (gamePointsP1 >= pointsToWin && playerNumsTurn == 1) {// if player 1 has more points than
																					// what's needed to win and the
																					// round is over (both parties had
																					// an equal chance to win)
						checkButton.setEnabled(false);// disable the checkButton
						if (gamePointsP1 < gamePointsP2) {// if player2 has higher points than player 1
							// display player two on player 2's textfield
							points2.setText("You won with " + gamePointsP2 + " points, " + MainMenu.Name2);

							// display play 1 lost on player 1's textfield
							points1.setText("You lost with " + gamePointsP1 + " points, " + MainMenu.Name1);

							// the textfield to enter words displays the game is over
							tf.setText("GAME OVER");
						} // close if (gamePointsP1 < gamePointsP2)

						// if player 1 has higher points than player two
						else if (gamePointsP1 > gamePointsP2) {
							// display that player two has lost on their points textfield
							points2.setText("You lost with " + gamePointsP2 + " points, " + MainMenu.Name2);
							// display that player two has won on their points textfield
							points1.setText("You won with " + gamePointsP1 + " points, " + MainMenu.Name1);

							// the textfield to enter words displays the game is over
							tf.setText("GAME OVER");

						} // close else if (gamePointsP1 >gamePointsP2)
						else if (gamePointsP1 == gamePointsP2) {// if the player 1 and 2's points are the same
							points2.setText("IT'S A TIE");// display it is a tie on player 2's points textfield
							points1.setText("IT'S A TIE");// display it is a tie on player 1's points textfield
							tf.setText("GAME OVER");// the textfield to enter words displays the game is over
						} // close else if (gamePointsP1 == gamePointsP2)
					} // close else if (gamePointsP1 >= pointsToWin && playerNumsTurn == 1)
				} // close if (word.length() < defaultLengthOfWord)
				else {// if word is of an adequate length
					recurring = checkPreviousword(word);// call this method to check whether the word has been entered
														// before
					if (recurring == true) {// if it was checked for before

						// display the word has already been entered before, so its invalid
						JOptionPane.showMessageDialog(null, "You've entered this word before. ",
								"You've entered this word before. Invalid entry", JOptionPane.WARNING_MESSAGE);
						if (playerNumsTurn == 1) {// if player 1 made this mistake

							tf.setText("Your turn " + MainMenu.Name2);// tell users it is now player 2's turn
							playerNumsTurn = 2;// set that it is player 2's turn
						} // close if (playerNumsTurn == 1)
						else if (playerNumsTurn == 2) {// if player 2 made this mistake
							// tf.setText("Your turn player 1");
							tf.setText("Your turn " + MainMenu.Name1);// tell users its player 1's turn now
							playerNumsTurn = 1;// set that it is player 1's turn
						} // close else if (playerNumsTurn == 2)
						if (gamePointsP2 >= pointsToWin) {// if player two has points higher than or equal to the points
															// needed to win
							checkButton.setEnabled(false);// disable the checkButton
							if (gamePointsP1 < gamePointsP2) {// if player2 has higher points than player 1
								points2.setText("You won with " + gamePointsP2 + " points, " + MainMenu.Name2);// display
																												// player
																												// two
																												// on
																												// player
																												// 2's
																												// textfield
								points1.setText("You lost with " + gamePointsP1 + " points, " + MainMenu.Name1);// display
																												// play
																												// 1
																												// lost
																												// on
																												// player
																												// 1's
																												// textfield
								tf.setText("GAME OVER");// the textfield to enter words displays the game is over
							} // close if (gamePointsP1 < gamePointsP2)
							else if (gamePointsP1 > gamePointsP2) {// if player 1 has higher points than player two
								points2.setText("You lost with " + gamePointsP2 + " points, " + MainMenu.Name2);// display
																												// that
																												// player
																												// two
																												// has
																												// lost
																												// on
																												// their
																												// points
																												// textfield
								points1.setText("You won with " + gamePointsP1 + " points, " + MainMenu.Name1);// display
																												// that
																												// player
																												// two
																												// has
																												// won
																												// on
																												// their
																												// points
																												// textfield
								tf.setText("GAME OVER");// the textfield to enter words displays the game is over
							} // close else if (gamePointsP1 >gamePointsP2)
							else if (gamePointsP1 == gamePointsP2) {// if the player 1 and 2's points are the same
								points2.setText("IT'S A TIE");// display it is a tie on player 2's points textfield
								points1.setText("IT'S A TIE");// display it is a tie on player 1's points textfield
								tf.setText("GAME OVER");// the textfield to enter words displays the game is over
							} // close else if (gamePointsP1 == gamePointsP2)
						} // close if (gamePointsP2 >= pointsToWin)
						else if (gamePointsP1 >= pointsToWin && playerNumsTurn == 1) {// if player 1 has more points
																						// than what's needed to win and
																						// the round is over (both
																						// parties had an equal chance
																						// to win)
							checkButton.setEnabled(false);// disable the checkButton
							if (gamePointsP1 < gamePointsP2) {// if player2 has higher points than player 1
								points2.setText("You won with " + gamePointsP2 + " points, " + MainMenu.Name2);// display
																												// player
																												// two
																												// on
																												// player
																												// 2's
																												// textfield
								points1.setText("You lost with " + gamePointsP1 + " points, " + MainMenu.Name1);// display
																												// play
																												// 1
																												// lost
																												// on
																												// player
																												// 1's
																												// textfield
								tf.setText("GAME OVER");// the textfield to enter words displays the game is over
							} // close if (gamePointsP1 < gamePointsP2)
							else if (gamePointsP1 > gamePointsP2) {// if player 1 has higher points than player two
								points2.setText("You lost with " + gamePointsP2 + " points, " + MainMenu.Name2);// display
																												// that
																												// player
																												// two
																												// has
																												// lost
																												// on
																												// their
																												// points
																												// textfield
								points1.setText("You won with " + gamePointsP1 + " points, " + MainMenu.Name1);// display
																												// that
																												// player
																												// two
																												// has
																												// won
																												// on
																												// their
																												// points
																												// textfield
								tf.setText("GAME OVER");// the textfield to enter words displays the game is over
							} // close else if (gamePointsP1 >gamePointsP2)
							else if (gamePointsP1 == gamePointsP2) {// if the player 1 and 2's points are the same
								points2.setText("IT'S A TIE");// display it is a tie on player 2's points textfield
								points1.setText("IT'S A TIE");// display it is a tie on player 1's points textfield
								tf.setText("GAME OVER");// the textfield to enter words displays the game is over
							} // close else if (gamePointsP1 == gamePointsP2)
						} // close else if (gamePointsP1 >= pointsToWin && playerNumsTurn == 1)
					} // close if (recurring == true)
					else {// else if the word has not been entered before, and the word is of an adequate
							// length

						ArrayList<Object> pastCor = new ArrayList<Object>();
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
											exist = (findPath(word, 1, i, j, pastCor, lettersGrid, visited));// Calling
																												// the
																												// method
																												// that
											// checks
											// if the word exist
											if (exist) {

												break;
											} else {
												//Removing every element in pastCor so that it will not contaminate the next round 
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
									int len = word.length();// length of the word entered by user is assigned to len
									if (playerNumsTurn == 1) {// if player 1 is the one who entered the word
										gamePointsP1 = gamePointsP1 + len;// players one's points is added with their
																			// newly earned ones
										System.out.println(gamePointsP1);// print it, to debug
										String displayPoints = Integer.toString(gamePointsP1);// convert the value to
																								// string to be able to
																								// display it
										points1.setText(MainMenu.Name1 + "'s points: " + displayPoints);// display the
																										// points on
																										// player 1's
																										// textfield
										tf.setText("Your turn " + MainMenu.Name2);// Display that it is player 2's turn
																					// on the textfield to enter the
																					// word

										playerNumsTurn = 2;// set that it is player 2's turn
									} // close if (playerNumsTurn == 1)
									else if (playerNumsTurn == 2) {// if the word was entered by player 2
										gamePointsP2 = gamePointsP2 + len;// add the length of the word entered to
																			// player 2's points
										System.out.println(gamePointsP2);// print it to debug
										String displayPoints = Integer.toString(gamePointsP2);// convert player 2's
																								// points to display on
										// points2.setText(displayPoints);
										// tf.setText("Your turn player 1");
										points2.setText(MainMenu.Name2 + "'s points: " + displayPoints);// display
																										// player two's
																										// points
										tf.setText("Your turn " + MainMenu.Name1);// let the users know its player 1's
																					// turn

										playerNumsTurn = 1;// set that it is player 1's turn
									} // close else if (playerNumsTurn == 2)

									for (Object i : pastCor) {
										grid[((int[]) i)[0]][((int[]) i)[1]].setBackground(green);
									}
									// Display that the word exists in wordlist
									JOptionPane.showMessageDialog(null, "Word Found In Wordlist", "Found In Wordlist",
											JOptionPane.INFORMATION_MESSAGE);

									if (gamePointsP2 >= pointsToWin) {// if player two has points higher than or equal
																		// to the points needed to win
										checkButton.setEnabled(false);// disable the checkButton
										if (gamePointsP1 < gamePointsP2) {// if player2 has higher points than player 1
											points2.setText(
													"You won with " + gamePointsP2 + " points, " + MainMenu.Name2);// display
																													// player
																													// two
																													// on
																													// player
																													// 2's
																													// textfield
											points1.setText(
													"You lost with " + gamePointsP1 + " points, " + MainMenu.Name1);// display
																													// play
																													// 1
																													// lost
																													// on
																													// player
																													// 1's
																													// textfield
											tf.setText("GAME OVER");// the textfield to enter words displays the game is
																	// over
										} // close if (gamePointsP1 < gamePointsP2)
										else if (gamePointsP1 > gamePointsP2) {// if player 1 has higher points than
																				// player two
											points2.setText(
													"You lost with " + gamePointsP2 + " points, " + MainMenu.Name2);// display
																													// that
																													// player
																													// two
																													// has
																													// lost
																													// on
																													// their
																													// points
																													// textfield
											points1.setText(
													"You won with " + gamePointsP1 + " points, " + MainMenu.Name1);// display
																													// that
																													// player
																													// two
																													// has
																													// won
																													// on
																													// their
																													// points
																													// textfield
											tf.setText("GAME OVER");// the textfield to enter words displays the game is
																	// over
										} // close else if (gamePointsP1 >gamePointsP2)
										else if (gamePointsP1 == gamePointsP2) {// if the player 1 and 2's points are
																				// the same
											points2.setText("IT'S A TIE");// display it is a tie on player 2's points
																			// textfield
											points1.setText("IT'S A TIE");// display it is a tie on player 1's points
																			// textfield
											tf.setText("GAME OVER");// the textfield to enter words displays the game is
																	// over
										} // close else if (gamePointsP1 == gamePointsP2)
									} // close if (gamePointsP2 >= pointsToWin)
									else if (gamePointsP1 >= pointsToWin && playerNumsTurn == 1) {// if player 1 has
																									// more points than
																									// what's needed to
																									// win and the round
																									// is over (both
																									// parties had an
																									// equal chance to
																									// win)
										checkButton.setEnabled(false);// disable the checkButton
										if (gamePointsP1 < gamePointsP2) {// if player2 has higher points than player 1
											points2.setText(
													"You won with " + gamePointsP2 + " points, " + MainMenu.Name2);// display
																													// player
																													// two
																													// on
																													// player
																													// 2's
																													// textfield
											points1.setText(
													"You lost with " + gamePointsP1 + " points, " + MainMenu.Name1);// display
																													// play
																													// 1
																													// lost
																													// on
																													// player
																													// 1's
																													// textfield
											tf.setText("GAME OVER");// the textfield to enter words displays the game is
																	// over
										} // close if (gamePointsP1 < gamePointsP2)
										else if (gamePointsP1 > gamePointsP2) {// if player 1 has higher points than
																				// player two
											points2.setText(
													"You lost with " + gamePointsP2 + " points, " + MainMenu.Name2);// display
																													// that
																													// player
																													// two
																													// has
																													// lost
																													// on
																													// their
																													// points
																													// textfield
											points1.setText(
													"You won with " + gamePointsP1 + " points, " + MainMenu.Name1);// display
																													// that
																													// player
																													// two
																													// has
																													// won
																													// on
																													// their
																													// points
																													// textfield
											tf.setText("GAME OVER");// the textfield to enter words displays the game is
																	// over
										} // close else if (gamePointsP1 >gamePointsP2)
										else if (gamePointsP1 == gamePointsP2) {// if the player 1 and 2's points are
																				// the same
											points2.setText("IT'S A TIE");// display it is a tie on player 2's points
																			// textfield
											points1.setText("IT'S A TIE");// display it is a tie on player 1's points
																			// textfield
											tf.setText("GAME OVER");// the textfield to enter words displays the game is
																	// over
										} // close else if (gamePointsP1 == gamePointsP2)
									} // close else if (gamePointsP1 >= pointsToWin && playerNumsTurn == 1)
								} // close if (wordlist.contains(word.toLowerCase()))
								else {// if the word found in the grid is not found on the wordlist

									for (Object i : pastCor) {
										grid[((int[]) i)[0]][((int[]) i)[1]].setBackground(red);
									}
									// Display that the word does not exist in wordlist
									JOptionPane.showMessageDialog(null, "Word Not Found In Wordlist", "Not Found",
											JOptionPane.INFORMATION_MESSAGE);
									if (playerNumsTurn == 1) {// if player 1 made this mistake

										tf.setText("Your turn " + MainMenu.Name2);// tell users it is now player 2's
																					// turn
										playerNumsTurn = 2;// set that it is player 2's turn
									} // close if (playerNumsTurn == 1)
									else if (playerNumsTurn == 2) {// if player 2 made this mistake
										// tf.setText("Your turn player 1");
										tf.setText("Your turn " + MainMenu.Name1);// tell users its player 1's turn now
										playerNumsTurn = 1;// set that it is player 1's turn
									} // close else if (playerNumsTurn == 2)
									if (gamePointsP2 >= pointsToWin) {// if player two has points higher than or equal
																		// to the points needed to win
										checkButton.setEnabled(false);// disable the checkButton
										if (gamePointsP1 < gamePointsP2) {// if player2 has higher points than player 1
											points2.setText(
													"You won with " + gamePointsP2 + " points, " + MainMenu.Name2);// display
																													// player
																													// two
																													// on
																													// player
																													// 2's
																													// textfield
											points1.setText(
													"You lost with " + gamePointsP1 + " points, " + MainMenu.Name1);// display
																													// play
																													// 1
																													// lost
																													// on
																													// player
																													// 1's
																													// textfield
											tf.setText("GAME OVER");// the textfield to enter words displays the game is
																	// over
										} // close if (gamePointsP1 < gamePointsP2)
										else if (gamePointsP1 > gamePointsP2) {// if player 1 has higher points than
																				// player two
											points2.setText(
													"You lost with " + gamePointsP2 + " points, " + MainMenu.Name2);// display
																													// that
																													// player
																													// two
																													// has
																													// lost
																													// on
																													// their
																													// points
																													// textfield
											points1.setText(
													"You won with " + gamePointsP1 + " points, " + MainMenu.Name1);// display
																													// that
																													// player
																													// two
																													// has
																													// won
																													// on
																													// their
																													// points
																													// textfield
											tf.setText("GAME OVER");// the textfield to enter words displays the game is
																	// over
										} // close else if (gamePointsP1 >gamePointsP2)
										else if (gamePointsP1 == gamePointsP2) {// if the player 1 and 2's points are
																				// the same
											points2.setText("IT'S A TIE");// display it is a tie on player 2's points
																			// textfield
											points1.setText("IT'S A TIE");// display it is a tie on player 1's points
																			// textfield
											tf.setText("GAME OVER");// the textfield to enter words displays the game is
																	// over
										} // close else if (gamePointsP1 == gamePointsP2)
									} // close if (gamePointsP2 >= pointsToWin)
									else if (gamePointsP1 >= pointsToWin && playerNumsTurn == 1) {// if player 1 has
																									// more points than
																									// what's needed to
																									// win and the round
																									// is over (both
																									// parties had an
																									// equal chance to
																									// win)
										checkButton.setEnabled(false);// disable the checkButton
										if (gamePointsP1 < gamePointsP2) {// if player2 has higher points than player 1
											points2.setText(
													"You won with " + gamePointsP2 + " points, " + MainMenu.Name2);// display
																													// player
																													// two
																													// on
																													// player
																													// 2's
																													// textfield
											points1.setText(
													"You lost with " + gamePointsP1 + " points, " + MainMenu.Name1);// display
																													// play
																													// 1
																													// lost
																													// on
																													// player
																													// 1's
																													// textfield
											tf.setText("GAME OVER");// the textfield to enter words displays the game is
																	// over
										} // close if (gamePointsP1 < gamePointsP2)
										else if (gamePointsP1 > gamePointsP2) {// if player 1 has higher points than
																				// player two
											points2.setText(
													"You lost with " + gamePointsP2 + " points, " + MainMenu.Name2);// display
																													// that
																													// player
																													// two
																													// has
																													// lost
																													// on
																													// their
																													// points
																													// textfield
											points1.setText(
													"You won with " + gamePointsP1 + " points, " + MainMenu.Name1);// display
																													// that
																													// player
																													// two
																													// has
																													// won
																													// on
																													// their
																													// points
																													// textfield
											tf.setText("GAME OVER");// the textfield to enter words displays the game is
																	// over
										} // close else if (gamePointsP1 >gamePointsP2)
										else if (gamePointsP1 == gamePointsP2) {// if the player 1 and 2's points are
																				// the same
											points2.setText("IT'S A TIE");// display it is a tie on player 2's points
																			// textfield
											points1.setText("IT'S A TIE");// display it is a tie on player 1's points
																			// textfield
											tf.setText("GAME OVER");// the textfield to enter words displays the game is
																	// over
										} // close else if (gamePointsP1 == gamePointsP2)
									} // close else if (gamePointsP1 >= pointsToWin && playerNumsTurn == 1)

								}

							} else {
								// Display that the word does not exist on the grid
								JOptionPane.showMessageDialog(null, "Word Not Found On Grid", "Not Found",
										JOptionPane.INFORMATION_MESSAGE);
								if (playerNumsTurn == 1) {// if player 1 made this mistake

									tf.setText("Your turn " + MainMenu.Name2);// tell users it is now player 2's turn
									playerNumsTurn = 2;// set that it is player 2's turn
								} // close if (playerNumsTurn == 1)
								else if (playerNumsTurn == 2) {// if player 2 made this mistake
									// tf.setText("Your turn player 1");
									tf.setText("Your turn " + MainMenu.Name1);// tell users its player 1's turn now
									playerNumsTurn = 1;// set that it is player 1's turn
								} // close else if (playerNumsTurn == 2)
								if (gamePointsP2 >= pointsToWin) {// if player two has points higher than or equal to
																	// the points needed to win
									checkButton.setEnabled(false);// disable the checkButton
									if (gamePointsP1 < gamePointsP2) {// if player2 has higher points than player 1
										points2.setText("You won with " + gamePointsP2 + " points, " + MainMenu.Name2);// display
																														// player
																														// two
																														// on
																														// player
																														// 2's
																														// textfield
										points1.setText("You lost with " + gamePointsP1 + " points, " + MainMenu.Name1);// display
																														// play
																														// 1
																														// lost
																														// on
																														// player
																														// 1's
																														// textfield
										tf.setText("GAME OVER");// the textfield to enter words displays the game is
																// over
									} // close if (gamePointsP1 < gamePointsP2)
									else if (gamePointsP1 > gamePointsP2) {// if player 1 has higher points than player
																			// two
										points2.setText("You lost with " + gamePointsP2 + " points, " + MainMenu.Name2);// display
																														// that
																														// player
																														// two
																														// has
																														// lost
																														// on
																														// their
																														// points
																														// textfield
										points1.setText("You won with " + gamePointsP1 + " points, " + MainMenu.Name1);// display
																														// that
																														// player
																														// two
																														// has
																														// won
																														// on
																														// their
																														// points
																														// textfield
										tf.setText("GAME OVER");// the textfield to enter words displays the game is
																// over
									} // close else if (gamePointsP1 >gamePointsP2)
									else if (gamePointsP1 == gamePointsP2) {// if the player 1 and 2's points are the
																			// same
										points2.setText("IT'S A TIE");// display it is a tie on player 2's points
																		// textfield
										points1.setText("IT'S A TIE");// display it is a tie on player 1's points
																		// textfield
										tf.setText("GAME OVER");// the textfield to enter words displays the game is
																// over
									} // close else if (gamePointsP1 == gamePointsP2)
								} // close if (gamePointsP2 >= pointsToWin)
								else if (gamePointsP1 >= pointsToWin && playerNumsTurn == 1) {// if player 1 has more
																								// points than what's
																								// needed to win and the
																								// round is over (both
																								// parties had an equal
																								// chance to win)
									checkButton.setEnabled(false);// disable the checkButton
									if (gamePointsP1 < gamePointsP2) {// if player2 has higher points than player 1
										points2.setText("You won with " + gamePointsP2 + " points, " + MainMenu.Name2);// display
																														// player
																														// two
																														// on
																														// player
																														// 2's
																														// textfield
										points1.setText("You lost with " + gamePointsP1 + " points, " + MainMenu.Name1);// display
																														// play
																														// 1
																														// lost
																														// on
																														// player
																														// 1's
																														// textfield
										tf.setText("GAME OVER");// the textfield to enter words displays the game is
																// over
									} // close if (gamePointsP1 < gamePointsP2)
									else if (gamePointsP1 > gamePointsP2) {// if player 1 has higher points than player
																			// two
										points2.setText("You lost with " + gamePointsP2 + " points, " + MainMenu.Name2);// display
																														// that
																														// player
																														// two
																														// has
																														// lost
																														// on
																														// their
																														// points
																														// textfield
										points1.setText("You won with " + gamePointsP1 + " points, " + MainMenu.Name1);// display
																														// that
																														// player
																														// two
																														// has
																														// won
																														// on
																														// their
																														// points
																														// textfield
										tf.setText("GAME OVER");// the textfield to enter words displays the game is
																// over
									} // close else if (gamePointsP1 >gamePointsP2)
									else if (gamePointsP1 == gamePointsP2) {// if the player 1 and 2's points are the
																			// same
										points2.setText("IT'S A TIE");// display it is a tie on player 2's points
																		// textfield
										points1.setText("IT'S A TIE");// display it is a tie on player 1's points
																		// textfield
										tf.setText("GAME OVER");// the textfield to enter words displays the game is
																// over
									} // close else if (gamePointsP1 == gamePointsP2)
								} // close else if (gamePointsP1 >= pointsToWin && playerNumsTurn == 1)

							}
						}
						//Removing every element in pastCor so that it will not contaminate the next round 
						pastCor.clear();
					} // close else if the word has not been entered before
				} // close else if the word is of an adequate length
			} // close if ((gamePointsP1 < pointsToWin && playerNumsTurn == 1) || gamePointsP2
				// < pointsToWin)
			else {// if a user has or users have won
				tf.setText("GAME OVER.");// display that the game's over
			} // close else if a user has or users have won
			
			if(rounds==2) {
				
				shakeButton.setPreferredSize(new Dimension(100, 30));
				remove(toolPanel);
				toolPanel.add(shakeButton);
				add(toolPanel,gbc);
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
		} else if (event.getSource() == restartButton) {

			int reset = JOptionPane.showConfirmDialog(null, "Do you want to reset this JFrame?", "Warning",
					JOptionPane.CANCEL_OPTION);

			if (reset == 0) {
				dispose();
				try {
					gamePointsP1 = 0;
					gamePointsP2 = 0;
					new DoublePlayerMode();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		else if (command.equals("Shake")) {

			shakeGrid(lettersGrid);
		}
	}

	// Modifies the value that determains the turns for each player
	public void nextTurn() {
		if (playerNumsTurn == 1) {
			tf.setText("Your turn " + MainMenu.Name2);
			playerNumsTurn = 2;
		} else {
			tf.setText("Your turn " + MainMenu.Name1);
			playerNumsTurn = 1;
		}

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

	
	
	//Generate a new set of random letters and sets the new letters to the 2d-array of JLabel
	public void shakeGrid(  char[][] lettersGrid ) {
		String letter;
		//Generates a new set of random letters 
		generateRandomLetter(lettersGrid);
		
		//Sets the new letters to the 2d-array of JLabel
		for (int row = 0; row < ROW; row++) {
			for (int col = 0; col < COL; col++) {
				letter = Character.toString(lettersGrid[row][col]);
				grid[row][col].setText(letter);

			}
		}
	}
	
	
	// Path finding method
	/**
	 * 
	 * @param word -String The word that we want to see if exists on the grid
	 * @param inx -int The current index of the letter in the word that we want to check
	 * @param x -int The index of the current row
	 * @param y -int The index of the current column
	 * @param pastCor -ArrayList This stores the path that the algorithm took
	 * @param lettersGrid -2D-Array of char The letters on the grid
	 * @param visited -2D-Array of boolean, the index of each element in the
	 *                    array represents
	 * @return if the each letter in the word is found, then the method would return
	 *         true
	 */
	public static boolean findPath(String word, int inx, int x, int y, ArrayList<Object> pastCor, char[][] lettersGrid,
			boolean[][] visited) {

		boolean exists = false;
		int[] cor = new int[2];

		//Terminating Conditions : When the algorithm checks the last letter in the word
		if (inx == (word.length() - 1)) {
			
			
			//There are 8 try/catch to test the 8 adjacent letters in the grid - they are identical

			try {
				cor[0] = x - 1;
				cor[1] = y;
				//Checks if the adjacent letters in the grid is the last letter in the word and if the letter is visited before
				if ((word.charAt(word.length() - 1) == lettersGrid[x - 1][y]) && !visited[x - 1][y]) {
					visited[x - 1][y] = true;
					//The coordinates of the last letters is added to pastCor
					pastCor.add(cor);
					
					//This terminates the recursion -> the top of the stack returns true
					return true;
				}
				
				//Catches when the coordiantes of the adjacent letters is not in the grid
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
			
			//There are 8 try/catch to test the 8 adjacent letters in the grid - they are identical

			// Check top
			try {
				//A new copy(shallow copy) of the pastCor
				ArrayList<Object> tempPastCor = new ArrayList<Object>(pastCor);
				
				//Create a new 2d-array that matches the size of visited
				boolean[][] tempVis = new boolean[visited.length][visited[0].length];

				//Copying the value from visited to tempVis
				for (int i = 0; i < visited.length; i++) {
					for (int j = 0; j < visited[0].length; j++) {
						tempVis[i][j] = visited[i][j];
					}
				}
				cor[0] = x - 1;
				cor[1] = y;

				if ((word.charAt(inx) == lettersGrid[x - 1][y]) && !(visited[x - 1][y])) {
					tempVis[x - 1][y] = true;
					//the coordinates that we checked is added to tempPastCor
					tempPastCor.add(cor);
					
					//tempPastCor and tempVis are passed because findPath is pass by referance that can change the value of PastCor and visited
					exists = findPath(word, inx + 1, x - 1, y, tempPastCor, lettersGrid, tempVis);
					
					//If the algorithm successfully finds the rest of the letters in the grid
					if (exists) {
						//Removing all elements in pastCor
						pastCor.clear();

						//Adding the elements in tempPastCor(after storing the coordinates with findPath ) to pastCor
						//Since this is pass by reference, the value in the original pastCor is also changed
						for (Object i : tempPastCor) {
							pastCor.add(i);
						}
						
						//Return true to the recursive call/base call before
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
		//return false when the algorithm fails to find a adjacent letter that matches the letter in the word
		return false;
	}
	/**
	 * Word Generation Methods: generateRandomLetter Generates a random list of letters
	 * @param lettersGrid The 2d-array of char that is going to be filled with words
	 */
	public static void generateRandomLetter(char[][] lettersGrid) {
		int randomDice, randomLetter;
		
		//Used to determine which dices are left from the 25 dices
		ArrayList<Integer> dicesLeft = new ArrayList<Integer>();
		
		//Contains the orientations of each dice
		String[] dices = { "AAAFRS", "AAEEEE", "AAFIRS", "ADENNN", "AEEEEM", "AEEGMU", "AEGMNN", "AFIRSY", "BJKQXZ",
				"CCNSTW", "CEIILT", "CEILPT", "CEIPST", "DDLNOR", "DHHLOR", "DHHNOT", "DHLNOR", "EIIITT", "EMOTTT",
				"ENSSSU", "FIPRSY", "GORRVW", "HIPRRY", "NOOTUW", "OOOTTU"

		};
		
		// Creating a 1d array with the size of the total amount of letters
		char[] letters = new char[lettersGrid[0].length * lettersGrid.length];
		// the total amount of letters in the grid

		//Adding the index of each dice to dicesLeft
		for (int i = 0; i < letters.length; i++) {
			dicesLeft.add(i);
		}

		//This takes an iterative approach -> determine the letter on the dice, one dice at a time
		for (int i = 0; i < letters.length; i++) {
			//Gives a random index with-in dicesLeft
			randomDice = (int) ((Math.random() * (dicesLeft.size()  )));
			
			//The value at that index is removed
			dicesLeft.remove(randomDice);
			//randomLetter is the random index of a letter with-in a dice
			randomLetter = (int) ((Math.random() * (5)));
			//the letter is assigned to the randomLetter index of a dice at randomDice index
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
