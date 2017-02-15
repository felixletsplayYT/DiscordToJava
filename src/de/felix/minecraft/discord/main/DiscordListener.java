package de.felix.minecraft.discord.main;

import org.bukkit.Bukkit;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import static de.felix.minecraft.discord.main.DiscordMain.*;

/**
 * Listener
 * Created by felix on 13.02.2017.
 */
public class DiscordListener {
    @EventSubscriber
    public void onChat(MessageReceivedEvent e){
        if (e.getMessage().getChannel() == channel){
            Bukkit.broadcastMessage(e.getMessage().getAuthor().getName()  + ":" + e.getMessage().getContent().replace("&", "§"));
        }else if(e.getMessage().getChannel() == channeladmin){
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), e.getMessage().getContent());
        }
    }
    @EventSubscriber
    public void onReadyEvent(ReadyEvent event) {
        System.out.println("Connect to Channel");
        channel = client.getGuildByID(DiscordMain.guildid).getChannelByID(DiscordMain.channelid);
        channeladmin = client.getGuildByID(DiscordMain.guildid).getChannelByID(DiscordMain.channelidadmin);
        try {
            channel.sendMessage("Server started!(You can join now)");
        } catch (MissingPermissionsException e) {
            e.printStackTrace();
        } catch (RateLimitException e) {
            e.printStackTrace();
        } catch (DiscordException e) {
            e.printStackTrace();
        }
    }
}
