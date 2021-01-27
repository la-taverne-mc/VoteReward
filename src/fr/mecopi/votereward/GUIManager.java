package fr.mecopi.votereward;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.event.Event;

import fr.mecopi.votereward.managers.Permissions;
import fr.mecopi.votereward.managers.VoteManager;
import net.md_5.bungee.api.ChatColor;

public class GUIManager
{
	private static ItemStack previousPage;
	private static ItemStack nextPage;
	private static ItemStack Notice;
	private static Player player;
	private static Inventory GUI;
	private static String inventoryName;
	private static List<DroppableItem> itemList;


	private int startIndex;
	public GUIManager(Inventory GUIEntry, int startIndexEntry, Player playerEntry, String inventoryNameEntry, List<DroppableItem> itemListEntry)
	{
		//Init properties

		inventoryName = inventoryNameEntry;
		GUI = GUIEntry;
		player = playerEntry;
		startIndex = startIndexEntry;
		itemList = itemListEntry;

		previousPage = new ItemStack(Material.BOOK, 1);
		ItemMeta itemMeta = previousPage.getItemMeta();
		itemMeta.setDisplayName("Revenir à la page précédente");
		previousPage.setItemMeta(itemMeta);

		//Next Page

		nextPage = new ItemStack(Material.BOOK, 1);
		itemMeta = nextPage.getItemMeta();
		itemMeta.setDisplayName("Aller à la page suivante");
		nextPage.setItemMeta(itemMeta);

		//Notice
		
		Notice = new ItemStack(Material.BOOK, 1);
		itemMeta = Notice.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&bNotice d'utilisation"));
		itemMeta.setLore(Arrays.asList(ChatColor.YELLOW + "Clic gauche : Ajoute un objet au stack", ChatColor.YELLOW + "Clic droit : Retire un objet au stack", ChatColor.YELLOW + "Clic molette : Retire le stack", ChatColor.YELLOW + "Shift clic gauche : Permet de récupérer un stack dans son propre inventaire", ChatColor.YELLOW + "Shit clic gauche : Permet d'envoyer un stack dans la GUI"));
		Notice.setItemMeta(itemMeta);
		
		showGUI();
		player.openInventory(GUI);
	}
	private Inventory setBottom()
	{
		if(startIndex > 0) //Is'nt the 1st page
			GUI.setItem(GUI.getSize()-9, previousPage); //Setting previous button
		if(itemList.size() > (startIndex + GUI.getSize()-9)) //startIndex + 1 page < numbers of items in rewards table
			GUI.setItem(GUI.getSize()-1, nextPage); //Setting next button
		if(player.hasPermission(Permissions.addItem))
			GUI.setItem(GUI.getSize()-5, Notice);
		return GUI; //Returning inventory (Nothing can change)
	}
	public void showGUI()
	{
		GUI.clear();
		int actualSlot = startIndex;
		for(int i = actualSlot; (i < (actualSlot + (GUI.getSize()-9)) && i < itemList.size()); i++)
		{
			GUI.addItem(itemList.get(i).getItemStack());
		}
		setBottom();
	}
	public void Event(Event e)
	{
		if(e instanceof InventoryCloseEvent)
		{
			if(((InventoryCloseEvent)e).getView().getTitle().equals(inventoryName))
				VoteManager.guiManagers.remove(this);
		}
		else if(e instanceof InventoryClickEvent)
		{
			if(((InventoryClickEvent)e).getView().getTitle().equals(inventoryName))
			{
				if((((InventoryClickEvent)e).getClickedInventory() != null) && !(((InventoryClickEvent)e).getClickedInventory().getType().equals(InventoryType.PLAYER)))
				{
					if(((InventoryClickEvent)e).getCurrentItem() != null)
					{
						if(((InventoryClickEvent) e).getCurrentItem().equals(previousPage))
						{
							startIndex -= (GUI.getSize()-9);
							showGUI();
						}
						else if(((InventoryClickEvent) e).getCurrentItem().equals(nextPage))
						{
							startIndex += GUI.getSize()-9;
							showGUI();
						}
					}
				}
				((InventoryClickEvent)e).setCancelled(true);
			}
		}
	}
	public Player getOwner() {
		return player;
	}
	public Inventory getGUI() {
		return GUI;
	}

}
