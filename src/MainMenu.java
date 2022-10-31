/**
 * @author: Ryan Liu, Vismaya Theertha, Daniel Wei
 * @date: 2021-05-11
 * @version: 2.0.0
 * @description: MainMenu of Boggle
 */


import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;



public class MainMenu extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JMenuBar menuBar;
	JMenu jMenu;
	JMenuItem menuItem1, menuItem2,menuItem3;
	JPanel panel1, panel2, panel3, panel4,panel5,panel7,panel8;//panels used to display options for player modes, difficulty level, names, points needed to win, the start button, the reset button
	JLabel label1, label2, label3,JLName1,JLName2,JLSeacrh;//labels used for user names
	JLabel label4,label5,label6,label7;//labels used for difficulty level, winningpoints
	JButton button1, button2;
	JTextField tf,JTfName1,JTfName2,JTfwordlen;//textfields to enter winningpoints, name(s) of user(s)
	JPasswordField pw;
	ButtonGroup bg;
	public static String Difficultylevel;//a variable to store the choice made by user for difficulty level, to pass to other classes
	public static String Name1;//a variable to store the name of first user, to pass to other classes
	public static String Name2 ;//a variable to store the name of second user, (if there is one), to pass to other classes
	public static int winningpoints;//a variable to store the choice made by the user for the points needed to win, to pass to other classes
	JRadioButton jrb1, jrb2, jrb3, jrb4;
	String[] choices1 = { "Easy","Medium", "Hard"};//a string array to store the options to display in the combobox for difficulty level
    JComboBox<String> cb1 = new JComboBox<String>(choices1);//creating a combobox with the elements of choices1 as the options
    
    static String pen= "src/static/file_example_WAV_10MG.wav";//music filepath
	  //ArrayList for storing names

	public MainMenu() {
		menuBar = new JMenuBar();
	    jMenu = new JMenu("Options");

	    menuItem1 = new JMenuItem("Help");
	    menuItem2 = new JMenuItem("Exit");
	    menuItem1.addActionListener(this);
	    menuItem2.addActionListener(this);
	    jMenu.add(menuItem1);
	    jMenu.add(menuItem2);
	 
	    menuBar.add(jMenu);
	    
	   

	    button1 = new JButton("Start");
	    button2 = new JButton("Reset");

	    button1.addActionListener(this);
	    button2.addActionListener(this);

	    jrb1 = new JRadioButton("Single Player");
	    jrb2 = new JRadioButton("Single Player Vs AI");
	    jrb3 = new JRadioButton("Two Player");
	    jrb4 = new JRadioButton("Two Player Vs AI");
	    

	    bg = new ButtonGroup();
	    bg.add(jrb1);
	    bg.add(jrb2);
	    bg.add(jrb3);
	    bg.add(jrb4);
	    
	    jrb1.setSelected(true);
	    
		JLName1 = new JLabel("Please enter your name:");  //JLabel for name entering message
		JTfName1 = new JTextField(10);  //JTextField for name entering
		JLName2 = new JLabel("Please enter player 2's name:");  //JLabel for name entering message
		JTfName2 = new JTextField(10);  //JTextField for name entering
	//	JLSeacrh = new JLabel("Search word length between 3 & 10 Characters:");  //JLabel for name entering message
		label4=new JLabel("Winning Points:");//label for points needed to win
		tf = new JTextField(10);//textfield to enter points needed to win
		tf.setText("50");//the textfield fore entering winning points, default is 50
		label5=new JLabel("Difficulty Level:");//label for level of difficulty of computer vs 1 player or computer vs 2 player
/*		label6=new JLabel("Search word Length:");
		JTfwordlen=new JTextField(10);*/
		
		//	JTextField JTwordlen =new JTextField("50");
	
	    panel1 = new JPanel();
	    panel2 = new JPanel();
	    panel3 = new JPanel();
	    panel4 = new JPanel();
	    panel5 = new JPanel();//The panel that will contain names of users, and the combobox
	    panel7 =new  JPanel();//The panel will contain winningpoints
	    panel8 =new  JPanel();
	   // label3 = new JLabel("BOGGLE Mode : ");

	   
	    pw = new JPasswordField(10);
//
	//    panel3.add(label3);
	    panel3.add(jrb1);
	    panel3.add(jrb2);
	    panel3.add(jrb3);
	    panel3.add(jrb4);
	
	    panel3.setAlignmentX(Component.CENTER_ALIGNMENT);
	    panel3.setAlignmentX(Component.CENTER_ALIGNMENT);
	    panel3.setPreferredSize(new Dimension(400, 60));
	    panel3.setMaximumSize(new Dimension(400, 150));
	    panel3.setBorder(BorderFactory.createTitledBorder("BOGGLE Mode :"));
	    this.add(panel3);
	    panel5.add(JLName1);//For all modes, the label for the name entered by the first user, gets added
	    panel5.add(JTfName1);//For all modes, the textfield for the name entered by the first user, gets added
//	    panel5.setBorder(BorderFactory.createTitledBorder("Ist Player Name :"));
	    panel5.setPreferredSize(new Dimension(300, 150));//set the preferred size of panel5
	    panel5.setMaximumSize(new Dimension(300, 150));//set the maximum size of panel5, the size it can not exceed
	    
	  
	   
	    
	    //Adding Action Listener for JButtons
	    jrb1.addActionListener(this);
	    jrb2.addActionListener(this);
	    jrb3.addActionListener(this);
	    jrb4.addActionListener(this);

	    this.setJMenuBar(menuBar);
	 
	    panel7.add(label4);//add the label for winningpoints to panel7
	    panel7.add(tf);//add the textfield for winningpoints to panel7
	
	    panel7.setPreferredSize(new Dimension(100, 60));//
	    panel7.setMaximumSize(new Dimension(100, 60));//
	    
	    this.add(panel5);//add panel5 to screen
	    this.add(panel7);//add panel7 to screen
	    

	    panel4.add(button1);
	    panel4.add(button2);

	    this.add(panel4);
	    

	    this.setLayout(new GridLayout(4, 1));
	    // Layout of menu
	    


	    this.setTitle("BOGGLE MENU");
	    this.setSize(700, 400);
	    this.setLocationRelativeTo(null);
	    this.setVisible(true);
	    this.setResizable(false);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {//actionPerformed method
		
	
		
		if ((e.getActionCommand().equals("Two Player"))){//If the mode selected is 2 Player Mode
			 panel5.remove(label5);//Difficulty level choice not necessary for 2 player
			 panel5.remove(cb1);//Difficulty level choice not necessary for 2 player
			 this.setVisible(false);//prevents the frames from appearing
			 panel5.add(JLName2);//add the label to enter second name to panel5
			 panel5.add(JTfName2);//add the textfield to enter second name to panel5
			 this.add(panel5);//add the panel5 to screen
			 this.add(panel7);//add the panel7 to screen
			 this.add(panel4);//add start and reset button to screen
	    	 this.setVisible(true);//all panels that were added should appear on screen
		}//close if ((e.getActionCommand().equals("Two PLayer")))
		if ((e.getActionCommand().equals("Two Player Vs AI"))){//If the mode selected is 2 Players Vs the computer
			 panel5.add(JLName2);//add the label to enter second name to panel5
			 panel5.add(JTfName2);//add the textfield to enter second name to panel5
			 panel5.add(label5);//add difficulty level label to panel5
			 panel5.add(cb1);//add the combobox for difficulty level to panel5
			 this.add(panel5);//add panel5 to the screen
			 this.setVisible(true);//all panels that were added should appear on screen
			 this.add(panel7);//add panel7 to the screen
			 this.add(panel4);//add panel4 to the screen
	    	 this.setVisible(true);//all panels that were added should appear on screen
		}//close if ((e.getActionCommand().equals("Two PLayer Vs AI")))
		if ((e.getActionCommand().equals("Single Player Vs AI"))){//If mode selected by user is 1 player vs computer
			panel5.remove(JLName2);//2nd name label not necessary for this mode
			panel5.remove(JTfName2);//2nd name textfield not necessary for this mode
			this.setVisible(false);//prevents the frames from appearing
			 panel5.add(label5);//add the label for difficulty level to panel5
			 panel5.add(cb1);//add the combobox for difficulty level to panel5
			 this.add(panel5);//add panel5 to the screen
			this.add(panel7);//add panel7 to the screen
			this.add(panel4);//add panel4 to the screen
			this.setVisible(true);//all panels that were added should appear on screen
		}//close if ((e.getActionCommand().equals("Single PLayer Vs AI")))
		if ((e.getActionCommand().equals("Single Player"))){//If mode selected by user is 1 player mode
			panel5.remove(JLName2);//2nd name label not necessary for this mode
			panel5.remove(JTfName2);//2nd name textfield not necessary for this mode
			panel5.remove(label5);//Difficulty level choice not necessary for 2 player from panel5
			panel5.remove(cb1);//Difficulty level choice not necessary for 2 player from panel5
			this.setVisible(false);//prevents the frames from appearing
			this.add(panel7);//adds panel7 to the screen
			this.add(panel4);//adds panel4 to the screen
			this.setVisible(true);//all panels that were added should appear on screen
		}//if ((e.getActionCommand().equals("Single PLayer")))
		
	
	
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("Start")) {//If user clicks the start button
			
			if(jrb1.isSelected() ) {//If single player mode selected
				try {//open try block
					MainMenu.Run(pen);//play music
				}//close try block
				catch (InterruptedException e1) {//open catch block
					// TODO Auto-generated catch block
					e1.printStackTrace();//handle exception
				}//close catch block
				
				try {//open try block
					Name1 = JTfName1.getText();//pass value entered for user's name to Name1
					String val=tf.getText();//pass value entered for points needed to win to val
					winningpoints= Integer.parseInt(val);//convert val from string to integer
					new SinglePlayerMode();//run SinglePlayerMode()
					
				} //close try block
				catch (FileNotFoundException e1) {//open try block
					// TODO Auto-generated catch block
					e1.printStackTrace();//handle exception
				}//close catch block
				dispose();//end execution of program
				
			}// close if(jrb1.isSelected() ) 
			else if(jrb3.isSelected()) {//if double player mode selected by user
				try {//open try block
					MainMenu.Run(pen);//play music
				}//close try block
				catch (InterruptedException e1) {//open catch block
					// TODO Auto-generated catch block
					e1.printStackTrace();//handle exception
				}//close catch block
				
				try {//open try block
	
					Name1 = JTfName1.getText();//pass value entered for first user's name to Name1
					Name2 = JTfName2.getText();//pass value entered for second user's name to Name2
					String val=tf.getText();//pass value entered for points needed to win to val
					winningpoints= Integer.parseInt(val);//convert val from String to integer
					new DoublePlayerMode();//run DoublePlayerMode
				
				} //close try block
				catch (FileNotFoundException e2) {//open catch block
					// TODO Auto-generated catch block
					e2.printStackTrace();//handle exception
				}//close catch block
				dispose();//end execution of program
				
			}//close else if(jrb3.isSelected())
			else if(jrb2.isSelected() ) {//if computer vs single player mode is selected
				try {//open try block
					MainMenu.Run(pen);//play music
				}//close try block
				catch (InterruptedException e1) {//open catch block
					// TODO Auto-generated catch block
					e1.printStackTrace();//handle exception
				}//close catch block
				
				try {//open try block
					Name1 = JTfName1.getText();//pass value entered for user's name to Name1
					Difficultylevel=String.valueOf(cb1.getSelectedItem());//pass choice made by user for difficulty level, to Difficultylevel
					String val=tf.getText();//pass value entered by user for the points needed to win, to val
					winningpoints= Integer.parseInt(val);//convert val from String to int
					new ComputerVsSinglePlayerMode();//run ComputerVsSinglePlayerMode()

				} //close try block
				catch (FileNotFoundException e3) {//open catch block
					// TODO Auto-generated catch block
					e3.printStackTrace();//handle exception
				}//close catch block
				dispose();//end execution of program
				
			}//close else if(jrb2.isSelected() )  
			else if(jrb4.isSelected() ) {//If user selects computer vs 2 players mode
				try {//open try block
					MainMenu.Run(pen);//play music
				}//close try block
				catch (InterruptedException e1) {//open catch block
					// TODO Auto-generated catch block
					e1.printStackTrace();//handle exception
				}//close catch block
				
				try {//open try block
					 Name1 = JTfName1.getText();//pass value enter for first user's name to Name1
					 Name2 = JTfName2.getText();//pass value enter for second user's name to Name2
					 Difficultylevel=String.valueOf(cb1.getSelectedItem());//pass choice made by user for difficulty level, to Difficultylevel
					 String val=tf.getText();//pass value entered by user for points need to win, to val
					 winningpoints= Integer.parseInt(val);//convert val into an int value
					new ComputerVsMultiPlayersMode();//run ComputerVsMultiPlayersMode()

				} //close try block
				catch (FileNotFoundException e4) {//open catch block
					// TODO Auto-generated catch block
					e4.printStackTrace();//handle exception
				}//close catch block
				dispose();//end execution of the program
				
			}//close else if(jrb4.isSelected() )
			
			
	}//close if(e.getActionCommand().equals("Start"))
	
	
	}//close actionPerformed method
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MainMenu();
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
	}
	//method that plays the music
	public static void Run(String pen)throws InterruptedException {//method header

		try {//open try block
		     // Open an audio input stream.           
		      File soundFile = new File(pen); //set the wav file containing the audio as a file, soundFile
		      AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);//AudioInputStream returns raw audio data              
		      Clip clip = AudioSystem.getClip();//obtains the audio file to be played

		     clip.open(audioIn);//reads the audio file
		     clip.start();//start the clip
		  } //close try block
		catch (UnsupportedAudioFileException e) {//open catch block to handle unsupported audio file exception
		     e.printStackTrace();//handle exception
		  } //close catch block to handle unsupported audio file exception
		catch (IOException e) {//open catch block to handle input output exception
		     e.printStackTrace();//handle exception
		  }//close catch block to handle input output exception
		catch (LineUnavailableException e) {//open catch block to handle line unavailable exception
		     e.printStackTrace();//handle exception
		  }//close catch block to handle line unavailable exception
		 }//close run method
}//end of class



