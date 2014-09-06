package gameauthoringenvironment.levelstatseditor;

import gameauthoringenvironment.frontend.LevelPanel;
import gameauthoringenvironment.frontend.PanelFactory;

import java.awt.Dimension;
import java.awt.Font;
import java.io.FileNotFoundException;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class BasicLevelStats extends JPanel {
	private static final String PLEASE_SELECT_A_LEVEL_TEXT = "Please select a level";
	private static final int JPANEL_SIZE_OFFSET_LARGER_X = 500;
	private static final int JPANEL_SIZE_OFFSET_LARGER_Y = 800;
	private static final int JPANEL_SIZE_OFFSET = 30;
	private static final String ADD_AND_SELECT_A_LEVEL_LABEL = "Add and Select a Level!";
	LevelPanel myLevelPanel;
	JLabel levelName;
	JPanel editPane;
	Font headingFont = new Font("Arial", Font.BOLD, 18);
	BackgroundChooser chooser;

	/**
	 * Central component of the GAE. Holds information about the current level,
	 * ability to add more, etc.
	 */
	public BasicLevelStats() {
		super();
		makeLevelEditor(this);
	}

	/**
	 * Sets the initial background based on the levelPanel given.
	 * @param levelPanel
	 *            LevelPanel that is to be selected.
	 */

	public void setLevelPanel(LevelPanel levelPanel) {
		try {
			chooser=new BackgroundChooser(levelPanel);
			editPane.add(chooser);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void toggleBGEnabled(boolean b){
		chooser.changeEnabled(b);
	} 
	private void makeLevelEditor(JPanel mainPanel) {
		editPane = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		editPane.setLayout(new BoxLayout(editPane, BoxLayout.Y_AXIS));
		levelName = new JLabel(ADD_AND_SELECT_A_LEVEL_LABEL);
		levelName.setFont(headingFont);
		levelName.setHorizontalAlignment(SwingConstants.CENTER);
		editPane.add(levelName);
		editPane.add(PanelFactory.makeVerticalSpacerPanel(JPANEL_SIZE_OFFSET));

		mainPanel.add(editPane);
		// mainPanel.add(new LevelSizeSliders());
		mainPanel.add(PanelFactory.makeVerticalSpacerPanel(JPANEL_SIZE_OFFSET_LARGER_X, JPANEL_SIZE_OFFSET_LARGER_Y));
	}

	/**
	 * Set the name of the level
	 * 
	 * @param name
	 *            Name of level
	 */
	public void setLevelName(String name) {
		levelName.setText(name);

		if (name == null) {
			levelName.setText(PLEASE_SELECT_A_LEVEL_TEXT);
		}
	}

}
