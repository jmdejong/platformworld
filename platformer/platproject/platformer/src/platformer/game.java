package platformer;

import java.util.ArrayList;

//this class should keep an overview of which objects are in the game
class Game {
	
	private ArrayList<Placable> objects;
	private int width, height;
	private StepController controller = new StepController();
	
	/*public boolean validLocation(int x, int y){
		return (x>=0 && x<width && y>=0 && y<height);
	}
	
	
	public Block get(int x, int y){
		if (!validLocation(x,y))
			return null;
		return values[x][y];
		
	}
	
	public void set(int x, int y, Block b){
		if (!validLocation(x,y))
			return;
		values[x][y] = b;
	}*/
	
	public ArrayList<Placable> getAllObjects(){
		return objects;
	}
	
	public void step(){
		controller.step();
	}
	
	public void setInputData(InputController inputData){
		controller.setInputData(inputData);
	}
	
	public void load(String fileName){
		MapLoader loader = new MapLoader(fileName);
		
		width=loader.getWidth();
		height=loader.getHeight();
		
		//values = new Block[width][height];
		objects = new ArrayList<Placable>();
		
		//System.out.println(values.size());
		
		for (int x = 0; x<width; x++){
			for (int y = 0; y<height; y++){
				if (loader.get(x,y)==0xff00ff00){ // we should make some map that maps colorvalues to an object/some objects
					Placable b = new Block(x,y);
					//set(x, y, b);
					objects.add(b);
				} else if (loader.get(x,y)==0xff0000ff){
					Controllable b = new Player(x,y);
					objects.add(b);
					controller.addObserver(b);
				}
			}
		}
	}
}



