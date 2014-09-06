package gameauthoringenvironment.frontend;

import gameauthoringenvironment.leveleditor.SliderObject;
import gameengine.Game;
import gameengine.eventactions.InvalidEventActionException;
import gameengine.eventactions.SetLivesAction;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GameChangesPanel extends JPanel {

	private static final int DEFAULT_NUMBER_OF_LIVES = 1;
	private static final int LEVEL_NAME_TEXTBOX_WIDTH = 1;
	private static final int CURRENT_LIVES_TEXTBOX_WIDTH = 3;
	private static final int VERTICAL_SPACER_PANEL_BOTTOM_HEIGHT = 900;
	private static final int VERTICAL_SPACER_PANEL_WIDTH = 50;
	private static final String CURRENT_LIVES_TEXT = "Current Lives: ";
	private static final String NEW_LEVEL_TEXT = "New Level (Must have unique name)";
	private static final String CHANGE_NUMBER_LIVES_BUTTON_TEXT = "Set Number Of Lives";
	private static final String LIVES_SLIDER_NAME = "Number of lives";
	
	private static final int[] LIVES_SLIDER_RANGE = {0,10};

	private LevelPanel myLevelPanel;
	private SliderObject userLivesChoiceSlider;
	private JTextField currentLives;

	/**
	 * Panel that holds all object creating functions, such as adding levels and
	 * objects to the game.
	 * 
	 * @param levelPanel
	 *            LevelPanel of the GAE
	 */
	public GameChangesPanel(LevelPanel levelPanel, Color backgroundColor) {
		myLevelPanel = levelPanel;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		makeMainFrame();
	}

	private void makeMainFrame() {
		makeLevelButton();
		createSeparationBetweenLevelAndLivesComponent();
		createNumberLivesOption();
		createBottomSpace();
	}

	/**
	 * Creates space between LevelButton and NumberLivesOption.
	 */

	private void createSeparationBetweenLevelAndLivesComponent() {
		add(PanelFactory.makeVerticalSpacerPanel(VERTICAL_SPACER_PANEL_WIDTH));
	}

	/**
	 * Creates space at bottom of component to allow other components to fit
	 * well in panel.
	 */

	private void createBottomSpace() {
		add(PanelFactory.makeVerticalSpacerPanel(VERTICAL_SPACER_PANEL_WIDTH,
				VERTICAL_SPACER_PANEL_BOTTOM_HEIGHT));
	}

	private void makeLevelButton() {
		JPanel buttonModule = new JPanel();

		buttonModule.setLayout(new BoxLayout(buttonModule, BoxLayout.Y_AXIS));
		final JTextField levelName = new JTextField(LEVEL_NAME_TEXTBOX_WIDTH);

		JButton level = new JButton(NEW_LEVEL_TEXT);
		level.addActionListener(new LevelNameListener(levelName));

		buttonModule.add(levelName);
		buttonModule.add(level);

		add(buttonModule);
	}
	
	private String getCurrentLivesString(){
		return CURRENT_LIVES_TEXT + Integer.toString(userLivesChoiceSlider.getValue());
	}

	private void createNumberLivesOption() {
		JPanel lifePanel = new JPanel(new BorderLayout());
		JButton changeNumberLivesButton = new JButton(
				CHANGE_NUMBER_LIVES_BUTTON_TEXT);
		changeNumberLivesButton.addActionListener(new ChangeLivesListener());

		userLivesChoiceSlider = new SliderObject(LIVES_SLIDER_NAME, DEFAULT_NUMBER_OF_LIVES, LIVES_SLIDER_RANGE);
		Game.setLives(DEFAULT_NUMBER_OF_LIVES);

		currentLives = new JTextField(CURRENT_LIVES_TEXTBOX_WIDTH);
		currentLives.setEditable(false);
		currentLives.setText(getCurrentLivesString());

		lifePanel.add(changeNumberLivesButton, BorderLayout.WEST);
		lifePanel.add(userLivesChoiceSlider, BorderLayout.CENTER);
		lifePanel.add(currentLives, BorderLayout.SOUTH);

		add(lifePanel);
	}
	
	private class ChangeLivesListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Game.setLives(userLivesChoiceSlider.getValue());
			currentLives.setText(getCurrentLivesString());
		}
	}

	private class LevelNameListener implements ActionListener {

		private JTextField levelName;

		public LevelNameListener(JTextField name) {
			levelName = name;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			myLevelPanel.addLevel(levelName.getText());
			levelName.setText("");
		}

	}

}