/**
 * @author: Ryan Liu, Vismaya Theertha, Daniel Wei
 * @date: 2021-05-12
 * @version: 2.0.0
 * @description: ComputerVsMultiPlayersMode of Boggle
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

public class ComputerVsMultiPlayersMode extends JFrame implements ActionListener // Class definition
{

	static String Difficultylevel = MainMenu.Difficultylevel;// difficulty level for the game that the user chose in the main
														// menu
	static String pen = "src/staticfile_example_WAV_10MG.wav";// music filepath
	int playerNumsTurn = 1;// variable that specifies whose turn it is
	private static final long serialVersionUID = 1L;
	// Initialize all Swing components
	static int gamePointsP2, gamePointsP1;// points of user 1 and user 2
	static int gamePointsComp;// computer's poins
	// JPanel to contain and organize other components
	JPanel toolPanel = new JPanel();
	JPanel pointsPanel = new JPanel();
	JPanel ArraytoolPanel = new JPanel();
	JPanel inputPanel = new JPanel();

	// Size of the grid
	final static int ROW = 5,COL = 5;
	// the words that were searched on the grid previously.
	ArrayList<String> searchedWords = new ArrayList<String>();

	// The object that will contain the elements of searched words
	JList<Object> list;
	// DefaultListModel allows for mutable list
	DefaultListModel<Object> listModel;
	// JScrollPane adds the functionality to scroll
	JScrollPane listScroller;
	// This list is here to prevent the reentry of words
	int pointsToWin = MainMenu.winningpoints;// points needed to win, set by the user in the main menu

	JLabel grid[][] = new JLabel[ROW][COL];// 2d array of JLabel

	// Buttons
	JButton checkButton1 = new JButton("Check Button");// checkButton for player 1
	JButton checkButton2 = new JButton("Check Button");// checkButton for player 2
	JButton quitButton = new JButton("Quit");// The button to exit the program
	JButton infoButton = new JButton("How to play?");
	JButton restartButton = new JButton("Reset"); // JButton for restarting the game

	// Textfield provides a area for the user to enter words
	JTextField tf = new JTextField("Enter your word here, " + MainMenu.Name1);

	// Labels to display information about Boggle
	JLabel titleLabel = new JLabel("BOGGLE", JLabel.CENTER);
	JLabel points1 = new JLabel(MainMenu.Name1 + "'s points : 0");// JLabel to display points of first user
	JLabel points2 = new JLabel(MainMenu.Name2 + "'s points : 0");// JLabel to display points of second user
	JLabel pointsComp = new JLabel("AI's points : 0");// JLabel to display points of computer
	JLabel gameProgress = new JLabel("Game Status: in progress");// JLabel to display whether game is over

	// Creating fonts
	Font font = tf.getFont().deriveFont(Font.PLAIN, 24f);
	Font titleFont = tf.getFont().deriveFont(Font.PLAIN, 30f);

	Font font2 = points2.getFont().deriveFont(Font.PLAIN, 20f);// font for player 1 & player 2 points
	Font titleFont2 = points2.getFont().deriveFont(Font.PLAIN, 20f);// font for player 1 & player 2 points

	Font font3 = pointsComp.getFont().deriveFont(Font.PLAIN, 20f);// font for computer's points
	Font titleFont3 = pointsComp.getFont().deriveFont(Font.PLAIN, 20f);// font for computer's points

	// Background color for the buttons
	Color green = new Color(204, 255, 204);
	Color blue = new Color(102, 204, 255);
	Color red = new Color(255, 0, 0);

	// 2d array of characters that make up the grid
	static char[][] lettersGrid = new char[ROW][COL];

	// ArrayList
	ArrayList<String> wordlist = new ArrayList<String>(); // provides the ability to check if a word is in wordlist
	ArrayList<String> wordsFound = new ArrayList<String>(); // stores the words found in the grid

	// GridBagConstraints used to control the placement of components in the
	// GridBagLayout
	GridBagConstraints gbc = new GridBagConstraints();

	// Class constructor
	public ComputerVsMultiPlayersMode() throws FileNotFoundException {
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
		wordsFound = checkForEachWord();// the method returns all english words found on the grid
		// Create and match each button with a letter in the lettersGrid
		for (int row = 0; row < ROW; row++) {
			for (int col = 0; col < COL; col++) {
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
		GridLayout layout1 = new GridLayout(3, 1);

		// Setting the font of the welcome label
		titleLabel.setFont(font);

		// Setting the layout of the pointPanel
		pointsPanel.setLayout(layout1);

		// Setting the layout of the ArraytoolPanel
		ArraytoolPanel.setPreferredSize(new Dimension(500, 500));
		ArraytoolPanel.setLayout(Array_Button_Layout);
		ArraytoolPanel.setBackground(Color.LIGHT_GRAY);

		
		
		// Adding Action Listener to buttons
		checkButton1.addActionListener(this);// add ActionListener to checkButton1
		checkButton2.addActionListener(this);// add ActionListener to checkButton2
		quitButton.addActionListener(this);// add ActionListener to quitButton
		infoButton.addActionListener(this);// add ActionListener to infoButton
		restartButton.addActionListener(this);

		// Setting the properties of the check buttons

		checkButton1.setPreferredSize(new Dimension(200, 30));// set size of player 1's check button
		checkButton1.setFont(font2);// set font of player 1's check button

		checkButton2.setPreferredSize(new Dimension(200, 30));// set size of player 2's check button
		checkButton2.setFont(font2);// set font of player 2's check button

		// Setting the properties of the textfield
		tf.setFont(font);
		tf.setPreferredSize(new Dimension(400, 30));

		// Adding boarders around the input panel
		inputPanel.setBorder(BorderFactory.createEtchedBorder());

		restartButton.setPreferredSize(new Dimension(130, 30));
		infoButton.setPreferredSize(new Dimension(130, 30));
		quitButton.setPreferredSize(new Dimension(100, 30));

		points1.setFont(font2);// set font for the textfield displaying user 1's points
		points1.setPreferredSize(new Dimension(300, 30));// set size for the textfield displaying user 1's points

		// set font for the textfield display user 2's points
		points2.setFont(font2);
		// set size for the textfield displaying user 2's points
		points2.setPreferredSize(new Dimension(300, 30));

		// set font for the textfield displaying computer's points
		pointsComp.setFont(font3);
		// set size for the textfield displaying computer's points
		pointsComp.setPreferredSize(new Dimension(300, 30));

		// set font for the textfield displaying game status
		gameProgress.setFont(font3);
		// set size for the textfield displaying game status
		gameProgress.setPreferredSize(new Dimension(300, 30));

		titleLabel.setFont(titleFont);

		pointsPanel.add(points1);
		pointsPanel.add(points2);
		pointsPanel.add(pointsComp);

		// Adding the quitButton to the button panel
		toolPanel.add(restartButton);
		toolPanel.add(infoButton);
		toolPanel.add(quitButton);

		restartButton.setSize(40, 40);

		// Adding the textfield and check button to the input panel
		inputPanel.add(tf);
		inputPanel.add(checkButton1);// add button to JPanel
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

		setTitle("Boggle : Computer Vs Multi Player Mode");// Title of the window
		setSize(1200, 700);// Size of the Window

		// Setting the window to be visible when executed
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// Interaction section

	public void actionPerformed(ActionEvent event) {// start of actionPerformed method
		boolean recurring;// a boolean variable to confirm whether a word has been entered before during
							// the game
		boolean exist = false;// boolean variable to confirm whether the word being searched for is found on
								// the grid
		String command = event.getActionCommand();// String value of the command

		// Changes the background color of every button in the grid to blue
		for (int row = 0; row < ROW; row++) {
			for (int col = 0; col < COL; col++) {
				grid[row][col].setBackground(blue);
			}
		}

		if (event.getSource() == checkButton1) {// If the first user clicks the checkButton
			if (gamePointsP1 < pointsToWin) {// first user's points are less than what necessary to win
				String word = tf.getText().toUpperCase();// the word enter by the user to search being converted to all
															// caps, to search on the grid
				int defaultLengthOfWord = 3;// the length of the word that the user is allowed to input
				if (word.length() < defaultLengthOfWord) {// If the word is too short

					JOptionPane.showMessageDialog(null, "This word is too short.", "This word is too short.",
							JOptionPane.WARNING_MESSAGE);// display it's too short
				} // close if (word.length() < defaultLengthOfWord)
				else {// If the word has an acceptable length
					recurring = checkPreviousword(word);// the boolean variable will be set to the value being return by
														// the method that checks whether
					// the word that was entered now, has been entered before
					if (recurring == true) {// If the word has been entered before
						JOptionPane.showMessageDialog(null, "You've entered this word before.",
								"You've entered this word before.", JOptionPane.WARNING_MESSAGE);// display the word has
																									// been entered
																									// before
					} // close if (recurring == true)
					else {// If the word has not been entered before, and has an acceptable length

						ArrayList<Object> pastCor = new ArrayList<Object>();// an array list that will hold the
																			// coordinate of the first character of the
																			// word entered, on the grid
						// Main loop to check if the word exist on grid

						if (word.length() == 0) {// Checking if there is a input in textfield
							JOptionPane.showMessageDialog(null, "Please Enter A Word", "Warning",
									JOptionPane.WARNING_MESSAGE);// if no word was entered
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

									int len = word.length();// len is the amount character in the word
									gamePointsP1 = gamePointsP1 + len;// increase player 1's points by len
									System.out.println(gamePointsP1);// display points to debug
									String displayPoints = Integer.toString(gamePointsP1);// convert the points to
																							// String
									// points1.setText(displayPoints);
									points1.setText(MainMenu.Name1 + "'s points: " + displayPoints);// display the
																									// points in String
																									// form

									for (Object i : pastCor) {
										grid[((int[]) i)[0]][((int[]) i)[1]].setBackground(green);
									}
									// Display that the word exists in wordlist
									JOptionPane.showMessageDialog(null, "Word Found In Wordlist", "Found In Wordlist",
											JOptionPane.INFORMATION_MESSAGE);

								} else {

									for (Object i : pastCor) {
										grid[((int[]) i)[0]][((int[]) i)[1]].setBackground(red);
									}
									// Display that the word does not exist in wordlist
									JOptionPane.showMessageDialog(null, "Word Not Found In Wordlist", "Not Found",
											JOptionPane.INFORMATION_MESSAGE);

								}

							} else {
								// Display that the word does not exist on the grid
								JOptionPane.showMessageDialog(null, "Word Not Found On Grid", "Not Found",
										JOptionPane.INFORMATION_MESSAGE);

							}
						}
						pastCor.clear();
					}
				}
				playerNumsTurn = 2;// It is player 2's turn now
				if (playerNumsTurn == 2) {// if it is player 2's turn now
					tf.setText("Your turn, " + MainMenu.Name2);// display that it is player two's turn

					//Removes checkButton1 and adds checkButton2 to the inputPanel
					remove(inputPanel);
					inputPanel.remove(checkButton1);
					inputPanel.add(checkButton2);
					gbc.gridx = 1;
					gbc.gridy = 1;
					gbc.anchor = GridBagConstraints.NORTH;
					add(inputPanel, gbc);
					
					//Refresh the gui
					revalidate();
					repaint();

				} // close if (playerNumsTurn == 2)
			} // close if (gamePointsP1 < pointsToWin)
			else {// if player one's points is higher than or equal to the points needed to win
				tf.setText("GAME OVER.");// display the game is over
			} // close the else statement for when player 1's points are not less than points
				// needed to win
		} // close if (command.equals("Check Button for "+MainMenu.Name1))

		if (event.getSource() == checkButton2) {// if player two clicks their check button

			// if player 2 has less points during a new round or if the computers points are
			// less than whats needed to win
			if ((gamePointsP2 < pointsToWin && playerNumsTurn == 1) || gamePointsComp < pointsToWin) {
				String word2 = tf.getText().toUpperCase();// the word entered by the user is coverted to upper case to
															// be able to search it on the grid
				int defaultLengthOfWord = 3;// the length of the word that the user is allowed to input
				if (word2.length() < defaultLengthOfWord) {// If the word is too short
					JOptionPane.showMessageDialog(null, "This word is too short.", "This word is too short.",
							JOptionPane.WARNING_MESSAGE);// display it's too short
					tf.setText("Computer's turn");// display its the computer's turn
					playerNumsTurn = 3;// set that it is the computer's turn

				} else {// If the word has an acceptable length
					recurring = checkPreviousword(word2);// the boolean variable will be set to the value being return
															// by
					// the method that checks whether
					// the word that was entered now, has been entered before
					if (recurring == true) {// If the word has been entered before
						JOptionPane.showMessageDialog(null, "You've entered this word before.",
								"You've entered this word before. ", JOptionPane.WARNING_MESSAGE);// display it has been
																									// entered before
						tf.setText("Computer's turn");// display it is the computer's turn
						playerNumsTurn = 3;// set that it is the computer's turn

					} // close if (recurring == true)
					else {// If the word has not been entered before, and has an acceptable length

						// an array list that will hold the coordinate of the first character of the
						// word entered, on the grid Main loop to check if the word exist on grid
						ArrayList<Object> pastCor = new ArrayList<Object>();
						if (word2.length() == 0) {// Checking if there is a input in textfield
							JOptionPane.showMessageDialog(null, "Please Enter A Word", "Warning",
									JOptionPane.WARNING_MESSAGE);// display no word was entered
						} // close if (word2.length() == 0)

						else {// When there is a word in textfield

							if (word2.length() > 1) {// General case

								// Searches through every occurrence of the first letter of the word in the grid
								for (int i = 0; i < lettersGrid.length; i++) {
									for (int j = 0; j < lettersGrid[i].length; j++) {

										char c = lettersGrid[i][j];
										if (c == word2.charAt(0)) {// If the first letter of the word in the at the grid
											int[] cor = { i, j };
											boolean[][] visited = new boolean[5][5];

											visited[i][j] = true;
											pastCor.add(cor);
											exist = (findPath(word2, 1, i, j, pastCor, lettersGrid, visited));// Calling
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
										if (c == word2.charAt(0)) {

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
								if (wordlist.contains(word2.toLowerCase())) {// words in wordlist are lowercase
									// Change the background color of the letters on the grid

									int len = word2.length();// len is the number of characters in player 2's word
									gamePointsP2 = gamePointsP2 + len;// len is added to player 2's points
									System.out.println(gamePointsP2);// display their game points, to debug
									String displayPoints = Integer.toString(gamePointsP2);// convert player 2's points
																							// to string
									// points2.setText(displayPoints);
									points2.setText(MainMenu.Name2 + "'s points: " + displayPoints);// display the
																									// points as a
																									// string the
																									// points2 textfield

									for (Object i : pastCor) {
										grid[((int[]) i)[0]][((int[]) i)[1]].setBackground(green);
									}
									// Display that the word exists in wordlist
									JOptionPane.showMessageDialog(null, "Word Found In Wordlist", "Found In Wordlist",
											JOptionPane.INFORMATION_MESSAGE);

								} else {

									for (Object i : pastCor) {
										grid[((int[]) i)[0]][((int[]) i)[1]].setBackground(red);
									}
									// Display that the word does not exist in wordlist
									JOptionPane.showMessageDialog(null, "Word Not Found In Wordlist", "Not Found",
											JOptionPane.INFORMATION_MESSAGE);
									tf.setText("Computer's turn");// display that it is the computer's turn
									playerNumsTurn = 2;// set playerNumsTurn to 2
								}

							} else {
								// Display that the word does not exist on the grid
								JOptionPane.showMessageDialog(null, "Word Not Found On Grid", "Not Found",
										JOptionPane.INFORMATION_MESSAGE);
								tf.setText("Computer's turn");// display that it is the computer 2
								playerNumsTurn = 2;// set playerNumsTurn to 2

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

				// a variable that returns whether the word about to be entered by the computer,
				// has been entered before
				boolean reccuringForComp = false;
				// the method returns a boolean that determines whether or not this word has
				// been entered before or not
				reccuringForComp = checkPreviousword(compsWord);

				while (reccuringForComp == true) {// if it has been entered before in the game
					compsWord = wordsFound.get(0);// take the new first word on the list and assign it to compsWord
					wordsFound.remove(0);// remove that word from the list
					reccuringForComp = checkPreviousword(compsWord);// again check whether that word has been entered
																	// before
				} // close while (reccuringForComp == true)

				length2 = compsWord.length();// the length of the word is assigned to length2
				// display what word the computer enters and tell the user its their turn now
				tf.setText("The AI has entered the word " + compsWord + ". Your turn " + MainMenu.Name1);

				ArrayList<Object> path = new ArrayList<Object>();
				// This pass by reference method adds the coordinatres to which the word is
				// found
				returnPath(path, compsWord);

				// Setting the background of the letters that are in the word to green
				for (Object i : path) {
					grid[((int[]) i)[0]][((int[]) i)[1]].setBackground(green);
				}

				// gamePointsComp has added the length of the word entered now to itself
				gamePointsComp = gamePointsComp + length2;
				String displayPoints = Integer.toString(gamePointsComp);// convert the computer's points to string
				pointsComp.setText("AI's points : " + displayPoints);// display the computer's points as a string

				// If any one one of the 3 player gets points equal to or higher than what is
				// needed to win
				if (gamePointsP1 >= pointsToWin || gamePointsP2 >= pointsToWin || gamePointsComp >= pointsToWin) {
					checkButton1.setEnabled(false);// disable player 1's check button
					checkButton2.setEnabled(false);// disable player 2's check button
					if (gamePointsP1 == gamePointsP2 && gamePointsP2 == gamePointsComp) {// if all players have the same
																							// number of points

						pointsComp.setText("It's a threeway tie over " + gamePointsComp + " points");// display that
																										// it's a
																										// threeway tie

						points1.setText("It's a threeway tie over " + gamePointsComp + " points");// display that it's a
																									// threeway tie

						points2.setText("It's a threeway tie over " + gamePointsComp + " points");// display that it's a
																									// threeway tie
						gameProgress.setText("GAME OVER");// display that the game is over
					} // close if (gamePointsP1 == gamePointsP2 && gamePointsP2 == gamePointsComp)
					else if (gamePointsP2 == gamePointsComp && gamePointsP1 < gamePointsComp) {// if it's a tie win for
																								// player two and the
																								// computer, and player
																								// 1 lost

						pointsComp.setText("It's a tie between me and " + MainMenu.Name2);// state that computer won
																							// with player 2

						points1.setText("you lost with " + gamePointsP1 + " points, " + MainMenu.Name1);// tell player 1
																										// they lost

						points2.setText(
								"It's a tie between you and me over " + gamePointsComp + " points, " + MainMenu.Name2);// tell
																														// player
																														// 2
																														// that
																														// they
																														// won,
																														// along
																														// with
																														// the
																														// computer
						gameProgress.setText("GAME OVER");// display the game is over
					} // close else if (gamePointsP2 == gamePointsComp && gamePointsP1 <
						// gamePointsComp)
					else if (gamePointsP1 == gamePointsComp && gamePointsP2 < gamePointsComp) {// if it's a tie win for
																								// player 1 and the
																								// computer, and player
																								// 2 lost

						pointsComp.setText("It's a tie between me and " + MainMenu.Name1);// state that the computer won
																							// alongside player 1

						points2.setText("you lost with " + gamePointsP2 + " points, " + MainMenu.Name2);// tell player 2
																										// they lost

						points1.setText("It's a tie between you and me over " + gamePointsComp + " points");// tell
																											// player 1
																											// they won
																											// alongside
																											// the
																											// computer
						gameProgress.setText("GAME OVER");// display the game is over
					} // close else if (gamePointsP1 == gamePointsComp && gamePointsP2 <
						// gamePointsComp)
					else if (gamePointsP2 == gamePointsP1 && gamePointsP2 > gamePointsComp) {// if it's a tie win
																								// between player 1 and
																								// player 2, and the
																								// computer lost

						points2.setText("It's a tie between you and " + MainMenu.Name1 + " over " + gamePointsP2 + ", "
								+ MainMenu.Name2);// display that its a tie win between player 1 and 2

						pointsComp.setText("I lost with " + gamePointsComp + " points");// display the computer lost

						points1.setText("It's a tie between you and " + MainMenu.Name2 + " over " + gamePointsP2 + ", "
								+ MainMenu.Name1);// display that its a tie win between player 1 and 2
						gameProgress.setText("GAME OVER");// display the game is over
					} // close else if (gamePointsP2 == gamePointsP1 && gamePointsP2 > gamePointsComp)
					else if (gamePointsP1 > gamePointsP2 && gamePointsP1 > gamePointsComp) {// if player 1 won

						points2.setText("You lost with " + gamePointsP2 + " points, " + MainMenu.Name2);// display
																										// player 2 lost

						pointsComp.setText("I lost with " + gamePointsComp + " points");// display computer lost

						points1.setText("You won with " + gamePointsP1 + " points, " + MainMenu.Name1);// display player
																										// 1 won
						gameProgress.setText("GAME OVER");// display the game is over
					} // close else if (gamePointsP1 > gamePointsP2 && gamePointsP1 > gamePointsComp)
					else if (gamePointsP2 > gamePointsP1 && gamePointsP2 > gamePointsComp) {// if player 2 won

						points1.setText("You lost with " + gamePointsP1 + " points, " + MainMenu.Name1);// displaye
																										// player 1 lost

						pointsComp.setText("I lost with " + gamePointsComp + " points");// display computer lost

						points2.setText("You won with " + gamePointsP2 + " points, " + MainMenu.Name2);// display player
																										// 2 won
						gameProgress.setText("GAME OVER");// display the game is over
					} // close else if (gamePointsP2 > gamePointsP1 && gamePointsP2 > gamePointsComp)
					else if (gamePointsComp > gamePointsP1 && gamePointsP2 < gamePointsComp) {// if computer won

						points1.setText("You lost with " + gamePointsP1 + " points, " + MainMenu.Name1);// display
																										// player 1 lost

						points2.setText("You lost with " + gamePointsP2 + " points, " + MainMenu.Name2);// display
																										// player 2 lost

						pointsComp.setText("I won with " + gamePointsComp + " points");// display that the computer won
						gameProgress.setText("GAME OVER");// display the game is over
					} // close else if (gamePointsComp > gamePointsP1 && gamePointsP2 <
						// gamePointsComp)
					tf.setText("The AI has entered the word " + compsWord);// display what word the computer
																			// entered
				} // close if (gamePointsP1 >= pointsToWin || gamePointsP2 >= pointsToWin ||
					// gamePointsComp >= pointsToWin)

				playerNumsTurn = 1;// it is set as player 1's turn

				remove(inputPanel);
				inputPanel.remove(checkButton2);
				inputPanel.add(checkButton1);
				gbc.gridx = 1;
				gbc.gridy = 1;
				gbc.anchor = GridBagConstraints.NORTH;
				add(inputPanel, gbc);
				revalidate();
				repaint();

			} // if ((gamePointsP2 < pointsToWin && playerNumsTurn == 1) || gamePointsComp <
				// pointsToWin)
			else {// if a user/users don't have less than the amount of points needed to win
				gameProgress.setText("GAME OVER.");// display the game is over
			} // close else statement (if a user/users don't have less than the amount of
				// points needed to win)

		}

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
				dispose();
				try {// open try block
					gamePointsComp = 0;
					gamePointsP1 = 0;
					gamePointsP2 = 0;
					new ComputerVsMultiPlayersMode();
				} // close try block
				catch (FileNotFoundException e) {// open catch block to handle file not found exception

					e.printStackTrace();// handles exception
				} // close catch block
				searchedWords.clear();// clear the list containing all the words searched

				checkButton1.setEnabled(true);// enable player 1's check button
				checkButton2.setEnabled(false);// disable player 2's check button
				gamePointsP2 = 0;// player 2's points are set to 0
				gamePointsP1 = 0;// player 1's points are set to 0
				gamePointsComp = 0;// computer's points are set to 0

				tf.setText("Enter Here, " + MainMenu.Name1);// reset textfield, to display the text it displayed when
															// the game first began
				points1.setText(MainMenu.Name1 + "'s points");// reset textfield, to display the text it displayed when
																// the game first began
				points2.setText(MainMenu.Name2 + "'s points");// reset textfield, to display the text it displayed when
																// the game first began
				pointsComp.setText("My (Computer's) points");// reset textfield, to display the text it displayed when
																// the game first began
				gameProgress.setText("Game Status: in progress");// reset textfield, to display the text it displayed
																	// when the game first began
				JOptionPane.showMessageDialog(null, "JFrame reset", "Warning", JOptionPane.INFORMATION_MESSAGE);
			}
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
	// with 3-4 characters in ascending order,
	// for when the player selects easy mode, so that the computer is entering
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

	// This method sequentially searches for each word on the wordlist chosen and
	// returns a list of the words
	// on the chosen wordlist that were found on the grid.
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

	public void returnPath(ArrayList<Object> path, String word) {
		// This boolean stores if the word exists
		boolean exist = false;
		// Looping though every
		for (int i = 0; i < lettersGrid.length; i++) {
			for (int j = 0; j < lettersGrid[i].length; j++) {

				char c = lettersGrid[i][j];
				if (c == word.charAt(0)) {// If the first letter of the word in the at the grid
					int[] cor = { i, j };
					boolean[][] visited = new boolean[5][5];

					visited[i][j] = true;
					// Adding the coordinates of the letter so that the alorithem will not use this
					// letter twice
					path.add(cor);

					// Calling the method that finds the path of the word on the grid
					exist = (findPath(word, 1, i, j, path, lettersGrid, visited));
					// checks if the word exist
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
