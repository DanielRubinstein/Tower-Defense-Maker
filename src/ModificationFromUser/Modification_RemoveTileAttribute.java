package ModificationFromUser;

import backEnd.Model;
import backEnd.GameData.State.Tile;
import backEnd.GameData.State.TileAttribute;
import backEnd.Mode.ModeEnum;
import backEnd.Mode.ModeException;

public class Modification_RemoveTileAttribute implements ModificationFromUser {

	private Tile myTile;
	private TileAttribute myAttr;
	public static final String ERROR_MESSAGE = "You cannot remove tile attributes in Player mode!";
	
	public Modification_RemoveTileAttribute(Tile myTile, TileAttribute removeAttr) {
		this.myTile = myTile;
		this.myAttr = removeAttr;

	}

	@Override
	public void invoke(ModeEnum currentMode, Model myController) {
		switch (currentMode) {
		case AUTHOR:
			myTile.removeTileAttribute(myAttr);
		case PLAYER:
			 throw new ModeException(ERROR_MESSAGE);
		}

	}

}
