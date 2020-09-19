package fr.mecopi.votereward.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.mecopi.votereward.Main;
import fr.mecopi.votereward.managers.FileManager;
import fr.mecopi.votereward.managers.Permissions;
import fr.mecopi.votereward.managers.VoteManager;

public class ClearCommand implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender Sender, Command Cmd, String arg2, String[] Arguments) 
	{
		if(Sender instanceof Player)
		{
			Player player = (Player)Sender;
			if(player.hasPermission(Permissions.clearEffect))
			{
				VoteManager.potionEffects.clear();
				FileManager.saveEffects();
				Bukkit.getScheduler().cancelTasks(Main.Instance);
				player.sendMessage(VoteManager.sendSuccessMessage("Tout les effets de potions ont été supprimés."));
			}
			else
				player.sendMessage(VoteManager.sendErrorMessage("Vous n'avez pas la permission de faire ça."));
		}
		return false;
	}
	
}
