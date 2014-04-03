package LevelStatsEditor;

import java.awt.Dimension;
import java.io.FileNotFoundException;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import FrontEnd.PanelFactory;

public class BasicLevelStats extends JPanel {
	public BasicLevelStats(/*Level object*/){
		super();
		makeLevelEditor(this);
		//Level l;
		
	}
public void makeLevelEditor(JPanel mainPanel) {
	JPanel editPane=new JPanel();
	editPane.setLayout(new BoxLayout(editPane,BoxLayout.Y_AXIS));
	JLabel label=new JLabel("Need to get Level name from object");
	editPane.add(label);
	editPane.add(PanelFactory.makeVerticalSpacerPanel(30));
	try {
		editPane.add(new BackgroundChooser());
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	mainPanel.add(editPane);
	
}


}
