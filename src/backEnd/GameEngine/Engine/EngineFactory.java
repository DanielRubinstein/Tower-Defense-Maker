package backEnd.GameEngine.Engine;

import java.util.ResourceBundle;

public class EngineFactory {
	
	private final static String RESOURCES_PATH = "resources/engineNames";
	private final static ResourceBundle myResources = ResourceBundle.getBundle(RESOURCES_PATH);
	
	public Engine getEngine(String string){
//		
//		String simpleName = Engine.class.getSimpleName();
//		String fullName = Engine.class.getName();
//		String basePath = fullName.substring(0, fullName.length() - simpleName.length());
//		System.out.println(basePath);
//		Object engine = null;
//		try {
//			engine = Class.forName(basePath + myResources.getString(simpleName)).newInstance();
//		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
//			engine = new NullEngine();
//		}
//		return (Engine)engine;
		switch(string){
		case "SpawnEngine": {
			return new SpawnEngine();
		}
		case "MoveEngine": {
			return new MoveEngine();
		}
		case "DeathEngine": {
			return new DeathEngine();
		}
		case "AttackEngine": {
			return new AttackEngine();
		}
		case "ProjectileEngine": {
			return new ProjectileEngine();
		}

		}
		return null;
	}

}
