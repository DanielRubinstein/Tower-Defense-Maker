package backEnd.Data;

import backEnd.GameDataInterface;

public interface XMLWriter {
	
	public void Save(GameDataInterface gameData, String filePath);

}
