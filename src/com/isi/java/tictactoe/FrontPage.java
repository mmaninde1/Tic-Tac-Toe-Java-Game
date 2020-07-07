package com.isi.java.tictactoe;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FrontPage extends JFrame
{
	private JLabel myJLabel;
	private JButton multiplayer;
	private JButton computer;
	
	private JPanel contentPane;
	
	public FrontPage()
	{
		super("my window");
		
		createComponent();
		createPanel();
		addComponentToPanels();
		addListnersToButton();
		
		setSize(400,400);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void createComponent()
	{
		myJLabel=new JLabel(" Select Game Mode : ");
		multiplayer=new JButton("Play with Friend");
		computer=new JButton("Play with A.I");
	}
	
	private void createPanel()
	{
		contentPane=(JPanel)getContentPane();
		contentPane.setLayout(new GridLayout(7,0));
		contentPane.setBorder(BorderFactory.createEmptyBorder(10, 12, 12,12));
	}
	
	private void addComponentToPanels()
	{
		contentPane.add( myJLabel);
		contentPane.add(multiplayer);
		contentPane.add(computer);
	}
	
	private void addListnersToButton()
	{
		multiplayer.addActionListener((ActionEvent e)->{ dispose(); new TicTacToeWindow();});
		computer.addActionListener((ActionEvent e)->{ dispose(); new PlayWithAI();});
	}
	
}
