package UserInterfaces;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.*;

public class SubUI {

	int FRAME_WIDTH = 960;
	int FRAME_HEIGHT = 720;
	int PANEL_X = 80;
	int PANEL_Y = 160;
	int PANEL_W = 800;
	int PANEL_H = 400; 

	JFrame subFrame;
	SignaturePanel signaturePanel;
	JButton cancelButton = new JButton();
	JButton confirmButton = new JButton();
	
	boolean cleanThePanel = false;

	public SubUI() {
		subFrame =  new JFrame("Signature Recognizer");
		subFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		subFrame.setResizable(false);
		Container mainPanel = subFrame.getContentPane();
		mainPanel.setLayout(null);
		mainPanel.setBackground(new Color(172/255f, 225/255f, 242/255f));

		// setting the signature panel
		signaturePanel = new SignaturePanel();
		signaturePanel.setBackground(new Color(1.0f, 1.0f, 1.0f));;
		signaturePanel.setBounds(PANEL_X, PANEL_Y, PANEL_W, PANEL_H);
		subFrame.add(signaturePanel);

		// setting cancel & confirm button
		addJButton(cancelButton, "Cancel", PANEL_X+PANEL_W-150-150-30, PANEL_Y+PANEL_H+30, 150, 60, 217f, 61f, 50f);
		addJButton(confirmButton, "Confirm", PANEL_X+PANEL_W-150, PANEL_Y+PANEL_H+30, 150, 60, 122f, 227f, 41f);
		
		subFrame.setVisible(true);
		subFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		centreFrame(subFrame);
	}
	
	protected void addLabel(String title, int x, int y, int w, int h){
		JLabel userIDLabel = new JLabel(title);
		userIDLabel.setBounds(x, y, w ,h);
		userIDLabel.setFont(new Font(null, Font.PLAIN, 30));
		subFrame.add(userIDLabel);
	}
	
	protected void addTextField(JTextField textField, String defaultString, int x, int y, int w, int h) {
		textField.setText(defaultString);
		textField.setBounds(x, y, w ,h);
		textField.setFont(new Font(null, Font.PLAIN, 25));
		textField.setForeground(Color.lightGray);
		subFrame.add(textField);
	}
	
	protected void addJButton(JButton jButton,String title, int x, int y, int w, int h, float r, float g, float b) {
		jButton.setText(title);
		jButton.setBounds(x, y, w, h);
		jButton.setFocusPainted(false);
		jButton.setBackground(new Color(r/255f, g/255f, b/255f));
		jButton.setFont(new Font("comic sans", Font.BOLD, 25));
		jButton.setForeground(new Color(1.0f, 1.0f, 1.0f));
		subFrame.add(jButton);
	}
	
	protected String getFilePath(String userID) {
		Path currentRelativePath = Paths.get("");
		String currentDirectory = currentRelativePath.toAbsolutePath().toString();
		return currentDirectory + "\\userInfo\\" + userID + ".txt";
	}
	
	protected void centreFrame(JFrame frame) {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
	    frame.setLocation(x, y);
	}
}
