package gameAuthoringEnvironment.levelEditor;

import java.awt.Dimension;

import gameEngine.Level;

import javax.swing.JPanel;

/**
 * 
 * 
 * 
 */
public class LevelObjectBar extends JPanel {
	
	/**
	 * Panel that displays all parameters that can be modified about the current
	 * level.
	 * 
	 * @param level
	 * 			Level being edited
	 * @param levelEditor
	 * 			LevelEditor that this object exists in
	 */
	public LevelObjectBar(Level level, LevelEditor levelEditor) {
		initialize(level, levelEditor);
	}

	private void initialize(Level lev, LevelEditor editor) {
		this.add(new LevelSizeSliders(lev, editor));
	}
}