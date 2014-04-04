package gameAuthoringEnvironment.frontEnd;

import gameAuthoringEnvironment.levelEditor.LevelEditorWindow;
import gameAuthoringEnvironment.levelStatsEditor.BackgroundChooser;
import gameEngine.Level;

import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;


/**
 * Component the LevelPanel. Each of these is an individual level that has its
 * own editable properties.
 * 
 * 
 */
public class LevelPanelComponent extends JPanel {

	LevelPanel levelPanel;
	public static final Color ACTIVE_COLOR = new Color(100, 100, 100);
	public static final Color HOVER_COLOR = new Color(150, 150, 150);
	public static final Color NORMAL_COLOR = new Color(200, 200, 200);
	private static final int WIDTH = 200;
	private static final int HEIGHT = 100;
	private boolean isActive = false;
	private Level level;

	private LevelEditorWindow editWindow;
	// TODO These two traits will need to go into the level object...
	private String backgroundName = "White";
	private int[] size;

	public LevelPanelComponent(Color c, String name, LevelPanel l) {
		super();
		level = new Level(name);
		levelPanel = l;
		setBackground(c);
		// Level level;
		TitledBorder title = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black), name);
		setBorder(title);
		setSize(WIDTH, HEIGHT);

		setPreferredSize(getSize());
		setFocusable(true);
		createMouseActions(this);
	}

	public Level getLevel() {
		return level;
	}

	private void createMouseActions(LevelPanelComponent level) {
		final LevelPanelComponent levels = level;
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if (getBackground() != ACTIVE_COLOR)
					setBackground(HOVER_COLOR);
				revalidate();
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (getBackground() != ACTIVE_COLOR)
					setBackground(NORMAL_COLOR);
				revalidate();
				repaint();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				levelPanel.resetBackgrounds();
				setBackground(ACTIVE_COLOR);
				isActive = true;
				BackgroundChooser.setSelectedToBackground(backgroundName);
				revalidate();
				repaint();

				if (e.getClickCount() == 2) { // run this on double click
					setBackground(ACTIVE_COLOR);
					isActive = false;
					// Pass this a level object as well, once we get those
					editWindow = new LevelEditorWindow(levels);
					// editWindow.setVisible(true);
				}
			}
		});
	}

	public void setActive(boolean b) {
		isActive = b;
	}

	public boolean isActive() {
		return isActive;
	}

	public void changeDefaultBackground(String newBG, String newBGName) {
		// TODO Auto-generated method stub
		backgroundName = newBGName;
		level.changeStartingBackground(newBG);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawString("Width: " + level.getLevelSize().x, 5, 30);
		g.drawString("Height: " + level.getLevelSize().y, 5, 50);

	}
}
