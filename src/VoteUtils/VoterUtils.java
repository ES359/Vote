package VoteUtils;

import me.ES359.Vote.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.UUID;

/**
 * Created by ES359 on 12/5/15.
 */
public class VoterUtils
{
    private String prefix = ChatColor.translateAlternateColorCodes('&',"&c&l[&6Vote&c&l] ");
    /**
     * Stores Author UUID.
     */
    private String author = "9c5dd792-dcb3-443b-ac6c-605903231eb2";
    /**
     * Predefined permission message.
     */
    private String permission = color(getPrefix()+"&cYou don't have permission for this.");

    public String getPrefix()
    {
        return " " +this.prefix;
    }
    /**
     * Gets the defined permission message set.
     * @return
     */
    public String getPermission()
    {
        return this.permission;
    }

    /**
     * Checks a players UUID against the one set inside the Author variable.
     * @param uuid
     * @return
     */
    public boolean checkAuthor(UUID uuid)
    {
        return uuid.toString().equals(author);
    }


    /**
     *  Informs author that plugin is being used by server.
     *
     * @param p
     */
    public void displayAuthInfo(Player p)
    {
        if(checkAuthor(p.getUniqueId()))
        {
            p.sendMessage(color("&a&l&oHello, &7"+ p.getName() +"\n &aThis server is using ") + getPrefix());
        }
    }

    /**
     *  Displays plugin description Information.
     *
     * @param sender
     * @param
     *
     */
    public void desc(Player sender, Main main)
    {
        sender.sendMessage(color("&2========== " + getPrefix().replace(":","") + "&2=========="));
        sender.sendMessage(color("&7[&9" + main.pdfFile.getName() + "&7] &6Created by, &b&l" +main.pdfFile.getAuthors()+"&6."));
        sender.sendMessage(color("&2" + main.pdfFile.getDescription() + "&2."));
        sender.sendMessage(color("&bWebsite: &e&l" + main.pdfFile.getWebsite()));
//        sender.sendMessage(color(""));
//        sender.sendMessage(color(""));
        sender.sendMessage(color("     &6&l>>>&2&l===============&6&l<<<\t"));
    }

    /**
     *  Returns this plugins version.
     */
    public String getPluginVersion(Main main, Player sender)
    {
        return color("&fHello, &a&n"+sender.getName() +".&r\nYou are currently running version &b&n"+main.pdfFile.getVersion() + "&r of &e&n"+main.pdfFile.getName() +"&r\n \n&6Your server is running version &c&n"+ main.getServer().getBukkitVersion());
    }


    public String color(String message) {

        String msg =  message;
        msg = msg.replace("&", "§");
        msg = msg.replace("%prefix%",getPrefix());

//        msg = msg.replace("%permission%",getPermission());
        return msg;
    }

    public void logToConsole(String log)
    {
        Bukkit.getServer().getConsoleSender().sendMessage(color("&c"+Debug.LOG +"&r"+ log));
    }


    public String msg (String msg)
    {
        return color(msg);
    }


    public void sendCommands(List<String> commands, Player p, String inform) {
        p.sendMessage(color(inform));
        for (String cmd : commands)
        {
            cmd = cmd.replace("%player%",p.getName());
            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), cmd);
            logToConsole("&aExecuted the command(s) &7" + cmd);
        }
    }


    public void sendText(List<String> text, Player p)
    {
        for(String txt: text)
        {
            txt = txt.replace("%player%",p.getName());
            txt = txt.replace("%uuid%",p.getUniqueId().toString());
            p.sendRawMessage(color(txt));
        }
    }



    public void reloadConfiguration(Plugin plugin)
    {
        plugin.reloadConfig();
        //plugin.saveConfig();
    }
}
