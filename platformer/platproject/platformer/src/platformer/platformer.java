package platformer;

class platformer {
	
	
	
	public static void main(String[] args){
		//MapLoader loader;
		Game game = new Game();
		if (args.length>0){
			game.load(args[0]);
		} else {
			game.load("testimg.png");
		}
		
		//loader.print();
		new GameFrame(game);
	}
}