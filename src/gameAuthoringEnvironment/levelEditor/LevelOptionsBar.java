package gameAuthoringEnvironment.levelEditor;

import gameEngine.Level;

import javax.swing.JPanel;

/**
 * Panel that displays all parameters that can be modified about the current
 * level.
 * 
 * 
 */
public class LevelOptionsBar extends JPanel {
	public LevelOptionsBar(Level level, LevelEditor levelEdit) {
		initialize(level, levelEdit);
	}

	private void initialize(Level l, LevelEditor editor) {
		this.add(new LevelSizeSliders(l, editor));
	}
}
