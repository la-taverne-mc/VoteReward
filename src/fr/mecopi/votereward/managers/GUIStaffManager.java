package fr.mecopi.votereward.managers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.mecopi.votereward.Main;

public class GUIStaffManager 
{
	public static void onClick(InventoryClickEvent e)
	{
		if(e.getView().getTitle().equals("Objets droppables"))
		{
			Player player = (Player)e.getWhoClicked();
			if(!VoteManager.isCustomPotion(e.getCurrentItem()))
			{
				if(e.getClickedInventory() != null && !e.getClickedInventory().getType().equals(InventoryType.PLAYER))
				{
					e.setCancelled(true);
					ClickType clickType = e.getClick();
					if(clickType.equals(ClickType.LEFT) && e.getCurrentItem() != null)
					{
						if(player.hasPermission(Permissions.addItem))
						{
							if(e.getCurrentItem().getMaxStackSize() == 1 || e.getCurrentItem().getAmount() < e.getCurrentItem().getMaxStackSize() )
								e.getCurrentItem().setAmount(e.getCurrentItem().getAmount() +1);
							else if(e.getCurrentItem().getAmount() == e.getCurrentItem().getMaxStackSize())
							{
								e.getClickedInventory().addItem(new ItemStack(e.getCurrentItem().getType(), 1));
							}
						}
						return;
					}
					else if(clickType.equals(ClickType.RIGHT) && e.getCurrentItem() != null)
					{
						if(player.hasPermission(Permissions.subItem))
						{
							if(e.getCurrentItem().getAmount() > 1)
								e.getCurrentItem().setAmount(e.getCurrentItem().getAmount() -1);
							else
								player.sendMessage(VoteManager.sendErrorMessage("Utilisez un clic de la molette si vous voulez supprimer un objet."));
						}
						return;
					}
					else if(clickType.equals(ClickType.MIDDLE) && e.getCurrentItem() != null)
					{
						if(player.hasPermission(Permissions.delItem))
						{
							e.getInventory().setItem(e.getSlot(), new ItemStack(Material.AIR));
						}
						return;
					}
					else if(clickType.equals(ClickType.SHIFT_LEFT) && e.getCurrentItem() != null)
					{
						if(player.hasPermission(Permissions.pickItem))
						{
							ItemStack correctedItem = e.getCurrentItem().clone();
							ItemMeta itemMeta = correctedItem.getItemMeta();
							List<String> correctedLore = itemMeta.getLore();
							correctedLore.remove(correctedLore.size()-1);
							correctedLore.remove(correctedLore.size()-1);
							itemMeta.setLore(correctedLore);
							correctedItem.setItemMeta(itemMeta);
							player.getInventory().addItem(correctedItem);
						}
						return;
					}
				}
				else if(e.getClickedInventory() != null && e.getClickedInventory().getType().equals(InventoryType.PLAYER))
				{
					ClickType clickType = e.getClick();
					if(clickType.equals(ClickType.SHIFT_LEFT) && e.getCurrentItem() != null)
					{
						if(player.hasPermission(Permissions.putItem))
						{
							player.getOpenInventory().getTopInventory().addItem(e.getCurrentItem());
						}
						return;
					}
					else
						e.setCancelled(true);
				}
			}
			else
			{
				e.setCancelled(true);
				if((e.getClick().equals(ClickType.SHIFT_LEFT) && e.getCurrentItem() != null) && !e.getClickedInventory().getType().equals(InventoryType.PLAYER))
				{
					if(player.hasPermission(Permissions.pickItem))
					{
						ItemStack correctedItem = e.getCurrentItem().clone();
						ItemMeta itemMeta = correctedItem.getItemMeta();
						List<String> correctedLore = itemMeta.getLore();
						correctedLore.remove(correctedLore.size()-1);
						correctedLore.remove(correctedLore.size()-1);
						itemMeta.setLore(correctedLore);
						correctedItem.setItemMeta(itemMeta);
						player.getInventory().addItem(correctedItem);
					}
					return;
				}
				if(player.hasPermission(Permissions.addItem) || player.hasPermission(Permissions.subItem) || player.hasPermission(Permissions.delItem) || player.hasPermission(Permissions.putItem))
				{
					player.sendMessage(VoteManager.sendErrorMessage("Vous ne pouvez pas touchez aux potions personnalisées."));
				}
			}
		}
	}
	public static void onClose(InventoryCloseEvent e)
	{
		if(e.getView().getTitle().equals("Objets droppables"))
		{
			Inventory inventoryToSave = e.getInventory();
			FileConfiguration configurationFile = Main.Instance.getConfig();
			List<String> materialsName = new ArrayList<String>();
			List<String> materialsInventory = new ArrayList<String>();
			for(String Key : configurationFile.getKeys(false))
				materialsName.add(Key.toUpperCase());
			for(ItemStack itemToSave : inventoryToSave)
				materialsInventory.add(itemToSave.getType().name().toUpperCase());
			if(materialsName != materialsInventory)
			{

			}
		}
	}
}
