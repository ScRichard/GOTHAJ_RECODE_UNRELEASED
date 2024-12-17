package vip.gothaj.client.utils.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import vip.gothaj.client.utils.Wrapper;

public class InventoryUtils extends Wrapper {

	public static List<Block> invalidBlocks = Arrays.asList(Blocks.enchanting_table, Blocks.carpet, Blocks.glass_pane,
			Blocks.ladder, Blocks.web, Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.air, Blocks.water,
			Blocks.flowing_water, Blocks.lava, Blocks.ladder, Blocks.soul_sand, Blocks.ice, Blocks.packed_ice,
			Blocks.sand, Blocks.flowing_lava, Blocks.snow_layer, Blocks.chest, Blocks.ender_chest, Blocks.torch,
			Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.jukebox, Blocks.wooden_pressure_plate,
			Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate,
			Blocks.stone_button, Blocks.tnt, Blocks.wooden_button, Blocks.lever, Blocks.crafting_table, Blocks.furnace,
			Blocks.stone_slab, Blocks.wooden_slab, Blocks.stone_slab2, Blocks.brown_mushroom, Blocks.red_mushroom,
			Blocks.gold_block, Blocks.red_flower, Blocks.yellow_flower, Blocks.flower_pot, Blocks.gravel, Blocks.sand);
	public static boolean isInventoryOpen;
	
	
	public static boolean isIgnoredItem(Item item) {
		return (item instanceof ItemAppleGold) || (item instanceof ItemEnderPearl) || (item instanceof ItemPotion) || item == Items.arrow;
	}
	
	public static ItemStack getStack(int slot) {
		return mc.thePlayer.inventoryContainer.getSlot(slot).getHasStack() ? mc.thePlayer.inventoryContainer.getSlot(slot).getStack() : null;
	}
	
	public static void drop(int slot) {
		mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, 1, 4, mc.thePlayer);
	}

	public static void switchToIndex(int index, int newIndex) {
		mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, index, 1, 2, mc.thePlayer);
		mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, newIndex, 1, 2, mc.thePlayer);
	}

	public static float getToolRating(ItemStack itemStack) {
		float damage = getToolMaterialRating(itemStack, false);
		damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack) * 2.00F;
		damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch.effectId, itemStack) * 0.50F;
		damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, itemStack) * 0.50F;
		damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemStack) * 0.10F;
		damage += (itemStack.getMaxDamage() - itemStack.getItemDamage()) * 0.000000000001F;
		return damage;
	}

	public static float getItemDamage(ItemStack itemStack) {
		float damage = getToolMaterialRating(itemStack, true);
		damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack) * 1.25F;
		damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, itemStack) * 0.50F;
		damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemStack) * 0.01F;
		damage += (itemStack.getMaxDamage() - itemStack.getItemDamage()) * 0.000000000001F;

		if (itemStack.getItem() instanceof ItemSword) {
			damage += 0.2;
		}

		return damage;
	}

	public static float getBowDamage(ItemStack itemStack) {
		float damage = 5;
		damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemStack) * 1.25F;
		damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemStack) * 0.75F;
		damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemStack) * 0.50F;
		damage += EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemStack) * 0.10F;
		damage += itemStack.getMaxDamage() - itemStack.getItemDamage() * 0.001F;
		return damage;
	}
	
	public static float getProtection(ItemStack stack) {
		float prot = 0.0F;

		if (stack.getItem() instanceof ItemArmor) {
			
			ItemArmor armor = (ItemArmor) stack.getItem();
			
			prot = (float) (prot + armor.damageReduceAmount + ((100 - armor.damageReduceAmount)
					* EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack)) * 0.0075D);
			prot = (float) (prot
					+ EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, stack) / 100.0D);
			prot = (float) (prot
					+ EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, stack) / 100.0D);
			prot = (float) (prot + EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack) / 100.0D);
			prot = (float) (prot
					+ EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack) / 50.0D);
			prot = (float) (prot
					+ EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack) / 100.0D);
		}

		return prot;
	}
	
	// 5 6 7 8
	public static List<Integer> getStackedSlots() {
		List<Integer> slots = new ArrayList();
		for(int i = 44; i >= 0; i--) {
			if (!mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
				continue;
			}
			slots.add(i);
		}
		return slots;
	}

	public static float getToolMaterialRating(ItemStack itemStack, boolean checkForDamage) {
		final Item is = itemStack.getItem();
		float rating = 0;

		if (is instanceof ItemSword) {
			switch (((ItemSword) is).getToolMaterialName()) {
			case "WOOD":
				rating = 4;
				break;

			case "GOLD":
				rating = 4;
				break;

			case "STONE":
				rating = 5;
				break;

			case "IRON":
				rating = 6;
				break;

			case "EMERALD":
				rating = 7;
				break;
			}
		} else if (is instanceof ItemPickaxe) {
			switch (((ItemPickaxe) is).getToolMaterialName()) {
			case "WOOD":
				rating = 2;
				break;

			case "GOLD":
				rating = 2;
				break;

			case "STONE":
				rating = 3;
				break;

			case "IRON":
				rating = checkForDamage ? 4 : 40;
				break;

			case "EMERALD":
				rating = checkForDamage ? 5 : 50;
				break;
			}
			;
		} else if (is instanceof ItemAxe) {
			switch (((ItemAxe) is).getToolMaterialName()) {
			case "WOOD":
				rating = 3;
				break;

			case "GOLD":
				rating = 3;
				break;

			case "STONE":
				rating = 4;
				break;

			case "IRON":
				rating = 5;
				break;

			case "EMERALD":
				rating = 6;
				break;
			}
			;
		} else if (is instanceof ItemSpade) {
			switch (((ItemSpade) is).getToolMaterialName()) {
			case "WOOD":
				rating = 1;
				break;

			case "GOLD":
				rating = 1;
				break;

			case "STONE":
				rating = 2;
				break;

			case "IRON":
				rating = 3;
				break;

			case "EMERALD":
				rating = 4;
				break;
			}
		}

		return rating;
	}

}
