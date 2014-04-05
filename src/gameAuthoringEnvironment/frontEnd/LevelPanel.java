package gameAuthoringEnvironment.frontEnd;

import gameAuthoringEnvironment.frontEnd.LevelPanelComponent;
import gameAuthoringEnvironment.levelStatsEditor.BasicLevelStats;

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
	// Game game
	private List<LevelPanelComponent> levelList = new ArrayList<LevelPanelComponent>();
private BasicLevelStats statsPanel;
	public LevelPanel(BasicLevelStats stats) {
		super();
		statsPanel=stats;
		initialize();
		System.out.println("l29LP");
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
		for (int i = 0; i < levelList.size(); i++) {
			this.add(levelList.get(i));
		}
		for (int i = 0; i < 10 - levelList.size(); i++) {
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
		int index = levelList.indexOf(lev);
		if (index + switchAmt < 0 || index + switchAmt > levelList.size() - 1
				|| index == -1)
			return levelList;
		LevelPanelComponent temp = levelList.get(index);
		LevelPanelComponent l = levelList.get(index + switchAmt);
		levelList.set(index, l);
		levelList.set(index + switchAmt, temp);
		fillPanels();
		return levelList;
	}

	public LevelPanelComponent findActivePanel() {
		for (LevelPanelComponent lev : levelList) {

			if(lev.isActive()){
				statsPanel.setLevelName(lev.getLevel().getName());
				return lev;
			}
		}
		
		return null;
	}

	public void addLevel(String name) {
		System.out.println("back "+name);
		levelList.add(new LevelPanelComponent(LevelPanelComponent.NORMAL_COLOR,
				name, this));
		fillPanels();
	}
	public void setLevelName(String name){
		statsPanel.setLevelName(name);
	}

	public void resetBackgrounds() {
		for (LevelPanelComponent l : levelList) {
			l.setBackground(LevelPanelComponent.NORMAL_COLOR);
			l.setActive(false);
			l.revalidate();
			l.repaint();

		}
	}
	
	public void deleteLevel(LevelPanelComponent level){
		levelList.remove(level);
		setLevelName(null);
		fillPanels();
	}
}
