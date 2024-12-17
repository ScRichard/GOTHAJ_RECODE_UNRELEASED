package vip.gothaj.client.modules.ext.player;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;
import vip.gothaj.client.event.EventListener;
import vip.gothaj.client.event.events.EventMouse;
import vip.gothaj.client.event.events.EventMoveButton;
import vip.gothaj.client.event.events.EventUpdate;
import vip.gothaj.client.modules.Category;
import vip.gothaj.client.modules.Mod;
import vip.gothaj.client.utils.client.Timer;
import vip.gothaj.client.utils.inventory.ManagerUtils;
import vip.gothaj.client.utils.rotations.KillauraUtils;
import vip.gothaj.client.values.settings.BooleanValue;
import vip.gothaj.client.values.settings.DescriptionValue;
import vip.gothaj.client.values.settings.RangeValue;

public class ChestStealerModule extends Mod {
	private Timer timer = new Timer();
	
	private int calcDelay = -1;
	
	private int lastSlot = -1;
	
	private DescriptionValue smartdesc = new DescriptionValue(this, "Makes it more legit, its very good for Polar");
	
	private BooleanValue smart = new BooleanValue(this ,"Smart delay", false, null);
	
	private RangeValue delay = new RangeValue(this, "Delay", 150, 170, 0, 500, 1);
	
	private BooleanValue randomized = new BooleanValue(this, "Randomized", false, null);
	private BooleanValue trashItems = new BooleanValue(this, "Ignore trash items", false, null);
	
	public BooleanValue silent = new BooleanValue(this, "Silent", false, null);
	
	public ChestStealerModule() {
		super("Chest Stealer", "Steals a chest", null, Category.PLAYER);
		this.addSettings(delay, silent,smart, smartdesc, randomized);
	}
	
	public void onDisable() {
		timer.reset();
		calcDelay = -1;
	}
	public ArrayList<Integer> getItems(ContainerChest chest) {
		ArrayList<Integer> slots = new ArrayList();
		for (int i = 0; i < chest.inventorySlots.size(); i++) {
			final ItemStack item = chest.getLowerChestInventory().getStackInSlot(i);
			if(item == null) continue;
			slots.add(i);
		}
		
		return slots;
	}
	
	@EventListener
	public void onUpdate(EventUpdate e) {
		if (mc.thePlayer.openContainer != null && mc.thePlayer.openContainer instanceof ContainerChest) {
			if(silent.isEnabled())
				mc.displayGuiScreen(null);
			final ContainerChest chest = (ContainerChest) mc.thePlayer.openContainer;
		
			ArrayList<Integer> slots =trashItems.isEnabled() ? ManagerUtils.getBestSlotsInChest(chest) : getItems(chest);

			int randomElementIndex = 0;
			if(randomized.isEnabled() && !slots.isEmpty())
				randomElementIndex = ThreadLocalRandom.current().nextInt(slots.size()) % slots.size();
			
			if(calcDelay == -1) {
				if(!slots.isEmpty())
					calculateDelay(slots.get(randomElementIndex));
				else calcDelay = (int) KillauraUtils.calculateButterfly((int)delay.getMinValue(), (int)delay.getMaxValue());
			}
			
			if (timer.hasTimeElapsed(calcDelay, true)) {
				calcDelay = -1;
				 
				if(slots.isEmpty()) {
					mc.thePlayer.closeScreen();
					return;
				}
					
				
				mc.playerController.windowClick(chest.windowId, slots.get(randomElementIndex), 0, 1, mc.thePlayer);
				lastSlot = slots.get(randomElementIndex);
			}
		}else {
			timer.reset();
			lastSlot = -1;
		}
	}
	@EventListener
	public void onMouseMove(EventMouse e) {
		if(silent.isEnabled() && mc.thePlayer.openContainer != null && mc.thePlayer.openContainer instanceof ContainerChest) e.setCancelled(true);
	}
	
	@EventListener
	public void onMoveButton(EventMoveButton e) {
		if(silent.isEnabled() && mc.thePlayer.openContainer != null && mc.thePlayer.openContainer instanceof ContainerChest) e.setCancelled(true);
	}
	public void calculateDelay(double nextSlot) {
		if(smart.isEnabled() && lastSlot != -1) {
			int rowLast = (int)(lastSlot / 9);
			
			int rowNext = (int)(nextSlot / 9);
			
			int diffY = Math.abs(rowLast - rowNext);
			
			int diffX = Math.abs(lastSlot % 9 - rowNext % 9);
			
			double distance = Math.sqrt(diffX*diffX + diffY*diffY);
			
			calcDelay = (int) ((int) KillauraUtils.calculateButterfly((int) ((int)delay.getMinValue()), (int) ((int)delay.getMaxValue())) + distance * 10);
		} else {
			calcDelay = (int) KillauraUtils.calculateButterfly((int)delay.getMinValue(), (int)delay.getMaxValue());
		}
	}
	
}
