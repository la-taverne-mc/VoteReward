package fr.mecopi.votereward.commands;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.mecopi.votereward.managers.FileManager;
import fr.mecopi.votereward.managers.Permissions;
import fr.mecopi.votereward.managers.VoteManager;

public class RVBag implements CommandExecutor
{

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender Sender, Command Cmd, String arg2, String[] Arguments) 
	{
		if(Sender instanceof Player)
		{
			Player player = (Player)Sender;
			if(Arguments.length == 0)
			{
				if(!(new File(FileManager.bagsFolder.getPath().concat("/" + player.getName() + ".yml")).exists()))
					FileManager.CreateBag(player.getName());
				VoteManager.getBagByOwner(player.getName()).OpenBag(player);
			}
			else if(Arguments.length == 1)
			{
				if(player.hasPermission(Permissions.seeOtherBag))
				{
					if(!(new File(FileManager.bagsFolder.getPath().concat("/" + player.getName() + ".yml")).exists()))
						player.sendMessage(VoteManager.sendErrorMessage("Ce joueur ne possède pas de sac de récompenses"));
					else if(Bukkit.getOfflinePlayer(Arguments[0]) == null)
					{
						player.sendMessage(VoteManager.sendErrorMessage("Le joueur est introuvable."));
					}
					else
						VoteManager.getBagByOwner(Arguments[0]).OpenBag(player);
				}
			}
		}
		return false;
	}

}
