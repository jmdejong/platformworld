import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.Observer;
import java.util.Observable;

/*
 * The player is the main object in the game.
 * It is controlled by the left, up and right arrow keys, or by the A, W and D keys.
 * F can be used to interact with objects.
 * The spacebar is a kind of cheat key that allows the player to jump while in the air.
 * The player has an invertory, and some methods from the interface Actor that modify or describe this inventory.
 * 
 * Sprite creator: Hobojoe0858 on deviantart
 * Source: http://hobojoe0858.deviantart.com/art/Gordon-Freeman-Sprite-136080175
 */

class Player extends Movable implements Actor{
	
	private Sprite sprite = new Sprite("images/Gordon_Freeman_Sprite_by_HoboJoe0858.png",0,0,1,2);
	private int health = 100;
	private int score = 0;
	
	
	private boolean goLeft, goRight, goJump, goFly, interact;
	private Inventory inventory = new Inventory(12);
	
	
	
	public Player(Game game){
		setSize(1,2);
		game.setMainObject(this);
	}
	
	
	
	
	public void update(Observable caller, Object data){
		if (data == null)
			return;
		InputController inputData = (InputController)data;
		
		updateCollisions();
		
		for (Placable object : getCloseObjects() ){
			if ( this.collidesWith(object) ){
				this.health -= object.damage();
			}
		}
		goLeft = inputData.isDown(65) || inputData.isDown(37);
		goRight = inputData.isDown(68) || inputData.isDown(39);
		goJump = inputData.isDown(87) || inputData.isDown(38);
		goFly = inputData.isDown(32); // actually cheating
		interact = inputData.isPressed(70); // interact with near objects if you press the 'f' key
		
		
		
		updatePhysics();
		
		
		if (interact){ // interactions can be things like opening a door, picking up something or pushing a button
			for (Placable obj : getCloseObjects()){
				if (obj.interactable() && closeTo(obj,0.2))
					obj.interact(this);
			}
		}
		
	}
	
	private boolean closeTo(Placable obj, double maxDist){ // return whether the objet is within a distance of maxDist of the player
		// this means that if the player would move maxdist to the object they would collide
		move(-maxDist,-maxDist);
		setSize(getWidth()+maxDist*2, getHeight()+maxDist*2);
		boolean close = collidesWith(obj);
		setSize(getWidth()-maxDist*2, getHeight()-maxDist*2);
		move(maxDist,maxDist);
		return close;
	}
	
	public boolean take(Item item){
		return inventory.add(item);
	}
	
	public boolean hasItem(String itemType){
		return inventory.contains(itemType);
	}
	
	public Item loseItem(String itemType){
		return inventory.remove(itemType);
	}
	
	public Inventory getInventory(){
		return inventory;
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
	public int getHealth(){
		return health;
	}
	
	public void setHealth(int newHealth){
		health = newHealth;
	}
	
	public boolean isDead(){
		return health<0;
	}
	
	public boolean isSolid(){
		return false;
	}
	
	public boolean left(){
		return goLeft;
	}
	
	public boolean right(){
		return goRight;
	}
	
	public boolean jump(){
		return goJump;
	}
	
	public boolean fly(){
		return goFly;
	}
	
	public double getWalkForce(){
		return 0.1;
	}
	public double getJumpForce(){
		return 0.5;
	}
	public double getGroundFriction(){
		return 0.6;
	}
	public double getAirFrictionX(){
		return 0.9;
	}
	public double getAirFrictionY(){
		return 0.988;
	}
	public double getGravity(){
		return 0.016;
	}
	
	
}