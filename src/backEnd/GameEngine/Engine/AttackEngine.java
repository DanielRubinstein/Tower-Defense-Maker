package backEnd.GameEngine.Engine;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import backEnd.GameData.GameData;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.ComponentGraph;
import frontEnd.CustomJavafxNodes.ErrorDialog;
import javafx.geometry.Point2D;

/**
 * Deals with firing projectiles from towers at targets
 * @author Christian Martindale
 * @author Daniel
 */

public class AttackEngine implements Engine {

	private double masterTime;
	private Map<Component, Point2D> toAdd;
	private GameData myGameData;


	@Override
	public void gameLoop(GameData gameData, double stepTime) {
		masterTime = gameData.getGameTime();
		toAdd = new HashMap<Component, Point2D>();
		myGameData=gameData;
		
		ComponentGraph myComponentGraph = gameData.getState().getComponentGraph();
		Map<Component, Component> attackersAndTargets=new HashMap<Component, Component>();
		
		for (Component attacker : myComponentGraph.getAllComponents()) {
			if (attacker.getAttribute("Type").getValue().equals("Tower")) {
				if (masterTime % ((Double) attacker.getAttribute("FireRate").getValue()/1000) <= stepTime/10) { 
					List<Component> targets = myComponentGraph.getComponentsWithinRadius(attacker,
							(double) attacker.getAttribute("FireRadius").getValue());
					for (Component potentialTarget : targets) {
						if (potentialTarget.getAttribute("Type").getValue().equals("Enemy")) {
							attackersAndTargets.put(attacker, potentialTarget);
						}
					}
				}
			}
		}
		for (Component c: attackersAndTargets.keySet()){
			addProjectileToState(c, attackersAndTargets.get(c));
		}
	}

	@SuppressWarnings("unchecked")
	public void addProjectileToState(Component attacker, Component target) {
		Component projectile;
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
		projectile.setAttributeValue("ProjectileMaxDistance", finalTargetPoint.magnitude());
		//projectile.setAttributeValue("ProjectileMaxDistance", Math.sqrt(Math.pow(finalTargetPoint.getX(),2) + Math.pow(finalTargetPoint.getY(), 2)));
		myGameData.getState().getComponentGraph().addComponentToGrid(projectile, bulletPos);
	}

	/**
	 * 
	 * @return a Component that represents a projectile
	 * @throws FileNotFoundException if the selected image files are not found
	 * 
	 */
	private Component makeProjectile(Component attacker) throws FileNotFoundException {
		ProjectileFactory bulletFactory = new ProjectileFactory(attacker);
		Component projectile=bulletFactory.getProjectile();
		//projectile.setMyType("Projectile");
		return projectile;
	}
}
