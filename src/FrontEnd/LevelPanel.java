package FrontEnd;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import FrontEnd.LevelPanelComponent;

public class LevelPanel extends JPanel {
	private List<LevelPanelComponent> levelList = new ArrayList<LevelPanelComponent>();

	public LevelPanel() {
		super();
		initialize();
	}

	public void initialize() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		Dimension s = getToolkit().getScreenSize();
		// setSize(s.width/2,s.height*4/5);
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

	public List<LevelPanelComponent> switchLevels(LevelPanelComponent lev, int switchAmt) {
		int index = levelList.indexOf(lev);
		System.out.println("ind "+index);
		LevelPanelComponent temp = levelList.get(index);
		if (index + switchAmt < 0 || index + switchAmt > levelList.size() - 1)
			return levelList;
		LevelPanelComponent l = levelList.get(index + switchAmt);
		levelList.set(index, l);
		levelList.set(index + switchAmt, temp);
		fillPanels();
		return levelList;
	}

	public LevelPanelComponent findActivePanel() {
		for (LevelPanelComponent lev : levelList) {
			if(lev.isActive()){
				System.out.println("111");
				return lev;
			}
		}
		System.out.println("1");
		return null;
	}

	public void addLevel(String name) {
		levelList.add(new LevelPanelComponent(Color.BLUE, name, this));
		fillPanels();
	}

	public void resetBackgrounds() {
		for (LevelPanelComponent l : levelList) {
			l.setBackground(Color.BLUE);
			l.setActive(false);
			System.out.println(l.getBackground());
			l.revalidate();
			l.repaint();

		}
	}
}
