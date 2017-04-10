package ModificationFromUser;

import backEnd.Model;
import backEnd.GameData.State.Tile;
import backEnd.GameData.State.TileAttribute;
import backEnd.GameEngine.Component;
import backEnd.GameEngine.Behaviors.Behavior;
import backEnd.Mode.ModeException;

public class Modification_AddTileAttribute<T> implements ModificationFromUser {

	private Tile myTile;
	private TileAttribute<T> myAttr;
	public static final String DESCRIPTION = "Add Tile Attribute";
	
	public Modification_AddTileAttribute(Tile myTile, TileAttribute<T> newAttr) {
		this.myTile = myTile;
		this.myAttr = newAttr;

	}

	@Override
	public void invoke(Model myModel) {
		switch (myModel.getMode().getUserMode()) {
		case AUTHOR:
			myTile.addTileAttribute(myAttr);
		case PLAYER:
			 throw new ModeException(myModel.getMode(), DESCRIPTION);
		}

	}

}
