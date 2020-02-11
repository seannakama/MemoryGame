//
//	Name:		Nakama, Sean

//	Description:	In this homework assignment, we are adding a menubar
//					with multiple menus and menuitems. To help with visibility,
//					we have added separators between items. All menus and 
//					menuitems have mnemonics that allow the user to use the 
//					keyboard to select them. The help and about menuitems open
//					up a dialog that shows information to the user. 

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;

import javax.swing.Timer;

public class MemoryGame implements ItemListener{
	private JPanel jpan;
	private JToggleButton[] jb = new JToggleButton[12];
	private JLabel jlab;
	private int check = 0;
	private JToggleButton first;
	private JToggleButton second;
	private int done = 0;
	private Timer swTimer;
	private long start; private long startT; private long stopT; private long diff;
	private JMenuItem pause;
	private JMenuItem resume;
	private JFrame frame;
	private ImageIcon icon;
	
	MemoryGame(){
		frame = new JFrame("Memory Game");
		frame.setLayout(new BorderLayout());
		frame.setSize(600,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("MemoryGame.png"));
		icon = new ImageIcon(image);
		frame.setIconImage(icon.getImage());
		
		Image pic1 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("1.png"));
		ImageIcon icon1 = new ImageIcon(pic1);
		Image pic2 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("2.png"));
		ImageIcon icon2 = new ImageIcon(pic2);
		Image pic3 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("3.png"));
		ImageIcon icon3 = new ImageIcon(pic3);
		Image pic4 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("4.png"));
		ImageIcon icon4 = new ImageIcon(pic4);
		Image pic5 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("5.png"));
		ImageIcon icon5 = new ImageIcon(pic5);
		Image pic6 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("6.png"));
		ImageIcon icon6 = new ImageIcon(pic6);
		ImageIcon[] squares = {icon1, icon1, icon2, icon2, icon3, icon3, icon4, icon4, icon5, icon5, icon6, icon6};
		Collections.shuffle(Arrays.asList(squares));
		
		ActionListener timerAL = new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				updateTime();
			}
		};
		
		swTimer = new Timer(1000, timerAL);
		
		jpan = new JPanel();
		jpan.setLayout(new GridLayout(3,4));
		for(int x = 0; x < 12; x++){
			jb[x] = new JToggleButton(icon);
			jb[x].setSelectedIcon(squares[x]);
			jpan.add(jb[x]);
			jb[x].addItemListener(this);
		}
		
		JMenuBar jmb = new JMenuBar();
		JMenu action = new JMenu("Action");
		action.setMnemonic('a');
		JMenu help = new JMenu("Help");
		help.setMnemonic('h');
		JMenu gameTimer = new JMenu("Game Timer");
		gameTimer.setMnemonic('t');
		
		pause = new JMenuItem("Pause", 'p');
		resume = new JMenuItem("Resume", 'r');
		pause.setEnabled(false);
		resume.setEnabled(false);
		JMenuItem reveal = new JMenuItem("Reveal", 'r');
		JMenuItem exit = new JMenuItem("Exit", 'x');
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		JMenuItem viewHelp = new JMenuItem("View Help", 'h');
		JMenuItem about = new JMenuItem("About", 'a');
		
		pause.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				stopT = 0;
				stopT = Calendar.getInstance().getTimeInMillis();
				swTimer.stop();
			}
		});
		
		resume.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				startT = 0;
				startT = Calendar.getInstance().getTimeInMillis();
				swTimer.start();
				diff = diff + startT - stopT;
			}
		});
		
		reveal.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				for(int y=0; y<12; y++){
					jb[y].setSelected(true);
				}
				stopT = 0;
				stopT = Calendar.getInstance().getTimeInMillis();
				swTimer.stop();;
			}
		});
		
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				System.exit(0);
			}
		});
		
		about.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				long tempStop = 0;
				long tempStart = 0;
				tempStop = Calendar.getInstance().getTimeInMillis();
				swTimer.stop();
				JOptionPane.showMessageDialog(frame, 
						"<html>Memory Game<br>Created by: Sean Nakama<br>Match tiles to win the game.<html/>",
						null, 0, icon);
				tempStart = Calendar.getInstance().getTimeInMillis();
				swTimer.start();
				diff = diff + tempStart - tempStop;
				
			}
		});
		
		
		viewHelp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				long tempStop = 0;
				long tempStart = 0;
				tempStop = Calendar.getInstance().getTimeInMillis();
				swTimer.stop();
				JOptionPane.showMessageDialog(frame, "Match all tiles to win the game.");
				tempStart = Calendar.getInstance().getTimeInMillis();
				swTimer.start();
				diff = diff + tempStart - tempStop;
			}
		});
		
		gameTimer.add(pause);
		gameTimer.add(resume);
		action.add(gameTimer);
		action.add(reveal);
		action.addSeparator();
		action.add(exit);
		help.add(viewHelp);
		help.addSeparator();
		help.add(about);
		
		jmb.add(action);
		jmb.add(help);
		frame.setJMenuBar(jmb);
		
		frame.add(jpan, BorderLayout.CENTER);
		jlab = new JLabel("00:00:00");
		jlab.setFont(new Font("Courier New", Font.PLAIN, 36));
		jlab.setHorizontalAlignment(JLabel.CENTER);
		frame.add(jlab, BorderLayout.NORTH);
		frame.setVisible(true);
	}
	
	void updateTime(){
		long temp = Calendar.getInstance().getTimeInMillis();
		jlab.setText((int)((temp-start)-diff)/1000/60/60 + ":" +
				(int)((temp-start)-diff)/1000/60%60 + ":" + (int)((temp - start)-diff)/1000%60);
	}
	
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				new MemoryGame();
			}
		});
	}
	@Override
	public void itemStateChanged(ItemEvent ie) {
		if(check == 1){
			second = (JToggleButton) ie.getItem();
			if(second.getSelectedIcon() == first.getSelectedIcon()){
				done++;
				check = 0;
			}
			else{
				first.setEnabled(true);
				check = 0;
			}
		}
		else{
			first = (JToggleButton) ie.getItem();
			check = 1;
			if(done == 0){
				start = Calendar.getInstance().getTimeInMillis();
				swTimer.start();
				pause.setEnabled(true);
				resume.setEnabled(true);
				done = 1;
			}
		}
	}
}
