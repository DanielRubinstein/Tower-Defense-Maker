package backEnd.Environment;

import java.util.List;

import backEnd.GameEngine.Attribute;

public interface Structure {

	
	
	/**
	 * @param health
	 */
	public void setHealth(double health);
	
	
	/**
	 * @return health
	 */
	
	public double getHealth();
	
	
	
	/**
	 *  executes action of structure based on behavior by calling Behavior.invoke for appropriate behavior 
	 */
	public void updateMove();
    public void updateDeath();
	public void updateInteraction();
	public void updateDamage();
	public void updateBreed();
	public void updateCollision();
	
	
	
	/**
	 * sets size of Structure
	 */
	public void setSize();
	
	/**
	 * sets behavior of structure based on parameter
	 * will be an overloaded method determined whose proper
	 * execution is determined by parameter
	 * 
	 * 
	 *ex:setBehavior(MoveBehavior newMove)
	 */
	public void setBehavior();
	
	/**
	 * sets location of structure
	 */
	public void setLocation(int i, int j);
	
	
	/**
	 * gets location of structure,
	 * return type is void for now since not sure what it should be
	 */
	public void getLocation();


	public void replaceAttributes(List<Attribute<?>> newAttributes);



}
