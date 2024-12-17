package vip.gothaj.client.scripts;

import java.io.File;
import java.util.HashMap;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import vip.gothaj.client.Client;
import vip.gothaj.client.event.Event;
import vip.gothaj.client.modules.Category;
import vip.gothaj.client.scripts.api.PlayerJS;
import vip.gothaj.client.scripts.api.WorldJS;

public class Script {
	
	private File file;
	
	private String name = "None", description = "None", author = "Unknown";
	
	private Category category;
	
	private String inside;
	
	private Context context;
	private Scriptable scope;
	
	private ScriptModule module;
	
	public HashMap<String ,Function> functions = new HashMap();

	public Script(File file, String inside) {
		this.file = file;
		this.inside = inside;
		
		context = Context.enter();
		scope = context.initStandardObjects();
        
		addBindings();
		
		context.evaluateString(scope, inside, file.getName(), 1, null);
	}
	
	private void addBindings() {
		PlayerJS p = new PlayerJS();
		WorldJS world = new WorldJS();
		ScriptableObject.putProperty(scope, "mod", Context.javaToJS(this, scope));
		
		ScriptableObject.putProperty(scope, "player", Context.javaToJS(p, scope));
		ScriptableObject.putProperty(scope, "world", Context.javaToJS(world, scope));
	}
	
	public void event(String name, Function function) {
		if(module == null) return;

		if(functions.containsKey(name.toLowerCase())) {
			return;
		}
		
		functions.put(name.toLowerCase(), function);
	}
	
	public Script register(String name, String description, String category, String author) {
		
		this.name = name;
		this.description = description;
		this.author = author;
		
		this.category = Category.CLIENT;
		
		for(Category c : Category.values()) {
			if(category.toLowerCase().equals(c.name().toLowerCase())) {
				this.category = c;
				break;
			}
		}
		
		
		module = new ScriptModule(this);
		Client.INSTANCE.getModuleManager().getModules().add(module);
		
		return this;
	}
	
	public void callEvent(String name, Event... args) {
		
		if(module == null) return;
		
		Function f = functions.get(name);
		
		if(f == null) {
			return;
		}
		f.call(context, f, f, args == null ? new Object[]{} : args);
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}
	

	public Context getContext() {
		return context;
	}

	public Scriptable getScope() {
		return scope;
	}

	public ScriptModule getModule() {
		return module;
	}

	public HashMap<String, Function> getFunctions() {
		return functions;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getInside() {
		return inside;
	}

	public void setInside(String inside) {
		this.inside = inside;
	}

	public Category getCategory() {
		return this.category;
	}

}
