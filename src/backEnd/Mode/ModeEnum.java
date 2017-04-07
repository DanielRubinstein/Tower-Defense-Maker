package backEnd.Mode;

public enum ModeEnum {
	AUTHOR, PLAYER;
	
	
	public static ModeEnum getNextMode(ModeEnum currentMode){
		switch(currentMode){
		case AUTHOR:
			return PLAYER;
		case PLAYER:
			return AUTHOR;
		}
		return null;
		
		
	}
	
	

}
