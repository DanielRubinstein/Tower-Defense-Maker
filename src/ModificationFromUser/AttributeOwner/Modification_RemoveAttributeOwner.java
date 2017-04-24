package ModificationFromUser.AttributeOwner;

import java.lang.reflect.Method;

import ModificationFromUser.ModificationFromUser;
import backEnd.ModelImpl;
import backEnd.Attribute.AttributeOwner;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.Tile;
import backEnd.GameData.State.TileImpl;

public class Modification_RemoveAttributeOwner implements ModificationFromUser {
	
	private AttributeOwner toRemove;
	private ModelImpl myModel;
	public static final String TILE_ERROR = "Cannot move tiles";
	public static final String DESCRIPTION_ERROR = "Not a recognized Attribute Owner";

	public Modification_RemoveAttributeOwner(AttributeOwner toRemove){
		this.toRemove = toRemove;
	}

	@Override
	public void invoke(ModelImpl model) throws Exception {
		myModel = model;
		try {
			Method remove = Modification_RemoveAttributeOwner.class.getDeclaredMethod("remove", toRemove.getClass());
			remove.setAccessible(true);
			remove.invoke(this, toRemove);
		} catch (NoSuchMethodException e) {
			throw new Exception(DESCRIPTION_ERROR);
		}
		
	}
	
	private void remove(TileImpl tile) throws Exception{
		throw new Exception(TILE_ERROR);
	}
	
	private void remove(Component component){
		myModel.getState().getComponentGraph().removeComponent(component);
	}

}
