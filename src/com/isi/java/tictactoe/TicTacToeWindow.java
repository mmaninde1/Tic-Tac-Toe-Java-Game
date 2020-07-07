package com.isi.java.tictactoe;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TicTacToeWindow extends JFrame implements ActionListener
{
	public JButton allButton[] = new JButton[9]; // array of buttons
	private JLabel firstplayerLabel;
	private JLabel firstplayerMsg;
	private JLabel secondplayerLabel;
	private JLabel secondplayerMsg;
	
	private JLabel turnLabel;
	private JLabel turnMessageLabel;
	
	private JLabel messageLabel;
	
	private JButton exitButton;
	
	private JPanel turnPanel;
	private JPanel firstPlayerPanel;
	private JPanel secondPlayerPanel;
	private JPanel messagePanel;
	private JPanel mainGamePanel;
	private JPanel exitButtonPanel;
	private JPanel otherPanel;
	private JPanel contentPane;
	
	private int checkZeroCross=0;
	private int xWins = 0;
	private int oWins = 0;
	private String player1Name, player2Name;
	
	private final DecimalFormat df = new DecimalFormat("0"); 
	
	public TicTacToeWindow()
	{
		super("Tic Tac Toe Game: MultiPlayer");
		
		createComponents();
		addListeners();
		setupPanels();
		addComponentsToPanels();
		getAndSetName();
		setTurn();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600,490);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void createComponents()
	{
		for (int i = 0; i < allButton.length; i++) {
			allButton[i] = new JButton();
			allButton[i].putClientProperty("index", i);
			allButton[i].setText("");
			allButton[i].addActionListener(this);
		}
		
		turnLabel = new JLabel("");
		turnMessageLabel = new JLabel(" Turn");
		turnLabel.setAlignmentX(0.5f);
		turnMessageLabel.setAlignmentX(0.5f);
		turnLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		firstplayerMsg = new JLabel("");
		firstplayerLabel = new JLabel("0");
		firstplayerMsg.setAlignmentX(0.5f);
		firstplayerLabel.setAlignmentX(0.5f);
		firstplayerMsg.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		secondplayerMsg = new JLabel("");
		secondplayerLabel = new JLabel("0");
		secondplayerMsg.setAlignmentX(0.5f);
		secondplayerLabel.setAlignmentX(0.5f);
		secondplayerMsg.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		exitButton = new JButton("Exit Game");
		
		messageLabel = new JLabel(" ");
		messageLabel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(),  BorderFactory.createEmptyBorder(20,20,20,20)));
		messageLabel.setPreferredSize(new Dimension(230,60));
		messageLabel.setAlignmentX(0.5f);
	}
	
	private void addListeners()
	{
		exitButton.addActionListener((ActionEvent e) -> exitCheck());
	}
	
	private void setupPanels()
	{
		turnPanel = new JPanel();
		turnPanel.setLayout(new BoxLayout(turnPanel, BoxLayout.X_AXIS));
		
		firstPlayerPanel = new JPanel();
		firstPlayerPanel.setLayout(new BoxLayout(firstPlayerPanel, BoxLayout.X_AXIS));
		
		secondPlayerPanel = new JPanel();
		secondPlayerPanel.setLayout(new BoxLayout(secondPlayerPanel, BoxLayout.X_AXIS));
		
		messagePanel = new JPanel();
		
		mainGamePanel = new JPanel();
		mainGamePanel.setLayout(new GridLayout(3,3));
		
		exitButtonPanel = new JPanel();
		exitButtonPanel.setLayout(new BoxLayout(exitButtonPanel, BoxLayout.X_AXIS));
		
		otherPanel = new JPanel();
		otherPanel.setLayout(new BoxLayout(otherPanel, BoxLayout.Y_AXIS));
		
		contentPane = (JPanel)getContentPane();
		contentPane.setLayout(new GridLayout(0,2));
		contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	}
	
	private void addComponentsToPanels()
	{
		turnPanel.add(turnLabel);
		turnPanel.add(turnMessageLabel);
		
		firstPlayerPanel.add(firstplayerMsg);
		firstPlayerPanel.add(firstplayerLabel);
		
		secondPlayerPanel.add(secondplayerMsg);
		secondPlayerPanel.add(secondplayerLabel);
		
		messagePanel.add(messageLabel);
		
		for (int i = 0; i < allButton.length; i++) {
			mainGamePanel.add(allButton[i]);
		}
		
		exitButtonPanel.add(exitButton);
		
		otherPanel.add(turnPanel);
		otherPanel.add(firstPlayerPanel);
		otherPanel.add(secondPlayerPanel);
		otherPanel.add(messagePanel);
		otherPanel.add(exitButtonPanel);
		
		
		contentPane.add(mainGamePanel);
		contentPane.add(otherPanel);
		
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		int val = 0;
		for (int i = 0; i < allButton.length; i++) // get index of button which is clicked
		{
			if(e.getSource()==allButton[i])
				val = i;
		}
		
		if(checkZeroCross%2==0) // check turn of zero and cross and disabled that button
		{
			allButton[val].setText("X");
			allButton[val].setEnabled(false);
		}
		else
		{
			allButton[val].setText("O");
			allButton[val].setEnabled(false);
		}
		
		checkZeroCross++;
		setTurn();
		
		if(checkWinOrNot()) // check 
		{
			String text = allButton[val].getText();
			if(text == "X")
			{
				xWins++;
				text = player1Name + " Wins";	
			}	
			else if(text == "O")
			{
				oWins++;
				text = player2Name + " Wins";
			}
			
			messageLabel.setText(text);
			firstplayerLabel.setText(df.format(xWins));
			secondplayerLabel.setText(df.format(oWins));
			resetButtons();
		}
		
		if(checkAllFill())
		{
			String text = "Match Tie";
			messageLabel.setText(text);
			resetButtons();
		}
	}
	
	//check win or not
	private boolean checkWinOrNot()
	{
		// check horizontal
		if(checkNeighbour(0, 1) && checkNeighbour(1, 2))
			return true;
		else if(checkNeighbour(3, 4) && checkNeighbour(4, 5))
			return true;
		else if(checkNeighbour(6, 7) && checkNeighbour(7, 8))
			return true;
		// check vertical
		else if(checkNeighbour(0, 3) && checkNeighbour(3, 6))
			return true;
		else if(checkNeighbour(1, 4) && checkNeighbour(4, 7))
			return true;
		else if(checkNeighbour(2, 5) && checkNeighbour(5, 8))
			return true;
		//check diagonally
		else if(checkNeighbour(0, 4) && checkNeighbour(4, 8))
			return true;
		else if(checkNeighbour(2, 4) && checkNeighbour(4, 6))
			return true;
		else
			return false;
	}
	
	// check neighbour field
	private boolean checkNeighbour(int a, int b)
	{
		if(allButton[a].getText().equals(allButton[b].getText()) && !allButton[a].getText().equals(""))
			return true;
		else
			return false;
	}
	
	// reset all fields
	private void resetButtons()
	{
		checkZeroCross = 0;
		setTurn();
		for (int i = 0; i < allButton.length; i++) {
			allButton[i].setText("");
			allButton[i].setEnabled(true);
		}
	}
	
	// set turn of player
	private void setTurn()
	{
		if(checkZeroCross%2==0)
			turnLabel.setText(player1Name);
		else
			turnLabel.setText(player2Name);
	}
	
	// check filling of all fields
	private boolean checkAllFill()
	{
		boolean check = true;
		for (int i = 0; i < allButton.length; i++) {
			if(allButton[i].getText() == "")
			{
				check = false;
				break;
			}
		}
		return check;
	}
	
	// check exit button click display final winner
	private void exitCheck()
	{
		String text="";
		if(xWins > oWins)
			text = player1Name + " Win the Game!";
		else if(oWins > xWins)
			text = player2Name + " Win the Game!";
		else
			text = "Match Tie!";
		
		JOptionPane.showMessageDialog(null, text);
		
		System.exit(0);
	}
	
	// Get user name and set name 
	private void getAndSetName()
	{
		do {
			player1Name = JOptionPane.showInputDialog("Enter Name of Player 1");
			firstplayerMsg.setText(player1Name);
		} while (firstplayerMsg.getText().equals(""));
		
		do {
			player2Name = JOptionPane.showInputDialog("Enter Name of Player 2");
		    secondplayerMsg.setText(player2Name);
		} while (secondplayerMsg.getText().equals(""));
		
	}
}