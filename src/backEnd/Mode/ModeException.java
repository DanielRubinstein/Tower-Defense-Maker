package backEnd.Mode;

@SuppressWarnings("serial")
public class ModeException extends RuntimeException {
	
	public ModeException(Mode mode, String message){
		super(String.format("Cannot preform action in %1$s Mode. Action: %2$s", mode.getUserModeString().toLowerCase() , message));
	}

}
