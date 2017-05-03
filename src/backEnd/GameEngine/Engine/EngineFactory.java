package backEnd.GameEngine.Engine;

/**
 * Constructs Engines for use in GameProcessController
 * @author Alex Salas
 * 
 */
public class EngineFactory {

	public Engine getEngine(String string){

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
