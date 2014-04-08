package gameAuthoringEnvironment.levelEditor;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

@SuppressWarnings("serial")
public class ObjectToolbar extends JPanel {
	private LevelEditor myLevel;
	private static final String RESOURCE_PATH = "src/gameAuthoringEnvironment/levelEditor/";
	//private static final String RESOURCE_PATH = "src/gameAuthoringEnvironment/levelEditor/Resources/";
	private ResourceBundle myResources;
	private static final String IMAGE_RESOURCES = "imagefiles";
	private static final String GAME_AUTHORING_ENVIRONMENT_RESOURCE_PACKAGE = "gameAuthoringEnvironment.levelEditor.Resources.";

	/**
	 * Toolbar that displays all available object images. Click on each image in
	 * order to make it the currently selected image.
	 * 
	 * @param level
	 *            Level being edited
	 */
	public ObjectToolbar(LevelEditor level) {
		myLevel = level;
		myResources = ResourceBundle
				.getBundle(GAME_AUTHORING_ENVIRONMENT_RESOURCE_PACKAGE
						+ IMAGE_RESOURCES);
		initializeToolbar();
	}

	private void initializeToolbar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setOrientation(1);
		this.setFocusable(false);
		
		//String[] images = {"blockobject.jpg", "goombaobject.png", "lemonobject.jpg", "limeobject.gif", "marioobject.jpg", "orangeobject.jpg", "pacmanobject.jpg", "treeobject.jpg"};
		Map<String,String> images=myLevel.getMap();
		for(String s:images.keySet()) {
			addButtonImage(new JButton(), s, toolbar);
//=======
//
//		for (String keyString : myResources.keySet()) {
//			addButtonImage(new JButton(), myResources.getString(keyString),
//					toolbar);
//>>>>>>> bea11868cebb7f44c966ad04be5f11e026813ebc
		}
		add(toolbar);
	}

	@SuppressWarnings("deprecation")

	private void addButtonImage(JButton button, String imageName, JToolBar toolbar) {
		File imageCheck = new File(RESOURCE_PATH+ myLevel.getMap().get(imageName));
		button.setFocusable(false);
		if (imageCheck.exists()) {
			try {
				URL imageURL = imageCheck.toURL();
				try {
					Image img = ImageIO.read(imageURL);
					button.setIcon(new ImageIcon(img));
					createListener(button, imageName);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		toolbar.add(button);
	}


	private void createListener(JButton button, final String imageName) {
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				myLevel.myMover.changeImage(imageName);
			}

		});
	}
}
