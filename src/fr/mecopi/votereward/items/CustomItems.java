package fr.mecopi.votereward.items;

import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCookEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.mecopi.votereward.DroppableItem;
import fr.mecopi.votereward.managers.VoteManager;
import net.md_5.bungee.api.ChatColor;

public class CustomItems 
{
	public static ItemStack goblinPickaxe; //Patched
	public static ItemStack giantBoots; //Patched
	public static ItemStack unbreakingHoe; //Patched
	public static ItemStack mendingBook; //Patched
	public static ItemStack fortuneBook; //Patched
	public static ItemStack silkTouchBook; //Patched
	public static ItemStack fireworkRocket; //Patched
	public static ItemStack rawBear; //Patched
	public static ItemStack rawHorse; //Patched
	public static ItemStack cookedBear; //Patched
	public static ItemStack cookedHorse; //Patched
	public static ItemStack ULU; //Patched
	public static ItemStack indianSpears; //Not Patched
	
	public static void Init()
	{
		setGoblinPickaxe();
		setGiantBoots();
		setUnbreakingHoe();
		setMendingBook();
		setFortuneBook();
		setSilkTouchBook();
		setFireworkRocket();
		setRawBear();
		setRawHorse();
		setCookedBear();
		setCookedHorse();
		setULU();
		setIndianSpears();
	}
	private static void setGoblinPickaxe()
	{
		goblinPickaxe = new ItemStack(Material.GOLDEN_PICKAXE);
		ItemMeta gPItemMeta = goblinPickaxe.getItemMeta();
		gPItemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&bPioche de Gobelin"));
		gPItemMeta.setLore(Arrays.asList(ChatColor.YELLOW + "Cette pioche vous confère", ChatColor.YELLOW + "une précision accrue et", ChatColor.YELLOW + "vous permet d'extraire", ChatColor.YELLOW + "des minéraux précieux", ChatColor.YELLOW + "des roches"));
		gPItemMeta.setCustomModelData(1);
		goblinPickaxe.setItemMeta(gPItemMeta);
		VoteManager.droppableItemsList.add(new DroppableItem(goblinPickaxe, 0.15));
	}	
	private static void setGiantBoots()
	{
		giantBoots = new ItemStack(Material.LEATHER_BOOTS);
        ItemMeta itemMeta = giantBoots.getItemMeta();

        itemMeta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.fromString("ec3474c3-b57c-4fbf-ba1f-a7c5ac9292c5"), "generic.armor", 1, Operation.ADD_NUMBER, EquipmentSlot.FEET));
		itemMeta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(UUID.fromString("dcc66b53-a22c-48cc-afbd-274a02967392"), "generic.movementSpeed", 0.75, Operation.MULTIPLY_SCALAR_1, EquipmentSlot.FEET));
		itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemMeta.setDisplayName("§bBottes de Géant");
		itemMeta.setLore(Arrays.asList("§eCes bottes semblent émettre", "§eune certaine forme de magie", "§eet vous permettraient", "§ed'aller plus vite", "", "§cAttention la durabilité", "§cbaisse avec la distance", "§cparcourue"));
        itemMeta.setCustomModelData(1);
		giantBoots.setItemMeta(itemMeta);
        VoteManager.droppableItemsList.add(new DroppableItem(giantBoots, 0.05));
	}
	private static void setUnbreakingHoe()
	{
		unbreakingHoe = new ItemStack(Material.STONE_HOE);
        ItemMeta itemMeta = unbreakingHoe.getItemMeta();

        itemMeta.addEnchant(Enchantment.DURABILITY, 20, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemMeta.setLore(Arrays.asList(ChatColor.GRAY + "Solidité XX"));
		unbreakingHoe.setItemMeta(itemMeta);
        VoteManager.droppableItemsList.add(new DroppableItem(unbreakingHoe, 0.01));
	}
	private static void setMendingBook()
	{
		mendingBook = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta itemMeta =  (EnchantmentStorageMeta)mendingBook.getItemMeta();
        itemMeta.addEnchant(Enchantment.MENDING, 3, false);
        itemMeta.setLore(Arrays.asList(ChatColor.YELLOW + "Enchanté avec Raccommodage"));
		mendingBook.setItemMeta(itemMeta);
        VoteManager.droppableItemsList.add(new DroppableItem(mendingBook, 0.04));
	}
	private static void setFortuneBook()
	{
		fortuneBook = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta itemMeta =  (EnchantmentStorageMeta)fortuneBook.getItemMeta();
        itemMeta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 3, false);
        itemMeta.setLore(Arrays.asList(ChatColor.YELLOW + "Enchanté avec Fortune"));
		fortuneBook.setItemMeta(itemMeta);
        VoteManager.droppableItemsList.add(new DroppableItem(fortuneBook, 0.07));
	}
	private static void setSilkTouchBook()
	{
		silkTouchBook = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta itemMeta =  (EnchantmentStorageMeta)silkTouchBook.getItemMeta();
        itemMeta.addEnchant(Enchantment.SILK_TOUCH, 3, false);
        itemMeta.setLore(Arrays.asList(ChatColor.YELLOW + "Enchanté avec Toucher de Soie"));
        silkTouchBook.setItemMeta(itemMeta);
        VoteManager.droppableItemsList.add(new DroppableItem(silkTouchBook, 0.09));
	}
	private static void setFireworkRocket()
	{
		fireworkRocket = new ItemStack(Material.FIREWORK_ROCKET, 64);
        FireworkMeta itemMeta = (FireworkMeta)fireworkRocket.getItemMeta();
        itemMeta.setPower(3);
        fireworkRocket.setItemMeta(itemMeta);
        VoteManager.droppableItemsList.add(new DroppableItem(fireworkRocket, 0.15));
	}
	private static void setRawBear()
	{
		rawBear = new ItemStack(Material.BEEF);
        ItemMeta itemMeta = rawBear.getItemMeta();
        itemMeta.setDisplayName("§fOurs Cru");
        itemMeta.setLocalizedName("rawBear");
        itemMeta.setLore(Arrays.asList(ChatColor.YELLOW + "Cette viande semble se cuire au feu de camp"));
        itemMeta.setCustomModelData(2);
        rawBear.setItemMeta(itemMeta);
	}
	private static void setRawHorse()
	{
		rawHorse = new ItemStack(Material.BEEF);
        ItemMeta itemMeta = rawHorse.getItemMeta();
        itemMeta.setDisplayName("§fCheval Cru");
        itemMeta.setLore(Arrays.asList(ChatColor.YELLOW + "Cette viande semble se cuire au feu de camp"));
        itemMeta.setCustomModelData(1);
        rawHorse.setItemMeta(itemMeta);
	}
	private static void setCookedBear()
	{
		cookedBear = new ItemStack(Material.COOKED_BEEF);
        ItemMeta itemMeta = cookedBear.getItemMeta();
        itemMeta.setDisplayName("§fOurs Cuit");
        itemMeta.setLocalizedName("cookedBear");
        itemMeta.setCustomModelData(2);
        cookedBear.setItemMeta(itemMeta);
	}
	private static void setCookedHorse()
	{
		cookedHorse = new ItemStack(Material.COOKED_BEEF);
        ItemMeta itemMeta = cookedHorse.getItemMeta();
        itemMeta.setDisplayName("§fCheval Cuit");
        itemMeta.setCustomModelData(1);
        cookedHorse.setItemMeta(itemMeta);
	}
	private static void setULU()
	{
		ULU = new ItemStack(Material.STONE_AXE);
        ItemMeta itemMeta = ULU.getItemMeta();

        itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.fromString("1de4ca9e-fe66-4222-966d-73f226e8fecd"), "generic.attackDamage", 4, Operation.ADD_NUMBER, EquipmentSlot.HAND));
		itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.fromString("c26bf64f-1a1a-4425-bc4e-77dc56845f8b"), "generic.attackSpeed", -0.65, Operation.MULTIPLY_SCALAR_1, EquipmentSlot.HAND));
		itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.fromString("10cea9ed-2627-4a6e-b904-52228bbff57b"), "generic.attackDamage", 4, Operation.ADD_NUMBER, EquipmentSlot.OFF_HAND));
		itemMeta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.fromString("48c680ff-6d3a-4bff-b62c-2badd65cf045"), "generic.attackSpeed", -0.65, Operation.MULTIPLY_SCALAR_1, EquipmentSlot.OFF_HAND));
		itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemMeta.setDisplayName("§bUlu");
		itemMeta.setLore(Arrays.asList("§eUn couteau inuit", "§epermettant de", "§edépecer un ours"));
		itemMeta.setCustomModelData(1);
		ULU.setItemMeta(itemMeta);
		VoteManager.droppableItemsList.add(new DroppableItem(ULU, 0.18));
	}
	private static void setIndianSpears()
	{
		indianSpears = new ItemStack(Material.GOLDEN_SWORD);
        ItemMeta itemMeta = indianSpears.getItemMeta();

		itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemMeta.setDisplayName("§bLance Indienne");
		itemMeta.setLore(Arrays.asList("§ePermet d'obtenir du", "§esteak de cheval"));
		itemMeta.setCustomModelData(1);
		indianSpears.setItemMeta(itemMeta);
		VoteManager.droppableItemsList.add(new DroppableItem(indianSpears, 0.21));
	}
	
	
	//Events
	
	public static void GoblinPickaxeEvent(BlockBreakEvent e)
	{
		if(e.getBlock() != null && !e.isCancelled())
		{
			if(!e.getPlayer().getInventory().getItemInMainHand().getType().isAir() && e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&bPioche de Gobelin"))) //Cast error & check if it's goblin pickaxe
			{
				if(e.getBlock().getType().equals(Material.STONE))
				{
					e.setDropItems(false);
					int mineralRand = new Random().nextInt(65);
					if (mineralRand >= 0 && mineralRand <= 14) {
						e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.IRON_NUGGET, getRandomNumberInRange(7, 11)));
					}
					else if (mineralRand >= 15 && mineralRand <= 29) {
						e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.LAPIS_LAZULI, getRandomNumberInRange(6, 10)));
					}
					else if (mineralRand >= 30 && mineralRand <= 39) {
						e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.REDSTONE, getRandomNumberInRange(6, 10)));
					}
					else if (mineralRand >= 40 && mineralRand <= 49) {
						e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.GOLD_NUGGET, getRandomNumberInRange(6, 10)));
					}
					else if (mineralRand >= 50 && mineralRand <= 57) {
						e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.DIAMOND, getRandomNumberInRange(1, 2)));
					}
					else if (mineralRand >= 58 && mineralRand <= 64) {
						e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.EMERALD, getRandomNumberInRange(1, 2)));
					}
				}
				else if(e.getBlock().getType().equals(Material.NETHERRACK))
				{
					e.setDropItems(false);
					e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.QUARTZ, getRandomNumberInRange(1, 5)));
				}
			}
		}
	}
	public static void ULUEvent(EntityDeathEvent e)
	{
		if(e.getEntity().getType().equals(EntityType.POLAR_BEAR) && e.getEntity().getKiller() instanceof Player)
		{
			Player Killer = e.getEntity().getKiller();
			if(Killer.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ULU.getItemMeta().getDisplayName()))
			{
				Bukkit.getWorld(Killer.getLocation().getWorld().getName()).dropItemNaturally(e.getEntity().getLocation(), rawBear);
			}
		}
	}
	public static void IndianSpearEvent(EntityDeathEvent e)
	{
		if(e.getEntity().getType().equals(EntityType.HORSE) && e.getEntity().getKiller() instanceof Player)
		{
			Player Killer = e.getEntity().getKiller();
			if(Killer.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(indianSpears.getItemMeta().getDisplayName()))
			{
				Bukkit.getWorld(Killer.getLocation().getWorld().getName()).dropItemNaturally(e.getEntity().getLocation(), rawHorse);
			}
		}
	}
	public static void BakeCustomFood(BlockCookEvent e)
	{
		if(e.getSource().getItemMeta().getDisplayName().equals(rawBear.getItemMeta().getDisplayName()))
		{
			e.getResult().setItemMeta(cookedBear.getItemMeta().clone());
		}
		else if(e.getSource().getItemMeta().getDisplayName().equals(rawHorse.getItemMeta().getDisplayName()))
		{
			e.getResult().setItemMeta(cookedHorse.getItemMeta().clone());
		}
	}
	public static void CantBake(InventoryClickEvent e)
	{
		if(e.getInventory().getType().equals(InventoryType.PLAYER))
		{
			if(e.getInventory().getItem(1) != null)
			{
				e.setCancelled(VoteManager.itemIsIn(e.getInventory().getItem(1).getItemMeta().getDisplayName()) != -1 ? true : false);
				return;
			}
			if(e.getInventory().getItem(2) != null)
			{
				e.setCancelled(VoteManager.itemIsIn(e.getInventory().getItem(2).getItemMeta().getDisplayName()) != -1 ? true : false);
				return;
			}
			if(e.getInventory().getItem(3) != null)
			{
				e.setCancelled(VoteManager.itemIsIn(e.getInventory().getItem(3).getItemMeta().getDisplayName()) != -1 ? true : false);
				return;
			}
			if(e.getInventory().getItem(4) != null)
			{
				e.setCancelled(VoteManager.itemIsIn(e.getInventory().getItem(4).getItemMeta().getDisplayName()) != -1 ? true : false);
				return;
			}
		}
		if(e.getCurrentItem() != null && !e.getCurrentItem().getType().equals(Material.AIR))
		{
			if((e.getInventory().getType().equals(InventoryType.SMOKER) || e.getInventory().getType().equals(InventoryType.FURNACE) || e.getInventory().getType().equals(InventoryType.ANVIL) || e.getInventory().getType().equals(InventoryType.ENCHANTING) || e.getInventory().getType().equals(InventoryType.WORKBENCH)) && VoteManager.itemIsIn(e.getCurrentItem().getItemMeta().getDisplayName()) != -1)
			{
				e.setCancelled(true);
			}
		}
	}
	public static void EatCustomFood(PlayerItemConsumeEvent e)
	{
		Player player = e.getPlayer();
		if(e.getItem().getItemMeta().getDisplayName().equals(cookedBear.getItemMeta().getDisplayName()))
		{
			player.setFoodLevel(20);
		}
		else if(e.getItem().getItemMeta().getDisplayName().equals(rawBear.getItemMeta().getDisplayName()))
		{
			if((player.getFoodLevel() + 3) <= 20)
				player.setFoodLevel(player.getFoodLevel() + 3);
			else
				player.setFoodLevel(20);
			player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (20 * 15), 3));
		}
		else if(e.getItem().getItemMeta().getDisplayName().equals(cookedHorse.getItemMeta().getDisplayName()))
		{
			if((player.getFoodLevel() + 5) <= 20)
				player.setFoodLevel(player.getFoodLevel() + 5);
			else
				player.setFoodLevel(20);
		}
		else if(e.getItem().getItemMeta().getDisplayName().equals(rawHorse.getItemMeta().getDisplayName()))
		{
			if((player.getFoodLevel() + 3) <= 20)
				player.setFoodLevel(player.getFoodLevel() + 3);
			else
				player.setFoodLevel(20);
			player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, (20 * 15), 3));
		}
	}
	
	
	private static int getRandomNumberInRange(int min, int max) {
		return new Random().nextInt((max - min) + 1) + min;
	}
}
