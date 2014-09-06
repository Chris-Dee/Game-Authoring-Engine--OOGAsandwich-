package util.positionalsystem;

public enum RelativeHorizontalPosition {
	LEFT, RIGHT, MIDDLE;
	public static RelativeHorizontalPosition parseString(String string){
		if(string.equals("LEFT")){
			return LEFT;
		}else if(string.equals("RIGHT")){
			return RIGHT;
		}else if(string.equals("MIDDLE")){
			return MIDDLE;
		}else{
			return null;
		}
	}
}