package de.strifel.minecraftplugins.DiscordToMinecraft;

import discord4j.common.util.Snowflake;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.channel.MessageChannel;
import org.bukkit.Bukkit;


import static de.strifel.minecraftplugins.DiscordToMinecraft.DiscordMain.*;

/**
 * Listener
 * Created by felix on 13.02.2017.
 */
public class DiscordListener {

    public void onChat(MessageCreateEvent e){
        if (e.getMember().get().isBot()) return;
        if (e.getMessage().getChannel().block().getId().equals(Snowflake.of(channelid))){
            Bukkit.broadcastMessage(e.getMessage().getAuthor().get().getUsername()  + ": " + e.getMessage().getContent().replace("&", "§"));
        } else if(e.getMessage().getChannel().block().equals(Snowflake.of(channelidadmin))){
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), e.getMessage().getContent());
        }
    }

    public void onReadyEvent(ReadyEvent event) {
        System.out.println("Connect to Channel");
        channel = ((MessageChannel) client.getChannelById(Snowflake.of(channelid)).block());
        channeladmin = ((MessageChannel) client.getChannelById(Snowflake.of(channelidadmin)).block());
        channel.createMessage("Server started! You can join now!").block();
    }
}
