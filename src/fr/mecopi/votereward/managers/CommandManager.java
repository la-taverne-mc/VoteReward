package fr.mecopi.votereward.managers;

import org.bukkit.Bukkit;

import fr.mecopi.votereward.commands.*;

public class CommandManager 
{
	public static void Init()
	{
		Bukkit.getPluginCommand("rvitem").setExecutor(new GUICommand());
		Bukkit.getPluginCommand("rvclear").setExecutor(new ClearCommand());
		Bukkit.getPluginCommand("rvbag").setExecutor(new RVBag());
		Bukkit.getPluginCommand("fakevote").setExecutor(new FakeVote());
	}
}
