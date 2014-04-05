package gameAuthoringEnvironment.frontEnd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
		
		JMenuItem saveItem = new JMenuItem("Save");
		
		saveItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				myViewer.saveToTextFile();
			}
		});
		
		file.add(saveItem);
		add(file);
		setVisible(true);
		myViewer.setJMenuBar(this);
	}
}
