package util.positionalsystem;

public enum RelativeVerticalPosition {
	TOP, BOT, MIDDLE;
	public static RelativeVerticalPosition parseString(String string){
		if(string.equals("TOP")){
			return TOP;
		}else if(string.equals("BOT")){
			return BOT;
		}else if(string.equals("MIDDLE")){
			return MIDDLE;
		}else{
			return null;
		}
	}
}