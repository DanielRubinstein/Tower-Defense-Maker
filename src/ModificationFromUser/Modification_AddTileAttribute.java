package ModificationFromUser;

import backEnd.Model;
import backEnd.GameData.State.Tile;
import backEnd.GameData.State.TileAttribute;
import backEnd.Mode.ModeException;

public class Modification_AddTileAttribute<T> implements ModificationFromUser {

	private Tile myTile;
	private T myAttrVal;
	public static final String DESCRIPTION = "Add Tile Attribute";
	
	public Modification_AddTileAttribute(Tile myTile, T newAttrVal) {
		this.myTile = myTile;
		this.myAttrVal = newAttrVal;

	}

	@Override
	public void invoke(Model myModel) {
		switch (myModel.getMode().getUserMode()) {
		case AUTHOR:
			myTile.addTileAttribute(new TileAttribute<T>(myAttrVal));
		case PLAYER:
			 throw new ModeException(myModel.getMode(), DESCRIPTION);
		}

	}

}
