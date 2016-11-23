package UserInterfaces;

import Adaboost.FeatureCalculator;
import ButtonListeners.CancelPressed;
import ButtonListeners.ConfirmLoad;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

import javax.swing.JTextField;


public class LoadUI extends SubUI {
	
	JTextField userIDTextField;
	JTextField userNameTextField;
	public int confirmTime = 0;
	
	String userID;
	String userName;
	File userFile;
	
	double[] featureList = new double[44];
	double[] weightList = new double[44];

	public LoadUI() {
		super();
		
		// setting user ID label
		addLabel("User ID", 80, 30, 200, 40);

		// setting user ID number input text field
		userIDTextField = new JTextField();
		addTextField(userIDTextField, " please input you user ID number", 80+200+30, 30, 500, 40);
		userIDTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				userIDTextField.setText(null);
				userIDTextField.setForeground(Color.BLACK);
			}
		});

		// setting user name label
		addLabel("User Name", 80, 30+40+30, 200, 40);

		// setting user name input text field
		userNameTextField = new JTextField();
		this.addTextField(userNameTextField, " please input you user name", 80+200+30, 30+40+30, 500, 40);
		userNameTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				userNameTextField.setText(null);
				userNameTextField.setForeground(Color.BLACK);
			}
		});

		cancelButton.addActionListener(new CancelPressed(signaturePanel));
		confirmButton.addActionListener(new ConfirmLoad(signaturePanel, this));
		
//		createTestList();
	}
	
	public boolean isUserExist() {
		try {
			userID = userIDTextField.getText();
			userName = userNameTextField.getText();
			
			String filePath = getFilePath(userID);
			userFile = new File(filePath);
			
			if (userFile.createNewFile() && !userID.equals(" please input you user ID number") ){
				return false;
			}else{
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false; 
	}

	public void saveToFile() {
		FeatureCalculator featureCalculator = new FeatureCalculator(signaturePanel.points);
		featureList = featureCalculator.getFeature();
		try {
			FileWriter fileWriter = new FileWriter(userFile, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			if (confirmTime == 1) writeToFileInNewLine(bufferedWriter, userName);
			for (double featrue : featureList) {
				writeToFileInNewLine(bufferedWriter, Double.toString(featrue));
			}
			for (double weight : weightList) {
				writeToFileInNewLine(bufferedWriter, Double.toString(weight));
			}
			bufferedWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void writeToFileInNewLine(BufferedWriter bufferedWriter, String content){
		try {
			bufferedWriter.write(content);
			bufferedWriter.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void disableButton() {
		confirmButton.setEnabled(false);
	}
	
	private void createTestList() {
		for (int i = 0; i < 44; i++) {
			featureList[i] = (double)i;
			weightList[i] = (double)i+0.5;
		}
	}

}
