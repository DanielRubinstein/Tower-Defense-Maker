package useCases;

import java.util.List;

import backEnd.Environment.Behavior;
import backEnd.Environment.EnvironmentInterface;
import backEnd.Environment.Mode;
import backEnd.Environment.ComponentBank;
import backEnd.Environment.Tile;
import backEnd.Environment.TileAttribute;
import backEnd.State.State;
import frontEnd.Skeleton.UserInteractor;
/**
 * @author Tim
 * This class represents a procedural implementation of use case 5, where the use toggles to switch from God mode to 
 * player mode (or vice versa). The class SwitchModeCase5 represents some class in the front end that has not yet been 
 * defined, but this is fine because the communication between interfaces is clearly outlined. How the user selects to toggle
 * the Mode and what changes on the display are implementation details.
 *
 */
public class SwitchModeUseCase5 implements UserInteractor{
	private Mode userMode;
	private EnvironmentInterface myEnvironment;
	
	public SwitchModeUseCase5(Mode defaultMode, EnvironmentInterface e) {
		userMode= defaultMode;
		myEnvironment= e;
	}
	/**
	 * When the user pressed the ToggleMode button, the following method is called to switch to the appropriate Mode.
	 * After this class's instance of Mode has been switched, the Environment's Mode is set to this Mode
	 */
	/*private void handleToggleMode(){
		//userMode = myEnvironment.changeMode();
		myEnvironment.setMode(userMode);
	}*/

	@Override
	public Mode getCurrentMode() {
		return userMode;
	}

	
	/**
	 * @author Tim
	 * This class is used as an inner class in order to procedurally understand each step and how the interfaces interact 
	 * with each other. This class is an implementation of Environment. Only the required method is implemented.
	 */
	/*private class EnvironmentImpl implements EnvironmentInterface{
		private Mode myMode;*/

		/* (non-Javadoc)
		 * For this use case, this is the only method that needs to be called.
		 * This class's instance of Mode is set to the parameter newMode. 
		 * @see backEnd.Environment.Environment#setMode(backEnd.Environment.Mode)
		 */
		/**
		@Override
		public void setMode(Mode newMode) {
			myMode = newMode;
		}
		*/
		/*
		 * Implementation not provided
		 * @see backEnd.Environment.Environment#changeMode()
		 
		@Override
		public Mode changeMode() {
			// TODO Auto-generated method stub
			return myMode;
		}

		@Override
		public List<Behavior> getStructBehaviorList(Component struct) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setStructBehaviorList(List<Behavior> newList) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public List<TileAttribute> getTileAttributeList(Tile tile) {
			// TODO Auto-generated method stub
			return null;
		}
		*/
/*
		@Override
		public void setTileAttributeList(List<TileAttribute> newList) {
			// TODO Auto-generated method stub
	*/		
		}
	/*
		@Override
		public void setState(State state) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public ComponentBank getComponentBank() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public Mode getMode() {
			// TODO Auto-generated method stub
			return null;
		}

		*/
