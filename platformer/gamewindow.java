import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class GameFrame extends JFrame {
	
	private JPanel gamePanel, menuPanel, Description;
	private CardLayout cardLayout = new CardLayout();
	// A seperate JPanel that holds the game and menu cards
	private JPanel cardContainer; 
	private Game game;
	
	public GameFrame(Game game, int width, int height){
		int w = 300, h = 75;
		JTextField title = new JTextField("Platformer Parcours");
		setTitle(title.getText()); 
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setSize(width,height);
		
		gamePanel = new GamePanel(game);
		gamePanel.setSize(width,height);
		
		menuPanel = new MenuPanel();
		
		JPanel description = new Description();
		
		
		
		
		JButton startGame = new JButton("Start Game");
		startGame.setAlignmentX( Component.CENTER_ALIGNMENT );
		startGame.setMaximumSize( new Dimension(w, h) );
		startGame.setBackground(Color.GRAY);
		startGame.setForeground(Color.WHITE);
		startGame.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
		menuPanel.add(startGame);
		startGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				cardLayout.show(cardContainer,"gamepanel");
				GameFrame.this.game.start();
				repaint();
			}
		});
		
		JButton howToPlay = new JButton("How to play");
		howToPlay.setAlignmentX( Component.CENTER_ALIGNMENT );
		howToPlay.setMaximumSize( new Dimension(w, h) );
		howToPlay.setBackground(Color.GRAY);
		howToPlay.setForeground(Color.WHITE);
		howToPlay.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
		menuPanel.add(howToPlay);
		howToPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				cardLayout.show(cardContainer,"description");
			}
		});
		
		JButton quitGame = new JButton("Quit to desktop");
		quitGame.setAlignmentX( Component.CENTER_ALIGNMENT );
		quitGame.setMaximumSize( new Dimension(w, h) );
		quitGame.setBackground(Color.GRAY);
		quitGame.setForeground(Color.WHITE);
		quitGame.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));	
		menuPanel.add(quitGame);
		quitGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		
		JButton backToMenu = new JButton("Back to menu");
		backToMenu.setAlignmentX( Component.CENTER_ALIGNMENT );
		backToMenu.setMaximumSize( new Dimension(300, 75) );
		backToMenu.setBackground(Color.GRAY);
		backToMenu.setForeground(Color.WHITE);
		backToMenu.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
		description.add(backToMenu);
		backToMenu.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent e){
				cardLayout.show(cardContainer,"menupanel");
			}
		});
		
		
		cardContainer = new JPanel(cardLayout);
		cardLayout.addLayoutComponent(gamePanel,"gamepanel");
		cardLayout.addLayoutComponent(menuPanel,"menupanel");
		cardLayout.addLayoutComponent(description,"description");
		cardContainer.add(gamePanel);
		cardContainer.add(menuPanel);
		cardContainer.add(description);
		cardLayout.show(cardContainer,"menupanel");
		
		add(cardContainer);
		
		this.game = game;
		
		InputController controller = new InputController();
		addKeyListener(controller);
		game.setInputData(controller);
		
		
	}
	
	public GameFrame(Game game){
		this(game,1200,720);
	}
	
	
}