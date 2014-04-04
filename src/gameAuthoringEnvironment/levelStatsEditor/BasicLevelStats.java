package gameAuthoringEnvironment.levelStatsEditor;

import gameAuthoringEnvironment.frontEnd.LevelPanel;
import gameAuthoringEnvironment.frontEnd.PanelFactory;
import gameAuthoringEnvironment.levelEditor.LevelSizeSliders;

import java.awt.Dimension;
import java.io.FileNotFoundException;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class BasicLevelStats extends JPanel {
	LevelPanel allLevels;
	JLabel levelName;
	public BasicLevelStats(){
		super();
		makeLevelEditor(this);
	}
	public void setLevelPanel(LevelPanel l){
		allLevels=l;
	}
public void makeLevelEditor(JPanel mainPanel) {
	JPanel editPane=new JPanel();
	mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
	editPane.setLayout(new BoxLayout(editPane,BoxLayout.Y_AXIS));
	levelName=new JLabel("");
	editPane.add(levelName);
	editPane.add(PanelFactory.makeVerticalSpacerPanel(30));
	try {
		editPane.add(new BackgroundChooser(allLevels));
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	mainPanel.add(editPane);
	//mainPanel.add(new LevelSizeSliders());
	mainPanel.add(PanelFactory.makeVerticalSpacerPanel(800));
}
public void setLevelName(String name) {
	// TODO Auto-generated method stub
	levelName.setText(name);
}


}
