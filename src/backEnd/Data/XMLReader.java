package backEnd.Data;

import java.io.File;

import backEnd.GameDataInterface;

public interface XMLReader {
	
	public GameDataInterface Load(File file);

}
