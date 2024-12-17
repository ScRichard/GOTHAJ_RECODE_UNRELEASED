package vip.gothaj.client.utils.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import vip.gothaj.client.utils.Wrapper;
import vip.gothaj.client.utils.client.ChatUtils;

public class ManagerUtils extends Wrapper {

	public static HashMap<String, List<Integer>> getBestItems(int stackSizeForBlocks, int stackSizeForProjectiles) {
		HashMap<String, List<Integer>> map = new HashMap();

		/*
		 * 0: Sword Slot 1: AxeSlot 2: Pickaxe slot 3: Shovel slot
		 */
		List<Integer> toolSlots = new ArrayList(Arrays.asList(-1, -1, -1, -1));

		List<Integer> armorSlots = new ArrayList(
				Arrays.asList(InventoryUtils.getStack(5) == null ? -1 : 5, InventoryUtils.getStack(6) == null ? -1 : 6,
						InventoryUtils.getStack(7) == null ? -1 : 7, InventoryUtils.getStack(8) == null ? -1 : 8));

		List<Integer> rodSlots = new ArrayList();
		List<Integer> blockSlots = new ArrayList();
		List<Integer> ignored = new ArrayList();
		int projectiles = 0;
		int blocks = 0;

		for (int slot : InventoryUtils.getStackedSlots()) {
			ItemStack item = mc.thePlayer.inventoryContainer.getSlot(slot).getStack();

			if (item.getItem() instanceof ItemSword || item.getItem() instanceof ItemAxe) {
				int o = item.getItem() instanceof ItemSword ? 0 : 1;

				if (toolSlots.get(o) == -1) {
					toolSlots.set(o, slot);
					continue;
				}
				if (InventoryUtils.getItemDamage(mc.thePlayer.inventoryContainer.getSlot(toolSlots.get(o))
						.getStack()) > InventoryUtils.getItemDamage(item)) {
					ignored.add(slot);
					continue;
				} else if (InventoryUtils.getItemDamage(mc.thePlayer.inventoryContainer.getSlot(toolSlots.get(o))
						.getStack()) < InventoryUtils.getItemDamage(item)) {
					ignored.add(toolSlots.get(o));
					toolSlots.set(o, slot);
					continue;
				} else {
					if (InventoryUtils.getToolMaterialRating(item, false) > InventoryUtils.getToolMaterialRating(
							mc.thePlayer.inventoryContainer.getSlot(toolSlots.get(o)).getStack(), false)) {
						ignored.add(toolSlots.get(o));
						toolSlots.set(o, slot);
					} else {
						ignored.add(slot);
					}
					continue;
				}
			}
			if (item.getItem() instanceof ItemPickaxe || item.getItem() instanceof ItemSpade) {
				int o = item.getItem() instanceof ItemPickaxe ? 2 : 3;
				if (toolSlots.get(o) == -1) {
					toolSlots.set(o, slot);
					continue;
				}
				if (InventoryUtils.getToolRating(mc.thePlayer.inventoryContainer.getSlot(toolSlots.get(o))
						.getStack()) > InventoryUtils.getToolRating(item)) {
					ignored.add(slot);
					continue;
				} else if (InventoryUtils.getToolRating(mc.thePlayer.inventoryContainer.getSlot(toolSlots.get(o))
						.getStack()) < InventoryUtils.getToolRating(item)) {
					ignored.add(toolSlots.get(o));
					toolSlots.set(o, slot);
					continue;
				} else {
					if (InventoryUtils.getToolMaterialRating(item, false) > InventoryUtils.getToolMaterialRating(
							mc.thePlayer.inventoryContainer.getSlot(toolSlots.get(o)).getStack(), false)) {
						ignored.add(toolSlots.get(o));
						toolSlots.set(o, slot);

					} else {
						ignored.add(slot);
					}

					continue;
				}

			}
			if (item.getItem() instanceof ItemFishingRod || item.getItem() instanceof ItemSnowball
					|| item.getItem() instanceof ItemEgg) {
				if (projectiles < stackSizeForProjectiles * 16) {
					if (item.getItem() instanceof ItemFishingRod) {
						stackSizeForProjectiles += 64;
					} else
						projectiles += item.stackSize;

					rodSlots.add(slot);
					continue;
				}
			}

			if (item.getItem() instanceof ItemBlock) {
				ItemBlock block = (ItemBlock) item.getItem();

				if (InventoryUtils.invalidBlocks.contains(block.getBlock())) {
					blockSlots.add(slot);
					continue;
				}

				if (blocks < stackSizeForBlocks * 64) {
					blocks += item.stackSize;
					blockSlots.add(slot);
					continue;
				}
			}

			if (item.getItem() instanceof ItemArmor) {
				ItemArmor armor = (ItemArmor) item.getItem();

				if (armorSlots.get(armor.armorType) == -1) {
					armorSlots.set(armor.armorType, slot);
					continue;
				}
				if (InventoryUtils.getProtection(item) > InventoryUtils
						.getProtection(InventoryUtils.getStack(armorSlots.get(armor.armorType)))) {
					ignored.add(armorSlots.get(armor.armorType));
					armorSlots.set(armor.armorType, slot);
					continue;
				} else if (InventoryUtils.getProtection(item) == InventoryUtils
						.getProtection(InventoryUtils.getStack(armorSlots.get(armor.armorType)))) {
					if (armorSlots.get(armor.armorType) != slot)
						ignored.add(slot);
					continue;
				}

			}
			if (!InventoryUtils.isIgnoredItem(item.getItem()))
				ignored.add(slot);
		}

		map.put("tools", toolSlots);
		map.put("armor", armorSlots);
		map.put("projectiles", rodSlots);
		map.put("blocks", blockSlots);
		map.put("ignored", ignored);

		return map;
	}

