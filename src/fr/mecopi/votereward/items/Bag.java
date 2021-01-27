package fr.mecopi.votereward.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.mecopi.votereward.BagClearManager;
import fr.mecopi.votereward.managers.Permissions;
import fr.mecopi.votereward.managers.VoteManager;

public class Bag 
{
	private List<ItemStack> playerRewardBag = new ArrayList<ItemStack>();
	private String Owner;
	private int startIndex;
	private Inventory GUI;
	
	public Bag(List<ItemStack> inventory, String owner)
	{
		playerRewardBag = inventory;
		Owner = owner;
	}
	public String getOwner()
	{
		return Owner;
	}
	public List<ItemStack> getInventory()
	{
		return playerRewardBag;
	}
	public void setInventory(List<ItemStack> playerRewardBagEntry)
	{
		playerRewardBag = playerRewardBagEntry;
	}
	public void OpenBag(Player Viewer)
	{
		BagClearManager.Task(this);
		startIndex = 0;
		GUI = Bukkit.createInventory(Viewer, 6*9, "Sac de récompenses");
		showGUI();
		Viewer.openInventory(GUI);
	}
	public void showGUI()
	{
		GUI.clear();
		int actualSlot = startIndex;
		for(int i = actualSlot; (i < (actualSlot + (GUI.getSize()-9)) && i < playerRewardBag.size()); i++)
		{
			GUI.addItem(playerRewardBag.get(i));
		}
		setBottom();
	}
	private Inventory setBottom()
	{
		ItemStack previousPage = new ItemStack(Material.BOOK, 1);
		ItemMeta itemMeta = previousPage.getItemMeta();
		itemMeta.setDisplayName("Revenir à la page précédente");
		previousPage.setItemMeta(itemMeta);

		//Next Page

		ItemStack nextPage = new ItemStack(Material.BOOK, 1);
		itemMeta = nextPage.getItemMeta();
		itemMeta.setDisplayName("Aller à la page suivante");
		nextPage.setItemMeta(itemMeta);
		
		if(startIndex > 0) //Is'nt the 1st page
			GUI.setItem(GUI.getSize()-9, previousPage); //Setting previous button
		if(playerRewardBag.size() > (startIndex + GUI.getSize()-9)) //startIndex + 1 page < numbers of items in rewards table
			GUI.setItem(GUI.getSize()-1, nextPage); //Setting next button
		return GUI; //Returning inventory (Nothing can change)
	}
	public void Event(Event e)
	{
		ItemStack previousPage = new ItemStack(Material.BOOK, 1);
		ItemMeta itemMeta = previousPage.getItemMeta();
		itemMeta.setDisplayName("Revenir à la page précédente");
		previousPage.setItemMeta(itemMeta);

		//Next Page

		ItemStack nextPage = new ItemStack(Material.BOOK, 1);
		itemMeta = nextPage.getItemMeta();
		itemMeta.setDisplayName("Aller à la page suivante");
		nextPage.setItemMeta(itemMeta);
		if(e instanceof InventoryCloseEvent)
		{
			try
			{
				int actualSlot = new Integer(startIndex);
				List<ItemStack> firstPart = new ArrayList<ItemStack>();
				List<ItemStack> secondPart = new ArrayList<ItemStack>();
				List<ItemStack> thirdPart = new ArrayList<ItemStack>();
				List<ItemStack> finalPart = new ArrayList<ItemStack>();
				for(int i = 0; i < actualSlot; i++)
				{
					firstPart.add(playerRewardBag.get(i));
				}
				for(int i = (actualSlot + GUI.getSize()-9); i < playerRewardBag.size(); i++)
				{
					secondPart.add(playerRewardBag.get(i));
				}
				for(int i = 0; i < GUI.getSize()-9; i++)
				{
					if(GUI.getItem(i) != null)
						thirdPart.add(GUI.getItem(i));
				}
				for(ItemStack itemStack : firstPart) { finalPart.add(itemStack); }
				for(ItemStack itemStack : thirdPart) { finalPart.add(itemStack); }
				for(ItemStack itemStack : secondPart) { finalPart.add(itemStack); }
				playerRewardBag.clear();
				playerRewardBag = finalPart;
			} 
			catch (Exception ex) {startIndex = 0;}
		}	
		else if(e instanceof InventoryClickEvent)
		{
			if(((InventoryClickEvent)e).getView().getTitle().equals("Sac de récompenses"))
			{
				if((((InventoryClickEvent)e).getClickedInventory() != null) && !(((InventoryClickEvent)e).getClickedInventory().getType().equals(InventoryType.PLAYER)))
				{
					if(((InventoryClickEvent)e).getCurrentItem() != null)
					{
						if(((InventoryClickEvent) e).getCurrentItem().equals(previousPage))
						{
							try 
							{
								int actualSlot = new Integer(startIndex);
								List<ItemStack> firstPart = new ArrayList<ItemStack>();
								List<ItemStack> secondPart = new ArrayList<ItemStack>();
								List<ItemStack> thirdPart = new ArrayList<ItemStack>();
								List<ItemStack> finalPart = new ArrayList<ItemStack>();
								for(int i = 0; i < actualSlot; i++)
								{
									firstPart.add(playerRewardBag.get(i));
								}
								for(int i = (actualSlot + GUI.getSize()-9); i < playerRewardBag.size(); i++)
								{
									secondPart.add(playerRewardBag.get(i));
								}
								for(int i = 0; i < GUI.getSize()-9; i++)
								{
									if(GUI.getItem(i) != null)
										thirdPart.add(GUI.getItem(i));
								}
								for(ItemStack itemStack : firstPart) { finalPart.add(itemStack); }
								for(ItemStack itemStack : thirdPart) { finalPart.add(itemStack); }
								for(ItemStack itemStack : secondPart) { finalPart.add(itemStack); }
								playerRewardBag.clear();
								playerRewardBag = finalPart;
								startIndex -= (GUI.getSize()-9);
							} 
							catch (Exception ex) {startIndex = 0;}
							showGUI();
							((InventoryClickEvent)e).setCancelled(true);
						}
						else if(((InventoryClickEvent) e).getCurrentItem().equals(nextPage))
						{
							try
							{
								int actualSlot = new Integer(startIndex);
								List<ItemStack> firstPart = new ArrayList<ItemStack>();
								List<ItemStack> secondPart = new ArrayList<ItemStack>();
								List<ItemStack> thirdPart = new ArrayList<ItemStack>();
								List<ItemStack> finalPart = new ArrayList<ItemStack>();
								for(int i = 0; i < actualSlot; i++)
								{
									firstPart.add(playerRewardBag.get(i));
								}
								for(int i = (actualSlot + GUI.getSize()-9); i < playerRewardBag.size(); i++)
								{
									secondPart.add(playerRewardBag.get(i));
								}
								for(int i = 0; i < GUI.getSize()-9; i++)
								{
									if(GUI.getItem(i) != null)
										thirdPart.add(GUI.getItem(i));
								}
								for(ItemStack itemStack : firstPart) { finalPart.add(itemStack); }
								for(ItemStack itemStack : thirdPart) { finalPart.add(itemStack); }
								for(ItemStack itemStack : secondPart) { finalPart.add(itemStack); }
								playerRewardBag.clear();
								playerRewardBag = finalPart; 
								startIndex += GUI.getSize()-9;
							} 
							catch (Exception ex) {startIndex = 0;}
							showGUI();
							((InventoryClickEvent)e).setCancelled(true);
						}
						else if(((InventoryClickEvent) e).getWhoClicked().getName().equals(Owner) || ((InventoryClickEvent) e).getWhoClicked().hasPermission(Permissions.pickItemOtherBag))
						{
							List<ItemStack> PlayerInventory = new ArrayList<ItemStack>(); for(ItemStack itemStack : ((InventoryClickEvent) e).getWhoClicked().getInventory()) { if(itemStack != null) PlayerInventory.add(itemStack); }
							if(PlayerInventory.size() >= (4*9))
							{
								((InventoryClickEvent) e).getWhoClicked().sendMessage(VoteManager.sendErrorMessage("Votre inventaire est plein, videz le d'abord"));
								((InventoryClickEvent)e).setCancelled(true);
							}
							else
							{
								if(!((InventoryClickEvent)e).getClickedInventory().getType().equals(InventoryType.PLAYER))
								{
									ItemStack itemToGive = ((InventoryClickEvent) e).getCurrentItem().clone();
									itemMeta = itemToGive.getItemMeta();
									itemMeta.setLocalizedName(new ItemStack(itemToGive.getType(), itemToGive.getAmount()).getItemMeta().getLocalizedName());
									List<String> correctedLore = new ArrayList<String>();
									for(int i = 0; i < itemMeta.getLore().size()-1; i++) { correctedLore.add(itemMeta.getLore().get(i)); }
									itemMeta.setLore(correctedLore);
									itemToGive.setItemMeta(itemMeta);
									((InventoryClickEvent) e).getWhoClicked().getInventory().addItem(itemToGive);
									
									//Delete from Bag
									GUI.setItem(((InventoryClickEvent) e).getSlot(), new ItemStack(Material.AIR));
								}
							}
						}
						((InventoryClickEvent)e).setCancelled(true);
					}
				}
			}
		}
	}
}
