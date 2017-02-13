package de.felix.minecraft.discord.main;

import org.bukkit.Bukkit;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;

import static de.felix.minecraft.discord.main.DiscordMain.channel;

/**
 * Listener
 * Created by felix on 13.02.2017.
 */
public class DiscordListener {
    @EventSubscriber
    public void onChat(MessageReceivedEvent e){
        if (e.getMessage().getChannel() == channel){
            Bukkit.broadcastMessage(e.getMessage().getAuthor().getName()  + ":" + e.getMessage().getContent().replace("&", "§"));
        }
    }
}
