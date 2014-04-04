package gameAuthoringEnvironment.levelEditor;

import gameEngine.Level;

import javax.swing.JPanel;

import jgame.JGColor;
import jgame.platform.JGEngine;

public class LevelEditor extends JGEngine {
	LevelMover myMover;
	Level level;
	
	private final String defaultImage="/gameAuthoringEnvironment/levelEditor/Resources/red.gif";
	public LevelEditor(Level l){
		super();
		level=l;
		dbgShowMessagesInPf(false);
		initEngine((int) 600, 600);
		//This just hides the null pointer exception error. If it ends up affecting anything, we can change it.
		
		defineImage("srball","",0,defaultImage,"-");
		myMover=new LevelMover(this);
		defineImage("background1","",0,level.getBackground(),"-");
		setBGImage("background1");
	}
	@Override
	public void initCanvas() {
		setCanvasSettings(1, 1, 300, 300, null, JGColor.white, null);
	}

	@Override
	public void initGame() {
		setFrameRate(250, 3);
		//width in spot 0, height in spot 1
		System.out.println("Width" + level.getLevelSize().x);
		System.out.println("Height" + level.getLevelSize().y);
		setPFSize(level.getLevelSize().x,level.getLevelSize().y);
	}
	private void checkInBounds(){
		if((Double)myMover.x==null)
		System.out.println((Double)myMover.x==null);
		if(myMover.x>=myMover.pfwidth){
			//myMover.x=el.xofs;
			myMover.x=myMover.pfwidth-20;
		}
		if(myMover.y>=myMover.pfheight){
			myMover.y=myMover.pfheight-20;
		}
		if(myMover.x<=0){
			myMover.x=5;
		}
		if(myMover.y<=0){
			myMover.y=5;
		}
		//System.out.println(el.xofs+" "+el.yofs+" "+myMover.x+" "+myMover.y+" "+myMover.pfwidth);
	}
	public void doFrame(){
		checkInBounds();
		moveObjects(null,0);
		setViewOffset((int)myMover.x, (int)myMover.y, true);
	}
	//TODO add method to check for collisions with screen boundary, and decide what to do when screen
	//shrinks and doesn't include mover.
	//TODO push a button to place selected object?
}
