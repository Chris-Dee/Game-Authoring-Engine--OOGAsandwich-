package gameAuthoringEnvironment.levelEditor;

import java.awt.Dimension;

import gameEngine.Level;

import javax.swing.JPanel;

/**
 * Panel that displays all parameters that can be modified about the current
 * level.
 * 
 * 
 */
public class LevelObjectBar extends JPanel {
	public LevelObjectBar(Level level, LevelEditor levelEdit) {
		initialize(level, levelEdit);
	}

	private void initialize(Level lev, LevelEditor editor) {
		this.add(new LevelSizeSliders(lev, editor));
	}
}