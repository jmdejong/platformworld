
/* 
 * A KeyObject is a placable object that can be picked up.
 * If it is picked up, an item that has "key" as type will be given to the object that picked it up/
 * 
 */

class KeyObject extends Placable {
	
	Sprite sprite = new Sprite("images/key.png",0,0,1,1);
	
	public KeyObject(Game game){
		setSize(1,1);
	}
	
	public boolean interactable(){
		return true;
	}
	
	public void interact(Actor player){
		boolean taken = player.take(new Item(){
			public String getType(){
				return "key";
			}
		});
		if (taken){
			removeFromMap();
		}
	}
	
	public boolean isSolid(){
		return false;
	}
	
	public Sprite getSprite(){
		return sprite;
	}
}