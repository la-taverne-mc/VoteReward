package fr.mecopi.votereward.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.mecopi.votereward.managers.Permissions;
import fr.mecopi.votereward.managers.VoteManager;

public class FakeVote implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender Sender, Command Cmd, String arg2, String[] Arguments)
	{
		if(Sender instanceof Player)
		{
			if(((Player)Sender).hasPermission(Permissions.fakevote))
			{
				if(Arguments.length == 0)
				{
					VoteManager.sendItemToPlayer(((Player)Sender).getName(), 1);
				}
				else if(Arguments.length == 1)
				{
					VoteManager.sendItemToPlayer(Arguments[0], 1);
				}
				else if(Arguments.length == 2)
				{
					int incursionNumber = 0;
					try
					{
						incursionNumber = Integer.parseInt(Arguments[1]);
					} catch (Exception ex) {}
					if(incursionNumber == 0)
					{
						VoteManager.sendItemToPlayer(Arguments[0], 1);
					}
					else
						VoteManager.sendItemToPlayer(Arguments[0], incursionNumber);
				}
			}
		}
		return false;
	}

}
