package backEnd.Mode;

public class ModeException extends RuntimeException {
	
	public ModeException(ModeImpl mode, String message){
		super(String.format("Cannot preform action in %1$s Mode. Action: %2$s", mode.getUserModeString().toLowerCase() , message));
	}

}
