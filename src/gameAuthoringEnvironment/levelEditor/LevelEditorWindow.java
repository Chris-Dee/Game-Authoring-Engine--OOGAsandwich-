package gameauthoringenvironment.leveleditor;

import gameauthoringenvironment.frontend.LevelPanelComponent;

import java.awt.BorderLayout;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class LevelEditorWindow extends JFrame {

	private static final String LEVEL_EDITOR_TITLE = "Level Editor";
	private LevelPanelComponent myLevelPanelComponent;
	private LevelEditor myLevelEditor;

	/**
	 * Window that holds the level editor
	 * 
	 * @param levelPanelComponent
	 *            Component in the GUI that holds the level information
	 */
	public LevelEditorWindow(LevelPanelComponent levelPanelComponent) {
		myLevelPanelComponent = levelPanelComponent;
		initialize();
	}

	private void initialize() {
		setVisible(true);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setTitle(LEVEL_EDITOR_TITLE);
		setLayout(new BorderLayout());
		setExitAction();
		makeMainPanel();
		myLevelPanelComponent.setAllObjectsActive();
	}

	private void setExitAction() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				myLevelEditor.clearGame();
				myLevelEditor.destroy();
				myLevelPanelComponent.setActive(true);
				e.getWindow().dispose();
				myLevelPanelComponent.changeEnableBG(true);
			}
		});
	}

	private void makeMainPanel() {
		JPanel mainPanel = (JPanel) getContentPane();

		try {
			myLevelEditor = new LevelEditor(myLevelPanelComponent.getLevel());
		} catch (Exception e) {
			e.printStackTrace();
		}
		mainPanel.add(myLevelEditor, BorderLayout.CENTER);
		mainPanel.add(new LevelStatsBar(myLevelEditor), BorderLayout.WEST);
		mainPanel.add(new ObjectEditorContainer(myLevelEditor),
				BorderLayout.EAST);

		pack();
	}
}