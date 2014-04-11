package gameAuthoringEnvironment.levelEditor;

import java.awt.BorderLayout;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ObjectEditorContainer extends JPanel {
	
	private LevelEditor myEditor;
	/**
	 * Holds the Object Toolbar, Object Stats Panel,
	 * and any other future swing components necessary to 
	 * edit an object in the level editor
	 */
	public ObjectEditorContainer(LevelEditor level) {
		myEditor = level;
		initialize();
	}

	private void initialize() {
		setLayout(new BorderLayout());
		add(new ObjectStatsPanel(this, myEditor), BorderLayout.EAST);
		//add(new ObjectToolbar(myEditor), BorderLayout.WEST);
	}
}
