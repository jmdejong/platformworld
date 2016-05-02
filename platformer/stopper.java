
/* 
 * This is an invisible placable object that is used to stop the player at the edges
 */

class Stop extends Block{
	
	public Stop(Game game){
		setSize(1,1);
	}
	
	public Sprite getSprite(){
		return null;
	}
}