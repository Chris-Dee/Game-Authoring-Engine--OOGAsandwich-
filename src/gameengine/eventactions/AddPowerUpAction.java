package gameengine.eventactions;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.management.ReflectionException;

import util.tuples.Tuple;
import gameengine.GameObject;
import gameengine.gameevents.ParameterDesc;
import gameengine.powerups.GunPowerUp;
import gameengine.powerups.PowerUp;
import gameplayer.GameEventManager;
import gameplayer.GamePlayerEngine;

public class AddPowerUpAction extends GameEventAction {

	@ParameterDesc(name = "Object to Give Power", description = "The Object's Collision ID", type = Integer.class)
	protected int objectToGivePower;
	@ParameterDesc(name = "Object it collides with", description = "The Object's Collision ID", type = Integer.class)
	protected int objectCollidedColid;
	@ParameterDesc(name = "powerUpName", description = "GunPowerUp InvincibilityPowerUp SpeedPowerUp SizePowerUp", type = String.class)
	protected String powerUpName;
	@ParameterDesc(name = "Number of uses left", description = "How many times can be used", type = Integer.class)
	protected int usesLeft;
	@ParameterDesc(name = "Change Player Image To", description = "mario mobile luigi", type = String.class)
	protected String imageName;

	private PowerUp newPowerUp;

	public AddPowerUpAction() {
		super();
	}

	public AddPowerUpAction(List<String> arguments)
			throws InvalidEventActionException, NumberFormatException {
		super(arguments);
		if (arguments.size() < 4) {
			throw new InvalidEventActionException("Invalid Parameters");
		}
		objectToGivePower = Integer.parseInt(arguments.get(0));
		objectCollidedColid = Integer.parseInt(arguments.get(1));
		powerUpName = arguments.get(2);
		usesLeft = Integer.parseInt(arguments.get(3));
		imageName = arguments.get(4);
		try {
			Class<?> c = Class.forName("gameengine.powerups." + powerUpName);
			Constructor<?> ctor = c.getConstructor(int.class, String.class);
			Object powerObject = ctor.newInstance(usesLeft, "mario");
			newPowerUp = (PowerUp) powerObject;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void act(GameEventManager manager, GamePlayerEngine gamePlayer) {
		Tuple<Integer> collisionRelation = new Tuple<Integer>(
				objectToGivePower, objectCollidedColid);
		collisionRelation.sort();
		if (gamePlayer.getCurrentCollisions().containsKey(collisionRelation)) {
			for (Tuple<GameObject> objects : gamePlayer.getCurrentCollisions()
					.get(collisionRelation)) {
				if (objects.x.colid == objectToGivePower) {
					objects.x.addPowerUp(newPowerUp);
					objects.x.myItems.get(objects.x.myItems.size() - 1)
							.useItem(objects.x);
				}
			}
		}
	}
}
