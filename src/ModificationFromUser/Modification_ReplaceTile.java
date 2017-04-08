package ModificationFromUser;


import backEnd.GameEngine.Component;
import backEnd.Mode.ModeEnum;
import backEnd.Mode.ModeException;
import backEnd.State.State;
import backEnd.State.Tile;
import javafx.geometry.Point2D;
import main.InteractivityController;

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
	public void invoke(ModeEnum currentMode, InteractivityController myController) {
		switch (currentMode) {
		case AUTHOR:
			myState.getTileGrid().setTile(newTile, location);
		case PLAYER:
			 throw new ModeException(ERROR_MESSAGE);
		}
		
	}

}
