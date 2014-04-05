package gameAuthoringEnvironment.frontEnd;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

/**
 * User options panel for tasks such as saving, loading, etc.
 * 
 * 
 */
// TODO implement this class

public class OptionsPanel extends JMenuBar {
	VAEview myViewer;
	public OptionsPanel(VAEview view) {
		myViewer = view;
		initialize();
	}
	
	public void initialize(){		
		JMenu file = new JMenu("File");
		
		add(file);
		
		myViewer.setJMenuBar(this);
	}
}
