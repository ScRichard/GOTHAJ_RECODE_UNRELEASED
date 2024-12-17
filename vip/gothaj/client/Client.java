package vip.gothaj.client;

import org.lwjgl.opengl.Display;

import de.florianmichael.viamcp.ViaMCP;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import vip.gothaj.client.clientsettings.ClientSettings;
import vip.gothaj.client.command.CommandManager;
import vip.gothaj.client.event.EventBus;
import vip.gothaj.client.modules.ModuleManager;
import vip.gothaj.client.scripts.ScriptManager;
import vip.gothaj.client.utils.client.ClientUtils;
import vip.gothaj.client.utils.client.DiscordHandlerRPC;
import vip.gothaj.client.utils.file.binds.BindFile;
import vip.gothaj.client.utils.file.config.ConfigManager;
import vip.gothaj.client.utils.file.themes.ThemeManager;
import vip.gothaj.client.utils.font.FontManager;
import vip.gothaj.client.utils.font.icons.IconUtils;
import vip.gothaj.client.utils.resource.ResourceUtils;
import vip.gothaj.client.utils.shader.impl.BloomUtils;
import vip.gothaj.client.utils.shader.impl.OutlineUtils;

public enum Client {

	INSTANCE;
	private EventBus eventBus;
	private ModuleManager moduleManager;
	private CommandManager commandManager;
	
	private ConfigManager configManager;
	
	private ThemeManager themeManager;
	
	private ClientSettings clientSettings;
	
	private BindFile binds;
	
	private FontManager fontManager;
	
	private ScriptManager scriptManager;
	
	private BloomUtils bloom = new BloomUtils();
	private OutlineUtils outline = new OutlineUtils();
	
	public void onEnable() {
		
		eventBus = new EventBus();
		
		clientSettings = new ClientSettings();
		
		moduleManager = new ModuleManager();
		commandManager = new CommandManager();

		fontManager = new FontManager();
		scriptManager = new ScriptManager();
		
		binds = new BindFile();
		
		configManager = new ConfigManager();
		
		themeManager = new ThemeManager();
		 
		DiscordHandlerRPC.run();
		
		IconUtils.init();
		ResourceUtils.loadResources();
		try {
			ViaMCP.create();
			ViaMCP.INSTANCE.initAsyncSlider();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Minecraft.getMinecraft().session = new Session("hashda", "0", "0", "mojang");
		DiscordHandlerRPC.update("Active in Menu", "");
		Display.setTitle("Gothaj Client | 1.8.9 | Version: " + ClientUtils.getVersion());
		
	}
	public void onDisable() {
		configManager.onShutdown();
		binds.write();
	}
	
	public ScriptManager getScriptManager() {
		return scriptManager;
	}
	public EventBus getEventBus() {
		return eventBus;
	}
	public ModuleManager getModuleManager() {
		return moduleManager;
	}
	public CommandManager getCommandManager() {
		return commandManager;
	}
	public FontManager getFontManager() {
		return fontManager;
	}
	public BloomUtils getBloom() {
		return bloom;
	}
	public ConfigManager getConfigManager() {
		return configManager;
	}
	public ThemeManager getThemeManager() {
		return themeManager;
	}
	public OutlineUtils getOutline() {
		return outline;
	}
	public void setOutline(OutlineUtils outline) {
		this.outline = outline;
	}
	public ClientSettings getClientSettings() {
		return clientSettings;
	}
	public BindFile getBinds() {
		return binds;
	}
}
