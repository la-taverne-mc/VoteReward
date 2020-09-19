package fr.mecopi.votereward.managers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.mecopi.votereward.BagClearManager;
import fr.mecopi.votereward.DroppableItem;
import fr.mecopi.votereward.Effect;
import fr.mecopi.votereward.Main;
import fr.mecopi.votereward.items.Bag;
import fr.mecopi.votereward.items.CustomItems;
import fr.mecopi.votereward.items.CustomPotion;
import net.md_5.bungee.api.ChatColor;

public class FileManager 
{
	private static File globalFolder = Main.Instance.getDataFolder();
	private static File effectFile = new File(globalFolder.getPath().replace("\\", "/").concat("/effects.yml"));
	private static File potionFile = new File(globalFolder.getPath().replace("\\", "/").concat("/potions.yml"));
	public static File bagsFolder = new File(globalFolder.getPath().replace("\\", "/").concat("/bags/"));
	
	public static void Init()
	{
		try 
		{
			if(!globalFolder.exists())
				globalFolder.mkdir();
			if(!effectFile.exists())
				effectFile.createNewFile();
			if(!bagsFolder.exists())
				bagsFolder.mkdir();
			if(!potionFile.exists())
				Main.Instance.saveResource("potions.yml", false);
		} catch (IOException e) { e.printStackTrace(); }
		ConfigInit();
		PotionInit();
		EffectsInit();
		CustomItems.Init();
		VoteManager.droppableItemsSetting();
		VoteManager.InitIDS();
		BagsInit();
	}
	public static void ConfigInit()
	{
		Main.Instance.saveDefaultConfig();
		FileConfiguration configurationFile = Main.Instance.getConfig();
		for(String Key : configurationFile.getKeys(false))
			VoteManager.droppableItemsList.add(new DroppableItem(new ItemStack(Material.getMaterial(Key.toUpperCase()), configurationFile.getInt(new String(Key).concat(".amount"))), configurationFile.getDouble(new String(Key).concat(".percent"))));
	}
	public static void PotionInit()
	{
		YamlConfiguration potionConfig = new YamlConfiguration();
		try { potionConfig.load(potionFile); } catch (Exception ex) { ex.printStackTrace(); }
		for(String Key : potionConfig.getKeys(false))
		{
			VoteManager.customPotionList.add(new CustomPotion(potionConfig.getString(new String(Key.concat(".name"))), potionConfig.getString(new String(Key.concat(".type"))), potionConfig.getInt(new String(Key.concat(".time"))), Arrays.asList(potionConfig.getString(new String(Key.concat(".lore"))).split("@"))));
			VoteManager.droppableItemsList.add(new DroppableItem(new CustomPotion(potionConfig.getString(new String(Key.concat(".name"))), potionConfig.getString(new String(Key.concat(".type"))), potionConfig.getInt(new String(Key.concat(".time"))), Arrays.asList(potionConfig.getString(new String(Key.concat(".lore"))).split("@"))).getItemStack(), potionConfig.getDouble(new String(Key.concat(".percent")))));
		}
	}
	public static void EffectsInit()
	{
		BufferedReader fileReader;
		String Line = "";
		try 
		{ 
			fileReader = new BufferedReader(new FileReader(effectFile)); 
			while((Line = fileReader.readLine()) != null)
			{
				Line = Line.replace(" ", "");
				VoteManager.potionEffects.add(new Effect(UUID.fromString(Line.split(":")[0]), Line.split(":")[1].split(",")[0], Integer.parseInt(Line.split(",")[1])));
			}
			fileReader.close();
		} catch (IOException e) { e.printStackTrace(); }
	}
	public static void saveEffects() //Save file for a reload issue
	{
		try
		{
			PrintWriter fileWriter = new PrintWriter(effectFile, "UTF-8");
			for(Effect effect : VoteManager.potionEffects)
				fileWriter.write(effect.getOwner().toString() + " : " + effect.getType() + ", " + effect.getRemainingTime());
			fileWriter.close();
		} 
		catch (IOException e) { e.printStackTrace(); }	
	}
	public static void BagsInit()
	{
		for(File playerBag : bagsFolder.listFiles())
		{
			YamlConfiguration bagConfig = new YamlConfiguration();
			List<ItemStack> bagContent = new ArrayList<ItemStack>();
			try { bagConfig.load(playerBag); } catch (Exception ex) { ex.printStackTrace(); } 
			for(String Key : bagConfig.getKeys(false))
			{
				if(bagConfig.getString(Key.concat(".ID")).equals("-1"))
				{
					ItemStack itemToAdd = new ItemStack(Material.getMaterial(bagConfig.getString(Key.concat(".type"))), bagConfig.getInt(Key.concat(".amount"))).clone();
					ItemMeta setExpirationDate = itemToAdd.getItemMeta();
					List<String> correctedLore = new ArrayList<String>();
					correctedLore.add("Expiration : ".concat(bagConfig.getString(Key.concat(".expiration"))));
					setExpirationDate.setLore(correctedLore);
					setExpirationDate.setLocalizedName(bagConfig.getString(Key.concat(".expiration")));
					itemToAdd.setItemMeta(setExpirationDate);
					bagContent.add(itemToAdd);
				}
				else
				{
					ItemStack itemToAdd = VoteManager.itemStackFromID.get(bagConfig.getInt(Key.concat(".ID"))).clone();
					ItemMeta setExpirationDate = itemToAdd.getItemMeta();
					List<String> correctedLore = setExpirationDate.getLore();
					if(correctedLore.get(correctedLore.size()-1).equals(ChatColor.GREEN + "d'obtenir cet objet"))
					{
						correctedLore.remove(correctedLore.size()-1);
						correctedLore.remove(correctedLore.size()-1);
					}
					correctedLore.add("Expiration : ".concat(bagConfig.getString(Key.concat(".expiration"))));
					setExpirationDate.setLore(correctedLore);
					setExpirationDate.setLocalizedName(bagConfig.getString(Key.concat(".expiration")));
					itemToAdd.setItemMeta(setExpirationDate);
					bagContent.add(itemToAdd);
				}
			}
			VoteManager.rewardBags.add(new Bag(bagContent, playerBag.getName().replace(".yml", "")));
		}
		BagClearManager.Init();
	}
	public static void CreateBag(String playerName)
	{
		File playerBag = new File(bagsFolder.getPath().concat("/" + playerName + ".yml"));
		try 
		{ playerBag.createNewFile(); } catch (IOException ex) { ex.printStackTrace(); }
		VoteManager.rewardBags.add(new Bag(new ArrayList<ItemStack>(), playerName));
	}
	public static void SaveBags()
	{
		try
		{
			for(Bag bag : VoteManager.rewardBags)
			{
				PrintWriter fileWriter = new PrintWriter(new File(globalFolder.getPath().replace("\\", "/").concat("/bags/".concat(bag.getOwner().concat(".yml")))), "UTF-8");
				int itemsIndex = 0;
				for(ItemStack item : bag.getInventory())
				{
					String Expiration;
					ItemMeta itemMeta = item.getItemMeta().clone();
					Expiration = itemMeta.getLocalizedName();
					fileWriter.write(new String(itemsIndex + ":\n"));
					fileWriter.write(new String("  type: ".concat(item.getType().name().concat("\n"))));
					fileWriter.write(new String("  amount: ".concat(String.valueOf(item.getAmount()).concat("\n"))));
					fileWriter.write(new String("  ID: ".concat(String.valueOf(VoteManager.itemIsIn(item.getItemMeta().getDisplayName()))).concat("\n")));
					fileWriter.write(new String("  expiration: ".concat(Expiration.concat("\n"))));
					itemsIndex++;
				}
				fileWriter.close();
			}
		}
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}
		
	}
}
