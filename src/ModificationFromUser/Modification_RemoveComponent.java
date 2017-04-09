package ModificationFromUser;

import backEnd.Model;
import backEnd.GameEngine.Component;

public class Modification_RemoveComponent implements ModificationFromUser {
	
	private Component toRemove;

	public Modification_RemoveComponent(Component toRemove){
		this.toRemove = toRemove;
	}

	@Override
	public void invoke(Model myModel) throws Exception {
		myModel.getState().getComponentGraph().removeComponent(toRemove);
		
	}

}
