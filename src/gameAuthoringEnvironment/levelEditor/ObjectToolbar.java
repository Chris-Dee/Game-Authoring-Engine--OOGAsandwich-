package gameauthoringenvironment.leveleditor;

import gameauthoringenvironment.leveleditor.imageactionlisteners.ImageSelectionMouseAdapter;
import gameauthoringenvironment.leveleditor.imageactionlisteners.ImageUploadActionListener;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.ScrollPane;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

@SuppressWarnings("serial")
public class ObjectToolbar extends JPanel {
	private static final int SCROLLER_PREFERRED_SIZE_X = 50;
	private static final int SCROLLER_SIZE_Y = 640;
	private static final int SCROLLER_SIZE_X = 35;
	private static final int VERTICAL_ORIENTATION = 1;
	private static final Dimension UPLOAD_BUTTON_SIZE = new Dimension(10, 20);
	private static final Color BUTTON_BACKGROUND_COLOR = new Color(238, 238,
			238);
	private static final String BUTTON_UPLOAD_ICON_PATH = "src/projectresources/Button-Upload-icon.png";
	private static final String IMAGE_RESOURCES = "initobject";
	private static final String GAME_AUTHORING_ENVIRONMENT_RESOURCE_PACKAGE_2 = "gameAuthoringEnvironment.levelEditor.Resources.";
	private JButton uploadButton;
	private JToolBar toolbar;
	private ResourceBundle myResourcesForInitObject;
	private LevelEditor myLevelEditor;

	private Map<String, String> imageMap = new HashMap<String, String>();

	/**
	 * Toolbar that displays all available object images. Click on each image in
	 * order to make it the currently selected image.
	 * 
	 * @param level
	 *            Level being edited
	 */
	public ObjectToolbar(LevelEditor level) {
		myLevelEditor = level;
		myResourcesForInitObject = ResourceBundle
				.getBundle(GAME_AUTHORING_ENVIRONMENT_RESOURCE_PACKAGE_2
						+ IMAGE_RESOURCES);
		fillImageMap();
		initializeToolbar();
	}

	/**
	 * Redraws the toolbar so the new images and pictures are put on the toolbar
	 * correctly.
	 */

	public void redrawBar() {
		remove(toolbar);
		initializeToolbar();
		revalidate();
		repaint();
	}

	/**
	 * Add image to the map of image names to pictures
	 * 
	 * @param imageName
	 *            Name of new image
	 * @param currentUpload
	 *            Path of uploaded image to the correct file
	 */

	public void addToImageMap(String imageName, String currentUpload) {
		imageMap.put(imageName, currentUpload);
	}

	/**
	 * Removes given string from the map of images so they no longer appear on
	 * the image bar.
	 * 
	 * @param imageName
	 *            Image to be removed
	 */

	public void removeFromImageMap(String imageName) {
		imageMap.remove(imageName);
	}

	private void fillImageMap() {
		for (String str : myResourcesForInitObject.keySet()) {
			imageMap.put(str, myResourcesForInitObject.getString(str));
		}
	}

	private void makeUploadButton(JPanel homePanel) {
		uploadButton = new JButton("");
		uploadButton.setPreferredSize(UPLOAD_BUTTON_SIZE);
		uploadButton.addActionListener(new ImageUploadActionListener(
				uploadButton, myLevelEditor, this));
		try {
			uploadButton.setIcon(new ImageIcon(ImageIO.read(new File(
					BUTTON_UPLOAD_ICON_PATH))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		homePanel.add(uploadButton);
	}

	private void initializeToolbar() {
		toolbar = new JToolBar();
		toolbar.setOrientation(VERTICAL_ORIENTATION);
		toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.Y_AXIS));
		ScrollPane scroller = new ScrollPane();
		scroller.setSize(SCROLLER_SIZE_X, SCROLLER_SIZE_Y);
		scroller.setPreferredSize(new Dimension(SCROLLER_PREFERRED_SIZE_X,
				SCROLLER_SIZE_Y));
		toolbar.add(scroller);
		JPanel verticalPanel = new JPanel();
		scroller.add(verticalPanel);
		verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));
		this.setFocusable(false);
		makeUploadButton(verticalPanel);

		for (String s : imageMap.keySet()) {
			JButton button = new JButton();
			button.setBackground(BUTTON_BACKGROUND_COLOR);
			button.setBorder(BorderFactory.createEmptyBorder());
			addButtonImage(button, s, verticalPanel);
		}
		add(toolbar);
	}

	@SuppressWarnings("deprecation")
	private void addButtonImage(JButton button, String imageName, JPanel toolbar) {
		File imageCheck = new File(imageMap.get(imageName));
		button.setFocusable(false);
		if (imageCheck.exists()) {
			try {
				URL imageURL = imageCheck.toURI().toURL();
				Image img = ImageIO.read(imageURL);
				button.setIcon(new ImageIcon(img));
				button.addMouseListener(new ImageSelectionMouseAdapter(myLevelEditor,this,imageName));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		toolbar.add(button);
	}

}
