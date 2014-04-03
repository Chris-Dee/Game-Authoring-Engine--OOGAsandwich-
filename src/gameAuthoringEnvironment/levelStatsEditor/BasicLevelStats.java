package gameAuthoringEnvironment.levelStatsEditor;

import gameAuthoringEnvironment.frontEnd.LevelPanel;
import gameAuthoringEnvironment.frontEnd.PanelFactory;

import java.awt.Dimension;
import java.io.FileNotFoundException;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class BasicLevelStats extends JPanel {
	LevelPanel allLevels;
	public BasicLevelStats(LevelPanel level){
		super();
		allLevels=level;
		makeLevelEditor(this);


		//Level l;
		
	}
public void makeLevelEditor(JPanel mainPanel) {
	JPanel editPane=new JPanel();
	mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
	editPane.setLayout(new BoxLayout(editPane,BoxLayout.Y_AXIS));
	JLabel label=new JLabel("Need to get Level name from object");
	editPane.add(label);
	editPane.add(PanelFactory.makeVerticalSpacerPanel(30));
	try {
		editPane.add(new BackgroundChooser(allLevels));
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	mainPanel.add(editPane);
	//mainPanel.add(new LevelSizeSliders());
	mainPanel.add(PanelFactory.makeVerticalSpacerPanel(800));
}


}
