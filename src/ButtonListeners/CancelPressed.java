package ButtonListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Adaboost.Points;
import UserInterfaces.SignaturePanel;

public class CancelPressed implements ActionListener {

	SignaturePanel signaturePanel;
	
	public CancelPressed(SignaturePanel signaturePanel) {
		this.signaturePanel = signaturePanel;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		signaturePanel.cleanPanel();
		signaturePanel.points = new Points();
	}

}
