package com.ericlam.mc.dnmclobby.command;


import com.ericlam.mc.dnmclobby.config.MainConfig;
import com.ericlam.mc.dnmclobby.config.MessagesConfig;
import com.ericlam.mc.dnmclobby.main.DNMCLobby;
import com.ericlam.mc.dnmclobby.manager.PlayerSettingManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class SpeedCommand extends SettingsCommandNode {

    public SpeedCommand(PlayerSettingManager psm) {
        super("speed", "切換速度", psm);
    }

    @Override
    public void executeSettings(Player name, CommandSender sender, MessagesConfig config) {
        UUID puuid = name.getUniqueId();
        boolean speed = !psm.getPlayerSetting(puuid).isSpeed();
        int amplifier = DNMCLobby.getConfigManager().getConfigAs(MainConfig.class).speedLevel - 1;
        if (sender != name)
            sender.sendMessage(config.getBeTurnOff("Stacker").replace("<player>", name.getName()));
        name.sendMessage(config.getTurnOff("Speed"));
        if (speed) name.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999 * 20, amplifier));
        else name.removePotionEffect(PotionEffectType.SPEED);
        psm.getPlayerSetting(puuid).setSpeed(speed);
    }
}
