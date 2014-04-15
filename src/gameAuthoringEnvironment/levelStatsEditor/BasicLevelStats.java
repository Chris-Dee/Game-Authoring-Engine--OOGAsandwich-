package gameAuthoringEnvironment.levelStatsEditor;

import gameAuthoringEnvironment.frontEnd.LevelPanel;
import gameAuthoringEnvironment.frontEnd.PanelFactory;


import java.awt.Dimension;
import java.awt.Font;
import java.io.FileNotFoundException;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class BasicLevelStats extends JPanel {
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

	// TODO: Honestly I have no idea when this method is called or what it is
	// doing. It's sometime in the beginning of the program
	/**
	 * 
	 * @param levelPanel
	 *            LevelPanel that is to be selected
	 */

	public void setLevelPanel(LevelPanel levelPanel) {
		myLevelPanel = levelPanel;
		try {
			chooser=new BackgroundChooser(myLevelPanel);
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
		levelName = new JLabel("Add and Select a Level!");
		levelName.setFont(headingFont);
		levelName.setHorizontalAlignment(SwingConstants.CENTER);
		editPane.add(levelName);
		editPane.add(PanelFactory.makeVerticalSpacerPanel(30));

		mainPanel.add(editPane);
		// mainPanel.add(new LevelSizeSliders());
		mainPanel.add(PanelFactory.makeVerticalSpacerPanel(800));
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
			levelName.setText("Please select a level");
		}
	}

}
