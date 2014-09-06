package xboxkeybinder;

import java.util.HashMap;
import java.util.Map;

import ch.aplu.util.Monitor;
import ch.aplu.xboxcontroller.XboxController;
import ch.aplu.xboxcontroller.XboxControllerAdapter;

public class XboxControllerInteractions{
	private XboxController xc;
	private Map<XboxKeys, Boolean> controllerDigitalKeyStatus = new HashMap<XboxKeys, Boolean>();
	private Map<XboxKeys, Double> controllerAnalogKeyStatus = new HashMap<XboxKeys, Double>();
	private XboxKeys lastPressed;

	public XboxControllerInteractions() {
		xc = new XboxController();
		if (!xc.isConnected()) {
			System.out.println("Xbox controller not connected\n");
		}
		else {
			System.out.println("Xbox controller is connected\n");
		}
	    xc.addXboxControllerListener(new XboxControllerAdapter()
	    {
	    	public void back(boolean pressed) {
	    		updateDigitalKeyStatus(XboxKeys.BACK, pressed);
	    	}

	    	public void buttonA(boolean pressed) {
	    		updateDigitalKeyStatus(XboxKeys.BUTTON_A, pressed);
	    	}

	    	public void buttonB(boolean pressed) {
	    		updateDigitalKeyStatus(XboxKeys.BUTTON_B, pressed);
	    	}

	    	public void buttonX(boolean pressed) {
	    		updateDigitalKeyStatus(XboxKeys.BUTTON_X, pressed);
	    	}

	    	public void buttonY(boolean pressed) {
	    		updateDigitalKeyStatus(XboxKeys.BUTTON_Y, pressed);
	    	}

	    	public void dpad(int direction, boolean pressed) {
	    		updateDigitalKeyStatus(XboxKeys.DPAD, pressed);
	    		updateAnalogKeyStatus(XboxKeys.DPAD_DIRECTION, pressed,  (double) direction);
	    	}
	    	
	    	public void leftShoulder(boolean pressed) {
	    		updateDigitalKeyStatus(XboxKeys.LEFT_SHOULDER, pressed);
	    	}
	    	
	    	public void leftThumb(boolean pressed) {
	    		updateDigitalKeyStatus(XboxKeys.LEFT_THUMB, pressed);
	    	}
	    	
	    	public void rightShoulder(boolean pressed) {
	    		updateDigitalKeyStatus(XboxKeys.RIGHT_SHOULDER, pressed);
	    	}
	    	
	    	public void rightThumb(boolean pressed) {
	    		updateDigitalKeyStatus(XboxKeys.RIGHT_THUMB, pressed);
	    	}
	    	
	    	public void start(boolean pressed) {
	    		updateDigitalKeyStatus(XboxKeys.START, pressed);
	    	}
	    	
	    	public void leftThumbMagnitude(double magnitude) {
	    		updateAnalogKeyStatus(XboxKeys.LEFT_THUMB_MAGNITUDE, true, (double) magnitude);
	    	}
	    	
	    	public void leftThumbDirection(double direction) {
	    		updateAnalogKeyStatus(XboxKeys.LEFT_THUMB_DIRECTION, true, (double) direction);
	    	}
	    	
	    	public void leftTrigger(double value) {
	    		updateAnalogKeyStatus(XboxKeys.LEFT_TRIGGER_MAGNITUDE, true, (double) value);
	    	}
	    	
	    	public void rightThumbMagnitude(double magnitude) {
	    		updateAnalogKeyStatus(XboxKeys.RIGHT_THUMB_MAGNITUDE, true, (double) magnitude);
	    	}
	    	
	    	public void rightThumbDirection(double direction) {
	    		updateAnalogKeyStatus(XboxKeys.RIGHT_THUMB_DIRECTION, true, (double) direction);
	    	}
	    	
	    	public void rightTrigger(double value) {
	    		updateAnalogKeyStatus(XboxKeys.RIGHT_TRIGGER_MAGNITUDE, true, (double) value);
	    	}
	    });
		lastPressed = XboxKeys.NULL;
	}
	
	/**
	 * Get the XboxKeys enumeration of the last key pressed, XboxKeys.NULL = none.
	 * @return XboxKeys enumeration of the last key pressed
	 */
	public XboxKeys getLastKey() {
		return lastPressed;
	}
	
	/**
	 * Clears the lastkey status
	 */
	public void clearLastKey() {
		lastPressed = XboxKeys.NULL;
	}

	/**
	 * Get the current status of a digital key. Analog keys will return false.
	 * @param key XboxKeys enumerated key
	 * @return Whether or not the key is pressed
	 */
	public boolean isDigitalKeyPressed(XboxKeys key) {
		return controllerDigitalKeyStatus.containsKey(key) ? controllerDigitalKeyStatus.get(key) : false;
	}
	
	/**
	 * Gets the value of the analog key. Digital keys will return Double.MIN_VALUE.
	 * @param key
	 * @return Value of the analog key input
	 */
	public Double getAnalogKeyValue(XboxKeys key) {
		return controllerAnalogKeyStatus.containsKey(key) ? controllerAnalogKeyStatus.get(key) : Double.MIN_VALUE;
	}

	private void updateAnalogKeyStatus (XboxKeys keyPressed, boolean pressed, Double value) {
		if(pressed) {
			controllerAnalogKeyStatus.put(keyPressed, value);
		}
		else {
			controllerAnalogKeyStatus.remove(keyPressed);
		}
	}

	private void updateDigitalKeyStatus (XboxKeys keyPressed, boolean pressed) {
		controllerDigitalKeyStatus.put(keyPressed, pressed ? true : false);
		lastPressed = keyPressed;
		//System.out.println(keyPressed.toString() + " " + (pressed ? "Pressed" : "Released") + "\nLast key pressed: " + keyPressed.toString());
	}
}
