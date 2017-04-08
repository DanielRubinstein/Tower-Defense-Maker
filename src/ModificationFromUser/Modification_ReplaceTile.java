package ModificationFromUser;


import backEnd.Model;
import backEnd.GameData.State.State;
import backEnd.GameData.State.Tile;
import backEnd.GameEngine.Component;
import backEnd.Mode.ModeEnum;
import backEnd.Mode.ModeException;
import javafx.geometry.Point2D;

public class Modification_ReplaceTile implements ModificationFromUser {

	private State myState;
	private Tile newTile;
	private Point2D location;
	public static final String ERROR_MESSAGE = "You cannot replace tiles in Player mode!";

	
	public Modification_ReplaceTile(State myState, Tile newTile, Point2D loc){
		this.myState = myState;
		this.newTile = newTile;
		this.location = loc;
		
	}

	@Override
	public void invoke(ModeEnum currentMode, Model myController) {
		switch (currentMode) {
		case AUTHOR:
			myState.getTileGrid().setTile(newTile, location);
		case PLAYER:
			 throw new ModeException(ERROR_MESSAGE);
		}
		
	}

}
