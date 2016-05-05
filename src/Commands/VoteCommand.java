package Commands;

import VoteAPI.VoteMenu;
import VoteUtils.Debug;
import VoteUtils.VotePermissions;
import VoteUtils.VoterUtils;
import me.ES359.Vote.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by ES359 on 12/5/15.
 */

//TODO This command will be implemented later.
public class VoteCommand extends VoterUtils implements CommandExecutor
{

    private Main main;
    private VoteMenu vm;
    public VoteCommand(Main instance, VoteMenu VMInstance)
    {
        this.main = instance;
       this.vm = VMInstance;
    }


    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String args[])
    {

        if(!(sender instanceof Player))
        {
            sender.sendMessage(ChatColor.RED +"Player usage only.");
            return true;
        }

        Player p = (Player)sender;

        if(cmd.getName().equalsIgnoreCase("vote"))
        {
           if(args.length < 1)
           {

               vm.showInventory(p);

               // desc(p,main);

           }else if(args.length > 0 && ! (p.hasPermission(VotePermissions.VOTE_ADMIN_PERM)))
           {
              Debug.log(p, "&c OK so whats going on..?");

               p.sendMessage(color(main.getConfig().getString("Commands-msgs.perm-denied")));

           }else
           {
               switch (args[0])
               {
                   case "rl":
                   case "reload":
                       this.main.reloadConfig();
                       p.sendMessage(color(main.getConfig().getString("Commands-msgs.plugin-reloaded")));
                    break;

                   case "about":
                       desc(p,main);
                       break;

                   case "version":
                   case "ver":
                       p.sendMessage(getPluginVersion(main,p));
                    break;

                    default:
                        p.sendRawMessage(color("%prefix% &eUnknown argument, &7" + args[0] + " &e!"));
               }
           }
        }
        return false;
    }
}
