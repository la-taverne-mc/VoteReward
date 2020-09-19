package fr.mecopi.votereward.managers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import com.vexsoftware.votifier.model.VotifierEvent;

public class VotifierManager implements Listener
{
	@EventHandler
	public void voteListener(VotifierEvent e)
	{
		VoteManager.sendItemToPlayer(e.getVote().getUsername(), 1);
	}

}
