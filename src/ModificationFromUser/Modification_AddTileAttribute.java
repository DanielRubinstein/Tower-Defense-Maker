package ModificationFromUser;

import backEnd.GameEngine.Behavior;
import backEnd.GameEngine.Component;
import backEnd.Mode.ModeEnum;
import backEnd.Mode.ModeException;
import backEnd.State.Tile;
import backEnd.State.TileAttribute;
import main.InteractivityController;

public class Modification_AddTileAttribute implements ModificationFromUser {

	private Tile myTile;
	private TileAttribute myAttr;
	public static final String ERROR_MESSAGE = "You cannot add tile attributes in Player mode!";
	
	public Modification_AddTileAttribute(Tile myTile, TileAttribute newAttr) {
		this.myTile = myTile;
		this.myAttr = newAttr;

	}

	@Override
	public void invoke(ModeEnum currentMode, InteractivityController myController) {
		switch (currentMode) {
		case AUTHOR:
			myTile.addTileAttribute(myAttr);
		case PLAYER:
			 throw new ModeException(ERROR_MESSAGE);
		}

	}

}
