Game Statistics Util Readme
(Created by Ethan Gottlieb and Sam Ginsburg)

Description: Easily display stats (including highscores) when your JGame game is over. Our util package takes care of just about everything including getting the player's name, displaying your game stats, and displaying the overall (sorted) top scores for each category. The util package keeps track of stats over infinite runs of the game engine and automatically saves different stats for each game.

Instructions:

1) Add the statistics package to your JGame project.

2) In your game engine class (that extends JGEngine):
	- add the following instance variable:     private StatsController statController; 
	- in the constructor, after you initEngine, instantiate it with:     statController=new StatsController(JGEngine gameEngine, String gameName);
		-Example:    StatsController(this, "Mario Game");

3) To add/update a stat use the following methods: 
	- GameStats.update(String statToChange, int amount)
		- Example: GameStats.update("Score", 1);    to change the user's "Score" by 1
	- GameStats.set(String statToSet, int amount){
		- Example: GameStats.set("Enemies Killed", 20);     to set the user's "Enemies Killed" to 20
	* Both methods create a stat with the given name and with initial value 0, if one does not already exist.
	- These lines of code should be added to methods in your project that are already implemented (e.g in your incrementScore() method).
4) When ready to display the stats, create a new GameState using:   setGameState("DisplayStats");
	- use the following code to implement that game State:

		public void startDisplayStats() {
			statController.saveGameStats();	
		}
	
		public void paintFrameDisplayStats() {
			statController.displayStats();
		}

5) You are ready to go! Add as many stats as you would like. To clear the stats, just delete the contents of the PersistentStats.txt file.