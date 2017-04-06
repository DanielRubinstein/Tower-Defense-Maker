package backEnd.GameEngine;

import java.util.Observable;

public class DeathBehavior implements Behavior {
	private AttributeData myAttributes;

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void update(Observable newData, Object arg) {
		myAttributes = (AttributeData) newData;
	}

}
