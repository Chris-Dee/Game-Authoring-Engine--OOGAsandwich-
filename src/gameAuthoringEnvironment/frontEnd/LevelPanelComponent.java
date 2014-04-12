package gameAuthoringEnvironment.frontEnd;

import gameAuthoringEnvironment.levelEditor.LevelEditorWindow;
import gameAuthoringEnvironment.levelStatsEditor.BackgroundChooser;
import gameEngine.Level;
import gameEngine.UninstantiatedGameObject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;


public class LevelPanelComponent extends JPanel {

	private static final int YPOS_FOR_HEIGHT_PRINT = 30;
	private static final int YPOS_FOR_WIDTH_PRINT = 50;
	private static final int XPOS_FOR_PRINT = 5;
	private static final int DOUBLE_CLICK_NUMBER = 2;
	private LevelPanel myLevelPanel;
	public static final Color ACTIVE_COLOR = new Color(100, 100, 100);
	public static final Color HOVER_COLOR = new Color(150, 150, 150);
	public static final Color NORMAL_COLOR = new Color(200, 200, 200);
	private static final int WIDTH = 200;
	private static final int HEIGHT = 100;
	private boolean isActive = false;
	private final Level myLevel;

	private LevelEditorWindow editWindow;
	// TODO These two traits will need to go into the level object...
	private String backgroundName = "White";
	private int[] size;

	/**
	 * Component the LevelPanel. Each of these is an individual level that has its
	 * own editable properties.
	 * @param color
	 * 			Color of the background
	 * @param name
	 * 			Name of the level
	 * @param levelPanel
	 * 				LevelPanel that this LevelPanelComponent belongs to
	 */
	public LevelPanelComponent(Color color, String name, LevelPanel levelPanel) {
		super();

		myLevel = new Level(name);
		myLevelPanel = levelPanel;
		initialize(color);
		
	}
	
	public LevelPanelComponent(Color color, Level level, LevelPanel levelPanel){
		super();
		
		myLevel = level;
		myLevelPanel = levelPanel;
		
		initialize(color);
	}
	
	public void setAllObjectsActive() {
		for (UninstantiatedGameObject object : myLevel.getObjects()) {
			object.instantiate();
		}
	}
	private void initialize(Color color){
		setBackground(color);
		TitledBorder title = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black), myLevel.getName());
		setBorder(title);
		setSize(WIDTH, HEIGHT);

		setPreferredSize(getSize());
		setFocusable(true);
		createMouseActions(this);
	}

	/**
	 * Gets level of this level panel component
	 * 
	 * @return Level
	 */
	public Level getLevel() {
		return myLevel;
	}

	public String getName() {
		return myLevel.getName();
	}

	/**
	 * Gets levelPanel of the viewer
	 * 
	 * @return LevelPanel
	 */
	public LevelPanel getLevelPanel() {
		return myLevelPanel;
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
				myLevelPanel.setLevelName(myLevel.getName());
				changeEnableBG(true);
				revalidate();
				repaint();
				if (e.getModifiers() == MouseEvent.BUTTON1_MASK) {
					if (e.getClickCount() == DOUBLE_CLICK_NUMBER) { // run this
																	// on double
						changeEnableBG(false);										// click
						setBackground(ACTIVE_COLOR);
						isActive = false;
						System.out.println("name " + level.getName());
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
			
				//
		});
	}

	public void setActive(boolean b) {
		isActive = b;
	}

	public boolean isActive() {
		return isActive;
	}
	public void changeEnableBG(boolean b){
		myLevelPanel.changeBGEnable(b);
	}
	public void changeDefaultBackground(String newBGName) {
		backgroundName = newBGName;
		myLevel.changeStartingBackground(newBGName);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawString("Width: " + myLevel.getLevelSize().x, XPOS_FOR_PRINT,
				YPOS_FOR_WIDTH_PRINT);
		g.drawString("Height: " + myLevel.getLevelSize().y, XPOS_FOR_PRINT,
				YPOS_FOR_HEIGHT_PRINT);
	}
}
