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

/**
 * Panel to display current levels available in game.
 * 
 * 
 */
public class LevelPanel extends JPanel {
	private static final int EMPTY_SPACE_CONSTANT = 10;
	// Game game
	private List<LevelPanelComponent> levelComponentList;
	private BasicLevelStats statsPanel;

	public LevelPanel(BasicLevelStats stats) {
		super();
		levelComponentList = new ArrayList<LevelPanelComponent>();
		statsPanel = stats;
		initialize();
		// System.out.println("l29LP");
		stats.setLevelPanel(this);
	}

	public void initialize() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		Dimension s = getToolkit().getScreenSize();
		setBackground(new Color(0, 0, 0));
		fillPanels();
	}

	public void makeLevelPanel() {
	}

	public void fillPanels() {
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

	public List<LevelPanelComponent> switchLevels(LevelPanelComponent lev,
			int switchAmt) {
		// need to switch it in game array as well
		int index = levelComponentList.indexOf(lev);
		if (index + switchAmt < 0 || index + switchAmt > levelComponentList.size() - 1
				|| index == -1)
			return levelComponentList;
		LevelPanelComponent temp = levelComponentList.get(index);
		LevelPanelComponent l = levelComponentList.get(index + switchAmt);
		levelComponentList.set(index, l);
		levelComponentList.set(index + switchAmt, temp);
		fillPanels();
		return levelComponentList;
	}

	public LevelPanelComponent findActivePanel() {
		for (LevelPanelComponent lev : levelComponentList) {

			if (lev.isActive()) {
				statsPanel.setLevelName(lev.getLevel().getName());
				return lev;
			}
		}
		
		return null;
	}

	public void addLevel(String name) {
		System.out.println("back " + name);
		levelComponentList.add(new LevelPanelComponent(LevelPanelComponent.NORMAL_COLOR,
				name, this));
		fillPanels();
	}

	public void setLevelName(String name) {
		statsPanel.setLevelName(name);
	}

	public void resetBackgrounds() {
		for (LevelPanelComponent levelComponent : levelComponentList) {
			levelComponent.setBackground(LevelPanelComponent.NORMAL_COLOR);
			levelComponent.setActive(false);
			levelComponent.revalidate();
			levelComponent.repaint();

		}
	}
	
	public void deleteLevel(LevelPanelComponent level){
		levelComponentList.remove(level);
		setLevelName(null);
		fillPanels();
	}
}
