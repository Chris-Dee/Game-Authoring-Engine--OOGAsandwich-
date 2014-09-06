package gameauthoringenvironment.leveleditor.imageactionlisteners;

import gameauthoringenvironment.leveleditor.LevelEditor;
import gameauthoringenvironment.leveleditor.ObjectToolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import tempresources.FileDeleter;

public class ImageSelectionMouseAdapter extends MouseAdapter {

	private static final int RIGHT_CLICK = 3;
	private static final int LEFT_CLICK = 1;

	private LevelEditor myLevelEditor;
	private ObjectToolbar myObjectToolbar;
	private String imageName;

	/**
	 * Mouse Listener to change the image of the object on the screen to the
	 * clicked image. Also allows user to remove images from bar with a right
	 * click.
	 * 
	 * @param levelEditor
	 * 			LevelEditor of the component.
	 * @param objectToolbar
	 * 			ObjectToolbar where the images are held.
	 * @param image
	 * 			Image of the icon selected.
	 */
	public ImageSelectionMouseAdapter(LevelEditor levelEditor,
			ObjectToolbar objectToolbar, String image) {
		myLevelEditor = levelEditor;
		myObjectToolbar = objectToolbar;
		imageName = image;
	}

	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
		if (e.getButton() == LEFT_CLICK) {
			myLevelEditor.getMover().changeImage(imageName);
			myLevelEditor.setStats(imageName);
		}
		if (e.getButton() == RIGHT_CLICK) {
			new FileDeleter().removeFromImageBar(imageName);
			myObjectToolbar.removeFromImageMap(imageName);
			myObjectToolbar.redrawBar();
		}
	};

}
