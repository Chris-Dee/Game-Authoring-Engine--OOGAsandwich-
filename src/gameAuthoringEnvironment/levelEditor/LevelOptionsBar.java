package gameAuthoringEnvironment.levelEditor;

import gameAuthoringEnvironment.frontEnd.LevelPanel;
import gameEngine.Level;

import javax.swing.JPanel;

public class LevelOptionsBar extends JPanel {
public LevelOptionsBar(Level level,LevelEditor levelEdit){
	initialize(level,levelEdit);
}
private void initialize(Level l,LevelEditor editor){
	this.add(new LevelSizeSliders(l,editor));
}
}
