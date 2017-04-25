package backEnd.GameEngine.Engine;

import backEnd.Attribute.Attribute;
import backEnd.GameData.GameData;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.State;
import backEnd.GameData.State.Tile;
import javafx.geometry.Point2D;

public class BaseCheckerEngine implements Engine {

	private State myState;
	private Tile currentTile;

	@Override
	public void gameLoop(GameData gameData, double stepTime) {
		myState = gameData.getState();
		for (Component c : myState.getComponentGraph().getAllComponents()) {
			Point2D currentLocation = c.<Point2D>getAttribute("Position").getValue();
			currentTile = gameData.getState().getTileGrid().getTileByScreenPosition(currentLocation);
			if ((boolean) currentTile.getAttribute("GoalTile").getValue()) {
				gameData.getStatus().decrementStatusItem("Health", 1);
				c.getAttribute("Health").setValue(new Integer(0));
			}
		}
	}

}
