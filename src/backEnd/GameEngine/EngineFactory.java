package backEnd.GameEngine;

import java.util.ResourceBundle;

public class EngineFactory {
	
	private final static String RESOURCES_PATH = "resources/engineNames";
	private final static ResourceBundle myResources = ResourceBundle.getBundle(RESOURCES_PATH);
	
	public EngineFactory(){
		
	}
	public Engine getEngine(Behavior behavior){
		String simpleName = behavior.getClass().getSimpleName();
		String fullName = behavior.getClass().getName();
		String basePath = fullName.substring(0, fullName.length() - simpleName.length());
		Object engine = null;
		try {
			engine = Class.forName(basePath + myResources.getString(simpleName)).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			//TODO: Help!!!!!!
			e.printStackTrace();
		}
		System.out.println(engine.getClass().getSimpleName());
		return (Engine)engine;
	}

}
