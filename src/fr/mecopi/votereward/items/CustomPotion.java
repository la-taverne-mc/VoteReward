package fr.mecopi.votereward.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import net.md_5.bungee.api.ChatColor;

public class CustomPotion 
{
	private String Name;
	private String Type;
	private int Level;
	private List<String> Lore;
	private ItemStack potionStack;
	
	public CustomPotion(String name, String type, int level, List<String> lore)
	{
		List<String> correctedLore = new ArrayList<String>();
		for(String loreLine : lore) { correctedLore.add(ChatColor.translateAlternateColorCodes('&', loreLine)); }
		ItemStack tempStack = new ItemStack(Material.POTION);
		ItemMeta stackMeta = tempStack.getItemMeta();
		stackMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		stackMeta.setLore(correctedLore);
		stackMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		dataModelApplicator((PotionMeta)stackMeta, type.toLowerCase(), level);
		tempStack.setItemMeta(stackMeta);
		
		//Set properties
		
		Name = name;
		Type = type;
		Level = level;
		Lore = correctedLore;
		potionStack = tempStack;
	}
	
	//Getters
	
	private void dataModelApplicator(PotionMeta stackMeta, String type, int Level)
	{
		if(type.equals("fly"))
		{
			switch(Level)
			{
				case 150:
					stackMeta.setColor(Color.fromRGB(0, 255, 255));
					stackMeta.setCustomModelData(1);
					break;
				case 300:
					stackMeta.setColor(Color.fromRGB(0, 255, 255));
					stackMeta.setCustomModelData(2);
					break;
				case 600:
					stackMeta.setColor(Color.fromRGB(0, 255, 255));
					stackMeta.setCustomModelData(3);
					break;
				case 1200:
					stackMeta.setColor(Color.fromRGB(0, 255, 255));
					stackMeta.setCustomModelData(4);
					break;
				default:
					stackMeta.setColor(Color.fromRGB(0, 255, 255));
					stackMeta.setCustomModelData(1);
					break;
			}	
		}
		else if(type.equals("phantom"))
		{
			stackMeta.setColor(Color.fromRGB(0, 43, 112));
			stackMeta.setCustomModelData(5);
		}
		else if(type.equals("creeper"))
		{
			stackMeta.setColor(Color.fromRGB(0, 135, 6));
			stackMeta.setCustomModelData(6);
		}
		else if(type.equals("mining"))
		{
			stackMeta.setColor(Color.fromRGB(254, 194, 0));
			stackMeta.setCustomModelData(7);
		}
		else if(type.equals("swimming"))
		{
			stackMeta.setColor(Color.fromRGB(7, 162, 186));
			stackMeta.setCustomModelData(8);
		}
	}
	
	public String getName() {
		return new String(ChatColor.translateAlternateColorCodes('&', Name));
	}
	public String getType() {
		return Type;
	}
	public int getLevel() {
		return Level;
	}
	public List<String> getLore() {
		return Lore;
	}
	public ItemStack getItemStack() {
		return potionStack;
	}
	
}
