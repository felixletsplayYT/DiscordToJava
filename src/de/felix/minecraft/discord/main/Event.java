package de.felix.minecraft.discord.main;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import static de.felix.minecraft.discord.main.DiscordMain.channel;


/**
 * Listener
 * Created by felix on 10.02.2017.
 */
public class Event implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        try {
            channel.sendMessage("Player " + e.getPlayer().getName() + " joined the game.");
        } catch (MissingPermissionsException e1) {
            e1.printStackTrace();
        } catch (RateLimitException e1) {
            e1.printStackTrace();
        } catch (DiscordException e1) {
            e1.printStackTrace();
        }
    }

    @EventHandler
    public void onLeft(PlayerQuitEvent e) {
        try {
            channel.sendMessage("Player " + e.getPlayer().getName() + " quit the game.");
        } catch (MissingPermissionsException e1) {
            e1.printStackTrace();
        } catch (RateLimitException e1) {
            e1.printStackTrace();
        } catch (DiscordException e1) {
            e1.printStackTrace();
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        try {
            channel.sendMessage(e.getPlayer().getDisplayName() + ":" + e.getMessage());
        } catch (MissingPermissionsException e1) {
            e1.printStackTrace();
        } catch (RateLimitException e1) {
            e1.printStackTrace();
        } catch (DiscordException e1) {
            e1.printStackTrace();
        }
    }



}
