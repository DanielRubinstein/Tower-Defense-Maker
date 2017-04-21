package data;

import java.io.File;
import java.util.List;
import java.util.Map;

import backEnd.GameData.GameData;
import backEnd.LevelProgression.LevelProgressionControllerImpl;

public interface XMLReader {

	GameData loadGameStateData(String filePath, String gameName) throws XMLReadingException;

	GameData loadGameStateData(File gameFile) throws XMLReadingException;

	List<Map<String, ?>> loadUniversalGameData(String filePath) throws XMLReadingException;
	
	Map<String,List<String>> loadGamesMap(String filePath) throws XMLReadingException;

}