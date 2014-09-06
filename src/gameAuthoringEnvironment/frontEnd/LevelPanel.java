package gameauthoringenvironment.frontend;

import gameauthoringenvironment.frontend.LevelPanelComponent;
import gameauthoringenvironment.levelstatseditor.BasicLevelStats;
import gameengine.Level;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class LevelPanel extends JPanel {
	private static final Color BLACK_COLOR = new Color(255, 255, 255);
	private static final int EMPTY_SPACE_CONSTANT = 10;
	private List<LevelPanelComponent> levelComponentList;
	private List<String> myLevelNames;
	private BasicLevelStats statsPanel;

	/**
	 * Panel to display current levels available in game.
	 * 
	 * @param stats
	 *            BasicLevelStats panel that this belongs to
	 */
	public LevelPanel(BasicLevelStats stats) {
		super();
		myLevelNames = new ArrayList<String>();
		levelComponentList = new ArrayList<LevelPanelComponent>();
		statsPanel = stats;
		initialize();
		stats.setLevelPanel(this);
	}

	/**
	 * Allows the user to be able to change the background.
	 * 
	 * @param b
	 *            Sets the stats panel to BG enabled if b is true.
	 */
	public void changeBGEnable(boolean b) {
		statsPanel.toggleBGEnabled(b);
	}

	/**
	 * Switch level with component either one above or below it. A negative
	 * number will switch it earlier in the list, while a positive number will
	 * push it down to a further position in the list. If the
	 * LevelPanelComponent at the end of the list, it will not extend past the
	 * maximum position.
	 * 
	 * @param levelPanelComponent
	 *            LevelPanelComponent of the level that is being swapped
	 * @param switchAmount
	 *            Amount to switch the level by. Negative is earlier in the
	 *            list, positive is further back in the list.
	 * @return
	 */
	public List<LevelPanelComponent> switchLevels(
			LevelPanelComponent levelPanelComponent, int switchAmount) {
		// need to switch it in game array as well
		int index = levelComponentList.indexOf(levelPanelComponent);
		if (index + switchAmount < 0
				|| index + switchAmount > levelComponentList.size() - 1
				|| index == -1)
			return levelComponentList;
		LevelPanelComponent temp = levelComponentList.get(index);
		LevelPanelComponent component = levelComponentList.get(index
				+ switchAmount);
		levelComponentList.set(index, component);
		levelComponentList.set(index + switchAmount, temp);
		fillPanels();
		return levelComponentList;
	}

	/**
	 * Finds the currently active panel in the LevelPanel list
	 * 
	 * @return Currently Active LevelPanelComponent
	 */
	public LevelPanelComponent findActivePanel() {
		for (LevelPanelComponent currentLevelPanelComponent : levelComponentList) {
			if (currentLevelPanelComponent.isActive()) {
				statsPanel.setLevelName(currentLevelPanelComponent.getLevel()
						.getLevelName());
				return currentLevelPanelComponent;
			}
		}
		return null;
	}

	/**
	 * Adds a level with default conditions to the game. Automatically adds at
	 * the end of the level list.
	 * 
	 * @param name
	 *            The name of the new level
	 */
	public void addLevel(String name) {
		if (!myLevelNames.contains(name)) {
			levelComponentList.add(new LevelPanelComponent(
					LevelPanelComponent.NORMAL_COLOR, name, this));
			myLevelNames.add(name);
			fillPanels();
		}
	}

	public void addLevel(Level level) {
		if (!myLevelNames.contains(level.getLevelName())) {
			LevelPanelComponent toAdd = new LevelPanelComponent(
					LevelPanelComponent.NORMAL_COLOR, level, this);
			levelComponentList.add(toAdd);
			myLevelNames.add(level.getLevelName());
			fillPanels();
		}
	}

	/**
	 * Sets the name of the level.
	 * 
	 * @param name
	 *            The name of the level
	 */
	public void setLevelName(String name) {
		statsPanel.setLevelName(name);
	}

	/**
	 * Resets the backgrounds of all LevelPanelComponents. Called in
	 * LevelPanelComponent when a level is clicked.
	 */
	public void resetBackgrounds() {
		for (LevelPanelComponent levelComponent : levelComponentList) {
			levelComponent.setBackground(LevelPanelComponent.NORMAL_COLOR);
			levelComponent.setActive(false);
			levelComponent.revalidate();
			levelComponent.repaint();
		}
	}

	/**
	 * Removes level from the game
	 * 
	 * @param levelPanelComponent
	 *            Level to be removed, given in the form of a
	 *            LevelPanelComponent
	 */
	public void deleteLevel(LevelPanelComponent levelPanelComponent) {
		levelComponentList.remove(levelPanelComponent);
		setLevelName(null);
		fillPanels();
	}

	/**
	 * Removes all levels from the current game. Used when loading a new game
	 * into the GAE.
	 */
	public void deleteAllLevels() {
		for (int i = levelComponentList.size() - 1; i >= 0; i--) {
			deleteLevel(levelComponentList.get(i));
		}
	}

	private void initialize() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(new Color(0, 0, 0));
		fillPanels();
	}

	private void fillPanels() {
		this.removeAll();

		for (int i = 0; i < levelComponentList.size(); i++) {
			this.add(levelComponentList.get(i));
		}

		for (int i = 0; i < EMPTY_SPACE_CONSTANT - levelComponentList.size(); i++) {
			JPanel emptyPanel = new JPanel();
			emptyPanel.setBackground(BLACK_COLOR);
			this.add(emptyPanel);
		}
		this.revalidate();
		this.repaint();
	}
}