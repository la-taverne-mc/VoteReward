package fr.mecopi.votereward;

import java.util.UUID;

public class Effect
{
	private UUID Owner;
	private int remainingTime;
	private String Type;
	private Task Task;
	
	public Effect(UUID owner, String type, int remainingtime)
	{
		Owner = owner;
		remainingTime = remainingtime;
		Type = type;
		Task = new Task();
		Task.addTask(this);
	}
	
	//Getters
	
	public UUID getOwner() {
		return Owner;
	}
	public int getRemainingTime() {
		return remainingTime;
	}
	public String getType() {
		return Type;
	}
	public Task getTask() {
		return Task;
	}
	
	//Setters
	
	public void setRemainingTime(int newRemaintingTime) {
		remainingTime = newRemaintingTime;
	}
}
