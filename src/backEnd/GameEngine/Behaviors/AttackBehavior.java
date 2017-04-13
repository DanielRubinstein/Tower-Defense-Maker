package backEnd.GameEngine.Behaviors;

import java.util.Observable;

import backEnd.Attribute.AttributeData;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.Tile;
import javafx.geometry.Point2D;

public class AttackBehavior implements Behavior{

	public AttributeData myAttributes;
	private Point2D currentPosition;
	private Point2D newPoint;
	private Component myComponent;
	private Tile currentTile;
	
	public AttackBehavior(Component inputComponent){
		myComponent=inputComponent;
		currentPosition=(Point2D) myComponent.getAttribute("Position").getValue();
	}
	
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T> void execute(T toModify) {
		// TODO Auto-generated method stub
		
	}

}
