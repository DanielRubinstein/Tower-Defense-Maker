package useCases;

import java.util.List;
import java.util.Map;

import backEnd.State;
import backEnd.Environment.EnvironmentInterface;
import backEnd.Environment.Mode;
import backEnd.Environment.Player;
import backEnd.GameEngine.Component;
import frontEnd.Skeleton.UserInteractor;

/**
 * This file is an example of how the process of "placing a structure" would work in the game. The "PlaceStruct_UseCase11" class
 * represents the front-end user-interactor that would start the process. Once the user places the structure using the visual
 * interface on the front end, the "handleStructDropped()" command would call ".addStruct()" on the current mode (in this case
 * we're in player mode). The Player class, as shown below, would then take that structure and place it in the State object
 * (in the grid) at the specified location.
 * 
 * @author Riley Nisbet
 */

public class PlaceStruct_UseCase11 implements UserInteractor{
	private Mode userMode;
	private EnvironmentInterface myEnvironment;
	
	public PlaceStruct_UseCase11(Mode defaultMode, EnvironmentInterface e) {
		userMode= defaultMode;
		myEnvironment= e;
	}
	
	public Mode getCurrentMode(){
		return null;
	}
	
	private void handleStructDropped(Component struct, int[] location){
		userMode.addStruct(struct, location);
	}
	
	/**
	 * This class is an example of the Player class (remade here for show purposes). The only method that it actually uses
	 * is the "addStruct()" method that places the structure in the State object as specified.
	 * @author Riley Nisbet
	 */
	private class PlayerInstance extends Player{
		private State state;
		
		@Override
		public void addStruct(Component struct, int[] location){
			List<Component>[][] structGrid = state.getStructureGrid();
			structGrid[location[0]][location[1]].add(struct);
		}
	}

}
