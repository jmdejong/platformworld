package platformer;

import java.util.Observer;
import java.util.Observable;

abstract class Controllable extends Placable implements Observer{
	
	public abstract void update(Observable caller, Object data);
}