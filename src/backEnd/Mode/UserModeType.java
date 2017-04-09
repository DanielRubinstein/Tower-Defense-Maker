package backEnd.Mode;

public enum UserModeType{
	AUTHOR, PLAYER;
	
	public static UserModeType getNextMode(UserModeType currentMode){
		switch(currentMode){
		case AUTHOR:
			return PLAYER;
		case PLAYER:
			return AUTHOR;
		}
		return null;
		
		
	}
}
