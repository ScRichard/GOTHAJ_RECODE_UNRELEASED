package vip.gothaj.client.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import vip.gothaj.client.modules.ext.client.ClickGuiModule;
import vip.gothaj.client.modules.ext.client.HudModule;
import vip.gothaj.client.modules.ext.client.SmoothRotationModule;
import vip.gothaj.client.modules.ext.combat.AutoRodModule;
import vip.gothaj.client.modules.ext.combat.KillAuraModule;
import vip.gothaj.client.modules.ext.combat.TeamsModule;
import vip.gothaj.client.modules.ext.movement.FlightModule;
import vip.gothaj.client.modules.ext.movement.NoFallModule;
import vip.gothaj.client.modules.ext.movement.SpeedModule;
import vip.gothaj.client.modules.ext.movement.SprintModule;
import vip.gothaj.client.modules.ext.player.AnnoyerModule;
import vip.gothaj.client.modules.ext.player.AntiFireModule;
import vip.gothaj.client.modules.ext.player.AutoHealModule;
import vip.gothaj.client.modules.ext.player.ChestStealerModule;
import vip.gothaj.client.modules.ext.player.ManagerModule;
import vip.gothaj.client.modules.ext.player.ScaffoldModule;
import vip.gothaj.client.modules.ext.visuals.ESPModule;
import vip.gothaj.client.modules.ext.visuals.ShaderESPModule;

public class ModuleManager {

	private ArrayList<Mod> modules = new ArrayList<Mod>();
	
	public ModuleManager() {
		this.addModules(
				new SprintModule(),
				new KillAuraModule(),
				new SpeedModule(),
				new ClickGuiModule(),
				new HudModule(),
				new ManagerModule(),
				new ESPModule(),
				new ChestStealerModule(),
				new FlightModule(),
				new ShaderESPModule(),
				new TeamsModule(),
				new SmoothRotationModule(),
				new AnnoyerModule(),
				new AntiFireModule(),
				new AutoHealModule(),
				new ScaffoldModule(),
				new NoFallModule(),
				new AutoRodModule()
				);
		modules.sort((m1, m2) -> m1.getName().compareTo(m2.getName()));
	}

	public void addModules(Mod...mods) {
		modules.addAll(Arrays.asList(mods));
	}
	public List<Mod> getModules(Category category) {
		return modules.stream().filter(m -> m.getCategory() == category).collect(Collectors.toList());
	}
	public Mod getModule(String name) {
		return modules.stream().filter(m -> m.getName().toLowerCase().replace(" ", "").equals(name.toLowerCase().replace(" ", ""))).findFirst().orElse(null);
	}
	public Mod getModule(Class clazz) {
		return modules.stream().filter(m -> m.getClass() == clazz).findFirst().orElse(null);
	}
	
	public ArrayList<Mod> getModules() {
		return modules;
	}
}
