package ModificationFromUser;

import backEnd.Model;
import backEnd.GameData.State.Tile;
import backEnd.Mode.ModeException;
import javafx.geometry.Point2D;

public class Modification_ReplaceTile implements ModificationFromUser {

	private Tile newTile;
	private Point2D location;
	public static final String DESCRIPTION = "Replace Tile";	

	
	public Modification_ReplaceTile(Tile newTile, Point2D loc){
		this.newTile = newTile;
		this.location = loc;
		
	}

	@Override
	public void invoke(Model myModel) throws Exception {
		switch (myModel.getMode().getUserMode()) {
		case AUTHOR:
			myModel.getState().getTileGrid().setTile(newTile, location);
		case PLAYER:
			 throw new ModeException(myModel.getMode(), DESCRIPTION);
		}		
	}

}
