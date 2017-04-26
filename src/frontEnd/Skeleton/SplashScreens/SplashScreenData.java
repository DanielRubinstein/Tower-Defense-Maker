package frontEnd.Skeleton.SplashScreens;


public class SplashScreenData
{
	private String messageBody;
	private SplashScreenType type;
	private Runnable action;
	
	public SplashScreenData(String messageBody, SplashScreenType type, Runnable action)
	{
		this.messageBody = messageBody;
		this.type = type;
		this.action = action;
	}

	public String getMessageBody() {
		return messageBody;
	}
	
	public SplashScreenType getType()
	{
		return type;
	}

	public Runnable getOnContinue() {
		return action;
	}
	
}
