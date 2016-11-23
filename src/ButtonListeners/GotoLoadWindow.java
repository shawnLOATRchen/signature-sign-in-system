package ButtonListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import UserInterfaces.LoadUI;

public class GotoLoadWindow implements ActionListener {
	
	public GotoLoadWindow() { }

	@Override
	public void actionPerformed(ActionEvent e) {
		new LoadUI();

	}

}
