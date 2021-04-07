package de.strifel.minecraftplugins.DiscordToMinecraft;

import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.channel.MessageChannel;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


import javax.swing.*;
import java.util.ArrayList;
/**
 * Main Plugin
 * Created by felix on 10.02.2017.
 */
public class DiscordMain extends JavaPlugin {
    public static ArrayList<Member> verify;
    public static MessageChannel channel;
    public static MessageChannel channeladmin;
    private DiscordListener listener;
    public static String token;
    public static String guildid;
    public static String channelid;
    public static String channelidadmin;

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Only useful as plugin on spigot/bukkit Server");
    }

    public static DiscordClient dClient;
    public static GatewayDiscordClient client;

    public void onEnable() {
        verify = new ArrayList<>();
        readConfig();
        dClient = DiscordClient.create(token);
        client = dClient.login().block();

        this.getServer().getPluginManager().registerEvents(new Event(), this);
        listener = new DiscordListener();
        client.on(MessageCreateEvent.class).subscribe(event -> listener.onChat(event));
        client.on(ReadyEvent.class).subscribe(event -> listener.onReadyEvent(event));
    }

    public void onDisable(){
        channel.createMessage("Server closed!").block();
    }

    private void readConfig(){
        this.getConfig().addDefault("config.connect.id", "Token");
        this.getConfig().addDefault("config.connect.Serverid", "Long ID");
        this.getConfig().addDefault("config.connect.channelid", "Long ID");
        this.getConfig().addDefault("config.connect.channelidadmin", "Long ID");
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        token = getConfig().getString("config.connect.id");
        guildid = getConfig().getString("config.connect.Serverid");
        channelid = getConfig().getString("config.connect.channelid");
        channelidadmin = getConfig().getString("config.connect.channelidadmin");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p;
        if (sender instanceof Player){
            p = (Player)sender;
        } else {
            return false;
        }
        if (command.getLabel().equalsIgnoreCase("verify")){
            Member member = client.getGuildById(Snowflake.of(guildid)).block().getMembers().name(args[0]).blockFirst();
            verify.add(member);
            member.getPrivateChannel().block().createMessage("Awnser to verify that you are " + p.getDisplayName()).block();
        }
        return  true;
    }
}
