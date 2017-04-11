package ModificationFromUser;

import backEnd.ModelImpl;
import backEnd.GameData.State.Tile;
import backEnd.GameData.State.TileAttribute;
<<<<<<< HEAD
=======
import backEnd.GameEngine.Component;
import backEnd.GameEngine.Behaviors.Behavior;
>>>>>>> 8a4c0cbcf3d59c2f0f20bdfe55709b2e111c5c03
import backEnd.Mode.ModeException;

public class Modification_AddTileAttribute<T> implements ModificationFromUser {

	private Tile myTile;
	private TileAttribute<T> myAttr;
	public static final String DESCRIPTION = "Add Tile Attribute";
	
	public Modification_AddTileAttribute(Tile myTile, TileAttribute<T> newAttr) {
		this.myTile = myTile;
		this.myAttrVal = newAttrVal;

	}

	@Override
	public void invoke(ModelImpl myModel) {
		switch (myModel.getMode().getUserMode()) {
		case AUTHOR:
			myTile.addTileAttribute(new TileAttribute<T>(myAttrVal));
		case PLAYER:
			 throw new ModeException(myModel.getMode(), DESCRIPTION);
		}

	}

}
