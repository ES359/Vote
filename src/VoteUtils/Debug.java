

package VoteUtils;

import me.ES359.Vote.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Debug {

    static VoterUtils util = new VoterUtils();


    static public String FAILED_ACTION = "[FAILED ACTION] ";
    static public String ACTION = "[ACTION] ";
    static public String LOG = "[LOG] ";
    static public String SEVERE = "[SEVERE] &c";


    static public void log(String message) {
        if (Main.DEBUG) {
            Bukkit.getServer().getConsoleSender().sendMessage(util.color(message));
        }
    }

    static public void log(Player p, String message) {
        if (Main.DEBUG) {
            p.sendMessage(util.color(message));
        }
    }

    public void runDeug(CommandSender sender, String[] arguements)
    {
        if( sender.isOp() &&arguements.length > 1 && arguements[0].equalsIgnoreCase("debug"))
        {

            /**
             * REPLACE MAINCLASS WITH YOUR MAINCLASS.
             *
             * EXAMPLE: <MAINCLASS>.DEBUG.
             *
             * Make sure to add:
             * static public DEBUG = false; // To your main class.
             *
             *
             */

            Main.DEBUG = Boolean.parseBoolean(arguements[1]);
            sender.sendMessage(util.color("[&4DEBUG&f] &c--> &7You have set Debug status to &4&l: " + Main.DEBUG));
        }
    }
}

