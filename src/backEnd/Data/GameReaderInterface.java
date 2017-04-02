package backEnd.Data;

import java.io.File;

import backEnd.GameData;
import backEnd.GameDataInterface;

public interface GameReaderInterface {
	
	public GameData Load(File file);

}
