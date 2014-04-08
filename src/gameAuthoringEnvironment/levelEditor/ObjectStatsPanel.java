package gameAuthoringEnvironment.levelEditor;

import gameEngine.Level;

import javax.swing.JPanel;

public class ObjectStatsPanel extends JPanel {
	LevelEditor myLevelEdit;
public ObjectStatsPanel(LevelEditor l){
	myLevelEdit=l;
	initialize();
}
public void initialize(){
	makeMainPanel();
}
public void makeMainPanel(){}
}
