package gameauthoringenvironment.levelstatseditor;

import gameauthoringenvironment.frontend.LevelPanel;
import gameauthoringenvironment.frontend.PanelFactory;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import tempresources.FileDeleter;
import tempresources.ImageCopyUtil;

//Adding new images is having problems because JGame is being a little bitch.

public class BackgroundChooser extends JPanel {
	private static final String DELETE_CURRENT_IMAGE_BUTTON_TEXT = "Delete Current Image";
	private static final String ADD_IMAGE_BUTTON_TEXT = "Add Image";
	private static final String UPLOAD_BUTTON_TEXT = "upload";
	private static final String BG_NAME_LABEL = "BG name";
	private static final String ADD_NEW_BACKGROUND_IMAGE_LABEL = "Add New Background Image";
	LevelPanel myLevelPanel;
	private final String delimiter = " ";
	private final String initialSelection = "Rock";
	private final String defaultFile = "src/gameAuthoringEnvironment/levelStatsEditor/Resources/InitBG";
	Map<String, String> myBackgroundMap = new TreeMap<String, String>();
	private String uploadedFile;
	private JTextField bgName;
	// TODO we may want to not amek this static. Then again...there will only
	// ever be one
	private static JComboBox model;

	/**
	 * ComboBox to choose backgrounds. Backgrounds and names are stored in
	 * LevelPanelComponents. Can update this slightly when level objects are
	 * introduced. On a high level, it uses Map to store names to show up in
	 * combo box vs. filepaths. It uses one static method to update the state of
	 * the ComboBox dynamically.
	 * 
	 * @param levelPanel
	 * 				LevelPanel object that the new background is assigned to
	 * @throws FileNotFoundException
	 */
	public BackgroundChooser(LevelPanel levelPanel) throws FileNotFoundException {
		super();
		myLevelPanel = levelPanel;
		myBackgroundMap = initializeImageMap(new File(defaultFile));
		makeBackgroundChooser(this);
		makeFileChooserButton(this);
	}

	private Map<String, String> initializeImageMap(File file)
			throws FileNotFoundException {
		Map<String, String> backgroundMap = new TreeMap<String, String>();
		Scanner s = new Scanner(file);
		while (s.hasNext()) {
			String[] str = s.nextLine().split(" ");
			if(str.length>=2)
			backgroundMap.put(str[0], str[1]);
		}
		return backgroundMap;
	}

	public void makeBackgroundChooser(JPanel homePanel) {
		homePanel.setLayout(new BoxLayout(homePanel, BoxLayout.Y_AXIS));
		model = new JComboBox(myBackgroundMap.keySet().toArray());
		model.setEnabled(false);
		setSelectionToDefault();
		model.addItemListener(new ItemChangeListener());
		homePanel.add(new JLabel("Select Background Image"));
		homePanel.add(model);
		homePanel.add(PanelFactory.makeVerticalSpacerPanel(15));
		// TODO disable model with nothing selected to avoid errors.
		// model.setEnabled(false);
	}
	
	/**
	 * Enable the model
	 */

	public void enableModel() {
		model.setEnabled(true);
	}
	
	/**
	 * Set the selected item to the default background
	 */

	public void setSelectionToDefault() {
		model.setSelectedItem(initialSelection);
	}

	private void makeFileChooserButton(JPanel homePanel) {
		JPanel editorPanel = new JPanel();
		homePanel.add(new JLabel(ADD_NEW_BACKGROUND_IMAGE_LABEL));
		editorPanel.setLayout(new BoxLayout(editorPanel, BoxLayout.X_AXIS));
		editorPanel.add(new JLabel(BG_NAME_LABEL));
		bgName = new JTextField(0);
		editorPanel.add(bgName);
		homePanel.add(editorPanel);
		JButton uploadImage = new JButton(UPLOAD_BUTTON_TEXT);
		uploadImage.addActionListener(new FileChoose(this));
		editorPanel.add(uploadImage);
		JPanel buttonPanel=new JPanel();
		JButton addImage = new JButton(ADD_IMAGE_BUTTON_TEXT);
		buttonPanel.add(addImage);
		addImage.addActionListener(new AddImage(this));
		JButton deleteImage=new JButton(DELETE_CURRENT_IMAGE_BUTTON_TEXT);
		deleteImage.addActionListener(new DeleteImage());
		buttonPanel.add(deleteImage);
		homePanel.add(buttonPanel);
	}

	private void setBackgroundToSelected() {
		String newBG = myBackgroundMap.get(model.getSelectedItem());
		myLevelPanel.findActivePanel().changeDefaultBackground((String) model.getSelectedItem());
	}
	
	/**
	 * Sets the selected background to the background in the currently active LevelPanelComponent
	 * @param backgroundName
	 * 					Name of the background
	 */

	public static void setSelectedToBackground(String backgroundName) {
		model.setSelectedItem(backgroundName);
	}
	public static void toggleChooserEnable(boolean b){
		model.setEnabled(b);
	}
	private class FileChoose implements ActionListener {
		private static final String DEFAULT_USER_DIRECTORY = "user.dir";
		JPanel home;

		public FileChoose(JPanel homePanel) {
			home = homePanel;
		}

		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			//fileChooser.setCurrentDirectory(new File(System.getProperty(DEFAULT_USER_DIRECTORY)));
			int returnVal = fileChooser.showOpenDialog(home);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				String file = fileChooser.getSelectedFile().getAbsolutePath();
				uploadedFile = file;
				try {
					// return the file path
				} catch (Exception ex) {
					// TODO deal with exception
				}
			}
		}
	}
	public void changeEnabled(boolean b){
		model.setEnabled(b);
	}
	private class AddImage implements ActionListener {
		JPanel home;

		public AddImage(JPanel homePanel) {
			home = homePanel;
		}

		public void actionPerformed(ActionEvent e) {
			model.addItem(bgName.getText());
			ImageCopyUtil imageCopier=new ImageCopyUtil();
			try {
				imageCopier.copyImageToResources(uploadedFile, bgName.getText());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			myBackgroundMap.put(bgName.getText(), uploadedFile);
			//System.out.println(myBackgroundMap);
			
		}

	}
	private class DeleteImage implements ActionListener{
		public void actionPerformed(ActionEvent e){
			new FileDeleter().unwriteFromBGinit((String)model.getSelectedItem());
			model.removeItem(model.getSelectedItem());
		}
	}

	class ItemChangeListener implements ItemListener {
		public void itemStateChanged(ItemEvent event) {
			if (event.getStateChange() == ItemEvent.SELECTED) {
				setBackgroundToSelected();
				// do something with object
			}
		}
	}
}
