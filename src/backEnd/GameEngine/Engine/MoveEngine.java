package backEnd.GameEngine.Engine;
import java.util.LinkedList;
import java.util.Queue;
import backEnd.Coord;
import backEnd.GameEngine.Component;
import backEnd.GameEngine.Behaviors.*;
import backEnd.GameData.State.*;
public class MoveEngine implements Engine{
	@Override
	public void gameLoop(State currentState) {
		
		//might need to be in State, not here
		for(int i=0; i<currentState.getGridWidth(); i++){ //find the start position
			for(int j=0; j<currentState.getGridHeight(); j++){
				if(currentState.getTileGrid().getTilebyCoord(i,j).getComponent == STARTPOS){
					formShortestPath(currentState.getTileGrid(), i, j);
				}
			}
		}
		//^^^^^^^^
		
		for(Component struct: currentState.getTileGrid().getComponentGraph.getAllComponents()){
			if(struct.getAttribute("Movable").getValue() == "True"){ //only move stuff that are movable
				Behavior myMovementInstructions = new MoveBehavior(struct); //can we avoid this?
				myMovementInstructions.execute(struct);
				
			}
		}
				
			
		
	}
	
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
	
	/**
	 * helper method for formShortestPath
	 * @param xPos
	 * @param yPos
	 * @return whether the given Tile can be moved onto
	 */
	private boolean isPassable(TileGrid stateGrid, int xPos, int yPos){
		if((xPos>=0 && xPos<stateGrid.getMyWidth()) && (yPos >=0 && yPos<stateGrid.getMyWidth())){
			if(!stateGrid.getTileByCoord(x, y).containsComponent(IMPASSABLELABEL)){
				return true;
			}
		}
		return false;
	}
}