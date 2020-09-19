package fr.mecopi.votereward.managers;

import org.bukkit.permissions.Permission;

public class Permissions 
{
	public static Permission clearEffect = new Permission("votereward.effects.clear");
	public static Permission addItem = new Permission("votereward.item.add");
	public static Permission subItem = new Permission("votereward.item.sub");
	public static Permission delItem = new Permission("votereward.item.del");
	public static Permission putItem = new Permission("votereward.item.put");
	public static Permission pickItem = new Permission("votereward.item.pick");
	public static Permission pickItemOtherBag = new Permission("votereward.bag.itempick");
	public static Permission seeOtherBag = new Permission("votereward.bag.seeother");
	public static Permission fakevote = new Permission("votereward.fakevote");
}
