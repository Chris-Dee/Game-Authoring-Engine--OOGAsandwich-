package gameplayer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gameengine.Game;

public class GamePlayerFrame extends JFrame {
	
	private static final String RESTART_GAME_QUESTION_TEXT = "Are you sure you want to start over?";
	private static final String RESTART_GAME = "Restart game?";
	private Game currentGame;
	private GamePlayerEngine myGame;

	/**
	 * GUI that runs the actual created game by the user. Shows the JGEngine in
	 * the main screen with a bar on top with options for the user to use.
	 * 
	 * @param loadedGame
	 *            Game to be loaded
	 */

	public GamePlayerFrame(Game loadedGame) {
		
		currentGame=loadedGame;
		setLayout(new BorderLayout());
		setJMenuBar(new GamePlayerOptionsBar(new RestartLevelAction()));
		setExitAction();
		myGame = new GamePlayerEngine(loadedGame);
		add(myGame, BorderLayout.CENTER);

		pack();
		setVisible(true);
	}

	private void setExitAction() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				myGame.destroy();
				e.getWindow().dispose();
			}
		});
	}
	public void resetGame() {
		myGame.destroy();
		remove(myGame);
		currentGame.setCurrentLevel(0);
		currentGame.reset();
		currentGame.getCurrentLevel().setCurrentTime(30);
		myGame=new GamePlayerEngine(currentGame);
		add(myGame);
		revalidate();
		repaint();
	}
	public GamePlayerEngine getGame(){
		return myGame;
	}
	
	private class RestartLevelAction implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			int restartGame = JOptionPane.showConfirmDialog(null,
					RESTART_GAME_QUESTION_TEXT, RESTART_GAME,
					JOptionPane.YES_NO_OPTION);
			if (restartGame==0){ //Player chose yes,restart the game
				resetGame();
			}
		}
	}
}