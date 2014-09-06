package xboxkeybinder;

import java.util.ArrayList;

import xboxkeybinder.binding_backend.BindingExecutor;
import xboxkeybinder.binding_backend.TabManager;

public class BindingRunner {
	public BindingRunner(String tab){
		BindingExecutor binder = new BindingExecutor("xboxkeybinder.BoundFunctions");

		TabManager newUtility = new TabManager(binder.getUniversalMethods(), binder.getObjectMethods(), tab);
	}
	public static void main(String[] args) {
		BindingExecutor binder = new BindingExecutor("xboxkeybinder.BoundFunctions");
		TabManager newUtility = new TabManager(binder.getUniversalMethods(), binder.getObjectMethods(),"chris");
	}
	
}