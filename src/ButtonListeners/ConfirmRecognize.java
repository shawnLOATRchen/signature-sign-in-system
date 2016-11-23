package ButtonListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Adaboost.Points;
import UserInterfaces.RecognizerUI;
import UserInterfaces.SignaturePanel;

public class ConfirmRecognize implements ActionListener {
	
	SignaturePanel signaturePanel;
	RecognizerUI recognizeUI;
	
	public ConfirmRecognize(SignaturePanel signaturePanel, RecognizerUI recognizeUI) {
		this.signaturePanel = signaturePanel;
		this.recognizeUI = recognizeUI;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		signaturePanel.cleanPanel();
		recognizeUI.recognize();
		signaturePanel.points = new Points();
	}

}
