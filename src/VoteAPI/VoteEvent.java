package VoteAPI;

import VoteUtils.Debug;
import VoteUtils.VoterUtils;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import me.ES359.Vote.Main;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;
import java.util.UUID;

/**
 * Created by ES359 on 12/5/15.
 */
public class VoteEvent extends VoterUtils implements Listener
{
    Main main;

    public VoteEvent(Main instance)
    {
        this.main = instance;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player p = event.getPlayer();
        UUID uuid = p.getUniqueId();

        displayAuthInfo(p);

       if(main.getConfig().getBoolean("Vote-Reminder.Enabled"))
       {
           List<String> msg = main.getConfig().getStringList("Vote-Reminder.msg");

           sendText(msg,p);
       }
    }

    @EventHandler
    public void onVote(VotifierEvent event)
    {
        String message = main.getConfig().getString("Broadcast.Message");
        String prefix = main.getConfig().getString("Broadcast.Custom-Prefix");
        Debug.log(Debug.ACTION + "&cStartup Vote Event...");
        Vote v = event.getVote();
        Player p = Bukkit.getServer().getPlayer(v.getUsername());
        message = message.replace("%prefix_custom%",prefix);
        message = message.replace("%username%",v.getUsername());
        message = message.replace("%service%",v.getServiceName());
        message = message.replace("%timestamp%",v.getTimeStamp());
        message = message.replace("%address%",v.getAddress());
        List<String> commands = main.getConfig().getStringList("Commands");
        String informer = color(main.getConfig().getString("Inform-msg"));
        sendCommands(commands,p,informer);

        if(p == null)
        {
            return; //TODO Implement offline transaction.
        }else {

            String amount = main.getConfig().getString("Economy.amount");
            String inform = main.getConfig().getString("Economy.message");

           if(main.getConfig().getBoolean("Economy.Enabled"))
           {
               EconomyResponse  r = main.econ.depositPlayer(p, Double.parseDouble(amount));
               inform = inform.replace("%amount%",amount);
               inform = inform.replace("%name%",p.getName());
               if(r.transactionSuccess()) {
                   p.sendMessage(color(inform));

//                   sendCommands("give %player% 20 1",p, "&aCommands have been sent!");
               } else {
                   p.sendMessage(ChatColor.RED +"&cAn error has occurred.");
               }
           }
            if(main.getConfig().getBoolean("Broadcast.Enabled"))
            {
                Bukkit.getServer().broadcastMessage(color(message));
            }

            if(main.getConfig().getBoolean("Log-To-Console"))
            {
                logToConsole(v.getUsername() + "Voted on: " +v.getServiceName() + " " + v.getTimeStamp() +"  " +amount);
            }
        }

    }
}
