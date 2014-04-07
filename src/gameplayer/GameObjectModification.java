package gameplayer;

import gameEngine.GameObject;

public class GameObjectModification {
	//linear modification
	int[][] modMatrix;
	public int speedMod;
	private int xSpeedMod;
	private int ySpeedMod;
	public GameObjectModification(){ //TODO: Differentiate collision based on which side of BBox
		int[][] tempMatrix = {{1, 0, 0, 0, 0, 0, 0, 0}, {0, 1, 0, 0, 0, 0, 0, 0}};
		this.modMatrix = tempMatrix;
		this.xSpeedMod = 1;
		this.ySpeedMod = 1;
	}
	public GameObjectModification(int[][] modMatrix, int xSpeedMod, int ySpeedMod){
		this.modMatrix = modMatrix;
		this.xSpeedMod = xSpeedMod;
		this.ySpeedMod = ySpeedMod;
	}
	public void apply(
			GameObject object, GameObject objectReference){
		object.x = modMatrix[0][0] * object.getBBox().x
				+ modMatrix[0][1] * object.getBBox().y
				+ modMatrix[0][2] * object.getBBox().width
				+ modMatrix[0][3] * object.getBBox().height
				+ modMatrix[0][4] * objectReference.getBBox().x
				+ modMatrix[0][5] * objectReference.getBBox().y
				+ modMatrix[0][6] * objectReference.getBBox().width
				+ modMatrix[0][7] * objectReference.getBBox().height;
		
		object.y = modMatrix[1][0] * object.getBBox().x
				+ modMatrix[1][1] * object.getBBox().y
				+ modMatrix[1][2] * object.getBBox().width
				+ modMatrix[1][3] * object.getBBox().height
				+ modMatrix[1][4] * objectReference.getBBox().x
				+ modMatrix[1][5] * objectReference.getBBox().y
				+ modMatrix[1][6] * objectReference.getBBox().width
				+ modMatrix[1][7] * objectReference.getBBox().height;
		object.xspeed = xSpeedMod * object.xspeed;
		object.yspeed = ySpeedMod * object.xspeed;
	}
}
