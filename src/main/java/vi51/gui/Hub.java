package vi51.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import vi51.environment.Environment;

//The hub is a JPanel in the right of the frame where we put all informations related to the world
public class Hub extends JPanel{
	private static final long serialVersionUID = 1L;
	Environment environment;
	JLabel nbUnit;//example to know the number of unit on the map
	JButton newSpawnA;
	JButton newSpawnB;
	JButton newSpawnC;
	JPanel container;
	public Hub (Environment environment) {//World TBD
		super();
		this.environment = environment;
		container = new JPanel();
		nbUnit = new JLabel ("pop : 0");
		newSpawnA = new SpawnButtonA("Spawn Visitor",environment);
		newSpawnB = new SpawnButtonB("Spawn Bodyguard",environment);
		newSpawnC = new SpawnButtonC("Spawn Zombie", environment);
		
		nbUnit.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
		container.setBackground(Color.LIGHT_GRAY);
		container.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
		container.setLayout(new FlowLayout());
		this.add(nbUnit);
		this.add(newSpawnA);
		this.add(newSpawnB);
		this.add(newSpawnC);
		this.add(container);
		this.setLayout(new GridLayout(6,1));
		this.setBackground(Color.LIGHT_GRAY);
		this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		this.getPreferredSize();
	}
	
	//This method permit to update the informations displayed in the hub
	public void updateLabels() {
		nbUnit.setText("pop. : "+this.nbUnitDisplayed());
	}

	private int nbUnitDisplayed() {
		int nb=0;
		nb = environment.getWorld().getBodyCount();
		
		return nb;
	}
	@Override
	public Dimension getPreferredSize() {
	    return new Dimension(150, 600);
	}
}
