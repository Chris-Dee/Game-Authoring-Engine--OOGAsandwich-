package gameauthoringenvironment.leveleditor.imageactionlisteners;

import gameauthoringenvironment.leveleditor.LevelEditor;
import gameauthoringenvironment.leveleditor.ObjectToolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import tempresources.ImageCopyUtil;

public class ImageUploadActionListener implements ActionListener {
	private static final String SET_CURRENT_DIRECTORY_CONSTANT = "user.dir";
	private static final String CHECKMARK_IMAGE_FILE_PATH = "src/projectresources/checkmark.jpg";

	private JButton uploadButton;
	private LevelEditor myLevelEditor;
	private ObjectToolbar myObjectToolbar;
	private String currentUpload = "";
	private ImageCopyUtil copier = new ImageCopyUtil(0);
	JPanel home;

	/**
	 * ActionListener that allows the user to upload new images into the images
	 * bar. Any image can be selected to be uploaded.
	 * 
	 * @param upButton
	 * 			Button that is clicked when user wants to upload image.
	 * @param levelEditor
	 * 			LevelEditor of this component.
	 * @param objectToolbar
	 * 			Toolbar that holds the images.
	 */

	public ImageUploadActionListener(JButton upButton, LevelEditor levelEditor,
			ObjectToolbar objectToolbar) {
		uploadButton = upButton;
		myLevelEditor = levelEditor;
		myObjectToolbar = objectToolbar;
	}

	public void actionPerformed(ActionEvent e) {
		if (currentUpload.equals("")) {
			JFileChooser fileChooser = new JFileChooser();
			//fileChooser.setCurrentDirectory(new File(System
				//	.getProperty(SET_CURRENT_DIRECTORY_CONSTANT)));
			int returnVal = fileChooser.showOpenDialog(home);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				String file = fileChooser.getSelectedFile().getAbsolutePath();
				currentUpload = file;
				try {
					uploadButton.setIcon(new ImageIcon(ImageIO.read(new File(
							CHECKMARK_IMAGE_FILE_PATH))));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					// return the file path
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} else {
			String[] imageArray = currentUpload.replace("\\", "/").split("/");
			String imageName = imageArray[imageArray.length - 1];
			try {
				copier.copyImageToResources(currentUpload, imageName);
				myLevelEditor.setDefaults(imageName);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			myObjectToolbar.addToImageMap(imageName, currentUpload);
			// imageMap.put(imageName, currentUpload);
			myObjectToolbar.redrawBar();
			currentUpload = "";
		}

	}
}
