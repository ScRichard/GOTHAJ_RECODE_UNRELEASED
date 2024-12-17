package vip.gothaj.client.command.cmds;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import vip.gothaj.client.Client;
import vip.gothaj.client.command.Command;
import vip.gothaj.client.modules.Bind;
import vip.gothaj.client.modules.Bind.Ac;
import vip.gothaj.client.modules.Bind.Type;
import vip.gothaj.client.modules.Mod;
import vip.gothaj.client.utils.client.ChatUtils;

public class BindCommand extends Command{

	public BindCommand() {
		super("Bind", new String[] {"bind", "b"}, "Binds module to specific key on keyboard or mouse");
	}

	@Override
	public void onCommand(String[] args) {
		if(args.length <= 1 || args.length > 3) {
			ChatUtils.debug(args.length +"");
			ChatUtils.send(getUsage());
			return;
		}
		
		Mod m = Client.INSTANCE.getModuleManager().getModule(args[0]);
		
		if(m == null) {
			ChatUtils.send("Module does not exist with name: "+args[0]);
			return;
		}
		
		if(Keyboard.getKeyIndex(args[1].toUpperCase()) == 0 && Mouse.getButtonIndex(args[1].toUpperCase()) == -1) {
			ChatUtils.send(args[1].toUpperCase()+" "+Keyboard.getKeyIndex(args[1].toUpperCase()) + " "+  Mouse.getButtonIndex(args[1].toUpperCase()) +" "+ Keyboard.getKeyIndex("r"));
			m.resetBind();
			ChatUtils.send("Bind was reseted for "+m.getName());
			return;
		}
		m.setBind(new Bind(Keyboard.getKeyIndex(args[1].toUpperCase()) == 0 ? Mouse.getButtonIndex(args[1].toUpperCase()) : Keyboard.getKeyIndex(args[1].toUpperCase()) , Keyboard.getKeyIndex(args[1].toUpperCase()) == 0 ? Ac.Mouse : Ac.Keyboard));
		
		if(args.length == 3) {
			
			m.getBind().setType(Type.HOLD.name().toLowerCase().equals(args[2]) ? Type.HOLD : Type.TOGGLE);
		}
		
		ChatUtils.send("Bind was set for "+ args[1].toUpperCase() + " with method " + m.getBind().getType().name());
	}

	@Override
	public String getUsage() {
		return ".bind <module> <key> (Optional: default Toggle) <Hold/Toggle>";
	}
	
}
