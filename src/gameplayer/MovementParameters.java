package gameplayer;

public class MovementParameters {
	private String behaviorName;
	private int[] y;

	public MovementParameters(String obj1, int... obj2) {
		behaviorName = obj1;
		y = obj2;
	}

	public String getBehaviorName() {
		return behaviorName;
	}

	public int getDuration() {
		return y[0];
	}

	public int getSpeed() {
		return y[1];
	}
}
