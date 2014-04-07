package gameAuthoringEnvironment.levelEditor;

import gameAuthoringEnvironment.frontEnd.LevelPanelComponent;
import gameAuthoringEnvironment.frontEnd.OptionsPanel;
import gameAuthoringEnvironment.frontEnd.PresetsBar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class LevelEditorWindow extends JFrame {
	private LevelPanelComponent myLevelPanelComponent;
	private LevelEditor levelEdit;

	/**
	 * Window that holds the level editor
	 * 
	 * @param levelPanelComponent
	 * 				Component in the GUI that holds the level information
	 */
	public LevelEditorWindow(LevelPanelComponent levelPanelComponent) {
		myLevelPanelComponent = levelPanelComponent;
		initialize();
	}

	private void initialize() {
		setVisible(true);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setTitle("Level Editor" /*levelEdit.getName()*/);
		setLayout(new BorderLayout());
		setExitAction();
		makeMainPanel();
		myLevelPanelComponent.setAllObjectsActive();
	}

	public void setExitAction() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				levelEdit.destroy();
				myLevelPanelComponent.setActive(true);
				e.getWindow().dispose();
			}
		});
	}

	private void makeMainPanel() {
		JPanel mainPanel = (JPanel) getContentPane();

		try {
			levelEdit = new LevelEditor(myLevelPanelComponent.getLevel());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mainPanel.add(levelEdit, BorderLayout.CENTER);
		mainPanel.add(new LevelObjectBar(myLevelPanelComponent.getLevel(), levelEdit),
				BorderLayout.WEST);
		// PresetsBar currently doesn't do anything, not sure of potential
		// future purpose

		// mainPanel.add(new PresetsBar(), BorderLayout.EAST);
		mainPanel.add(new ObjectToolbar(levelEdit), BorderLayout.EAST);

		pack();
	}
}
