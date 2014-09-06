package gameauthoringenvironment.frontend;

import java.awt.IllegalComponentStateException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

public class LevelPanelRightClickMenu extends JPopupMenu {

	private static final String SPACE_QUESTION_MARK = " ?";
	private static final String THIS_IS_NOT_REVERSIBLE_WARNING = "This is not reversible";
	private static final String ARE_YOU_SURE_YOU_WANT_TO_DELETE_THE_LEVEL = "Are you sure you want to delete the Level: ";
	private static final String DELETE_BUTTON_NAME = "Delete level";
	private static final int WIDTH = 150;
	private static final int HEIGHT = 100;

	private Point myMouseLocation;
	private LevelPanelComponent myLevelComponent;

	/**
	 * PopupMenu for right-clicking a LevelPanelComponent
	 * 
	 * @param levelPanelComponent
	 *            LevelPanelComponent that was right-clicked
	 * @param mouseLoc
	 *            Location of mouse click relative to the LevelPanelComponent
	 */
	public LevelPanelRightClickMenu(LevelPanelComponent levelPanelComponent,
			Point mouseLoc) {
		myLevelComponent = levelPanelComponent;
		myMouseLocation = mouseLoc;
		createPanel();
	}

	private void createPanel() {
		JMenuItem deleteItem = new JMenuItem(DELETE_BUTTON_NAME);
		deleteItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int delete = JOptionPane.showConfirmDialog(null,
						ARE_YOU_SURE_YOU_WANT_TO_DELETE_THE_LEVEL
								+ myLevelComponent.getName()
								+ SPACE_QUESTION_MARK,
						THIS_IS_NOT_REVERSIBLE_WARNING,
						JOptionPane.YES_NO_OPTION);
				if (delete == JFileChooser.APPROVE_OPTION) { // yes option selected
					myLevelComponent.getLevelPanel().deleteLevel(
							myLevelComponent);
				}
			}
		});
		add(deleteItem);
		setSize(WIDTH, HEIGHT);
		show(myLevelComponent, myMouseLocation.x, myMouseLocation.y);
	}
}