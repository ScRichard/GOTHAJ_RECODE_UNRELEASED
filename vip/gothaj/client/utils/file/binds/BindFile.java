package vip.gothaj.client.utils.file.binds;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import vip.gothaj.client.Client;
import vip.gothaj.client.modules.Bind;
import vip.gothaj.client.modules.Bind.Ac;
import vip.gothaj.client.modules.Bind.Type;
import vip.gothaj.client.modules.Mod;
import vip.gothaj.client.utils.file.FileUtils;

public class BindFile extends FileUtils{

	private File file = new File("Gothaj", "binds.json");
	
	public BindFile() {
		read();
	}

	@Override
	public void write() {
		JsonObject binds = new JsonObject();
		for(Mod m : Client.INSTANCE.getModuleManager().getModules()) {
			if(m.getBind() == null) continue;
			JsonObject mod = new JsonObject();
			mod.addProperty("Key", m.getBind().getKey());
			mod.addProperty("Type", m.getBind().getType().name().toLowerCase());
			mod.addProperty("Ac", m.getBind().getAc().name().toLowerCase());
			binds.add(m.getName().toLowerCase(), mod);
		}
		
		try {
			PrintWriter s = new PrintWriter(new FileWriter(file));
			s.println(FileUtils.prettyGson.toJson(binds));
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void read() {
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			write();
		}
		
		try {
			JsonParser parser = new JsonParser();
			JsonObject content = (JsonObject) parser.parse(org.apache.commons.io.FileUtils.readFileToString(file, StandardCharsets.UTF_8));
			
			for(Mod m : Client.INSTANCE.getModuleManager().getModules()) {
				JsonObject mod = (JsonObject) content.get(m.getName().toLowerCase());
				
				if(mod == null) continue;
				
				Bind b = new Bind(mod.get("Key").getAsInt(), mod.get("Type").getAsString().equals("hold") ? Type.HOLD : Type.TOGGLE, mod.get("Ac").getAsString().equals("mouse") ? Ac.Mouse : Ac.Keyboard);
				
				m.setBind(b);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
