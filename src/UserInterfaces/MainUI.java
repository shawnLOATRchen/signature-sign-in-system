package UserInterfaces;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import ButtonListeners.GotoLoadWindow;
import ButtonListeners.GotoRecognizerWindow;

public class MainUI {
	
	JFrame mainFrame;

	public MainUI() {

		mainFrame =  new JFrame("Signature Recognizer");
		mainFrame.setSize(480, 360);
		mainFrame.setResizable(false);
		
		Container mainPanel = mainFrame.getContentPane();
		mainPanel.setLayout(null);
		mainPanel.setBackground(new Color(172/255f, 225/255f, 242/255f));
		

		addJLabel("Signature Recognize", 0, 10, 480, 100, Font.BOLD, 20);
		addJLabel("Copyright@2016 ME-Hackers. All right reserved", 0, 100+150, 480, 100, Font.PLAIN, 14);
		
		addJButton("Load Data", 50, 120, 165, 100, new GotoLoadWindow());
		addJButton("Recognize", 50+165+50, 120, 165, 100, new GotoRecognizerWindow());

		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		centreFrame(mainFrame);
	}
	
	private void addJLabel(String title, int x, int y, int w, int h, int style, int size){
		JLabel jlabel = new JLabel(title, JLabel.CENTER);
		jlabel.setBounds(x, y, w, h);
		jlabel.setFont(new Font("comic sans", style, size));
		mainFrame.add(jlabel);
	}
	
	private void addJButton(String title, int x, int y, int w, int h, ActionListener actionListener) {
		JButton jButton = new JButton(title);
		jButton.setBounds(x, y, w, h);
		jButton.setFocusPainted(false);
		jButton.setBackground(new Color(46/255f, 138/255f, 219/255f));
		jButton.setFont(new Font("comic sans", Font.BOLD, 25));
		jButton.setForeground(new Color(1.0f, 1.0f, 1.0f));
		jButton.addActionListener(actionListener);
		mainFrame.add(jButton);
	}
	
	private void centreFrame(JFrame frame) {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
	    frame.setLocation(x, y);
	}

}
