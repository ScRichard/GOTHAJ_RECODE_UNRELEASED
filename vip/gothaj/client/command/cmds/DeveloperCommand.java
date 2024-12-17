package vip.gothaj.client.command.cmds;

import vip.gothaj.client.Client;
import vip.gothaj.client.command.Command;
import vip.gothaj.client.utils.client.ChatUtils;

public class DeveloperCommand extends Command{

	public DeveloperCommand() {
		super("Developer", new String[] {
				"dev", "developer"
		}, "Reloads everything in client (Do not use it)");
	}

	@Override
	public void onCommand(String[] args) {
		Client.INSTANCE.getThemeManager().getActiveTheme().setDefaults();
		if(args.length > 0) {
			ChatUtils.send(this.getUsage());
			return;
		}
		if(!ChatUtils.isDebugMode()) {
			ChatUtils.send("Sorry, but you must have enabled debug mode!");
			return;
		}
		
		Client.INSTANCE.onDisable();
		Client.INSTANCE.onEnable();
		ChatUtils.send("Client was successfuly reloaded!");
	}

	@Override
	public String getUsage() {
		return ".developer";
	}

}
