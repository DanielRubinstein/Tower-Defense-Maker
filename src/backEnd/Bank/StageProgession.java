package backEnd.Bank;

public interface StageProgession {

	public void addLevelToQueue();
	
	//need to change return type to Level if we create a Level object
	public void getNextLevel();
}
