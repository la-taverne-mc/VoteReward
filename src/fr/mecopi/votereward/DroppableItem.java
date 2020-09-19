package fr.mecopi.votereward;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class DroppableItem 
{
	private ItemStack itemStack;
	private Double Percentage;
	
	public DroppableItem(ItemStack itemstack, Double percentage)
	{
		itemStack = itemstack;
		Percentage = percentage;
		
		//Adding ratio
		
		ItemMeta itemMeta = itemStack.getItemMeta();
		List<String> itemLore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<String>();
		itemLore.add(ChatColor.GREEN + "Vous avez ".concat(String.valueOf(percentage)).concat(" % de chances"));
		itemLore.add(ChatColor.GREEN + "d'obtenir cet objet");
		itemMeta.setLore(itemLore);
		itemStack.setItemMeta(itemMeta);
	}
	
	//Getters
	
	public ItemStack getItemStack() {
		return itemStack;
	}
	public Double getPercentage() {
		return Percentage;
	}
	
	//Setters
	
	public void setPercentage(Double newPercentage) {
		Percentage = newPercentage;
	}
}
