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

	LevelPanelComponent myLevel;

	public LevelPanelRightClickMenu(LevelPanelComponent level) {
		myLevel = level;
		initialize();

	}

	private void initialize() {
		JMenuItem deleteItem = new JMenuItem("Delete level");
		deleteItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int delete;

				delete = JOptionPane.showConfirmDialog(
						null,
						"Are you sure you want to delete the Level: "
								+ myLevel.getName() + "?",
						"This is not reversible", JOptionPane.YES_NO_OPTION);
				if (delete == 0) { //yes option selected
					myLevel.getLevelPanel().deleteLevel(myLevel);
				}
			}
		});
		add(deleteItem);

		setSize(150, 100);
		Point myMouseLocation = MouseInfo.getPointerInfo().getLocation();
		// setLocation(myMouseLocation.x, myMouseLocation.y);
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		show(myLevel, myMouseLocation.x, myMouseLocation.y);
	}

}
