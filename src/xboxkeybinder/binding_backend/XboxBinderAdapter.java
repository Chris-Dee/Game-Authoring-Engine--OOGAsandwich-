package xboxkeybinder.binding_backend;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ch.aplu.xboxcontroller.XboxControllerAdapter;
import ch.aplu.xboxcontroller.XboxControllerListener;

public class XboxBinderAdapter extends XboxControllerAdapter {
	private String lastHitKey;
	private double lastMagnitude=0;
	private double lastDirection=0;
	private String lastDirPress;
	private String lastMagPress;
	public static final int keyOffset=500;
	private Map<String,Integer> dirMap=new HashMap<String,Integer>();
	private Map<String,Double> magMap=new HashMap<String,Double>();
	KeybindUtility keys; 
	List<String> xboxKeyNumbers;
	String[] names=new String[]{"A","B","X","Y","St","Bk","RT","LT","D-Pad","Left Stick","Right Stick","LB","RB"};

	public XboxBinderAdapter(){
		xboxKeyNumbers = new ArrayList<String>();
		fillKeyNums();
		//System.out.println(xboxKeyNumbers);
		//System.out.println("d");
	}
	public void fillKeyNums(){
		for(int i=0;i<names.length;i++){
			xboxKeyNumbers.add(names[i]);
		}
	}
	public void setKeys(KeybindUtility k) {
		keys=k;
	}

	public void buttonA(boolean pressed)
	{
		bindKeys("A");
	}

	public void buttonB(boolean pressed)
	{
		bindKeys("B");
	}

	public void buttonX(boolean pressed)
	{
		bindKeys("X");
	}

	public void buttonY(boolean pressed)
	{
		bindKeys("Y");
	}

	public void back(boolean pressed)
	{
		bindKeys("Bk");
	}

	public void start(boolean pressed)
	{
		bindKeys("St");
	}

	public void leftShoulder(boolean pressed)
	{
		bindKeys("LB");
	}

	public void rightShoulder(boolean pressed)
	{
		bindKeys("RB");
	}

	public void leftThumb(boolean pressed)
	{
		bindKeys("Left Stick");
	}

	public void rightThumb(boolean pressed)
	{
		bindKeys("Right Stick");
	}

	public void dpad(int direction, boolean pressed)
	{
		bindKeys("D-Pad");
		dirMap.put("D-Pad",direction*45);
		lastDirPress="D-Pad";
	}

	public void leftTrigger(double value)
	{
		bindKeys("LT");
	}

	public void rightTrigger(double value)
	{	
		bindKeys("RT");
	}

	public void leftThumbMagnitude(double magnitude)
	{
		magMap.put("Left Stick",magnitude);
		lastHitKey="Left Stick";
		lastDirPress="Left Stick";
		lastMagPress="Left Stick";
	}

	public void leftThumbDirection(double direction)
	{
		dirMap.put("Left Stick", (int)direction);
		lastHitKey="Left Stick";
		lastDirPress="Left Stick";
		lastMagPress="Left Stick";
	}

	public void rightThumbMagnitude(double magnitude)
	{
		magMap.put("Right Stick", magnitude);
		lastHitKey="Right Stick";
		lastDirPress="Right Stick";
		lastMagPress="Right Stick";
	}

	public void rightThumbDirection(double direction)
	{
		dirMap.put("Right Stick", (int)direction);
		lastHitKey="Right Stick";
		lastDirPress="Right Stick";
		lastMagPress="Right Stick";
	}

	public void isConnected(boolean connected)
	{

	}
	public void bindKeys(String keyID){
		//System.out.println(lastHitKey);
		//System.out.println(lastMagnitude);
		//System.out.println(lastDirection);
		lastHitKey=keyID;
		if(keys!=null)
		keys.setXboxBinding(keys.isBoundAlready(xboxKeyNumbers.indexOf(keyID)+keyOffset), xboxKeyNumbers.indexOf(keyID)+keyOffset, keys.buttonToCommandMap.get(keys.findFocused()));
	}
	public Integer getLastButton(){
		return xboxKeyNumbers.indexOf(lastHitKey)+keyOffset;
	}
	public Integer getLastDir(){
		return dirMap.get(lastDirPress);
	}
	public double getLastMag(){
		return magMap.get(lastMagPress);
	}
	public List<String> getKeyList(){
		return xboxKeyNumbers;
	}
	public void clearLastHit(){
		//lastDirection=0;
		if(!lastHitKey.equals("Right Stick")&&!lastHitKey.equals("Left Stick")&&!lastHitKey.equals("D-Pad"))
		lastHitKey="";
		//lastMagnitude=0;
	}
}
