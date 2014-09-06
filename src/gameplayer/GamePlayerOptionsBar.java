package gameplayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class GamePlayerOptionsBar extends JMenuBar {

	private static final String FILE_CONSTANT = "File";
	private static final String RESTART_GAME_BUTTON_TEXT = "Reset Game";

	private ActionListener myRestartListener;

	/**
	 * Makes JMenuBar at top of the game player.
	 */

	public GamePlayerOptionsBar(ActionListener restartListener) {
		myRestartListener = restartListener;
		initialize();
	}

	private void initialize() {
		JMenu file = new JMenu(FILE_CONSTANT);
		JMenuItem startItem = new JMenuItem(RESTART_GAME_BUTTON_TEXT);
		startItem.addActionListener(myRestartListener);

		file.add(startItem);
		add(file);
		setVisible(true);
	}
}