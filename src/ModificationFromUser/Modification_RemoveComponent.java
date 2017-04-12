package ModificationFromUser;

import backEnd.ModelImpl;
import backEnd.GameData.State.Component;

public class Modification_RemoveComponent implements ModificationFromUser {
	
	private Component toRemove;

	public Modification_RemoveComponent(Component toRemove){
		this.toRemove = toRemove;
	}

	@Override
	public void invoke(ModelImpl myModel) throws Exception {
		myModel.getState().getComponentGraph().removeComponent(toRemove);
		
	}

}
