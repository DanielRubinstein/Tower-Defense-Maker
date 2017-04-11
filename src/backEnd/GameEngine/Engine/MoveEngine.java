package backEnd.GameEngine.Engine;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import backEnd.Coord;
import backEnd.GameEngine.Component;
import backEnd.GameEngine.Behaviors.*;
import backEnd.GameData.State.*;


/**
 * Performs movement behaviors for all movable components
 * @author Christian Martindale
 *
 */
public class MoveEngine implements Engine{
	
		
		/*
		for(Component struct: currentState.getTileGrid().getAllComponents()){

			if(struct.getAttribute("Movable").getValue() == "True"){ //only move stuff that are movable
				Behavior myMovementInstructions = new MoveBehavior(struct); 
				myMovementInstructions.execute(struct);
				
			}
		}
		*/
				
			
		
	
	
	/**
	 * simple BFS to label each Tile with the
	 * direction an object on the Tile should move
	 * 
	 * might need to be in State? will need to be recalled when a tower is added or removed from State
	 * 
	 * @param TileGrid the Grid of Tiles that Components must navigate
	 * @param xStart the starting x-coordinate
	 * @param yStart the starting y-coordinate
	 */
	private Coord formShortestPath(TileGrid stateGrid, int xStart, int yStart){
		Queue <Coord> path = new LinkedList<Coord>();
		
		path.add(new Coord(xStart, yStart, null));
		
		while(!path.isEmpty()){
			Coord cur = path.remove();
			
			if(stateGrid[cur.x][cur.y].containsComponent(ENDGOALTILE)){
				return cur;
			}
			
	
		}
	}
	


	@Override
	public void gameLoop(State currentState) {
		// TODO Auto-generated method stub
		
	}
}