/*
 * Grass is a normal solid block
 */

class Grass extends Block {
	private Sprite sprite = new Sprite("images/grass.png",0,0,1,1);
	
	public Grass(Game game){
		setSize(1,1);
	}
	
	public Sprite getSprite(){
		return sprite;
	}
}