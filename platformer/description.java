
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Description extends JPanel {
	
	
	public Description() {
		
		// Set color of menu panel background
		setBackground(Color.BLACK);
		
		// Make a box layout of vertical buttons, rigid area dimensions determine it's location on the y-axis
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(Box.createRigidArea(new Dimension(0, 200)));
		
		
		JLabel text = new JLabel("<html>This is a parcours game.<br /><br />The player can be controlled with the arrow keys"+
			" or with 'a', 'w' and 'd'.<br />'f' is used to interact with items.<br />The goal is to reach the end"+
			" (the red rectangle) without dying.<br />To be able to reach this, you first need the key"+
			" to open the door.<br /><br /></html>"
		);
		add(text);
		
	}
	
}