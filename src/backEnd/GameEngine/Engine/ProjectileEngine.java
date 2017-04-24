package backEnd.GameEngine.Engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import backEnd.GameData.GameData;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.State;
import backEnd.GameData.State.Tile;
import javafx.geometry.Point2D;

/**
 * governs the behavior of projectiles in the State
 * 
 * @author Christian Martindale
 * @author Daniel
 *
 */
public class ProjectileEngine implements Engine {
	private GameData myGameData;
	@Override
	public void gameLoop(GameData gameData, double stepTime) {
		//System.out.println("ProjectileEngine called");
		myGameData=gameData;
		List<Component> toRemove=new ArrayList<Component>();
		for (Component c : gameData.getState().getComponentGraph().getAllComponents()) {
			if (((String) c.getAttribute("Type").getValue()).equals("Projectile")) {
				Point2D newPos=calculateNewPos(c);
				Tile currentTile = myGameData.getState().getTileGrid().getTileByScreenLocation(newPos);
				if (currentTile==null){
					toRemove.add(c);
					return;
				}	
				if ((Double) c.getAttribute("ProjectileTraveled").getValue() >= (Double) c.getAttribute(("ProjectileMaxDistance")).getValue()) {
					//System.out.println("Projectile has reached target");
					List<Component> targets = (ArrayList<Component>) gameData.getState().getComponentGraph().getComponentsWithinRadius(c,
							(Double) c.getAttribute("ExplosionRadius").getValue());
					//System.out.println("About to perform projectile action");
					performProjectileAction(c, targets);

					toRemove.add(c);
					continue;

				}
				//c.setAttributeValue("Position", newPos);
			}
		}
		for(Component c:toRemove){
			System.out.println("Removing component at location" + c.getAttribute("Position").getValue());
			gameData.getState().getComponentGraph().removeComponent(c);
			//System.out.println("PROJECTILE GOT REMOVED");
		}

	}

	private Point2D calculateNewPos(Component c) {
		Double curVel = (Double) c.getAttribute(("Velocity")).getValue();
		
		//TODO: CHRISTIAN targeting is screwed up for going backwards - probably in slope (if not, then error
		//might be in the target point initialization in AttackEngine (.subtract?)
		Point2D curPos = (Point2D) c.getAttribute(("Position")).getValue();
		Point2D targetPos = (Point2D) c.getAttribute(("ProjectileTargetPosition")).getValue();
		Point2D difference = targetPos.subtract(curPos);

		Double slope = difference.getY() / difference.getX();
		Double distTraveled = Math.sqrt(Math.pow(curVel, 2) + Math.pow(slope * curVel, 2));
		Point2D newPos = new Point2D(curPos.getX()+curVel, curPos.getY()+curVel* slope);
		c.setAttributeValue("ProjectileTraveled",((Double) c.getAttribute(("ProjectileTraveled")).getValue()) + distTraveled);
		return newPos;
		//System.out.println("curPos.get(X) is "+curPos.getX()+" , curPos.get(Y) is "+curPos.getY()+" , curVel is: "+curVel+" ,slope is "+slope);
		//curPos.add((curVel), curVel * slope);
		//System.out.println("SKIRT SKIRT SIZES ARE: width: "+myGameData.getState().getGridWidth()+" height:"+myGameData.getState().getGridHeight());

		//System.out.println("Slope is " + slope + "distanceTraveled" + distTraveled);
		//System.out.println("performed targeting math" + newPos + curPos + targetPos + difference);
	}

	/**
	 * Upon collision, takes action on the target based on the projectile's
	 * Attributes
	 * 
	 * @param projectile
	 *            the projectile performing the action
	 * @param target
	 *            the object of the projectile's action (usually an enemy)
	 */
	private void performProjectileAction(Component projectile, List<Component> targetList) {
		//System.out.println("PERFORMING PROJECTILE ACTION SKIRT SKIRT");
			for (Component target : targetList) {

				System.out.println(targetList.size()+ "LIST SIZE SKIRT SKIRT");
				//System.out.println("Target health is " + target.getAttribute("Health").getValue());
				target.setAttributeValue("Health", (Integer)target.getAttribute("Health").getValue() - (Integer) projectile.getAttribute("FireDamage").getValue());
				target.setAttributeValue("Velocity", ((Double) projectile.getAttribute("SlowFactor").getValue()
						* (Double) target.getAttribute("Speed").getValue()));
				System.out.println("projectile action performed");
				if(projectile.getAttribute("FireType").getValue().equals("SingleTarget")){
					break; //if AOE, continue to loop through all targets, else only affect one target
				}
			}
		
	}
}
