package vip.gothaj.client.utils.client;

import net.minecraft.util.ChatComponentText;
import vip.gothaj.client.utils.Wrapper;

public class ChatUtils extends Wrapper{

	private static String prefix = "§c[G]§r ";
	
	private static boolean debugMode = false;
	
	public static void send(String message) {
		mc.thePlayer.addChatMessage(new ChatComponentText(prefix+message));
	}
	
	public static void debug(String message) {
		if(debugMode)
			mc.thePlayer.addChatMessage(new ChatComponentText("DEBUG: "+message));
	}
	
	public static void toggleDebug() {
		debugMode = !debugMode;
	}

	public static boolean isDebugMode() {
		return debugMode;
	}
}
