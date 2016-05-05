package VoteAPI;

import VoteUtils.VoterUtils;
import me.ES359.Vote.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.List;

/**
 * Created by ES359 on 5/5/16.
 */
public class VoteMenu extends VoterUtils implements Listener
{
    private Inventory inv;
    protected ItemStack voteInfo, closeInv, links,website;

    public ItemStack createItem(Material mat, String name) {
        ItemStack is = new ItemStack(mat);
        ItemMeta meta = is.getItemMeta();
        meta.setDisplayName(name);
        is.setItemMeta(meta);
        return is;
    }

    private Main main;

    public VoteMenu(Main instance, Plugin plugin)
    {
        main = instance;
        inv = Bukkit.getServer().createInventory(null, instance.getConfig().getInt("Vote-Gui.settings.gui-size"), color(instance.getConfig().getString("Vote-Gui.settings.gui-name")));

        Bukkit.getServer().getPluginManager().registerEvents(this,plugin);
        links = createItem(Material.valueOf(instance.getConfig().getString("Vote-Gui.weblinks.settings.item-name")), color(instance.getConfig().getString("Vote-Gui.weblinks.settings.item-desc"))); //color(instance.getConfig().getString("Vote-Gui.weblinks.settings.item-desc")
        inv.setItem(instance.getConfig().getInt("Vote-Gui.weblinks.settings.item-pos") , links);

        website = createItem(Material.valueOf(instance.getConfig().getString("Vote-Gui.website.settings.item-name")), color(instance.getConfig().getString("Vote-Gui.website.settings.item-desc")));
        inv.setItem(instance.getConfig().getInt("Vote-Gui.website.settings.item-pos"), website);

        voteInfo = createItem(Material.valueOf(instance.getConfig().getString("Vote-Gui.voteInfo.settings.item-name")), color(instance.getConfig().getString("Vote-Gui.voteInfo.settings.item-desc")));
        inv.setItem(instance.getConfig().getInt("Vote-Gui.voteInfo.settings.item-pos"),voteInfo);

        closeInv = createItem(Material.valueOf(instance.getConfig().getString("Vote-Gui.closeInv.settings.item-name")),color(instance.getConfig().getString("Vote-Gui.closeInv.settings.item-desc")));
        inv.setItem(instance.getConfig().getInt("Vote-Gui.closeInv.settings.item-pos"), closeInv);
    }

    public void showInventory(Player p) {
        p.openInventory(inv);
    }


    //

    @EventHandler
    public void onPlayerClick(InventoryClickEvent event) {

        Player p = (Player) event.getWhoClicked();

        if (event.getCurrentItem() == null)
        {
            //TODO.
        }else
        {
            if(event.getCurrentItem().equals(website))
            {

                if(event.isRightClick() || event.isLeftClick() || event.isShiftClick()) {


                    event.setCancelled(true);


                    List<String> web = main.getConfig().getStringList("Vote-Gui.website.information");

                    sendText(web, p);
                    p.closeInventory();
                }
            }else if(event.getCurrentItem().equals(closeInv))
            {
                if(event.isRightClick() || event.isLeftClick() || event.isShiftClick())
                {
                    event.setCancelled(true);

                    p.closeInventory();
                }
            }else if(event.getCurrentItem().equals(links))
            {
                if(event.isRightClick() || event.isLeftClick() || event.isShiftClick())
                {
                    event.setCancelled(true);
                    List<String> links = main.getConfig().getStringList("Vote-Gui.weblinks.information");
                    sendText(links,p);
                    p.closeInventory();
                }
            }else if(event.getCurrentItem().equals(voteInfo))
            {
                if(event.isRightClick() || event.isLeftClick() || event.isShiftClick())
                {
                    event.setCancelled(true);

                    List<String> voteinfo = main.getConfig().getStringList("Vote-Gui.voteInfo.information");
                    sendText(voteinfo,p);
                    p.closeInventory();
                }
            }
        }
    }
}
