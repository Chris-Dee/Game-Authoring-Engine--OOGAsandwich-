package gameauthoringenvironment.frontend;

import java.awt.Dimension;

import javax.swing.JPanel;

public class PanelFactory {

	/**
	 * Makes a new JPanel offset that will create a space in a swing object.
	 * This is useful for formatting JPanels to the desired size of the creator.
	 * 
	 * @param size
	 *            Width and Height of the space to be made.
	 * @return
	 */
	public static JPanel makeVerticalSpacerPanel(int size) {
		JPanel spacer = new JPanel();
		spacer.setPreferredSize(new Dimension(size, size));
		return spacer;
	}

	/**
	 * Makes a new JPanel offset that will create a space in a swing object.
	 * This is useful for formatting JPanels to the desired size of the creator.
	 * 
	 * @param width
	 * 			Width of the space to be made.
	 * @param height
	 * 			Height of the space to be made.
	 * @return
	 */
	public static JPanel makeVerticalSpacerPanel(int width, int height) {
		JPanel spacer = new JPanel();
		spacer.setPreferredSize(new Dimension(width, height));
		return spacer;
	}
}
