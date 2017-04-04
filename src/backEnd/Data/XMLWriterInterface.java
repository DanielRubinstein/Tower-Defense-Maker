package backEnd.Data;

import backEnd.GameDataInterface;

public interface XMLWriterInterface {
	
	public void Save(GameDataInterface gameData, String filePath);

}
