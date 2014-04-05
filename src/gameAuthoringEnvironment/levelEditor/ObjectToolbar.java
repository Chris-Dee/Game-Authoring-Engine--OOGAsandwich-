package gameAuthoringEnvironment.levelEditor;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

@SuppressWarnings("serial")
public class ObjectToolbar extends JPanel {

	private static final String RESOURCE_PATH = "src/gameAuthoringEnvironment/levelEditor/Resources/";

	public ObjectToolbar() {
		initializeToolbar();
	}

	private void initializeToolbar() {
		JToolBar toolbar = new JToolBar();
		toolbar.setOrientation(1);
		
		String[] images = {"blockobject.jpg", "goombaobject.png", "lemonobject.jpg", "limeobject.gif", "marioobject.jpg", "orangeobject.jpg", "pacmanobject.jpg", "treeobject.jpg"};

		for(int i = 0; i < images.length; i++) {
			addButtonImage(new JButton(), images[i], toolbar);
		}
		add(toolbar);
	}

	@SuppressWarnings("deprecation")
	private void addButtonImage(JButton button, String fileName, JToolBar toolbar) {
		File imageCheck = new File(RESOURCE_PATH + fileName);
		if (imageCheck.exists()) {
			try {
				URL imageURL = imageCheck.toURL();
				try {
					Image img = ImageIO.read(imageURL);
					button.setIcon(new ImageIcon(img));
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		button.setBorderPainted(false);  
        button.setFocusPainted(false);  
        button.setContentAreaFilled(false);  
        
        
        
        toolbar.add(button);
	}

}
