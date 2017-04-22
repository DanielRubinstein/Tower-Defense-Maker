package backEnd.GameEngine.Engine.Status;

public class StatusEffect {
	
	private Double myTime;
	private Double myFactor;
	
	public StatusEffect(Double time, Double factor) {
		myTime = time;
		myFactor = factor;
	}
	
	public Double getFactor() {
		return myFactor;
	}
	
	public Double getTime() {
		return myTime;
	}
	
	public void setFactor(Double myFactor) {
		this.myFactor = myFactor;
	}
	
	public void setTime(Double myTime) {
		this.myTime = myTime;
	}
}
