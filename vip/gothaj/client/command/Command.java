package vip.gothaj.client.command;

public abstract class Command {

	private String name;
	
	private String[] aliases;
	
	private String description;
	
	public Command(String name, String[] aliases, String description) {
		this.name = name;
		this.aliases = aliases;
		this.description = description;
	}

	public abstract void onCommand(String[] args);
	
	public abstract String getUsage();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getAliases() {
		return aliases;
	}

	public void setAliases(String[] aliases) {
		this.aliases = aliases;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
