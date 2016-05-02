
/*
 * The Exit block is a placable block.
 * If an object interacts with this block, the player has won the game
 */
class Exit extends Placable{
	private Game game;
	private Sprite sprite = new Sprite(0xff0000,0,0,1,2);
	
	
	public Exit(Game game){
		setSize(1,2);
		this.game = game;
	}
	
	public boolean interactable(){
		return true;
	}
	
	public void interact(Actor obj){
		//System.out.println("exit");
		game.end(true);
	}
	
	public boolean isSolid(){
		return false;
	}
	
	public Sprite getSprite(){
		return sprite;
	}
}