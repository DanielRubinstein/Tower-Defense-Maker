package backEnd.LevelProgression;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import backEnd.LevelProgression.LevelProgressionControllerEditor;
import backEnd.Mode.Mode;
import data.DataController;
import data.XMLReadingException;
import frontEnd.Skeleton.SplashScreens.SplashScreenData;

/**
 * 
 * @author Riley Nisbet
 * @author Juan
 *
 */
public class LevelProgressionControllerImpl implements LevelProgressionControllerReader, LevelProgressionControllerEditor
{
	private Map<String,List<String>> gamesMap; //String gameName -> List of Level names
	private DataController myDataController;
	private Mode myMode;
	private Consumer<SplashScreenData> splashScreenLoader;
	private Consumer<Object> gameLoader;
	
	private static final String LEVEL_TEMPLATE_PATH = "data/LevelTemplates/";
	
	public LevelProgressionControllerImpl(Mode mode, DataController dataController, Consumer<SplashScreenData> splashScreenLoader,
			Consumer<Object> gameLoader)
	{
		this.myMode = mode;
		this.myDataController = dataController;
		this.splashScreenLoader = splashScreenLoader;
		this.gameLoader = gameLoader;
		
		setGamesMap();
	}
	
	public void initiateSplashScreen(SplashScreenData data)
	{
		System.out.println("LPC line 46");
		splashScreenLoader.accept(data);
		System.out.println("LPC line 48");
	}
	
	@Override
	public List<String> getGameList(){
		return new ArrayList<String>(gamesMap.keySet());
	}
	
	
	@Override
	public List<String> getLevelList(String gameName)
	{	
		List<String> toRemove = new ArrayList<String>();
		
		//check to see if a level still exists as a template
		for (String level : gamesMap.get(gameName))
		{
			if (!getFullLevelList().contains(level))
			{
				toRemove.add(level);
			}
		}
		gamesMap.get(gameName).removeAll(toRemove);
		
		return gamesMap.get(gameName);
	}
	
	public void saveGamesMap()
	{
		myDataController.saveGamesMap(gamesMap);
	}
	
	@Override
	public List<String> getFullLevelList(){
		File file = new File(LEVEL_TEMPLATE_PATH);
		String[] directories = file.list(new FilenameFilter() {
		  @Override
		  public boolean accept(File current, String name) {
		    return new File(current, name).isDirectory();
		  }
		});
		
		/*List<String> allLevels = new ArrayList<String>();
		for (List<String> levels : gamesMap.values()){
			allLevels.addAll(levels);
		}*/
		return Arrays.asList(directories);
	}
	
	
	@Override
	public void setLevelList(String gameName, List<String> levelList){
		gamesMap.put(gameName, levelList);
	}
	
	
	@Override
	public void addNewGame(String gameName){
		gamesMap.put(gameName, new ArrayList<String>());
	}
	
	@Override
	public void removeGame(String gameName){
		gamesMap.remove(gameName);
	}
	
	@Override
	public String getNextLevel(){
		List<String> levelPathsList = gamesMap.get(myMode.getGameMode());
		String nextLevel = null;
		for (int i = 0; i < levelPathsList.size(); i++){
			if (levelPathsList.get(i).equals(myMode.getLevelMode())){
				nextLevel = levelPathsList.get(++i);
				break;
			}
		}
		return nextLevel;
	}
	
	@Override
	public void addLevelToGame(String gameName, String level){
		List<String> levelPathsList = gamesMap.get(gameName);
		levelPathsList.add(level);
		gamesMap.put(gameName, levelPathsList);
	}
	
	@Override
	public void removeLevelFromGame(String gameName, String level){
		List<String> levelPathsList = gamesMap.get(gameName);
		levelPathsList.remove(level);
		gamesMap.put(gameName, levelPathsList);
	}

	public Map<String,List<String>> getGamesMap(){
		return gamesMap;
	}
	
	public Mode getMode(){
		return myMode;
	}


	@Override
	public List<String> getModeCategories() {
		return Arrays.asList("User", "Game", "Level");
	}


	@Override
	public List<String> getOptions(String category) {
		switch(category){
		case "User":
			return myMode.getAllUserModes();
		case "Game":
			return this.getGameList();
		case "Level":
			return this.getFullLevelList();
		default:
			return null;
		}
	}
	

	private void setGamesMap() {
		try {
			this.gamesMap = myDataController.loadGamesMapData();
		} catch (XMLReadingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void loadNextGame()
	{
		System.out.println("LPC line 183");
		gameLoader.accept(getNextLevel());
		System.out.println("LPC line 185");
	}
}
