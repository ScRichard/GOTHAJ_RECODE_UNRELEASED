package vip.gothaj.client.command.cmds;

import vip.gothaj.client.command.Command;
import vip.gothaj.client.utils.client.ChatUtils;

public class DebugCommand extends Command{

	public DebugCommand() {
		super("Debug", new String[] { "debug" }, "Shows debug messages from client");
	}

	@Override
	public void onCommand(String[] args) {
		if(args.length != 0) {
			ChatUtils.send(getUsage());
			return;
		}
		ChatUtils.toggleDebug();
		ChatUtils.send("Debug mode: " + ChatUtils.isDebugMode());
	}
	@Override
	public String getUsage() {
		return ".debug";
	}

}
