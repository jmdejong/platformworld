

class parcours {
	
	/*
	 * To do:
	 * 
	 * make it possible to pause the game
	 * draw the inventory
	 * 
	 * 
	 */
	
	public static void main(String[] args){
		Game game = new Game();
		if (args.length>0){
			game.load(args[0]);
		} else {
			game.load("map.png");
		}
		
		new GameFrame(game);
	}
}