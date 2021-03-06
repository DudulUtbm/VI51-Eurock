package vi51.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import vi51.environment.AgentBody;
import vi51.environment.Environment;
import vi51.util.ConstantContainer;
import vi51.util.Semantic;

public class Window extends JFrame {

	private static final long serialVersionUID = 1L;

	Environment env;

	Thread tempo; //pour la synchrinisation de l'affichage

	Hub displayer; // pour mettre des boutons etc.
	EventLog log; /*voir si on met une console 
					pour comprendre ce qu'il se passe dans l'affichage directement*/
	Background bg;//background de la map
	JLayeredPane jlp;
	private Map<UUID,Unit> units = new TreeMap<>();
	public Window(Environment environment){//World TBD

		env = environment;
		// Creation of the JPanel and his

		// Create the HUB
		displayer = new Hub(environment);

		//Create the log area
		log = new EventLog (environment);

		
		// background image
		bg = new Background();
		bg.setSize(ConstantContainer.BG_SIZE);
		bg.setOpaque(false);
		bg.getPreferredSize();
		
		UnitPanel unitPanel = new UnitPanel();
		unitPanel.setSize(ConstantContainer.BG_SIZE);
		unitPanel.setOpaque(false);
		unitPanel.getPreferredSize();

		//JLayeredPane : permet de représenter nos objets sur différentes couches
		jlp = new JLayeredPane();
		jlp.setPreferredSize(ConstantContainer.BG_SIZE);
		jlp.setOpaque(false);
		// Add the images to the JLayeredPane with a different deep level
		jlp.add(bg, new Integer(1));//plus integer est grand grand, plus c'est avancé
		jlp.add(unitPanel, new Integer(2));
		
		//JscrollPane
		JScrollPane scrollPane = new JScrollPane(jlp);
		scrollPane.setPreferredSize(ConstantContainer.VIEWPORT_SIZE);
		this.setTitle("RTS Game Engine");
		this.setResizable(true);
		//this.setLocation
		//this.setLocationRelativeTo(null); // JFrame in the center of the window
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add the JLayeredPane to the frame
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(displayer, BorderLayout.EAST);
		this.add(log, BorderLayout.SOUTH);
		for (AgentBody body : env.getWorld().getAgentBodies())
		{
			if (body != null)
				units.put(body.getId(),new Unit(body));
		}

		pack();
		this.setVisible(true);
	}
	
	public void updateWindow(){
//		long t1 = System.nanoTime();
		synchronized(getTreeLock()) {
			units = new TreeMap<>();
			
			for (AgentBody body : env.getWorld().getAgentBodies())
			{
				assert (body != null);
				if (!units.containsKey(body.getId()))
					units.put(body.getId(),new Unit(body));
				else
					units.get(body.getId()).setPosition(body.getPosition());
			}
			displayer.updateLabels();
//			for (EnvironmentEvent events : env.getEventFire()){
//				this.log.updateLog("New spawn event.");
//			}
		}
//		long t2 = System.nanoTime();
//		System.out.println("Time to draw GUI : " + (t2-t1));
		repaint();
	}
	private class UnitPanel extends JPanel {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		protected BufferedImage image1;
		protected BufferedImage image2;
		protected BufferedImage image3;
		
		public UnitPanel (){
			super();
			// Resources dans AFC vmutils
			URL url;
			try {
				url = getClass().getClassLoader().getResource("images/unit.png");
				assert (url != null);
				image1 = ImageIO.read(url);
				BufferedImage newImage = new BufferedImage(image1.getWidth(), image1.getHeight(), BufferedImage.TYPE_INT_ARGB);
		    	Graphics2D g = newImage.createGraphics();
		    	g.drawImage(image1, 0, 0, image1.getWidth(), image1.getHeight(), null);
		    	g.dispose();
		    	image1=newImage;
				url = getClass().getClassLoader().getResource("images/unit2.png");
				assert (url != null);
				image2 = ImageIO.read(url);
				newImage = new BufferedImage(image2.getWidth(), image2.getHeight(), BufferedImage.TYPE_INT_ARGB);
		    	g = newImage.createGraphics();
		    	g.drawImage(image2, 0, 0, image2.getWidth(), image2.getHeight(), null);
		    	g.dispose();
		    	image2=newImage;
				url = getClass().getClassLoader().getResource("images/unit3.png");
				assert (url != null);
				image3 = ImageIO.read(url);
				newImage = new BufferedImage(image3.getWidth(), image3.getHeight(), BufferedImage.TYPE_INT_ARGB);
		    	g = newImage.createGraphics();
		    	g.drawImage(image3, 0, 0, image3.getWidth(), image3.getHeight(), null);
		    	g.dispose();
		    	image3=newImage;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		@Override
		public void paint(Graphics g) {
			for (Unit unit : Window.this.units.values()) {
				if (unit.type.equals(Semantic.BodyGuard))
					unit.paint(g,image1, this);
				else if(unit.type.equals(Semantic.Visitor))
					unit.paint(g,image2, this);
				else if(unit.type.equals(Semantic.Zombie))
					unit.paint(g,image3, this);
			}
		}
		
	}
	@Override
	public Dimension getPreferredSize() {
	    return new Dimension(800, 600);
	}
}