	public int getBlocksCount() {

		int blocksCount = 0;

		for (int slot : InventoryUtils.getStackedSlots()) {
			ItemStack item = mc.thePlayer.inventoryContainer.getSlot(slot).getStack();
			if (item.getItem() instanceof ItemBlock) {
				ItemBlock block = (ItemBlock) item.getItem();

				if (InventoryUtils.invalidBlocks.contains(block.getBlock())) {
					continue;
				}

				blocksCount += item.stackSize;
			}
		}

		return blocksCount;
	}

	public static ArrayList<Integer> getBestSlotsInChest(ContainerChest chest) {
		ArrayList<Integer> slots = new ArrayList();
		
		HashMap<String, List<Integer>> bestItemsInInv = getBestItems(10, 10);

		int swordSlot = -1;
		int axeSlot = -1;
		int spadeSlot = -1;
		int pickaxeSlot = -1;
		
		Integer[] bestArmor = new Integer[] {
				-1,-1,-1,-1
		};

		for (int i = 0; i < chest.inventorySlots.size(); i++) {
			final ItemStack item = chest.getLowerChestInventory().getStackInSlot(i);
			if(item == null) continue;

			if (item.getItem() instanceof ItemSword) {
				
				if (swordSlot == -1) {
					if(bestItemsInInv.get("tools").get(0) == -1) {
						swordSlot = i;
					}else {
						if (compareItemsDamage(item, InventoryUtils.getStack(bestItemsInInv.get("tools").get(0)))) {
							swordSlot = i;
						}
						
					}
					continue;
				}
				ItemStack thisItem = chest.getLowerChestInventory().getStackInSlot(swordSlot);

				if (compareItemsDamage(item, thisItem)) {
					swordSlot = i;
					continue;
				}
			} else if (item.getItem() instanceof ItemAxe) {
				if (axeSlot == -1) {
					if(bestItemsInInv.get("tools").get(1) == -1) {
						axeSlot = i;
					}else {
						if (compareItemsDamage(item, InventoryUtils.getStack(bestItemsInInv.get("tools").get(1)))) {
							axeSlot = i;
						}
						
					}
					continue;
				}
				ItemStack thisItem = chest.getLowerChestInventory().getStackInSlot(axeSlot);

				if (compareItemsDamage(item, thisItem)) {
					axeSlot = i;
					continue;
				}
			} else if (item.getItem() instanceof ItemSpade) {
				if (spadeSlot == -1) {
					if(bestItemsInInv.get("tools").get(3) == -1) {
						spadeSlot = i;
					}else {
						if (compareItemsRating(item, InventoryUtils.getStack(bestItemsInInv.get("tools").get(3)))) {
							spadeSlot = i;
						}
						
					}
					continue;
				}
				ItemStack thisItem = chest.getLowerChestInventory().getStackInSlot(spadeSlot);
				if (compareItemsRating(item, thisItem)) {
					spadeSlot = i;
					continue;
				}
				
			} else if (item.getItem() instanceof ItemPickaxe) {
				if (pickaxeSlot == -1) {
					if(bestItemsInInv.get("tools").get(2) == -1) {
						pickaxeSlot = i;
					}else {
						if (compareItemsRating(item, InventoryUtils.getStack(bestItemsInInv.get("tools").get(2)))) {
							pickaxeSlot = i;
						}
						
					}
					continue;
				}
				ItemStack thisItem = chest.getLowerChestInventory().getStackInSlot(pickaxeSlot);
				if (compareItemsRating(item, thisItem)) {
					pickaxeSlot = i;
					continue;
				}
			}else if (item.getItem() instanceof ItemArmor) {
				ItemArmor armor = (ItemArmor) item.getItem();

				if (bestArmor[armor.armorType] == -1) {
					if(bestItemsInInv.get("armor").get(armor.armorType) == -1) {
						bestArmor[armor.armorType] = i;
					}else {
						if (InventoryUtils.getProtection(item) > InventoryUtils.getProtection(InventoryUtils.getStack(bestItemsInInv.get("armor").get(armor.armorType)))) {
							bestArmor[armor.armorType] = i;
						}
					}
					continue;
				}
				
				if (InventoryUtils.getProtection(item) > InventoryUtils.getProtection(chest.getLowerChestInventory().getStackInSlot(bestArmor[armor.armorType]))) {
					bestArmor[armor.armorType] = i;
					continue;
				}

			}else if (item.getItem() instanceof ItemBlock) {
				ItemBlock block = (ItemBlock) item.getItem();

				if (InventoryUtils.invalidBlocks.contains(block.getBlock())) {
					continue;
				}

				slots.add(i);
			}else if (item.getItem() instanceof ItemFishingRod || item.getItem() instanceof ItemSnowball
					|| item.getItem() instanceof ItemEgg) {
				slots.add(i);
			}
			if(InventoryUtils.isIgnoredItem(item.getItem())) {
				slots.add(i);
			}
		}
		slots.add(swordSlot);
		slots.add(axeSlot);
		slots.add(spadeSlot);
		slots.add(pickaxeSlot);
		slots.addAll(Arrays.asList(bestArmor));
		
		slots.removeIf(a -> a == -1);
		slots.sort((a,a2) -> a < a2 ? -1 : 1);
		
		return slots;
	}

	private static boolean compareItemsDamage(ItemStack one, ItemStack last) {
		return (InventoryUtils.getItemDamage(one) > InventoryUtils.getItemDamage(last))
				|| (InventoryUtils.getItemDamage(one) == InventoryUtils.getItemDamage(last)
						&& InventoryUtils.getToolMaterialRating(one, false) > InventoryUtils.getToolMaterialRating(last, false));
	}
	private static boolean compareItemsRating(ItemStack one, ItemStack last) {
		return (InventoryUtils.getToolRating(one) > InventoryUtils.getToolRating(last))
				|| (InventoryUtils.getToolRating(one) == InventoryUtils.getToolRating(last)
						&& InventoryUtils.getToolMaterialRating(one, false) > InventoryUtils.getToolMaterialRating(last, false));
	}

}
