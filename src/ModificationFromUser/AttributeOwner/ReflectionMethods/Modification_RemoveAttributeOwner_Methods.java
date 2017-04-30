package ModificationFromUser.AttributeOwner.ReflectionMethods;

import backEnd.Model;
import backEnd.ModelImpl;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.TileImpl;

public class Modification_RemoveAttributeOwner_Methods{
	private Model myModel;
	public static final String TILE_ERROR = "Cannot move tiles";
	public static final String DESCRIPTION_ERROR = "Not a recognized Attribute Owner";

	public Modification_RemoveAttributeOwner_Methods(Model model){
		this.myModel = model;
	}
	
	public void remove(TileImpl tile) throws Exception{
		throw new Exception(TILE_ERROR);
	}
	
	public void remove(Component component){
		myModel.getState().getComponentGraph().removeComponent(component);
	}

}
