package gameauthoringenvironment.frontend;

import gameauthoringenvironment.leveleditor.LevelEditorWindow;
import gameauthoringenvironment.levelstatseditor.BackgroundChooser;
import gameengine.Level;
import gameengine.UninstantiatedGameObject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class LevelPanelComponent extends JPanel {

	private static final String NUMBER_OBJECTS_TEXT = "Number objects: ";
	private static final String HEIGHT_TEXT = "Height: ";
	private static final String WIDTH_TEXT = "Width: ";
	private static final int YPOS_FOR_WIDTH_PRINT = 30;
	private static final int YPOS_FOR_HEIGHT_PRINT = 50;
	private static final int YPOS_FOR_OBJECTS_PRINT = 70;
	private static final int XPOS_FOR_PRINT = 5;
	private static final int DOUBLE_CLICK_NUMBER = 2;
	private static final int WIDTH = 200;
	private static final int HEIGHT = 100;
	private boolean isActive = false;
	public static final Color ACTIVE_COLOR = new Color(100, 100, 100);
	public static final Color HOVER_COLOR = new Color(150, 150, 150);
	public static final Color NORMAL_COLOR = new Color(200, 200, 200);
	private LevelPanel myLevelPanel;
	private Level myLevel;

	private LevelEditorWindow editWindow;
	// TODO These two traits will need to go into the level object...
	private String backgroundName = "White";
	private int[] size;

	/**
	 * Component of the LevelPanel. Each of these is an individual level that
	 * has its own editable properties.
	 * 
	 * @param color
	 *            Color of the background.
	 * @param name
	 *            Name of the level.
	 * @param levelPanel
	 *            LevelPanel that this LevelPanelComponent belongs to.
	 */
	public LevelPanelComponent(Color color, String name, LevelPanel levelPanel) {
		super();
		myLevel = new Level(name);
		myLevelPanel = levelPanel;
		initialize(color);
	}

	/**
	 * Component of the LevelPanel. Each of these is an individual level that
	 * has its own editable properties.
	 * 
	 * @param color
	 *            Color of the background
	 * @param level
	 *            Level to be added.
	 * @param levelPanel
	 *            LevelPanel that this LevelPanelComponent belongs to.
	 */
	public LevelPanelComponent(Color color, Level level, LevelPanel levelPanel) {
		super();
		myLevel = level;
		myLevelPanel = levelPanel;
		initialize(color);
	}

	/**
	 * Sets all of the Objects in the level to active.
	 */
	public void setAllObjectsActive() {
		for (UninstantiatedGameObject object : myLevel.getObjects()) {
			object.instantiate(true);
		}
	}

	/**
	 * Gets level of this level panel component
	 * 
	 * @return Level
	 */
	public Level getLevel() {
		return myLevel;
	}

	/**
	 * Returns the name of the Level
	 * 
	 * @return The name of the Level
	 */
	public String getName() {
		return myLevel.getLevelName();
	}

	/**
	 * Gets levelPanel of the viewer
	 * 
	 * @return LevelPanel
	 */
	public LevelPanel getLevelPanel() {
		return myLevelPanel;
	}

	/**
	 * Sets the component to active or not.
	 * 
	 * @param b
	 *            True if the component should be set active, false if not.
	 */
	public void setActive(boolean b) {
		isActive = b;
	}

	/**
	 * Returns if the component is active or not.
	 * 
	 * @return True if the component is active, false if not.
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * Sets the changing background enabling in the LevelPanel.
	 * 
	 * @param b
	 *            True if changing BG should be on, false if not.
	 */
	public void changeEnableBG(boolean b) {
		myLevelPanel.changeBGEnable(b);
	}

	/**
	 * Changes the default background of the Level.
	 * 
	 * @param newBGName
	 *            String of the new BG name.
	 */
	public void changeDefaultBackground(String newBGName) {
		backgroundName = newBGName;
		myLevel.changeStartingBackground(newBGName);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawString(WIDTH_TEXT + myLevel.getLevelSize().y, XPOS_FOR_PRINT,
				YPOS_FOR_WIDTH_PRINT);
		g.drawString(HEIGHT_TEXT + myLevel.getLevelSize().x, XPOS_FOR_PRINT,
				YPOS_FOR_HEIGHT_PRINT);
		g.drawString(NUMBER_OBJECTS_TEXT + myLevel.getObjects().size(),
				XPOS_FOR_PRINT, YPOS_FOR_OBJECTS_PRINT);
	}

	private void initialize(Color color) {
		setBackground(color);
		setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black), myLevel.getLevelName()));
		setSize(WIDTH, HEIGHT);
		setPreferredSize(getSize());
		setFocusable(true);
		createMouseActions(this);
	}

	private void createMouseActions(final LevelPanelComponent level) {
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
				myLevelPanel.resetBackgrounds();
				setBackground(ACTIVE_COLOR);
				isActive = true;
				BackgroundChooser.setSelectedToBackground(backgroundName);
				myLevelPanel.setLevelName(myLevel.getLevelName());
				changeEnableBG(true);
				revalidate();
				repaint();
				if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {
					if (e.getClickCount() == DOUBLE_CLICK_NUMBER) {
						// run this on double click
						changeEnableBG(false);
						setBackground(ACTIVE_COLOR);
						isActive = false;
						// Pass this a level object as well, once we get those
						editWindow = new LevelEditorWindow(levels);
						// editWindow.setVisible(true);
					}
				}
				if (e.getModifiers() == MouseEvent.BUTTON3_MASK) {
					LevelPanelRightClickMenu menu = new LevelPanelRightClickMenu(
							level, e.getPoint());
				}
			}
		});
	}
}