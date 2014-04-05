package gameAuthoringEnvironment.levelStatsEditor;

import gameAuthoringEnvironment.frontEnd.LevelPanel;
import gameAuthoringEnvironment.frontEnd.PanelFactory;
import gameAuthoringEnvironment.levelEditor.LevelSizeSliders;

import java.awt.Dimension;
import java.awt.Font;
import java.io.FileNotFoundException;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
public class BasicLevelStats extends JPanel {
	LevelPanel allLevels;
	JLabel levelName;
	JPanel editPane;
	Font headingFont=new Font("Arial",Font.BOLD,18);
	public BasicLevelStats(){
		super();
		makeLevelEditor(this);
	}
	public void setLevelPanel(LevelPanel l){
		allLevels=l;
		try {
			editPane.add(new BackgroundChooser(allLevels));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
public void makeLevelEditor(JPanel mainPanel) {
	 editPane=new JPanel();
	mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
	editPane.setLayout(new BoxLayout(editPane,BoxLayout.Y_AXIS));
	levelName=new JLabel("Add and Select a Level!");
	levelName.setFont(headingFont);
	levelName.setHorizontalAlignment(SwingConstants.CENTER);
	editPane.add(levelName);
	editPane.add(PanelFactory.makeVerticalSpacerPanel(30));
	
	mainPanel.add(editPane);
	//mainPanel.add(new LevelSizeSliders());
	mainPanel.add(PanelFactory.makeVerticalSpacerPanel(800));
}
public void setLevelName(String name) {
	levelName.setText(name);
	
	if (name == null){
		levelName.setText("Please select a level");
	}
}


}
