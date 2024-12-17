package vip.gothaj.client.modules;

public class Bind {
	
	private int key;
	
	private Type type = Type.TOGGLE;
	private Ac ac;
	
	public Bind(int key, Ac ac) {
		this.key = key;
		this.ac = ac;
	}
	public Bind(int key, Type type, Ac ac) {
		this.key = key;
		this.type = type;
		this.ac = ac;
	}
	
	public enum Type {
		HOLD,
		TOGGLE
	}
	public enum Ac {
		Mouse,
		Keyboard
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	public Ac getAc() {
		
		return ac;
	}

}
