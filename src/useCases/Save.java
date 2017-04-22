package useCases;

import backEnd.GameData.GameDataInterface;
import backEnd.GameData.Rules.Rule;
import backEnd.GameData.State.State;
import backEnd.GameData.State.TileGridImpl;
import data.XMLWriter;

/**
 * This class represents the process after a user hits Save in the Environment. 
 * XML is written out and saved to a file detailing the State at the time of Save.
 * @author christianmartindale
 *
 */
/*
public class Save implements GameDataInterface{
	

	private GameDataInterface currentGame;
	private XMLWriter myXML;
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
		
		class XMLSave implements XMLWriter{

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
*/
