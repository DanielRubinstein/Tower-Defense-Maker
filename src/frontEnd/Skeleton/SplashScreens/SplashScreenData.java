package frontEnd.Skeleton.SplashScreens;

public class SplashScreenData
{
	private String messageBody;
	private SplashScreenType type;
	
	public SplashScreenData(String messageBody, SplashScreenType type)
	{
		this.messageBody = messageBody;
		this.type = type;
	}

	public String getMessageBody() {
		return messageBody;
	}
	
	public SplashScreenType getType()
	{
		return type;
	}
	
}
