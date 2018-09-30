package lobby.listener;

import addon.ericlam.Variable;
import functions.hypernite.mc.Functions;
import main.ericlam.HyperNiteMC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class BasicListener implements Listener {
    @EventHandler
    public void OnLeftHandDisable(PlayerSwapHandItemsEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void NoHunger(FoodLevelChangeEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void NoFallDamage(EntityDamageEvent e){
        if (e.getEntity() instanceof Player){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void noWeather(WeatherChangeEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void noExplodeBlockDamage(EntityExplodeEvent e){
        e.setYield(0.0F);
    }


    //private TablistBuilder tb = TablistBuilder.getInstance();
    private Functions fs = new Functions(HyperNiteMC.plugin);

    /*@EventHandler
    public void changeTablistTitle(PlayerJoinEvent e){
        Player player = e.getPlayer();
        String header = Variable.lobbyfile.getString("tablist.header");
        String color = Variable.lobbyfile.getString("tablist.color");
        tb.setTablistHeader(player, header, color);

    }*/

    public void TeleportToLobby(Player player){
        World lobby = Bukkit.getWorld(Variable.lobbyfile.getString("spawntp.world"));
        Double X = Variable.lobbyfile.getDouble("spawntp.x");
        Double Y = Variable.lobbyfile.getDouble("spawntp.y");
        Double Z = Variable.lobbyfile.getDouble("spawntp.z");
        Location spawn = new Location(lobby,X,Y,Z);
        player.teleport(spawn);
    }

    @EventHandler
    public void onLobbyJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        if (player.hasPermission("donor.join")){
            Bukkit.broadcastMessage(fs.returnColoredMessage(Variable.lobbyfile,"join.donor-msg").replace("<player>",player.getDisplayName()));
        }
        TeleportToLobby(player);
    }

    @EventHandler
    public void onLobbyRespawn(PlayerRespawnEvent e){
        Player player = e.getPlayer();
        Bukkit.getScheduler().scheduleSyncDelayedTask(HyperNiteMC.plugin, () ->{
            TeleportToLobby(player);
        });
    }

    @EventHandler
    public void onVoidTeleport(PlayerMoveEvent e){
        boolean Void = e.getPlayer().getLocation().getY() <= 0.0;
        if (Void) TeleportToLobby(e.getPlayer());
    }


    @EventHandler
    public void antiBlockBreak(BlockBreakEvent e){
        Player player = e.getPlayer();
        if (!player.hasPermission("hypernite.build")) e.setCancelled(true);
    }

    @EventHandler
    public void antiBlockPlace(BlockPlaceEvent e){
        Player player = e.getPlayer();
        if (!player.hasPermission("hypernite.build")) e.setCancelled(true);
    }













}
