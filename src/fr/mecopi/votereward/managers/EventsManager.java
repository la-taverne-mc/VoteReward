package fr.mecopi.votereward.managers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCookEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.mecopi.votereward.Effect;
import fr.mecopi.votereward.GUIManager;
import fr.mecopi.votereward.Main;
import fr.mecopi.votereward.items.Bag;
import fr.mecopi.votereward.items.CustomItems;
import fr.mecopi.votereward.items.CustomPotion;
import net.md_5.bungee.api.ChatColor;

public class EventsManager implements Listener
{
	@EventHandler
	public void onConsume(PlayerItemConsumeEvent e)
	{
		if(VoteManager.isCustomPotion(e.getItem()))
		{
			CustomPotion customPotion = VoteManager.getCustomPotion(e.getItem());
			if(VoteManager.getEffectByPlayer(e.getPlayer().getUniqueId()) == null && customPotion != null)
			{
				VoteManager.potionEffects.add(new Effect(e.getPlayer().getUniqueId(), customPotion.getType().toLowerCase(), customPotion.getLevel()));
				Bukkit.getScheduler().runTaskLater(Main.Instance, () -> Modif(customPotion, e.getPlayer()), 1);
			}
			else
			{
				e.setCancelled(true);
				Effect playerEffect = VoteManager.getEffectByPlayer(e.getPlayer().getUniqueId());
				Bukkit.getScheduler().cancelTask(playerEffect.getTask().taskID);
				VoteManager.sendBarMessage(e.getPlayer(), ChatColor.translateAlternateColorCodes('&', "&cVous êtes déjà sous l'effet d'une potion."));
				playerEffect.setRemainingTime(playerEffect.getRemainingTime() - 2);
				Bukkit.getScheduler().runTaskLater(Main.Instance, () -> playerEffect.getTask().addTask(playerEffect), (20 * 2));
			}
		}
		else
		{
			if(e.getItem().getType().equals(Material.MILK_BUCKET))
			{
				Effect playerEffect = VoteManager.getEffectByPlayer(e.getPlayer().getUniqueId());
				if(playerEffect != null)
					playerEffect.getTask().disableEffect();
			}
			CustomItems.EatCustomFood(e);
		}
	}
	public void Modif(CustomPotion customPotion, Player player)
	{
		ItemStack Cloned = player.getInventory().getItemInMainHand();
		ItemMeta stackMeta = Cloned.getItemMeta();
		if(customPotion.getType().equals("fly"))
		{
			switch(customPotion.getLevel())
			{
				case 150:
					stackMeta.setCustomModelData(1);
					break;
				case 300:
					stackMeta.setCustomModelData(2);
					break;
				case 600:
					stackMeta.setCustomModelData(3);
					break;
				case 1200:
					stackMeta.setCustomModelData(4);
					break;
				default:
					stackMeta.setCustomModelData(1);
					break;
			}	
		}
		else if(customPotion.getType().equals("phantom"))
		{
			stackMeta.setCustomModelData(5);
		}
		else if(customPotion.getType().equals("creeper"))
		{
			stackMeta.setCustomModelData(6);
		}
		else if(customPotion.getType().equals("mining"))
		{
			stackMeta.setCustomModelData(7);
		}
		else if(customPotion.getType().equals("swimming"))
		{
			stackMeta.setCustomModelData(8);
		}
		Cloned.setItemMeta(stackMeta);
		player.getInventory().setItemInMainHand(Cloned);
	}
	
	@EventHandler
	public void CantCraftCustomItem (CraftItemEvent e)
	{
		boolean found = false;
		if(e.getInventory().getItem(1) != null && !found)
		{
			found = VoteManager.itemIsIn(e.getInventory().getItem(1).getItemMeta().getDisplayName()) != -1 ? true : false;
			e.setCancelled(found);
		}
		if(e.getInventory().getItem(2) != null && !found)
		{
			found = VoteManager.itemIsIn(e.getInventory().getItem(2).getItemMeta().getDisplayName()) != -1 ? true : false;
			e.setCancelled(found);
		}
		if(e.getInventory().getItem(3) != null && !found)
		{
			found = VoteManager.itemIsIn(e.getInventory().getItem(3).getItemMeta().getDisplayName()) != -1 ? true : false;
			e.setCancelled(found);
		}
		if(e.getInventory().getItem(4) != null && !found)
		{
			found = VoteManager.itemIsIn(e.getInventory().getItem(4).getItemMeta().getDisplayName()) != -1 ? true : false;
			e.setCancelled(found);
		}
	}
	@EventHandler
	public void onTakeDamage(EntityDamageByEntityEvent e)
	{
		if(e.getEntity() instanceof Player)
		{
			Effect currentPlayerEffect = VoteManager.getEffectByPlayer(((Player)e.getEntity()).getUniqueId());
			if(currentPlayerEffect != null && (currentPlayerEffect.getType().equalsIgnoreCase("creeper") || currentPlayerEffect.getType().equalsIgnoreCase("phantom")))
			{
				if(e.getDamager().getType().equals(EntityType.CREEPER) || e.getDamager().getType().equals(EntityType.PHANTOM))
					e.setCancelled(true);
			}
		}
	}
	@EventHandler(priority = EventPriority.LOWEST)
	public void onClick(InventoryClickEvent e)
	{
		if(e.getWhoClicked() instanceof Player)
		{
			GUIManager guiManager = VoteManager.getGUIManager((Player)e.getWhoClicked());
			if(guiManager != null)
			{
				guiManager.Event(e);
			}
			else
			{
				Bag playerBag = VoteManager.getBagByOwner(e.getWhoClicked().getName());
				if(playerBag != null)
				{
					playerBag.Event(e);
				}
			}
			CustomItems.CantBake(e);
			GUIStaffManager.onClick(e);
		}
	}
	@EventHandler
	public void onDied(PlayerDeathEvent e)
	{
		if(VoteManager.getEffectByPlayer(e.getEntity().getUniqueId()) != null)
			VoteManager.getEffectByPlayer(e.getEntity().getUniqueId()).getTask().disableEffect();
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCloseInventory(InventoryCloseEvent e)
	{
		if(e.getPlayer() instanceof Player)
		{
			GUIManager guiManager = VoteManager.getGUIManager((Player)e.getPlayer());
			if(guiManager != null)
			{
				guiManager.Event(e);
			}
			else
			{
				Bag playerBag = VoteManager.getBagByOwner(e.getPlayer().getName());
				if(playerBag != null)
				{
					playerBag.Event(e);
				}
			}
		}
	}
	@EventHandler (priority = EventPriority.LOW)
	public void onBlockBreak(BlockBreakEvent e)
	{
		CustomItems.GoblinPickaxeEvent(e);
	}
	@EventHandler
	public void onKill(EntityDeathEvent e)
	{
		CustomItems.ULUEvent(e);
		CustomItems.IndianSpearEvent(e);
	}
	@EventHandler
	public void onBake(BlockCookEvent e)
	{
		CustomItems.BakeCustomFood(e);
	}
}
