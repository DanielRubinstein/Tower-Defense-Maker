package backEnd.Data;

import java.io.FileOutputStream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import backEnd.GameData;
import backEnd.GameDataInterface;
import backEnd.Environment.BankController;

public class XMLWriterImpl implements XMLWriter{
	private XStream xStream;
	
	public XMLWriterImpl(){
		xStream = new XStream(new StaxDriver());
		xStream.alias("GameData", GameData.class);
		xStream.alias("BankController", GameData.class);
	}
	
	public void saveGameStateData(GameDataInterface gameData, String filePath, String GameName){
		String gameDataXML = xStream.toXML(gameData);
		FileOutputStream fos;
		try {
		    fos = new FileOutputStream("myfilename");
		    fos.write("<?xml version=\"1.0\"?>".getBytes("UTF-8"));
		    byte[] bytes = gameDataXML.getBytes("UTF-8");
		    fos.write(bytes);
		} catch(Exception e) {
		    throw new GameFileException();
		}
		fos.close();
	}
	
	public void saveUniversalGameData(BankController bankController, String filePath){
		
	}

}
