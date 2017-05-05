package frontEnd.Skeleton.SplashScreens;

import java.util.HashMap;
import java.util.Map;

import backEnd.GameData.State.PlayerStatusReader;
import backEnd.LevelProgression.LevelProgressionControllerEditor;
import backEnd.LevelProgression.LevelProgressionControllerReader;
import javafx.event.Event;

public class SplashScreenFactory {
	
	private Map<SplashScreenType, SplashScreen> splashMap = new HashMap<SplashScreenType, SplashScreen>();
	
	public SplashScreenFactory(LevelProgressionControllerEditor myLPC){
		//instantiate all splash screens, use myLPC to specify action for each button
		splashMap.put(SplashScreenType.LEVEL_WON, new LevelWinSplashScreen((Event e) -> myLPC.reloadLevel(), (Event e) -> myLPC.loadNextLevel()));
		splashMap.put(SplashScreenType.GAME_WON, new GameWinSplashScreen((Event e) -> myLPC.reloadLevel(), (Event e) -> myLPC.loadFirstLevel()));
		splashMap.put(SplashScreenType.LEVEL_LOST, new LevelLossSplashScreen((Event e) -> myLPC.reloadLevel(), (Event e) -> myLPC.loadFirstLevel()));
	}
	
	public SplashScreenReader get(SplashScreenType type, String mode, PlayerStatusReader playerStatus){
		//System.out.println(type.toString());
		SplashScreen mySS = splashMap.get(type);
		//System.out.println(type.toString());
		if(mode.equalsIgnoreCase("Author")){
			mySS.addRestartLevelButton();
		}
		mySS.setStatusDisplayValues(playerStatus);
		return mySS;
	}

}
