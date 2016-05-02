/* 
 * An Actor object should be able to interact with other objects, and to pick up items.
 * In this case, the Player object is the only actor
 */
interface Actor {  
	
	public boolean take(Item item);
	
	public boolean hasItem(String itemType);
	
	public Item loseItem(String itemType);
}