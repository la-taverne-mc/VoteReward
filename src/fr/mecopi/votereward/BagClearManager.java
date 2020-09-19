package fr.mecopi.votereward;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import fr.mecopi.votereward.items.Bag;
import fr.mecopi.votereward.managers.VoteManager;

public class BagClearManager 
{
	public static int Day;
	public static int Month;
	public static int Year;
	private static File configFile = new File(Main.Instance.getDataFolder().getPath().replace("\\", "/").concat("/configuration.yml"));
	
	public static void Init()
	{
		Setting();
		Task();
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.Instance, new Runnable() {
		    @Override
		    public void run() {
		        Task();
		    }
		}, 0, (((60 * 20) * 60) * 24)); //Every 24 hours
	}
	private static void Setting()
	{
		YamlConfiguration fileConfig = new YamlConfiguration();
		if(!configFile.exists())
			Main.Instance.saveResource("configuration.yml", false);
		try { fileConfig.load(configFile); } catch (Exception ex) { ex.printStackTrace(); }
		Day = fileConfig.getInt("expiration_time.d");
		Month = fileConfig.getInt("expiration_time.m");
		Year = fileConfig.getInt("expiration_time.y");
	}
	
	
	public static void Task() 
	{
		LocalDate NOW = LocalDate.now(); 
		NOW.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		for(Bag bag : VoteManager.rewardBags)
		{
			for(int i = 0; i < bag.getInventory().size(); i++)
			{
				ItemStack rewardItem = bag.getInventory().get(i);
				String expirationDate = rewardItem.getItemMeta().getLocalizedName();
				String[] IDS = expirationDate.replace(" ", "").split("/");
				LocalDate itemLocalDate = LocalDate.parse(IDS[2].concat("-").concat(IDS[1].concat("-").concat(IDS[0])));
				itemLocalDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				if(NOW.isAfter(itemLocalDate))
				{
					bag.getInventory().remove(i);
				}
			}
		}
	}
	public static void Task(Bag bag) 
	{
		LocalDate NOW = LocalDate.now(); 
		NOW.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		for(int i = 0; i < bag.getInventory().size(); i++)
		{
			ItemStack rewardItem = bag.getInventory().get(i);
			String expirationDate = rewardItem.getItemMeta().getLocalizedName();
			String[] IDS = expirationDate.replace(" ", "").split("/");
			LocalDate itemLocalDate = LocalDate.parse(IDS[2].concat("-").concat(IDS[1].concat("-").concat(IDS[0])));
			itemLocalDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			if(NOW.isAfter(itemLocalDate))
			{
				bag.getInventory().remove(i);
			}
		}
	}
}
