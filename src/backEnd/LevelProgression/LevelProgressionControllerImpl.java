package backEnd.LevelProgression;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import backEnd.GameData.State.PlayerStatusReader;
import backEnd.LevelProgression.LevelProgressionControllerEditor;
import backEnd.Mode.Mode;
import data.DataController;
import data.XMLReadingException;
import frontEnd.Skeleton.SplashScreens.SplashScreenFactory;
import frontEnd.Skeleton.SplashScreens.SplashScreenReader;
import frontEnd.Skeleton.SplashScreens.SplashScreenType;
import resources.constants.StringResourceBundle;

/**
 * 
 * @author Riley Nisbet
 * @author Juan
 *
 */
public class LevelProgressionControllerImpl
		implements LevelProgressionControllerReader, LevelProgressionControllerEditor {
	private Map<String, List<String>> gamesMap; // String gameName -> List of
												// Level names
	private DataController myDataController;
	private Mode myMode;
	private Consumer<SplashScreenReader> splashScreenLoader;
	private Consumer<Object> gameLoader;
	private SplashScreenFactory mySplashFactory;
	private static final StringResourceBundle strResources = new StringResourceBundle();

	public LevelProgressionControllerImpl(Mode mode, DataController dataController,
			Consumer<SplashScreenReader> splashScreenLoader, Consumer<Object> gameLoader) {
		this.myMode = mode;
		this.myDataController = dataController;
		this.splashScreenLoader = splashScreenLoader;
		this.gameLoader = gameLoader;
		this.mySplashFactory = new SplashScreenFactory(this);

		setGamesMap();
	}

	public void initiateSplashScreen(SplashScreenType type, PlayerStatusReader playerStatus) {
		SplashScreenReader splashScreen = mySplashFactory.get(type, myMode.getUserMode(), playerStatus);
		splashScreenLoader.accept(splashScreen);
	}

	@Override
	public List<String> getGameList() {

		return new ArrayList<String>(gamesMap.keySet());
	}

	@Override
	public List<String> getLevelList(String gameName) {
		return gamesMap.get(gameName);
	}

	public void saveGamesMap() {
		myDataController.saveGamesMap(gamesMap);
	}

	public List<String> getDirList(String filePath) {
		File file = new File(filePath);
		System.out.println(filePath);
		String[] directories = file.list(new FilenameFilter() {
			@Override
			public boolean accept(File current, String name) {
				return new File(current, name).isDirectory();
			}
		});

		return Arrays.asList(directories);
	}

	@Override
	public void setLevelList(String gameName, List<String> levelList) {
		gamesMap.put(gameName, levelList);
		saveGamesMap();
	}

	@Override
	public void addNewGame(String gameName) {
		gamesMap.put(gameName, new ArrayList<String>());

		File file = new File("data/games/" + gameName + "/templates/");
		file.mkdirs();

		file = new File("data/games/" + gameName + "/saves");
		file.mkdirs();

		saveGamesMap();
	}

	@Override
	public void removeGame(String gameName) {
		gamesMap.remove(gameName);
	}

	@Override
	public String getNextLevel() {
		List<String> levelPathsList = gamesMap.get(myMode.getGameMode());
		String nextLevel = null;
		for (int i = 0; i < levelPathsList.size(); i++) {
			if (levelPathsList.get(i).equals(myMode.getLevelMode())) {
				if (levelPathsList.size() > i + 1)
					nextLevel = levelPathsList.get(++i);
				break;
			}
		}
		return nextLevel;
	}

	// @Override
	public void addLevelToGame(String gameName, String level) {
		List<String> levelPathsList = gamesMap.get(gameName);
		if (!levelPathsList.contains(level))
			levelPathsList.add(level);
		System.out.println("adding level");
		gamesMap.put(gameName, levelPathsList);
		saveGamesMap();
	}

	@Override
	public void removeLevelFromGame(String gameName, String level) {
		List<String> levelPathsList = gamesMap.get(gameName);
		levelPathsList.remove(level);
		gamesMap.put(gameName, levelPathsList);
	}

	public Map<String, List<String>> getGamesMap() {
		return gamesMap;
	}

	public Mode getMode() {
		return myMode;
	}

	@Override
	public List<String> getModeCategories() {
		return Arrays.asList(strResources.getFromStringConstants("User"), strResources.getFromStringConstants("Game"),
				strResources.getFromStringConstants("Level"));
	}

	@Override
	public List<String> getOptions(String category) {
		switch (category) {
		case "User":
			return myMode.getAllUserModes();
		case "Game":
			return this.getGameList();

		default:
			return null;
		}
	}

	private void setGamesMap() {
		try {
			this.gamesMap = myDataController.loadGamesMapData();
			if (!gamesMap.containsKey(myMode.getGameMode())) {
				gamesMap.put(myMode.getGameMode(), new ArrayList<String>());
			}
			
			gamesMap = new HashMap<String, List<String>>();
			
			updateFromFileSystem();
			
			saveGamesMap();

		} catch (XMLReadingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void updateFromFileSystem()
	{
		updateGames();
		updateLevels();
	}

	private void updateLevels()
	{
		for (String game : gamesMap.keySet())
		{
			for (String level : getDirList(generateTemplatePath(game, "")))
			{
				if (!gamesMap.get(game).contains(level)) gamesMap.get(game).add(level);
			}
		}
	}

	private void updateGames()
	{
		for (String game : getDirList("data/games/"))
		{
			if (!gamesMap.containsKey(game)) gamesMap.put(game, new ArrayList<String>());
		}
	}

	@Override
	public void loadNextLevel() {
		gameLoader.accept(new File(generateTemplatePath(myMode.getGameMode(), getNextLevel())));
	}

	private String generateTemplatePath(String game, String level) {
		return "data/games/" + game + "/templates/" + level;
	}

	public void reloadLevel() {
		gameLoader.accept(new File("data/games/" + myMode.getGameMode() + "/templates/" + myMode.getLevelMode()));
	}

	@Override
	public boolean contains(String mode) {
		if (getGameList().contains(mode))
			return true;
		else {
			for (String gameName : getGameList()) {
				if (gamesMap.get(gameName).contains(mode))
					return true;
			}
		}

		return false;
	}

	@Override
	public List<String> getCurrentLevelList() {
		return gamesMap.get(myMode.getGameMode());
	}

	@Override
	public void addLevelToCurrentGame(String newName) {
		if (!gamesMap.get(myMode.getGameMode()).contains(newName)) {
			gamesMap.get(myMode.getGameMode()).add(newName);
		}

	}

	@Override
	public boolean existsNextLevel() {
		return getNextLevel() != null;
	}

	@Override
	public void loadFirstLevel() {
		String firstLevel = gamesMap.get(myMode.getGameMode()).get(0);
		gameLoader.accept(new File(generateTemplatePath(myMode.getGameMode(), firstLevel)));

	}

}
