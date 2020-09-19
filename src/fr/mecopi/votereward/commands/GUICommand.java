package fr.mecopi.votereward.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.mecopi.votereward.GUIManager;
import fr.mecopi.votereward.managers.VoteManager;

public class GUICommand implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender Sender, Command Cmd, String arg2, String[] Arguments) 
	{
		if(Sender instanceof Player)
		{
			Player player = (Player)Sender;
			VoteManager.guiManagers.add(new GUIManager(Bukkit.createInventory(player, 4*9, "Objets droppables"), 0, player, "Objets droppables", VoteManager.droppableItemsList));
		}
		return false;
	}
	
}
