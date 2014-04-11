package gameAuthoringEnvironment.levelEditor;

public class ObjectStats {
	//TODO could be int depending on how we do it?
	public String colType;
	public Integer collID;
	public String movementPattern;
	public Integer speed;
	public Integer duration;
	public Integer gravMag;
	public boolean cameraFollow;
	public String imageName;
	//TODO could definitely get rid of this
	public boolean isFloating;
	public ObjectStats(String c, Integer co, String m, Integer s, Integer d, Integer g, boolean cam, String i, boolean f){
	colType=c;
	collID = co;
	movementPattern=m;
	speed=s;
	duration=d;
	gravMag=g;
	cameraFollow=cam;
	imageName=i;
	isFloating=f;
}
	public ObjectStats(String[] statsArray) {
		colType=statsArray[0];
	}
}
