package gameAuthoringEnvironment.levelEditor;

import gameAuthoringEnvironment.frontEnd.LevelPanel;
import gameAuthoringEnvironment.levelStatsEditor.LevelSizeSliders;
import gameEngine.Level;

import javax.swing.JPanel;

public class LevelOptionsBar extends JPanel {
public LevelOptionsBar(Level level){
	initialize(level);
}
private void initialize(Level l){
	this.add(new LevelSizeSliders(l));
}
}
