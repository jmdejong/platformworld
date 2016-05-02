package platformer;

import java.util.Observer;
import java.util.Observable;

class StepController extends Observable {
	
	InputController inputData;
	
	public void setInputData(InputController inputData){
		this.inputData=inputData;
	}
	
	public void step(){
		setChanged();
		notifyObservers(inputData);
	}
	
}