package backEnd.GameEngine.Engine;

import backEnd.GameData.GameData;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.State;
import backEnd.GameData.State.Tile;
import javafx.geometry.Point2D;
import resources.constants.StringResourceBundle;

public class BaseCheckerEngine implements Engine {

	private State myState;
	private Tile currentTile;
	private StringResourceBundle STRING_RESOURCES = new StringResourceBundle();

	@Override
	public void gameLoop(GameData gameData, double stepTime) {
		myState = gameData.getState();
		for (Component c : myState.getComponentGraph().getAllComponents()) {
			Point2D currentLocation = c.<Point2D>getAttribute("Position").getValue();
			currentTile = gameData.getState().getTileGrid().getTileByScreenPosition(currentLocation);
			if (currentTile != null && currentTile.<Boolean>getAttribute("GoalTile").getValue() && c.getAttribute(STRING_RESOURCES.getFromAttributeNames("Type")).getValue().equals(STRING_RESOURCES.getFromValueNames("EnemyType"))) {
				gameData.getStatus().decrementStatusItem("Health", 1);
				c.setAttributeValue("Health", 0);
			}
		}
	}
}
