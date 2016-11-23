package UserInterfaces;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Adaboost.Classification;
import Adaboost.Classification2;
import Adaboost.FeatureCalculator;
import Adaboost.User;
import ButtonListeners.CancelPressed;
import ButtonListeners.ConfirmRecognize;

public class RecognizerUI extends SubUI {

	JTextField userIDTextField;
	String userID;
	File userFile;
	
	

	public RecognizerUI() {
		super();

		// setting user ID label
		addLabel("User ID", 80, 60, 200, 40);
		
		// setting user ID number input text field
		userIDTextField = new JTextField();
		addTextField(userIDTextField, " please input you user ID number", 80+200+30, 60, 500, 40);
		userIDTextField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				userIDTextField.setText(null);
				userIDTextField.setForeground(Color.BLACK);
			}
		});

		cancelButton.addActionListener(new CancelPressed(signaturePanel));
		confirmButton.addActionListener(new ConfirmRecognize(signaturePanel, this));
	}
	
	
	public void recognize() {
		userID = userIDTextField.getText();
		String filePath = getFilePath(userID);
		userFile = new File(filePath);
		if (userFile.exists()){
			User[] trainers = getTrainersFromData();
			User trainer1 = trainers[0];
			User trainer2 = trainers[1];
			User tester = getTester();
			
			Classification cf = new Classification(trainer1, trainer2, tester);
			Classification2 cf2 = new Classification2(trainer1, trainer2, tester);
			double cf2score = cf2.getResult();
			double finalResult = 0;
			System.out.println(cf.isTrue);
			if (cf.isTrue && cf2score > 0.45){
				finalResult = 1 - (1-cf2score) * 0.6;
			}else if (cf.isTrue) {
				finalResult = cf2score;
			}else if(cf2score > 0.45){
				finalResult = 1 - (1-cf2score) * 0.75;
			}
			else {
				finalResult = 1 - (1-cf2score) * 1.15;
			}
			if (finalResult < 0) finalResult = -finalResult;
			System.out.println(finalResult);
			
			if (finalResult > 0.7){
				JOptionPane.showMessageDialog(null, "Weclome, " + trainer1.getName());
			}else{
				JOptionPane.showMessageDialog(null, "Sorry, we cannot let you log in");
			}
			
		}else{
			JOptionPane.showMessageDialog(null, "User is not exist");
		}
	}
	
	private User getTester() {
		User tester = new User();
		FeatureCalculator featureCalculator = new FeatureCalculator(signaturePanel.points);
		tester.setCardNumber(userID);
		tester.setName("default");
		tester.setNewFeature(featureCalculator.getFeature());
		tester.setWeight(new double[44]);
		return tester;
	}
	
	private User[] getTrainersFromData() {
		User train1 = new User();
		User train2 = new User();
		train1.setCardNumber(userID);
		train2.setCardNumber(userID);
		try {
			FileReader fileReader = new FileReader(userFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			double[] featureList1 = new double[44];
			double[] weightList1 = new double[44];
			double[] featureList2 = new double[44];
			double[] weightList2 = new double[44];
			
			int numOfLine = 0;
			while ((line = bufferedReader.readLine()) != null) {
//				System.out.println(line);
				if (numOfLine == 0){
					train1.setName(line);
					train2.setName(line);
					
				}else if (numOfLine>0 && numOfLine<=44) {
					featureList1[numOfLine-1] = Double.parseDouble(line);
				}else if (numOfLine==44+1){
					train1.setNewFeature(featureList1);
					weightList1[numOfLine-1-44] = Double.parseDouble(line);
					
				}else if (numOfLine>44+1 && numOfLine<=44*2) {
					weightList1[numOfLine-1-44] = Double.parseDouble(line);
				}else if (numOfLine==44*2+1) {
					train1.setWeight(weightList1);
					featureList2[numOfLine-1-44*2] = Double.parseDouble(line);
					
				}else if (numOfLine>44*2+1 && numOfLine<=44*3) {
					featureList2[numOfLine-1-44*2] = Double.parseDouble(line);
				}else if (numOfLine==44*3+1) {
					train2.setNewFeature(featureList2);
					weightList2[numOfLine-1-44*3] = Double.parseDouble(line);
					
				}else if (numOfLine>44*3 && numOfLine<=44*4){
					weightList2[numOfLine-1-44*3] = Double.parseDouble(line);
				}
				numOfLine += 1;
		    }
			train2.setWeight(weightList2);
			
			bufferedReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		User[] trainers = {train1, train2};
		return trainers;
	}
	
	private void printUser(User user) {
		System.out.println("---start print user----");
		System.out.println(user.getName());
		for (double feature : user.getFeature()) {
			System.out.println(feature);
		}
		for (double weight : user.getWeight()) {
			System.out.println(weight);
		}
		System.out.println("---end print user----");
	}
}
