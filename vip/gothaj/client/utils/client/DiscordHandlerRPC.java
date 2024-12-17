package vip.gothaj.client.utils.client;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

public class DiscordHandlerRPC {
	
	private static long created;
	
	public static void run() {
		DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((user) -> {
		      System.out.println("Welcome " + user.username + "#" + user.discriminator + "!");
		  }).build();
	    String applicationId = "1214901542499516476";
	    DiscordRPC.discordInitialize(applicationId, handlers, true);
	    created = System.currentTimeMillis() /1000;
	    new Thread(() -> {
	        while (!Thread.currentThread().isInterrupted()) {
	        	DiscordRPC.discordRunCallbacks();
	            try {
	                Thread.sleep(2000);
	            } catch (InterruptedException ignored) {}
	        }
	    }, "RPC-Callback-Handler").start();
	    
	}

	public static void update(String first, String second) {
		DiscordRichPresence.Builder b = new DiscordRichPresence.Builder(second);
		b.setBigImage("gothaj", "");
		b.setDetails(first);
		b.setStartTimestamps(created);
		
		DiscordRPC.discordUpdatePresence(b.build());
	}
	
}
