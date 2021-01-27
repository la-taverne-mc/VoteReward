package fr.mecopi.votereward.managers;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.mecopi.votereward.BagClearManager;
import fr.mecopi.votereward.DroppableItem;
import fr.mecopi.votereward.Effect;
import fr.mecopi.votereward.GUIManager;
import fr.mecopi.votereward.Main;
import fr.mecopi.votereward.items.Bag;
import fr.mecopi.votereward.items.CustomItems;
import fr.mecopi.votereward.items.CustomPotion;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class VoteManager
{
	public enum validMinable 
	{
		STONE,
		GOLD_ORE,
		IRON_ORE,
		COAL_ORE,
		LAPIS_ORE,
		DIAMOND_ORE,
		REDSTONE_ORE,
		GLOWSTONE,
		EMERALD_ORE,
		NETHER_QUARTZ_ORE,
		DIORITE,
		GRANITE,
		ANDESITE,
		COBBLESTONE
	}
	
	
	
	public static List<DroppableItem> droppableItemsList = new ArrayList<DroppableItem>();
	public static List<Effect> potionEffects = new ArrayList<Effect>();
	public static List<DroppableItem> droppableItems = new ArrayList<DroppableItem>();
	public static List<CustomPotion> customPotionList = new ArrayList<CustomPotion>();
	public static List<Bag> rewardBags = new ArrayList<Bag>();
	public static List<GUIManager> guiManagers = new ArrayList<GUIManager>();
	public static List<ItemStack> itemStackFromID = new ArrayList<ItemStack>();

	public static String sendSuccessMessage(String Message) {
		return new String(ChatColor.translateAlternateColorCodes('&', "[&aVoteReward&f] " + Message));
	}
	public static String sendErrorMessage(String Message) {
		return new String(ChatColor.translateAlternateColorCodes('&', "[&cVoteReward&f] " + Message));
	}
	public static void sendBarMessage(Player player, String Message) {
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Message));
	}
	public static Effect getEffectByPlayer(UUID Player)
	{
		for(Effect effect : potionEffects)
		{
			if(effect.getOwner().equals(Player))
				return effect;
		}
		return null;
	}
	public static void InitIDS()
	{
		itemStackFromID.add(VoteManager.customPotionList.get(0).getItemStack());
		itemStackFromID.add(VoteManager.customPotionList.get(1).getItemStack());
		itemStackFromID.add(VoteManager.customPotionList.get(2).getItemStack());
		itemStackFromID.add(VoteManager.customPotionList.get(3).getItemStack());
		itemStackFromID.add(VoteManager.customPotionList.get(4).getItemStack());
		itemStackFromID.add(VoteManager.customPotionList.get(5).getItemStack());
		itemStackFromID.add(VoteManager.customPotionList.get(6).getItemStack());
		itemStackFromID.add(VoteManager.customPotionList.get(7).getItemStack());
		itemStackFromID.add(CustomItems.cookedBear);
		itemStackFromID.add(CustomItems.cookedHorse);
		itemStackFromID.add(CustomItems.fireworkRocket);
		itemStackFromID.add(CustomItems.fortuneBook);
		itemStackFromID.add(CustomItems.giantBoots);
		itemStackFromID.add(CustomItems.goblinPickaxe);
		itemStackFromID.add(CustomItems.indianSpears);
		itemStackFromID.add(CustomItems.mendingBook);
		itemStackFromID.add(CustomItems.rawBear);
		itemStackFromID.add(CustomItems.rawHorse);
		itemStackFromID.add(CustomItems.silkTouchBook);
		itemStackFromID.add(CustomItems.ULU);
		itemStackFromID.add(CustomItems.unbreakingHoe);
	}
	public static String convertTime(int Time) {
		String sTime = "";
		if (Time >= 3600) {
			sTime += String.valueOf(Time / 3600).concat("h");
			Time = Time % 3600;
		}
		if (Time >= 60) {
			sTime += String.valueOf(Time / 60).concat("min");
			Time = Time % 60;
		}
		if (Time >= 1) {
			sTime += String.valueOf(Time).concat("s");
		}
		return sTime;
	}
	public static void droppableItemsSetting()
	{
		int Multiplicator = 1;
		double totalPercent = 0;
		for(DroppableItem droppableItem : droppableItemsList)
			totalPercent += droppableItem.getPercentage();
		if(totalPercent > 100)
		{
			Bukkit.getConsoleSender().sendMessage(sendErrorMessage("La configuration du taux des drops est erronee"));
			Bukkit.getPluginManager().disablePlugin(Main.Instance);
			return;
		}
		for(DroppableItem droppableItem : droppableItemsList)
		{
			int integerPart = droppableItem.getPercentage().intValue();
			String tempDecimal = String.valueOf(droppableItem.getPercentage() - integerPart);
			String Decimal = "";
			for(int i = tempDecimal.indexOf('.')+1; i < tempDecimal.length(); i++) { Decimal += tempDecimal.charAt(i); }
			if(!Decimal.equals("0"))
			{
				int tempMultiplicator = 1;
				for(int i = 0; i < Decimal.length(); i++)
					tempMultiplicator = (tempMultiplicator * 10);
				if(Multiplicator < tempMultiplicator)
					Multiplicator = tempMultiplicator;
			}
		}
		for(DroppableItem droppableItem : droppableItemsList)
		{
			for(int i = 0; i < (droppableItem.getPercentage() * Multiplicator); i++)
				droppableItems.add(droppableItem);
		}
	}
	public static boolean isCustomPotion(ItemStack consummedItem)
	{
		if(consummedItem == null)
			return false;
		if(!consummedItem.getType().equals(Material.POTION))
			return false;
		for(DroppableItem droppableItem : droppableItemsList)
		{
			if(droppableItem.getItemStack().getItemMeta().getDisplayName().equals(consummedItem.getItemMeta().getDisplayName()))
				return true;
		}
		return false;
	}
	public static CustomPotion getCustomPotion(ItemStack consummedItem)
	{
		for(CustomPotion customPotion : customPotionList)
		{
			if(customPotion.getItemStack().getItemMeta().getDisplayName().equals(consummedItem.getItemMeta().getDisplayName()))
				return customPotion;
		}
		return null;
	}
	public static Bag getBagByOwner(String OwnerName)
	{
		for(Bag bag : rewardBags)
		{
			if(bag.getOwner().equals(OwnerName))
				return bag;
		}
		return null;
	}
	public static GUIManager getGUIManager(Player player)
	{
		for(GUIManager guiManager : guiManagers)
		{
			if(guiManager.getOwner().equals(player))
				return guiManager;
		}
		return null;
	}
	public static int itemIsIn(String itemName)
	{
		if(itemName.equals(""))
			return -1;
		for(ItemStack item : itemStackFromID)
		{
			if(item.getItemMeta().getDisplayName().equals(itemName))
				return itemStackFromID.indexOf(item);
		}
		return -1;
	}
	public static void sendItemToPlayer(String Player, int repetitionNumber)
	{
		for(int c = 0; c < repetitionNumber; c++)
		{
			int Randomized = new Random().nextInt(droppableItems.size());
			ItemStack pickedItem = droppableItems.get(Randomized).getItemStack().clone();
			ItemMeta pickedItemMeta = pickedItem.getItemMeta();
			List<String> correctedLore = new ArrayList<String>();
			for(int i = 0; i < pickedItemMeta.getLore().size()-2; i++) { correctedLore.add(pickedItemMeta.getLore().get(i)); }
			LocalDate itemLocalDate = LocalDate.now();
			itemLocalDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			itemLocalDate = itemLocalDate.plusDays(BagClearManager.Day);
			itemLocalDate = itemLocalDate.plusMonths(BagClearManager.Month);
			itemLocalDate = itemLocalDate.plusYears(BagClearManager.Year);
			String[] IDS = itemLocalDate.toString().split("-");
			pickedItemMeta.setLocalizedName(IDS[2].concat("/".concat(IDS[1].concat("/".concat(IDS[0])))));
			correctedLore.add("Expiration : ".concat(IDS[2].concat("/".concat(IDS[1].concat("/".concat(IDS[0]))))));
			pickedItemMeta.setLore(correctedLore);
			pickedItem.setItemMeta(pickedItemMeta);
			if(getBagByOwner(Player) != null)
				getBagByOwner(Player).getInventory().add(pickedItem);
			else
			{
				FileManager.CreateBag(Player);
				getBagByOwner(Player).getInventory().add(pickedItem);
			}
		}
		if(Bukkit.getPlayer(Player) != null)
		{
			if(repetitionNumber > 1)
				Bukkit.getPlayer(Player).sendMessage(sendSuccessMessage("Vous avez obtenu ".concat(repetitionNumber + "").concat(" récompenses, consultez votre sac")));
			else
				Bukkit.getPlayer(Player).sendMessage(sendSuccessMessage("Vous avez obtenu une récompense, consultez votre sac"));
		}
	}
}
