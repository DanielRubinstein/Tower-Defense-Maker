package backEnd.GameEngine.Engine;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import backEnd.GameData.GameData;
import backEnd.GameData.State.ComponentImpl;
import backEnd.GameData.State.ComponentGraph;
import backEnd.GameData.State.State;
import frontEnd.CustomJavafxNodes.ErrorDialog;
import javafx.geometry.Point2D;

/**
 * Deals with firing projectiles from towers at targets
 * @author Christian Martindale
 * @author Daniel
 */

public class AttackEngine implements Engine {

	private double masterTime;
	private Map<ComponentImpl, Point2D> toAdd;
	private GameData myGameData;


	@Override
	public void gameLoop(GameData gameData, double stepTime) {
		masterTime = gameData.getGameTime();
		toAdd = new HashMap<ComponentImpl, Point2D>();
		myGameData=gameData;
		ComponentGraph myComponentGraph = gameData.getState().getComponentGraph();
		Map<ComponentImpl, ComponentImpl> attackersAndTargets=new HashMap<ComponentImpl, ComponentImpl>();
		
		for (ComponentImpl attacker : myComponentGraph.getAllComponents()) {
			if (attacker.getAttribute("Type").getValue().equals("Tower")) {
				if (masterTime % ((Double) attacker.getAttribute("FireRate").getValue()/1000) <= stepTime/10) { 
					List<ComponentImpl> targets = myComponentGraph.getComponentsWithinRadius(attacker,
							(double) attacker.getAttribute("FireRadius").getValue());
					for (ComponentImpl potentialTarget : targets) {
						if (potentialTarget.getAttribute("Type").getValue().equals("Enemy")) {
							attackersAndTargets.put(attacker, potentialTarget);
						}
					}
				}
			}
		}
		for (ComponentImpl c: attackersAndTargets.keySet()){
			addProjectileToState(c, attackersAndTargets.get(c));
		}
	}

	@SuppressWarnings("unchecked")
	public void addProjectileToState(ComponentImpl attacker, ComponentImpl target) {
		ComponentImpl projectile;
		try {
			projectile = makeProjectile(attacker);
		} catch (FileNotFoundException e1) {
			System.out.println("projectile creation failed");
			ErrorDialog fnf = new ErrorDialog();
			fnf.create("Error", "File not found");
			return;
		}

		Object bulletPosObj=attacker.getAttribute(("Position")).getValue();
		Point2D bulletPos = (Point2D) bulletPosObj;
		Object targetPosObj=target.getAttribute(("Position")).getValue();
		Point2D targetPos = (Point2D) targetPosObj;
		projectile.setAttributeValue("Position", bulletPos);
		projectile.setAttributeValue("ProjectileTargetPosition", targetPos);
		projectile.setAttributeValue("ProjectileTarget", target);
		Point2D finalTargetPoint = targetPos.subtract(bulletPos);
		//TODO: USE .magnitude() method below
		projectile.setAttributeValue("ProjectileMaxDistance", Math.sqrt(Math.pow(finalTargetPoint.getX(),2) + Math.pow(finalTargetPoint.getY(), 2)));
		myGameData.getState().getComponentGraph().addComponentToGrid(projectile, bulletPos);
	}

	/**
	 * 
	 * @return a Component that represents a projectile
	 * @throws FileNotFoundException if the selected image files are not found
	 * 
	 */
	private ComponentImpl makeProjectile(ComponentImpl attacker) throws FileNotFoundException {
		ProjectileFactory bulletFactory = new ProjectileFactory(attacker);
		ComponentImpl projectile=bulletFactory.getProjectile();
		//projectile.setMyType("Projectile");
		return projectile;
	}
}
