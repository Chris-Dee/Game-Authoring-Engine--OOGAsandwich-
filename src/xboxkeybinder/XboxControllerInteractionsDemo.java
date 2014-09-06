package xboxkeybinder;

public class XboxControllerInteractionsDemo {
	public static void main(String[] args) {
		XboxControllerInteractions controller = new XboxControllerInteractions();
		while(true) {
			if(controller.isDigitalKeyPressed(XboxKeys.BUTTON_A)) {
				actionForAKey();
			}
			System.out.println(controller.getAnalogKeyValue(XboxKeys.LEFT_THUMB_DIRECTION));
		}
	}

	private static void actionForAKey() {
		System.out.println("A was pressed!");
	}
}
