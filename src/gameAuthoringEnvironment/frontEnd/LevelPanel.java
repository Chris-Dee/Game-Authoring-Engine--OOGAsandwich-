package gameAuthoringEnvironment.frontEnd;

import gameAuthoringEnvironment.frontEnd.LevelPanelComponent;
import gameAuthoringEnvironment.levelStatsEditor.BasicLevelStats;
import gameEngine.Level;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class LevelPanel extends JPanel {
	private static final int EMPTY_SPACE_CONSTANT = 10;
	// Game game
	private List<LevelPanelComponent> levelComponentList;
	private BasicLevelStats statsPanel;

	/**
	 * Panel to display current levels available in game.
	 * 
	 * @param stats
	 *            BasicLevelStats panel that this belongs to
	 */
	public LevelPanel(BasicLevelStats stats) {
		super();
		levelComponentList = new ArrayList<LevelPanelComponent>();
		statsPanel = stats;
		initialize();
		// System.out.println("l29LP");
		stats.setLevelPanel(this);
	}

	private void initialize() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		Dimension s = getToolkit().getScreenSize();
		setBackground(new Color(0, 0, 0));
		fillPanels();
	}

	private void fillPanels() {
		this.removeAll();

		for (int i = 0; i < levelComponentList.size(); i++) {
			this.add(levelComponentList.get(i));
		}

		for (int i = 0; i < EMPTY_SPACE_CONSTANT - levelComponentList.size(); i++) {
			// this.add(new LevelPanelComponent(new Color(255,255,255),""+i));
			JPanel emptyPanel = new JPanel();
			emptyPanel.setBackground(new Color(255, 255, 255));
			this.add(emptyPanel);
		}
		this.revalidate();
		this.repaint();
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
		LevelPanelComponent l = levelComponentList.get(index + switchAmount);
		levelComponentList.set(index, l);
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
						.getName());
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
		levelComponentList.add(new LevelPanelComponent(
				LevelPanelComponent.NORMAL_COLOR, name, this));
		fillPanels();
	}
	
	public void addLevel(Level level){
		levelComponentList.add(new LevelPanelComponent(LevelPanelComponent.NORMAL_COLOR,level,this));
		fillPanels();
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
	 * Removes all levels from the current game. Used when loading a new game into the GAE.
	 */
	
	public void deleteAllLevels(){
		for (int i = levelComponentList.size() - 1; i>=0; i--) {
			deleteLevel(levelComponentList.get(i));
		}
	}
}
