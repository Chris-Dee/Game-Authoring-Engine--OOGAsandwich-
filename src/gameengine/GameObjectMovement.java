package gameengine;

import gameplayer.GamePlayerEngine;
import gameplayer.MovementParameters;
import jgame.JGPoint;
import jgame.JGTimer;
import java.lang.reflect.*;
import java.util.List;

public class GameObjectMovement {

	private boolean isStart = true;
	private String behavior;
	private int behaviorSpeed;
	private int behaviorTime;
	private boolean behaviorFlag = false;
	private JGPoint initialPosition;
	private JGTimer currentTimer;
	private GamePlayerEngine engine;

	public GameObjectMovement() {

	}

	public GameObjectMovement(String behavior, int time) {
		this.behavior = behavior;
		behaviorTime = time;
		behaviorFlag = true;
		behaviorSpeed = 0;
	}

	public void setInitialPosition(JGPoint pos) {
		initialPosition = pos;
	}

	public GameObjectMovement(MovementParameters myMovement) {
		this.behavior = myMovement.getBehaviorName();
		behaviorSpeed = myMovement.getSpeed();
		behaviorFlag = true;
		behaviorTime = myMovement.getDuration();
	}

	public String getMovementPattern() {
		return behavior;
	}
	
	public void usercontrolled(int jumpspeed, final int speed, final GameObject myObj){
		myObj.setMoveSpeed(speed);
		myObj.setJumpSpeed(jumpspeed/5);
	}

	public void pace(int time, final int speed, final GameObject myObj)
			throws InterruptedException {
		// System.out.println("I am Pacing!!");
		myObj.xspeed = speed;
		final int timer = time;
		currentTimer = new JGTimer(timer, false) {
			public void alarm() {
				myObj.xspeed = -myObj.xspeed;
				if(myObj.xspeed == 0){
					myObj.xspeed = -speed;
				}
			}
		};
	}
	
	public void verticalpace(int time, final int speed, final GameObject myObj) throws InterruptedException{
		System.out.println("vert");
		myObj.setFloat(true);
		myObj.yspeed = speed;
		final int timer = time;
		currentTimer = new JGTimer(timer, false) {
			public void alarm() {
				myObj.yspeed = -myObj.yspeed;
				if(myObj.yspeed == 0){
					myObj.yspeed = -speed;
				}
			}
		};
	}
	
	public void chaseplayerhorizontal(int time, final int speed, final GameObject myObj) throws InterruptedException{
		GamePlayerEngine gpe = (gameplayer.GamePlayerEngine)myObj.eng;
		List<GameObject> golist = gpe.getObjectsByColid(0);
		final GameObject go = golist.get(0);
		final int timer = 2;
		currentTimer = new JGTimer(timer, false) {
			boolean activated = false;
			public void alarm() {
				if(Math.abs((go.x - myObj.x)) < 400){
					activated = true;
				}
				if(go.x >= myObj.x && activated){
					myObj.xspeed = speed/2;
				}
				else if(activated){
					myObj.xspeed = -speed/2;
				}
			}
		};
		
	}
	
	public void chaseplayerflying(int time, final int speed, final GameObject myObj) throws InterruptedException{
		myObj.setFloat(true);
		GamePlayerEngine gpe = (gameplayer.GamePlayerEngine)myObj.eng;
		List<GameObject> golist = gpe.getObjectsByColid(0);
		final GameObject go = golist.get(0);
		final int timer = 2;
		currentTimer = new JGTimer(timer, false) {
			boolean activated = false;
			public void alarm() {
				if(Math.abs((go.x - myObj.x)) < 400 && Math.abs((go.y - myObj.y)) < 400){
					activated = true;
				}
				if(go.x >= myObj.x && activated){
					myObj.xspeed = speed/4;
				}
				else if(activated){
					myObj.xspeed = -speed/4;
				}
				if(go.y >= myObj.y && activated){
					myObj.yspeed = speed/4;
				}
				else if(activated){
					myObj.yspeed = -speed/4;
				}
			}
		};
	}
	
	public void jumping(int time, final int speed, final GameObject myObj) throws InterruptedException{
		myObj.setFloat(false);
		final int timer = time*2;
		currentTimer = new JGTimer(timer, false) {
			public void alarm() {
				if(myObj.yspeed == 0){
					myObj.yspeed = -speed*2;
				}
			}
		};
	}

	private void stationary(int time, int speed, final GameObject myObj) {
		myObj.xspeed = 0;
		myObj.yspeed = 0;
	}

	private void none(int time, int speed, final GameObject myObj) {

	}

	public void start(GameObject obj) {
		// setInitialPosition(new JGPoint((int)obj.x,(int)obj.y));
		isStart = false;
		if (behaviorFlag) {
			doReflect(behavior.toLowerCase(), behaviorTime, behaviorSpeed, obj);
		}
	}

	public boolean getIsStart() {
		return isStart;
	}

	private void doReflect(String behavior, int time, int speed, GameObject obj) {
		Class<?> moveClass = this.getClass();

		Object[] args = new Object[3];
		args[0] = time;
		args[1] = speed;
		args[2] = obj;

		Method[] allMethods = moveClass.getDeclaredMethods();

		for (Method m : allMethods) {
			String mname = m.getName();
			if (mname.startsWith(behavior)) {
				try {
					m.invoke(this, args);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void setSpeed(int speed, GameObject myObj) {
		behaviorSpeed = speed;
		if (initialPosition != null) {
			myObj.x = initialPosition.x;
			myObj.y = initialPosition.y;
		}
		if (currentTimer != null)
			currentTimer.set(behaviorTime, false);
		myObj.xspeed = Math.abs(speed);
	}

	public void setDuration(int duration, GameObject myObj)
			throws InterruptedException {
		behaviorTime = duration * 10;
		if (initialPosition != null) {
			myObj.x = initialPosition.x;
			myObj.y = initialPosition.y;
		}
		myObj.xspeed = Math.abs(myObj.xspeed);
		if (currentTimer != null)
			currentTimer.set(duration * 10, false);
	}
	
}
