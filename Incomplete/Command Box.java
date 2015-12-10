package mc.fbmc.border;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class Main
extends JavaPlugin
implements Listener {
    public void onArrowShoot(EntityShootBowEvent event) {
        if (event.getEntity() instanceof Arrow) {
            event.getEntity().playEffect(this.getLocation(event.getEntity().getLocation(), Effect.SMOKE));
        }
        if (event.getEntity().getVelocity().equals((Object)3)) {
            Bukkit.broadcastMessage((String)("Speed: " + (Object)event.getEntity().getVelocity()));
        }
    }

    private EntityEffect getLocation(Location location, Effect smoke) {
        return null;
    }

    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getEntity().getPlayer();
        e.getDrops().clear();
        e.setKeepLevel(true);
        e.getDrops().remove(true);
        e.setKeepInventory(true);
        Player ent = e.getEntity();
        EntityDamageEvent ede = ent.getLastDamageCause();
        EntityDamageEvent.DamageCause dc = ede.getCause();
        if (ent instanceof Player) {
            e.setDeathMessage((Object)ChatColor.BLUE + "Death> " + (Object)ChatColor.WHITE + "You were killed by " + (Object)dc);
        }
    }
}
