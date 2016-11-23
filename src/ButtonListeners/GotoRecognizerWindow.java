package ButtonListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import UserInterfaces.RecognizerUI;

public class GotoRecognizerWindow implements ActionListener {

	public GotoRecognizerWindow() { }
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		new RecognizerUI();

	}

}
