package gameAuthoringEnvironment.frontEnd;

import java.awt.Dimension;

import javax.swing.JPanel;

public class PanelFactory {
//Add any more types of panels that are used frequently in making this.
	public static JPanel makeVerticalSpacerPanel(int height){
		JPanel spacer=new JPanel();
		spacer.setPreferredSize(new Dimension(height,height));
		return spacer;
	}
	//can add more if any are repeated a lot
}
