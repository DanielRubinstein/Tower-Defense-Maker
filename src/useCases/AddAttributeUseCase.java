package useCases;

import backEnd.Environment.Behavior;
import backEnd.Environment.EnvironmentInterface;
import backEnd.Environment.Mode;
import backEnd.Environment.ComponentBank;
import backEnd.Environment.Tile;
import backEnd.Environment.TileAttribute;
import backEnd.GameEngine.Attribute;
import backEnd.GameEngine.Component;
import backEnd.GameEngine.Engine.Engine;
/**
 * 
 * @author christianmartindale
 * This class represents an implementation of use case 31, where the user decides to
 * add a predefined attribute to a tower structure. 
 */
/*
public class AddAttributeUseCase implements Attribute{

	public State oldState; //the Master State held by the super controller, accessible to GameProcessController

	public AddAttributeUseCase(){
		
		class thisState implements State{
			@Override
			public void getUnmodifiableTileGrid() {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void getUnmodifiableStructureGrid() {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void getTileGrid() {
				// TODO Auto-generated method stub
				
			}
			@Override
			public Grid getStructureGrid() {
				// TODO Auto-generated method stub
				return new Grid();
			}
		}
		this.oldState = new thisState();
		
	}
	
	private Component getComponentToModify(State current, int x, int y){
		Component ans = oldState.getStructureGrid().myComponent;//.getCell(x,y) when actually implemented;
		return ans;
	}
	
	*/
	/**
	 * 
	 * @param addTo the Component to be modified
	 * @param addedAttribute the desired attribute to be added to a Component
	 */
/**
	public void addAttribute(Component addTo, Attribute addedAttribute){
		addTo.addAttribute(addedAttribute);
	}
	
	@Override
	public void setValue(Object newVal) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void getValue(Object currentVal) {
		// TODO Auto-generated method stub
		
	}

	
	
	
	
}
**/