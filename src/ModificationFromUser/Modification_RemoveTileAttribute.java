package ModificationFromUser;

import backEnd.Model;
import backEnd.GameData.State.Tile;
import backEnd.GameData.State.TileAttribute;
import backEnd.Mode.ModeException;

public class Modification_RemoveTileAttribute implements ModificationFromUser {

	private Tile myTile;
	private TileAttribute myAttr;
	public static final String DESCRIPTION = "Remove Tile Attribute";
	
	public Modification_RemoveTileAttribute(Tile myTile, TileAttribute removeAttr) {
		this.myTile = myTile;
		this.myAttr = removeAttr;

	}

	@Override
	public void invoke(Model myModel) throws Exception {
		switch (myModel.getMode().getUserMode()) {
		case AUTHOR:
			//FIXME need removeTileAttribute method in Tile
			myTile.removeTileAttribute(myAttr);
		case PLAYER:
			 throw new ModeException(myModel.getMode(), DESCRIPTION);
		}		
	}

}
