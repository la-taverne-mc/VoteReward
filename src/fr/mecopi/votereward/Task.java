package fr.mecopi.votereward;

import fr.mecopi.votereward.managers.VoteManager;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Task 
{
	private Effect Effect;
	public int taskID;
	public void addTask(Effect effect)
	{
		Effect = effect;
		taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.Instance, new Runnable() 
		{
			@Override
			public void run() 
			{
				if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(Effect.getOwner())))
				{
					if(Effect.getRemainingTime() > 0)
					{
						enableEffect();
						VoteManager.sendBarMessage(Bukkit.getPlayer(Effect.getOwner()), ChatColor.translateAlternateColorCodes('&', new String("&6Il vous reste ").concat(VoteManager.convertTime(Effect.getRemainingTime()).concat(" (" + Effect.getType() + ")"))));
					}
					else
					{
						VoteManager.sendBarMessage(Bukkit.getPlayer(Effect.getOwner()), ChatColor.translateAlternateColorCodes('&', "&cCette potion ne fait plus effet !"));
						disableEffect();
					}
					Effect.setRemainingTime(Effect.getRemainingTime() - 1);
				}
			}
			
		}, 0, 20); //Each seconds
	}
	private void enableEffect()
	{
		switch(Effect.getType())
		{
			case "fly":
				Bukkit.getPlayer(Effect.getOwner()).setAllowFlight(true);
				break;
			case "swimming":
				Player player = Bukkit.getPlayer(Effect.getOwner());
				if(player != null)
				{
					player.addPotionEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER, 60, 1));
					//disableEffect();
				}
				break;
			case "creeper":
				for(Entity entity : Bukkit.getPlayer(Effect.getOwner()).getNearbyEntities(16.0, 16.0, 16.0))
				{
					if(entity.getType().equals(EntityType.CREEPER))
						entity.remove();
				}
				break;
			case "phantom":
				for(Entity entity : Bukkit.getPlayer(Effect.getOwner()).getNearbyEntities(16.0, 16.0, 16.0))
				{
					if(entity.getType().equals(EntityType.PHANTOM))
						entity.remove();
				}
				break;
			case "mining":
				Bukkit.getPlayer(Effect.getOwner()).addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 60, 1));
				//disableEffect();
				break;
		}
	}
	public void disableEffect()
	{
		switch(Effect.getType())
		{
			case "fly":
				if(Bukkit.getPlayer(Effect.getOwner()).getGameMode().equals(GameMode.SURVIVAL))
					Bukkit.getPlayer(Effect.getOwner()).setAllowFlight(false);
				break;
			case "swimming":
				Player player = Bukkit.getPlayer(Effect.getOwner());
				if(player != null)
				{
					player.removePotionEffect(PotionEffectType.CONDUIT_POWER);
				}
				break;
		}
		Bukkit.getScheduler().cancelTask(taskID);
		VoteManager.potionEffects.remove(Effect);
	}
}
