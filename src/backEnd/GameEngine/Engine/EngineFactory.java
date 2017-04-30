package backEnd.GameEngine.Engine;

public class EngineFactory {

	public Engine getEngine(String string){
		//			
		//		String simpleName = Engine.class.getSimpleName();
		//		String fullName = Engine.class.getName();
		//		String basePath = fullName.substring(0, fullName.length() - simpleName.length());
		//		System.out.println(basePath);
		//      return (Engine) Reflection.createInstance(string);
		
		// TODO !!!!
		
		switch(string)
		{
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
		case "RulesEngine": {
			return new RulesEngine();
		}
		case "BaseCheckerEngine":{
			return new BaseCheckerEngine();
		}}
		return null;
	}
}
