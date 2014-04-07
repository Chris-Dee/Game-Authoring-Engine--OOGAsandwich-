package gameplayer;

import gameEngine.GameForce;
import gameEngine.GameObject;

public class Collision {
	public int colid1;
	public int colid2;
	public GameObjectModification mod1;
	public GameObjectModification mod2;

	public Collision(int colid1, int colid2, GameObjectModification mod1, GameObjectModification mod2){
		this.colid1 = colid1;
		this.colid2 = colid2;
		this.mod1 = mod1;
		this.mod2 = mod2;
	}
}
