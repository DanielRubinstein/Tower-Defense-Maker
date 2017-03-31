package useCases;

import java.util.List;

import backEnd.Environment.Behavior;
import backEnd.Environment.Environment;
import backEnd.Environment.Mode;
import backEnd.Environment.Structure;
import backEnd.Environment.StructureBank;
import backEnd.Environment.Tile;
import backEnd.Environment.TileAttribute;
import frontEnd.UserInteractor;
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
	private Environment myEnvironment;
	
	public SwitchModeUseCase5(Mode defaultMode, Environment e) {
		userMode= defaultMode;
		myEnvironment= e;
	}
	/**
	 * When the user pressed the ToggleMode button, the following method is called to switch to the appropriate Mode.
	 * After this class's instance of Mode has been switched, the Environment's Mode is set to this Mode
	 */
	private void handleToggleMode(){
		//userMode.switchMode() ???
		myEnvironment.setMode(userMode);
	}

	@Override
	public Mode getCurrentMode() {
		// TODO Auto-generated method stub
		return userMode;
	}

	
	/**
	 * @author Tim
	 * This class is used as an inner class in order to procedurally understand each step and how the interfaces interact 
	 * with each other. This class is an implementation of Environment. Only the required method is implemented.
	 */
	private class EnvironmentImpl implements Environment{
		private Mode myMode;

		/* (non-Javadoc)
		 * For this use case, this is the only method that needs to be called.
		 * This class's instance of Mode is set to the parameter newMode. 
		 * @see backEnd.Environment.Environment#setMode(backEnd.Environment.Mode)
		 */
		@Override
		public void setMode(Mode newMode) {
			myMode = newMode;
		}
		@Override
		public StructureBank getStructureBank() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setState() {
			// TODO Auto-generated method stub
			
		}


		@Override
		public List<Behavior> getStructBehaviorList(Structure struct) {
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

		@Override
		public void setTileAttributeList(List<TileAttribute> newList) {
			// TODO Auto-generated method stub
			
		}
		
	}

}
