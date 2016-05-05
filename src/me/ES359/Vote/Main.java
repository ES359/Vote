package me.ES359.Vote;

import Commands.VoteCommand;
import VoteAPI.VoteEvent;
import VoteAPI.VoteMenu;
import VoteUtils.Debug;
import VoteUtils.VoterUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by ES359 on 12/5/15.
 */
public class Main extends JavaPlugin
{
    public Economy econ = null;
    VoterUtils util = new VoterUtils();
    public PluginDescriptionFile pdfFile = this.getDescription();

    VoteMenu vm;

    PluginManager pm  = Bukkit.getServer().getPluginManager();
    public void onEnable()
    {
        if (!setupEconomy() ) {
            util.logToConsole(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        vm = new VoteMenu(this,this);

        getCommand("vote").setExecutor(new VoteCommand(this,vm));
        util.logToConsole(util.getPrefix() + "Has been enabled!");
        Debug.log(Debug.LOG + util.getPrefix() +"&aPlugin startup...");
        pm.registerEvents(new VoteEvent(this), this);
        Debug.log(Debug.ACTION + "Vote Event registered...");
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        Debug.log("Below this VVVV");
    }

    //TODO moved eco resource jar
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public void onDisable()
    {
    }

    public static boolean DEBUG = true;

}
