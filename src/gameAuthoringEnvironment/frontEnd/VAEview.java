package gameAuthoringEnvironment.frontEnd;

import gameAuthoringEnvironment.levelStatsEditor.BasicLevelStats;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.ScrollPane;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class VAEview extends JFrame {
	private LevelPanel levels;
	public static final Color backColor = Color.BLACK;

	public VAEview() {
		initialize();
	}

	public static void main(String[] args) {
		VAEview v = new VAEview();
		v.setMainPanel();
	}

	public void initialize() {
		setTitle("OOGASandwich Game Editor");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setVisible(true);
		setLayout(new BorderLayout());
	}

	public void setMainPanel() {
		JPanel mainPanel = (JPanel) this.getContentPane();
		mainPanel.setBackground(backColor);
		// mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setLayout(new BorderLayout());
		JPanel editPanel = new JPanel(new BorderLayout());
		ScrollPane scroller = new ScrollPane();
		scroller.setSize(600, 600);
		BasicLevelStats stats=new BasicLevelStats();
		LevelPanel l = new LevelPanel(stats);
		levels = l;
		scroller.add(l);
		// editPanel.add(scroller, BorderLayout.WEST);
		editPanel.add(new ObjectPanel(l), BorderLayout.EAST);
		editPanel.add(stats, BorderLayout.CENTER);
		// mainPanel.add(new OptionsPanel(),BorderLayout.NORTH);
		mainPanel.add(scroller);
		mainPanel.add(editPanel, BorderLayout.EAST);
		setExtendedState(Frame.MAXIMIZED_BOTH);
		KeyboardFocusManager manager = KeyboardFocusManager
				.getCurrentKeyboardFocusManager();
		MyDispatcher dispatch = new MyDispatcher();
		manager.addKeyEventDispatcher(dispatch);
		pack();
	}

	private class MyDispatcher implements KeyEventDispatcher {
		@Override
		public boolean dispatchKeyEvent(KeyEvent e) {
			if (e.getID() == KeyEvent.KEY_PRESSED) {

			} else if (e.getID() == KeyEvent.KEY_RELEASED) {
				if (e.getKeyCode() == 38)
					levels.switchLevels(levels.findActivePanel(), -1);
				if (e.getKeyCode() == 40) {
					levels.switchLevels(levels.findActivePanel(), 1);
				}
			} else if (e.getID() == KeyEvent.KEY_TYPED) {
			}
			return false;
		}
	}

}
