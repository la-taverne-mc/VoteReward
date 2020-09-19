package fr.mecopi.votereward;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import fr.mecopi.votereward.managers.CommandManager;
import fr.mecopi.votereward.managers.EventsManager;
import fr.mecopi.votereward.managers.FileManager;
import fr.mecopi.votereward.managers.VoteManager;
import fr.mecopi.votereward.managers.VotifierManager;

public class Main extends JavaPlugin
{
	public static Plugin Instance;
	@Override
	public void onEnable()
	{
		Instance = this;
		Bukkit.getPluginManager().registerEvents(new EventsManager(), this);
		Bukkit.getPluginManager().registerEvents(new VotifierManager(), this);
		FileManager.Init();
		CommandManager.Init();
		Bukkit.getConsoleSender().sendMessage(VoteManager.sendSuccessMessage("Enabled successfully"));
	}
	@Override
	public void onDisable()
	{
		for(Player player : Bukkit.getOnlinePlayers())
		{
			if(player.getOpenInventory() != null && player.getOpenInventory().getTitle().equals("Objets droppables"))
				player.closeInventory();
		}
		FileManager.SaveBags();
		FileManager.saveEffects();
		Bukkit.getConsoleSender().sendMessage(VoteManager.sendErrorMessage("Disabled Sucessfully"));
	}
}
