package backEnd.GameEngine.Engine;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import backEnd.GameData.GameData;
import backEnd.GameData.State.Component;
import backEnd.GameData.State.ComponentGraph;
import backEnd.GameData.State.Tile;
import backEnd.GameData.State.TileGrid;
import frontEnd.CustomJavafxNodes.ErrorDialog;
import javafx.geometry.Point2D;
import resources.constants.StringResourceBundle;

/**
 * Deals with firing projectiles from towers at targets
 * @author Christian Martindale
 */

public class AttackEngine implements Engine {

	private double masterTime;
	private GameData myGameData;
	private ProjectileFactory myProjectileFactory;
	private StringResourceBundle STRING_RESOURCES = new StringResourceBundle();
	private boolean tileGroupsInitialized;
	private List<Set<Tile>> myTileGroups;
	private ComponentGraph myComponentGraph;

	@Override
	public void gameLoop(GameData gameData, double stepTime) {
		myGameData = gameData;
		masterTime = gameData.getGameTime();
		try {
			myProjectileFactory = new ProjectileFactory();
		} catch (FileNotFoundException e) {
			ErrorDialog fnf = new ErrorDialog();
			fnf.create("Error", "Image File Not Found");
		}
		
		myComponentGraph = gameData.getState().getComponentGraph();
		Map<Component, Component> attackersAndTargets=new HashMap<Component, Component>();
		
		for (Component attacker : myComponentGraph.getAllComponents()) {
			boolean isTower      = attacker.getAttribute(STRING_RESOURCES.getFromAttributeNames("Type")).getValue().equals(STRING_RESOURCES.getFromValueNames("TowerType"));
			boolean isEnemy 	 = attacker.getAttribute(STRING_RESOURCES.getFromAttributeNames("Type")).getValue().equals(STRING_RESOURCES.getFromValueNames("EnemyType"));
			boolean isProjectile = attacker.getAttribute(STRING_RESOURCES.getFromAttributeNames("Type")).getValue().equals(STRING_RESOURCES.getFromValueNames("ProjectileType"));
			if (isTower) {
				if (masterTime % ((Double) attacker.getAttribute(STRING_RESOURCES.getFromAttributeNames("FireRate")).getValue()/1000) <= stepTime) { 
					List<Component> targets = getTargetList(attacker);
					for (Component potentialTarget : targets) {
						if (potentialTarget.getAttribute(STRING_RESOURCES.getFromAttributeNames("Type")).getValue().equals(STRING_RESOURCES.getFromValueNames("EnemyType"))) {
							System.out.println("added an enemy to targetsList in AttackEngine");
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

	/**
	 * Gets target list based on tower attack variable
	 * @param attacker
	 * @return
	 */
	private List<Component> getTargetList(Component attacker) {
		String targetType = attacker.<String>getAttribute("TargetSelector").getValue();
		TileGrid tileGrid = myGameData.getState().getTileGrid();
		//targetType = "Radius";
		if(targetType.equals("Group")){
			if(!tileGroupsInitialized){
				myTileGroups = myGameData.getState().getTileGrid().getTileGroups();
				tileGroupsInitialized = true;
			}
			for(Set<Tile> tempSet : myTileGroups){
				if(tempSet.contains(tileGrid.getTileByScreenPosition(attacker.<Point2D>getAttribute("Position").getValue()))){
					List<Component> targets = new ArrayList<Component>();
					List<Component> potentialTargets = myComponentGraph.getAllComponents();
					for (Component component : potentialTargets) {
						if (tempSet.contains(tileGrid.getTileByScreenPosition(component.<Point2D>getAttribute("Position").getValue()))) {
							targets.add(component);
						}
					}
					return targets;
				}
			}
			System.out.println(this.getClass().getSimpleName() + ": Tile not found");
			return null;
		} else if (targetType.equals("Radius")) {
			return myComponentGraph.getComponentsWithinRadius(attacker,(double) attacker.getAttribute(STRING_RESOURCES.getFromAttributeNames("FireRadius")).getValue());
		} else {
			return myComponentGraph.getComponentsWithinRadius(attacker,(double) attacker.getAttribute(STRING_RESOURCES.getFromAttributeNames("FireRadius")).getValue());
		}
		
	}
	
	/**
	 * Creates a projectile, sets its target, and adds it to ComponentGraph in State
	 * @param attacker the tower Component generating a projectile
	 * @param target the enemy Component the projectile will be fired at
	 */
	@SuppressWarnings("unchecked")
	public void addProjectileToState(Component attacker, Component target) {
		Component projectile;
		try {
			projectile = makeProjectile(attacker);
		} catch (FileNotFoundException e1) {
			ErrorDialog fnf = new ErrorDialog();
			fnf.create("Error", "File not found");
			return;
		}

		Object bulletPosObj=attacker.getAttribute(STRING_RESOURCES.getFromAttributeNames("Position")).getValue();
		Point2D bulletPos = (Point2D) bulletPosObj;
		Object targetPosObj=target.getAttribute(STRING_RESOURCES.getFromAttributeNames("Position")).getValue();
		Point2D targetPos = (Point2D) targetPosObj;
		
		projectile.setAttributeValue(STRING_RESOURCES.getFromAttributeNames("Position"), bulletPos);
		projectile.setAttributeValue(STRING_RESOURCES.getFromAttributeNames("ProjectileTargetPosition"), targetPos);
		projectile.setAttributeValue(STRING_RESOURCES.getFromAttributeNames("ProjectileTarget"), target);
		Point2D finalTargetPoint = targetPos.subtract(bulletPos);
		projectile.setAttributeValue(STRING_RESOURCES.getFromAttributeNames("ProjectileMaxDistance"), finalTargetPoint.magnitude());

		myGameData.getState().getComponentGraph().addComponentToGrid(projectile, bulletPos);
	}

	/**
	 * @return a Component that represents a projectile
	 * @throws FileNotFoundException if the selected image files are not found
	 */
	private Component makeProjectile(Component attacker) throws FileNotFoundException {
		Component projectile = myProjectileFactory.getProjectile(attacker);
		return projectile;
	}
}

