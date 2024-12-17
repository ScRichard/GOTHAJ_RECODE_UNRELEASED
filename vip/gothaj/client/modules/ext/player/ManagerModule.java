package vip.gothaj.client.modules.ext.player;

import java.util.HashMap;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemSnowball;
import vip.gothaj.client.event.EventListener;
import vip.gothaj.client.event.events.EventUpdate;
import vip.gothaj.client.modules.Category;
import vip.gothaj.client.modules.Mod;
import vip.gothaj.client.utils.client.Timer;
import vip.gothaj.client.utils.inventory.InventoryUtils;
import vip.gothaj.client.utils.inventory.ManagerUtils;
import vip.gothaj.client.utils.rotations.KillauraUtils;
import vip.gothaj.client.values.Value;
import vip.gothaj.client.values.settings.CategoryValue;
import vip.gothaj.client.values.settings.ModeValue;
import vip.gothaj.client.values.settings.NumberValue;
import vip.gothaj.client.values.settings.RangeValue;

public class ManagerModule extends Mod {

	private Timer timer = new Timer();
	
	private boolean open, packetSended;
	
	private int calcDelay = -1;

	public ManagerModule() {
		super("Manager", "Sorts your inventory, puts armour and throws the garbage", null, Category.PLAYER);
		slots.addSettings(swordSlot, rodSlot, axeSlot, spadeSlot, pickaxeSlot, blockSlot);
		this.addSettings(delay, mode, slots, blockStacks, projectilesStacks);
	}
	
	RangeValue delay = new RangeValue(this,"Delay", 150, 170, 0, 500, 1);
	
	public ModeValue mode = new ModeValue(this,"Mode", new Value[] {
		new Value(this, "Silent"), new Value(this, "Legit")
	});
	
	CategoryValue slots = new CategoryValue(this, "Slots");
	
	NumberValue swordSlot = new NumberValue(this, "Sword", 1, 1, 9, 1);
	NumberValue rodSlot = new NumberValue(this, "Rod", 2, 1, 9, 1);
	NumberValue axeSlot = new NumberValue(this, "Axe", 3, 1, 9, 1);
	NumberValue spadeSlot = new NumberValue(this, "Shovel", 4, 1, 9, 1);
	NumberValue pickaxeSlot = new NumberValue(this, "Pickaxe", 5, 1, 9, 1);
	NumberValue blockSlot = new NumberValue(this, "Blocks", 6, 1, 9, 1);
	
	NumberValue blockStacks = new NumberValue(this, "Maximum stacks of blocks", 5, 0, 10, 1);
	NumberValue projectilesStacks = new NumberValue(this, "Maximum stacks of projectiles", 2, 0, 10, 1);

	public void onDisable() {
		timer.reset();
		calcDelay = -1;
		open = false;
		packetSended = false;
	}

	@EventListener
	public void onUpdate(EventUpdate e) {
		HashMap<String, List<Integer>> slots = ManagerUtils.getBestItems((int) blockStacks.getValue(), (int) projectilesStacks.getValue());
		
		switch(mode.getActiveMode().getName().toLowerCase()) {
		case "legit":
			open = mc.currentScreen instanceof GuiInventory;
			break;
		case "silent":
			
			break;
		}
		if (open) {
			if(calcDelay == -1) {
				calcDelay = (int) KillauraUtils.calculateButterfly((int)delay.getMin(), (int)delay.getMax());
			}
			if (timer.hasTimeElapsed(calcDelay, true)) {
				
				calcDelay = -1;
				//Armor
				List<Integer> armor = slots.get("armor");
				for(int i = 0; i < armor.size(); i++) {
					if(5+i == armor.get(i) || armor.get(i) == -1) {
						continue;
					}
					InventoryUtils.switchToIndex(armor.get(i), 5+i);
					return;
				}
				
				
				List<Integer> tools = slots.get("tools");
				if (!slots.get("ignored").isEmpty()) {
					InventoryUtils.drop(slots.get("ignored").get(0));
					return;
				}

				if (tools.get(0) != -1 && swordSlot.getValue() + 35 != tools.get(0)) { // SWORD
					InventoryUtils.switchToIndex(tools.get(0), (int) (swordSlot.getValue() + 35));
					return;
				}
				if (tools.get(1) != -1 && axeSlot.getValue() + 35 != tools.get(1)) { // AXE
					InventoryUtils.switchToIndex(tools.get(1), (int) (axeSlot.getValue() + 35));
					return;
				}
				if (tools.get(2) != -1 && pickaxeSlot.getValue() + 35 != tools.get(2)) { // PICKAXE
					InventoryUtils.switchToIndex(tools.get(2), (int) (pickaxeSlot.getValue() + 35));
					return;
				}
				if (tools.get(3) != -1 && spadeSlot.getValue() + 35 != tools.get(3)) { // SHOWEL
					InventoryUtils.switchToIndex(tools.get(3), (int) (spadeSlot.getValue() + 35));
					return;
				}

				// Blocks
				if (!slots.get("blocks").isEmpty()) {
					if(InventoryUtils.getStack((int) (blockSlot.getValue() + 35)) == null || !slots.get("blocks").contains((int)(blockSlot.getValue()) + 35)) {
						InventoryUtils.switchToIndex(slots.get("blocks").get(0), (int) (blockSlot.getValue() + 35));
						return;
					}
					
				}
				//rods
				if ((InventoryUtils.getStack((int) (blockSlot.getValue() + 35)) == null
						|| !(InventoryUtils.getStack((int) (blockSlot.getValue() + 35)).getItem() instanceof ItemFishingRod
								&& InventoryUtils.getStack((int) (blockSlot.getValue() + 35)).getItem() instanceof ItemEgg
								&& InventoryUtils.getStack((int) (blockSlot.getValue() + 35)).getItem() instanceof ItemSnowball))
								&& !slots.get("projectiles").isEmpty()) {
					
					InventoryUtils.switchToIndex(slots.get("projectiles").get(0), (int) (rodSlot.getValue() + 35));
					return;
				}
				open = false;
			}
		}

	}
}
