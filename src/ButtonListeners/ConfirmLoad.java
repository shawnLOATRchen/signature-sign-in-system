package ButtonListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import Adaboost.Points;
import UserInterfaces.LoadUI;
import UserInterfaces.SignaturePanel;

public class ConfirmLoad implements ActionListener {

	SignaturePanel signaturePanel;
	LoadUI loadUI;

	public ConfirmLoad(SignaturePanel signaturePanel, LoadUI loadUI) {
		this.signaturePanel = signaturePanel;
		this.loadUI = loadUI;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		signaturePanel.cleanPanel();
		
		if (loadUI.isUserExist() && loadUI.confirmTime != 1){
			JOptionPane.showMessageDialog(null, "User already exist, please try again!");
		} else {
			loadUI.confirmTime += 1;
			loadUI.saveToFile();
			if (loadUI.confirmTime >= 2){
				JOptionPane.showMessageDialog(null, "Thanks!");
				loadUI.disableButton();
			} else {
				JOptionPane.showMessageDialog(null, "plase sign you name one more time");
			}
		}
		signaturePanel.points = new Points();
	}
}
