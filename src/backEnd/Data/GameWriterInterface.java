package backEnd.Data;

import backEnd.GameDataInterface;

public interface GameWriterInterface {
	
	public void Save(GameDataInterface gameData, String filePath);

}
