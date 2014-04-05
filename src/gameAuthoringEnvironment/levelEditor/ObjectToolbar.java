package gameAuthoringEnvironment.levelEditor;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

@SuppressWarnings("serial")
public class ObjectToolbar extends JPanel {
	
	public ObjectToolbar() {
		initializeToolbar();
	}
	
	private void initializeToolbar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setOrientation(1);
		JButton exitButton = new JButton("Exit");
		toolbar.add(exitButton);
		JButton testOne = new JButton("Test1");
		toolbar.add(testOne);
		JButton testTwo = new JButton("Test2");
		toolbar.add(testTwo);
		JButton testThree = new JButton("Test3");
		toolbar.add(testThree);
		JButton testFour = new JButton("Test4");
		toolbar.add(testFour);
		JButton testFive = new JButton("Test5");
		toolbar.add(testFive);
		JButton testSix= new JButton("Test6");
		toolbar.add(testSix);
		JButton testSeven = new JButton("Test7");
		toolbar.add(testSeven);
		JButton testEight = new JButton("Test8");
		toolbar.add(testEight);
		JButton testNine = new JButton("Test9");
		toolbar.add(testNine);
		add(toolbar);
	}

}
