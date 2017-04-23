package backEnd.GameEngine.Engine;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import backEnd.GameData.GameData;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.ComponentGraph;
import backEnd.GameData.State.State;
import frontEnd.CustomJavafxNodes.ErrorDialog;
import javafx.geometry.Point2D;

/**
 * Deals with firing projectiles from towers at targets
 * @author Christian
 */

public class AttackEngine implements Engine {

	private double masterTime;

	@Override
	public void gameLoop(GameData gameData, double stepTime) {
		System.out.println("AttackEngine called");
		masterTime = gameData.getGameTime();

		//Need to make a toAdd (and toRemove?) list, then do that after for loop terminates
		ComponentGraph myComponentGraph = gameData.getState().getComponentGraph();
		Map<Component, Point2D> toAdd = new HashMap<Component, Point2D>();
		
		for (Component attacker : myComponentGraph.getAllComponents()) {
			if (attacker.getAttribute("Type").getValue().equals("Tower")) {
				System.out.println("Tower found");
				if ( masterTime % ((Double) attacker.getAttribute("FireRate").getValue()) <= stepTime) {
					List<Component> targets = myComponentGraph.getComponentsWithinRadius(attacker,
							(double) attacker.getAttribute("FireRadius").getValue());
					for(Component potentialTarget:targets){ //only want to fire at enemies
						if(potentialTarget.getAttribute("Type").getValue().equals("Enemy")){
							System.out.println("Decided to create a projectile, found an enemy with position " + potentialTarget.getAttribute("Position").getValue());
							addProjectileToState(gameData.getState(), attacker, targets.get(0), toAdd);
						}
					}
				}
			}
		}
		//do adding here
		for(Component c:toAdd.keySet()){
			System.out.println("toAdd's size is " + toAdd.size());
			System.out.println("component toAdd location is " + c.getAttribute("Position").getValue());
			gameData.getState().getComponentGraph().addComponentToGrid(c, toAdd.get(c));
		}
		System.out.println("Projectiles should all be added to State");
	}

	@SuppressWarnings("unchecked")
	public void addProjectileToState(State currentState, Component attacker, Component target, Map<Component, Point2D> toAdd) {
		Component projectile = null;
		try {
			projectile = makeProjectile(attacker);
			System.out.println("Made projectile");

		} catch (FileNotFoundException e) {
			ErrorDialog fnf = new ErrorDialog();
			fnf.create("Error", "File not found");
		}

		Point2D bulletPos = (Point2D) attacker.getAttribute(("Position")).getValue();
		Point2D targetPos = (Point2D) target.getAttribute(("Position")).getValue();

		System.out.println("bulletPos is " + bulletPos);
		System.out.println("TargetPos is " + targetPos);
		
		projectile.setAttributeValue("Position", bulletPos);
		projectile.setAttributeValue("ProjectileTargetPosition", targetPos);
		System.out.println("subtracted point is" + targetPos.subtract(bulletPos));
		projectile.setAttributeValue("ProjectileMaxDistance", targetPos.subtract(bulletPos));
		projectile.setAttributeValue("ProjectileTarget", target);

		//currentState.getComponentGraph().addComponentToGrid(projectile, bulletPos);
		toAdd.put(projectile, bulletPos);
		System.out.println("Projectile should be added to toAdd Map");
	}

	/**
	 * 
	 * @return a Component that represents a projectile
	 * @throws FileNotFoundException if the selected image files are not found
	 * 
	 */
	@SuppressWarnings("unchecked")
	private Component makeProjectile(Component attacker) throws FileNotFoundException {
		System.out.println("Making projectile");
		
		ProjectileFactory bulletFactory = new ProjectileFactory(attacker);
		Component projectile = new Component(bulletFactory.getMyAttributeData());
		projectile.setMyType("Projectile");
		return projectile;
	}
}
