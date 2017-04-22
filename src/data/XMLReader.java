package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import backEnd.GameData.GameData;
import backEnd.LevelProgression.LevelProgressionControllerImpl;

public interface XMLReader {

	GameData loadGameStateData(String filePath, String levelName) throws XMLReadingException, FileNotFoundException;

	List<Map<String, ?>> loadUniversalGameData(String filePath) throws XMLReadingException;
	
	Map<String,List<String>> loadGamesMap(String filePath) throws XMLReadingException;


}