package backEnd.Mode;

public interface ModeEditor extends ModeReader{

	public void changeMode();

	public Object play();

	public Object pause();

	public Object fastForward();

	public Object toggleMode();
	
	
}
