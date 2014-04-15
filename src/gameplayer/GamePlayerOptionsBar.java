package gameplayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class GamePlayerOptionsBar extends JMenuBar {

	private static final String RESTART_GAME_QUESTION_TEXT = "Are you sure you want to start over?";
	private static final String RESTART_GAME = "Restart game?";

	/**
	 * Makes JMenuBar at top of the game player.
	 */
	public GamePlayerOptionsBar() {
		initialize();
	}

	private void initialize() {
		JMenu file = new JMenu("File");
		JMenuItem startItem = new JMenuItem("Play game");
		startItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int restartGame = JOptionPane.showConfirmDialog(null,
						RESTART_GAME_QUESTION_TEXT, RESTART_GAME,
						JOptionPane.YES_NO_OPTION);
				if (restartGame==0){ //Player chose yes,restart the game
					System.out.println("yay!");
				}
			}

		});

		JMenuItem highScoresItem = new JMenuItem("High Scores");
		highScoresItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Implement action

			}

		});

		file.add(startItem);
		file.add(highScoresItem);

		add(file);
		setVisible(true);
	}

}
