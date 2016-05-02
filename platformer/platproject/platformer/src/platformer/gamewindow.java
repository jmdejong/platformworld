package platformer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;


class GameFrame extends JFrame {
	
	private GamePanel panel;
	private Game game;
	
	public GameFrame(Game game, int width, int height){
		
		// ik heb besloten om te stoppen het keyword 'this' overal te gebruiken waar het niet nodig is
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(width,height);
		
		panel = new GamePanel(game);
		panel.setSize(width,height);
		
		add(panel);
		
		this.game = game;
		
		InputController controller = new InputController();
		addKeyListener(controller);
		game.setInputData(controller);
	}
	
	public GameFrame(Game game){
		this(game,1200,720);
	}
	
	private class GamePanel extends JPanel {
		private Game game;
		
		public GamePanel(Game game){
			this.game=game;
			repaint();
			
			Timer timer = new Timer();
			timer.schedule(new TimerTask(){
				public void run(){
					GamePanel.this.game.step();
					GamePanel.this.repaint();
				}
			},0,33);
		}
		
		public void paintComponent(Graphics g){
			
			
			g.clearRect(0,0,getWidth(),getHeight());
			ArrayList<Placable> gameObjects=game.getAllObjects();
			//System.out.println(blocks[0].getMessage());
			
			for (Placable object : gameObjects){
				Sprite sprite = object.getSprite();
				g.setColor(new Color(sprite.getColor()));
				g.fillRect((int)(32*(object.getX()-sprite.getXmin())),(int)(32*(object.getY()-sprite.getYmin())),(int)(32*sprite.getWidth()),(int)(32*sprite.getHeight()));
				//System.out.println("drawing a block");
			}
			g.setColor(Color.BLUE);
			g.fillRect(800,200,150,250);
		}
	}
	
}
