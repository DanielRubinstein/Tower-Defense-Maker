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
	public static final String ERROR_DESCRIPTION = "Cannot remove tiles";

	public Modification_RemoveAttributeOwner(AttributeOwner toRemove){
		this.toRemove = toRemove;
	}

	@Override
	public void invoke(ModelImpl model) throws Exception {
		myModel = model;
		try {
			Method remove = Modification_RemoveAttributeOwner.class.getDeclaredMethod("add", toRemove.getClass());
			remove.setAccessible(true);
			remove.invoke(this, toRemove);
		} catch (NoSuchMethodException e) {
			System.out.println("in Modification_RemoveAttributeOwner, No method found, ugh");
			// do nothing
			// this means the thing being put in attribute command center is a tile
		} catch (Exception e) {
			// something went wrong
			System.out.println("Something went wrong in Modification_RemoveAttributeOwner");
			// TODO add exception?
		}
		
	}
	
	private void remove(TileImpl tile) throws Exception{
		throw new Exception(ERROR_DESCRIPTION);
	}
	
	private void remove(Component component){
		myModel.getState().getComponentGraph().removeComponent(component);
	}

}
