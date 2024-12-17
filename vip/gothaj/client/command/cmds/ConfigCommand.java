package vip.gothaj.client.command.cmds;

import vip.gothaj.client.Client;
import vip.gothaj.client.command.Command;
import vip.gothaj.client.utils.client.ChatUtils;
import vip.gothaj.client.utils.file.FileUtils.ConfigType;
import vip.gothaj.client.utils.file.config.ConfigFile;

public class ConfigCommand extends Command {

	public ConfigCommand() {
		super("Config", new String[] {
				"config", "cfg"
		}, "Creates and loads your local and public configs");
	}

	@Override
	public void onCommand(String[] args) {
		if(args.length == 0) {
			ChatUtils.send(getUsage());
			return;
		}
		switch(args[0]) {
		case "load":
			if(args.length == 2) {
				ConfigFile config = Client.INSTANCE.getConfigManager().getConfig(args[1]);
				
				if(config == null || config.getType() == ConfigType.ONLINE) {
					ChatUtils.send("Config doesnt exists!");
					return;
				}
				Client.INSTANCE.getConfigManager().getActiveConfig().write();
				Client.INSTANCE.getConfigManager().setActiveConfig(config);
				config.read();
				ChatUtils.send("Config was successufully loaded!");
			}else ChatUtils.send(getUsage());
			break;
		case "save":
			if(args.length == 1) {
				ChatUtils.send("Your active config was saved!");
				Client.INSTANCE.getConfigManager().getActiveConfig().write();
			}else if(args.length >= 2) {
				ConfigFile config = Client.INSTANCE.getConfigManager().getConfig(args[1]);
				if(config == null || config.getType() == ConfigType.ONLINE) {
					ChatUtils.send("Config was successufully created!");
					Client.INSTANCE.getConfigManager().getConfigs().add(new ConfigFile(args[1]));
				} else {
					ChatUtils.send("Config was saved with name: "+ args[1]);
					config.write();
				}
			} else {
				ChatUtils.send(getUsage());
			}
			break;
		case "list":
			if(args.length == 1) {
				ChatUtils.send("Showing you list of Configs:");
				for(ConfigFile config : Client.INSTANCE.getConfigManager().getConfigs()) {
					ChatUtils.send("- " + config.getName() + ((Client.INSTANCE.getConfigManager().getActiveConfig() == config) ? " §aACTIVE" : ""));
				}
				
			}else {
				ChatUtils.send(getUsage());
			}
			break;
		}
	}

	@Override
	public String getUsage() {
		return ".config <load, save, list, onlinelist, onlineload> <name>";
	}

}
