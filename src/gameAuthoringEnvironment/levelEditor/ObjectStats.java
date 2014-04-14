package gameAuthoringEnvironment.levelEditor;

public class ObjectStats {
	// TODO could be int depending on how we do it?
	public String myColType;
	public Integer myCollID;
	public String myMovementPattern;
	public Integer mySpeed;
	public Integer myDuration;
	public Integer myGravMag;
	public boolean isCameraFollow;
	public String myImageName;
	// TODO could definitely get rid of this
	public boolean isFloating;

	public ObjectStats(String[] statsArray) {
		myColType = statsArray[0];
		myCollID = Integer.parseInt(statsArray[1]);
		myMovementPattern = statsArray[2];
		mySpeed = Integer.parseInt(statsArray[3]);
		myDuration = Integer.parseInt(statsArray[4]);
		myGravMag = Integer.parseInt(statsArray[5]);
		isCameraFollow = Boolean.parseBoolean(statsArray[6]);
		myImageName = statsArray[7];
		isFloating = Boolean.parseBoolean(statsArray[8]);
	}

	public ObjectStats(String colType, Integer collID, String movementPattern,
			Integer speed, Integer duration, Integer gravMag,
			boolean cameraFollow, String imageName, boolean floating) {
		this(new String[] { colType, collID.toString(), movementPattern,
				speed.toString(), duration.toString(), gravMag.toString(),
				Boolean.toString(cameraFollow), imageName,
				Boolean.toString(floating) });
	}
}