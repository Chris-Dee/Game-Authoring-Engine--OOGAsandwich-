package xboxkeybinder.binding_backend;

import ch.aplu.xboxcontroller.XboxController;
import jgame.JGColor;
import jgame.platform.JGEngine;

public class Tester extends JGEngine {
private BindingExecutor binder=new BindingExecutor("xboxkeybinder.BoundFunctions");
private XboxController xc=new XboxController();
private XboxBinderAdapter myAdapter;
	public Tester(){
		initEngine(640,640);
	}
	@Override
	public void initCanvas() {
		setCanvasSettings(1,1,40,40,JGColor.white,JGColor.white,null);
		
	}

	@Override
	public void initGame() {
		//myAdapter=new XboxAdapter();
		//xc.addXboxControllerListener(myAdapter);
		xc.setLeftThumbDeadZone(0.4);
		xc.setRightThumbDeadZone(0.4);
		
	}
	public void doFrame(){
	//System.out.println(myAdapter.getLastButton());
		//binder.executeXboxInput();
		binder.executeInput(this);
		
	}
	public static void main(String[] args){
		Tester x=new Tester();
		
	}
}
