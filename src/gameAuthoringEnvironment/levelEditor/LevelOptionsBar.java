package gameAuthoringEnvironment.levelEditor;

import gameAuthoringEnvironment.levelStatsEditor.LevelSizeSliders;

import javax.swing.JPanel;

public class LevelOptionsBar extends JPanel {
public LevelOptionsBar(){
	initialize();
}
private void initialize(){
	this.add(new LevelSizeSliders());
}
}
