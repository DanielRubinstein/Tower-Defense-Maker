package useCases;

import backEnd.GameDataInterface;
import backEnd.Rules;
import backEnd.Data.XMLWriterInterface;
import backEnd.State.State;
import backEnd.State.TileGrid;

/**
 * This class represents the process after a user hits Save in the Environment. 
 * XML is written out and saved to a file detailing the State at the time of Save.
 * @author christianmartindale
 *
 */
public class Save implements GameDataInterface{
	

	private GameDataInterface currentGame;
	private XMLWriterInterface myXML;
	private String myFilePath;
	
	public Save(){
		class GameInterface implements GameDataInterface{

			@Override
			public State getState() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Rules getRules() {
				// TODO Auto-generated method stub
				return null;
			}
		}
		currentGame = new GameInterface();
		
		class XMLSave implements XMLWriterInterface{

			@Override
			public void Save(GameDataInterface gameData, String filePath) {
				// TODO Auto-generated method stub
				
			}
			
		}
		myXML = new XMLSave();
	}


	public void saveState(GameDataInterface gameData, String filePath){
		myXML.Save(gameData, filePath);
	}
	
	@Override
	public State getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rules getRules() {
		// TODO Auto-generated method stub
		return null;
	}
}
