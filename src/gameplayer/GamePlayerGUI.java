package gameplayer;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import gameEngine.Game;

public class GamePlayerGUI extends JFrame {

	/**
	 * GUI that runs the actual created game by the user. Shows the JGEngine in
	 * the main screen with a bar on top with options for the user to use.
	 * 
	 * @param loadedGame
	 * 				Game to be loaded
	 */

	public GamePlayerGUI(Game loadedGame) {
		setLayout(new BorderLayout());
		setJMenuBar(new GamePlayerOptionsBar());
		add(new GamePlayerGame(loadedGame), BorderLayout.CENTER);

		pack();
		setVisible(true);
	}

}
