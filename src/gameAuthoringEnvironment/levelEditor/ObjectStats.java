package gameauthoringenvironment.leveleditor;

/**
 * This is a constants stats file.
 * 
 */
public class ObjectStats {
	public String myColType = "";
	public String myMovementPattern = "";
	public String myImageName = "";
	public Integer myCollID = 0;
	public Integer mySpeed = 0;
	public Integer myDuration = 0;
	public boolean isCameraFollow = false;
	public boolean isFloating = false;
	public Integer hitPoints = 0;

	public ObjectStats() {
	}

	/**
	 * Constants file for getting an object's statistics.
	 * 
	 * @param statsArray
	 *            Array of the statistics of the object.
	 */

	public ObjectStats(String[] statsArray) {
		myColType = statsArray[0];
		myCollID = Integer.parseInt(statsArray[1]);
		myMovementPattern = statsArray[2];
		mySpeed = Integer.parseInt(statsArray[3]);
		myDuration = Integer.parseInt(statsArray[4]);
		isCameraFollow = Boolean.parseBoolean(statsArray[5]);
		myImageName = statsArray[6];
		isFloating = Boolean.parseBoolean(statsArray[7]);
		hitPoints = Integer.parseInt(statsArray[8]);
	}

	/**
	 * Stats for a game object.
	 * 
	 * @param colType
	 *            Name of the object type as related to its collision.
	 * @param collID
	 *            Collision ID of the object.
	 * @param movementPattern
	 *            Movement type of the object.
	 * @param speed
	 *            How fast the object moves when it is doing its movement
	 *            pattern.
	 * @param duration
	 *            How long each sequence of the movement lasts.
	 * @param cameraFollow
	 *            Whether or not the camera follows the object.
	 * @param imageName
	 *            Image of the object.
	 * @param floating
	 *            If the object is affected by gravity or not.
	 */

	public ObjectStats(String colType, Integer collID, String movementPattern,
			Integer speed, Integer duration, boolean cameraFollow,
			String imageName, boolean floating, int points) {
		this(new String[] { colType, collID.toString(), movementPattern,
				speed.toString(), duration.toString(),
				Boolean.toString(cameraFollow), imageName,
				Boolean.toString(floating), Integer.toString(points) });
	}
}