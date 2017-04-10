package ModificationFromUser;

import backEnd.ModelImpl;
import backEnd.GameData.State.Tile;
import backEnd.GameData.State.TileAttributeImpl;
import backEnd.GameEngine.Component;
import backEnd.GameEngine.Behaviors.Behavior;
import backEnd.Mode.ModeException;

public class Modification_AddTileAttribute<T> implements ModificationFromUser {

	private Tile myTile;
	private TileAttributeImpl<T> myAttr;
	public static final String DESCRIPTION = "Add Tile Attribute";
	
	public Modification_AddTileAttribute(Tile myTile, TileAttributeImpl<T> newAttr) {
		this.myTile = myTile;
		this.myAttr = newAttr;

	}

	@Override
	public void invoke(ModelImpl myModel) {
		switch (myModel.getMode().getUserMode()) {
		case AUTHOR:
			myTile.addTileAttribute(myAttr);
		case PLAYER:
			 throw new ModeException(myModel.getMode(), DESCRIPTION);
		}

	}

}
