/*
 * The door is a placable block that can be opened if the player has a key item in the inventory
 * If the door is closed, it is solid. If it is open, it is not.
 * A door is closed by default
 */


class Door extends Placable{
	
	private boolean open = false;
	private Sprite closedSprite = new Sprite(0xcc6600,0,0,1,2);
	private Sprite openSprite = new Sprite(0xcc66600,0.1,0,0.2,2);
	
	
	public Door(Game game){
		setSize(1,2);
	}
	
	public boolean interactable(){
		return true;
	}
	
	public void interact(Actor player){ // open or close the door
		if (player.hasItem("key"))
			open = !open;
	}
	
	public boolean isSolid(){
		return !open;
	}
	
	public Sprite getSprite(){ // toggle between the sprite of an open and the sprite of a closed door
		return open ? openSprite : closedSprite; 
	}
}