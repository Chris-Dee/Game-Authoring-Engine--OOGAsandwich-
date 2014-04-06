package gameAuthoringEnvironment.frontEnd;

import java.awt.IllegalComponentStateException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

public class LevelPanelRightClickMenu extends JPopupMenu {

	private final static int WIDTH = 150;
	private final static int HEIGHT = 100;
	private Point myMouseLocation;
	LevelPanelComponent myLevelComponenet;

	/**
	 * PopupMenu for right-clicking a LevelPanelComponent
	 * 
	 * @param levelPanelComponent
	 * 			LevelPanelComponent that was right-clicked
	 * @param mouseLoc
	 * 			Location of mouse click
	 */
	public LevelPanelRightClickMenu(LevelPanelComponent levelPanelComponent, Point mouseLoc) {
		myLevelComponenet = levelPanelComponent;
		myMouseLocation = mouseLoc;
		createPanel();
	}

	private void createPanel() {
		JMenuItem deleteItem = new JMenuItem("Delete level");
		deleteItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int delete;

				delete = JOptionPane.showConfirmDialog(
						null,
						"Are you sure you want to delete the Level: "
								+ myLevelComponenet.getName() + " ?",
						"This is not reversible", JOptionPane.YES_NO_OPTION);
				if (delete == 0) { //yes option selected
					myLevelComponenet.getLevelPanel().deleteLevel(myLevelComponenet);
				}
			}
		});
		add(deleteItem);

		setSize(WIDTH,HEIGHT);
		
		show(myLevelComponenet, myMouseLocation.x, myMouseLocation.y);
	}

}
