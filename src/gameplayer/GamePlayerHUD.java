package gameplayer;

public class GamePlayerHUD {
	private static final String LIVES_STRING = "Lives: ";
	private static final int WIDTH_OFFSET_FOR_LIVES = 1 / 8;
	private static final int OFFSET_FACTOR_FOR_TIME_WIDTH = 6 / 8;
	private static final String TIME_STRING = "Time: ";
	private static final int SCORE_WIDTH_OFFSET_FACTOR = 7 / 8;
	private static final String SCORE_STRING = "Score: ";
	private static final int WIDTH_DIVISION_FACTOR_FOR_LEVEL_NAME = 2;
	private static final int DEFAULT_Y_POS_FOR_LEVEL_NAME_SCORE_TIME_AND_LIVES = 24;
	private static final String DEFAULT_LEVEL_NAME_TEXT = "myLevel";
	private GamePlayerEngine myGame;
	private int gameWidth;

	/**
	 * Initializes a HUD for a specific GamePlayerEngine.
	 * 
	 * @param game
	 *            The GamePlayerEngine object.
	 */
	public GamePlayerHUD(GamePlayerEngine game) {
		myGame = game;
		gameWidth = myGame.viewWidth();
	}

	/**
	 * Paints the contents of the HUD in the JGame instance.
	 */
	public void paintHUD() {
		String levelText = DEFAULT_LEVEL_NAME_TEXT;
		if (!myGame.getGame().getCurrentLevel().getLevelName().equals("")) {
			levelText = myGame.getGame().getCurrentLevel().getLevelName();
		}
		myGame.drawString(levelText, gameWidth
				/ WIDTH_DIVISION_FACTOR_FOR_LEVEL_NAME,
				DEFAULT_Y_POS_FOR_LEVEL_NAME_SCORE_TIME_AND_LIVES, 0);
		paintScore();
		paintLives();
		if (myGame.getGame().getCurrentLevel().hasTimer()) {
			paintLevelTime();
		}
	}

	/**
	 * Display the total game score.
	 */
	public void paintScore() {
		String totalScore = SCORE_STRING + myGame.getScore();
		myGame.drawString(totalScore, SCORE_WIDTH_OFFSET_FACTOR * gameWidth,
				DEFAULT_Y_POS_FOR_LEVEL_NAME_SCORE_TIME_AND_LIVES, 0);
	}

	/**
	 * Display the time in the level.
	 */
	public void paintLevelTime() {
		String totalScore = TIME_STRING
				+ myGame.getGame().getCurrentLevel().getCurrentTime();
		myGame.drawString(totalScore, OFFSET_FACTOR_FOR_TIME_WIDTH * gameWidth,
				DEFAULT_Y_POS_FOR_LEVEL_NAME_SCORE_TIME_AND_LIVES, 0);
	}

	/**
	 * Display the lives count.
	 */
	public void paintLives() {
		String lifecounter = LIVES_STRING + myGame.getGame().getLives();
		myGame.drawString(lifecounter, WIDTH_OFFSET_FOR_LIVES * gameWidth,
				DEFAULT_Y_POS_FOR_LEVEL_NAME_SCORE_TIME_AND_LIVES, 0);
	}
}