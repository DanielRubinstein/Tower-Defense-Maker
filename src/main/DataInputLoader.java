package main;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import backEnd.Data.DataController;
import backEnd.Data.XMLReadingException;
import backEnd.GameData.GameData;
import frontEnd.Splash.StartingInput;
/**
 * Wrapper class to get GameData without using reflection and hardcoding a method name.
 * This class is much more extendible and hides any ugly implementation details
 * @author Tim
 *
 */
public class DataInputLoader {

	private GameData myGameData;
	public DataInputLoader(String s,DataController dataContr) throws XMLReadingException{
		myGameData = dataContr.generateGameData(s);
	}
	
	
	public DataInputLoader(File f,DataController dataContr) throws XMLReadingException{
		myGameData = dataContr.generateGameData(f);
	}
	public DataInputLoader(StartingInput input,DataController dataContr) throws XMLReadingException{
		myGameData = dataContr.generateGameData(input);
	}
	
	public DataInputLoader(Object o,DataController dataContr) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Constructor<? extends DataInputLoader> cons = this.getClass().getDeclaredConstructor(o.getClass(),dataContr.getClass());
		DataInputLoader d = cons.newInstance(o,dataContr);
		myGameData = d.getGameData();
	}


	public GameData getGameData(){
		return myGameData;	
	}
	
	
	
}
