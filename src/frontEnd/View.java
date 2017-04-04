package frontEnd;

import java.util.Observable;
import java.util.Observer;

import backEnd.Model.Model;

public class View implements Observer{
	private Model obeservedModel;

	@Override
	public void update(Observable observable, Object arg) {
		if(obeservedModel == observable){
			
		}
		
	}

}
