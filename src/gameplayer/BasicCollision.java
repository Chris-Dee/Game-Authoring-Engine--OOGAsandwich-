package gameplayer;

import gameEngine.GameObject;

public class BasicCollision extends Collision{
	public GameObjectModification mod1;

	public BasicCollision(int colid1, int colid2, GameObjectModification mod1){
		super(colid1, colid2);
		this.mod1 = mod1;
	}
}
