package vip.gothaj.client.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vip.gothaj.client.Client;
import vip.gothaj.client.command.cmds.BindCommand;
import vip.gothaj.client.command.cmds.ConfigCommand;
import vip.gothaj.client.command.cmds.DebugCommand;
import vip.gothaj.client.command.cmds.DeveloperCommand;
import vip.gothaj.client.event.EventListener;
import vip.gothaj.client.event.events.EventChat;
import vip.gothaj.client.utils.client.ChatUtils;

public class CommandManager {

	private List<Command> commands = new ArrayList<Command>();

	public CommandManager() {
		Client.INSTANCE.getEventBus().register(this);
		
		this.addCommands(
				new BindCommand(),
				new DebugCommand(),
				new DeveloperCommand(),
				new ConfigCommand()
				);
	}
	
	private void addCommands(Command...command) {
		this.commands.addAll(Arrays.asList(command));
	}
	@EventListener
	public void handleChatInput(EventChat event) {
		if(!event.getMessage().startsWith(".")) return;
		
		event.setCancelled(true);
		
		String message = event.getMessage().substring(1);
		
		String[] arguments = message.toLowerCase().split(" ");
		
		for(Command cmd : commands) {
			if(!Arrays.asList(cmd.getAliases()).contains(arguments[0])) {
				continue;
			}
			
			cmd.onCommand(Arrays.copyOfRange(arguments, 1, arguments.length));
			return;
		}
		
		ChatUtils.send("Command does not exists! Use .help for help");
	}
}

