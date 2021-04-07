package de.strifel.minecraftplugins.DiscordToMinecraft;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static de.strifel.minecraftplugins.DiscordToMinecraft.DiscordMain.channel;


/**
 * Listener
 * Created by felix on 10.02.2017.
 */
public class Event implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        channel.createMessage("Player " + e.getPlayer().getName() + " joined the game.").block();
    }

    @EventHandler
    public void onLeft(PlayerQuitEvent e) {
        channel.createMessage("Player " + e.getPlayer().getName() + " quit the game.").block();
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        channel.createMessage(e.getPlayer().getDisplayName() + ": " + e.getMessage()).block();
    }



}
