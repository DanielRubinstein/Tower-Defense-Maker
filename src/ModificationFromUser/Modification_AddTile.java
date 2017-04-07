package ModificationFromUser;


import backEnd.GameEngine.Component;
import backEnd.Mode.ModeEnum;
import backEnd.Mode.ModeException;
import backEnd.State.State;
import javafx.geometry.Point2D;

public class Modification_AddTile implements ModificationFromUser {

	private State myState;
	private Tile newTile;
	private Point2D location;
	
	public Modification_AddTile(State myState, Tile newTile, Point2D loc){
		this.myState = myState;
		this.newTile = newTile;
		this.location = loc;
		
	}

	@Override
	public void invoke(ModeEnum currentMode) {
		switch (currentMode) {
		case AUTHOR:
			
		case PLAYER:
			 throw new ModeException(ERROR_MESSAGE);
		}
		
	}

}
