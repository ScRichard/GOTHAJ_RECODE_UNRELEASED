package vip.gothaj.client.event;

public enum EventPriority {
	LOWEST(-200),
	LOW(-100),
	MEDIUM(0),
	HIGH(100),
	HIGHEST(200);
	private int value;

	EventPriority(int  value){
	    this.value = value;
	}

	public int getValue(){
		return value;
	}
}
