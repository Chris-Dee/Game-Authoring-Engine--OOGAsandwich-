package util.keybinder;

import java.util.ArrayList;

public class BindingRunner {
public BindingRunner(String tab){
	BindingExecutor binder = new BindingExecutor("util.keybinder.BoundFunctions", "gameplayer.GamePlayerEngine");

	TabManager newUtility = new TabManager(binder.getUniversalMethods(), binder.getObjectMethods(), tab);
}
	public static void main(String[] args) {

		BindingExecutor binder = new BindingExecutor("util.keybinder.BoundFunctions", "gameplayer.GamePlayerEngine");

		TabManager newUtility = new TabManager(binder.getUniversalMethods(), binder.getObjectMethods(), null);
	}
	
}