package de.felix.minecraft.discord.main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;


import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Main Plugin
 * Created by felix on 10.02.2017.
 */
public class DiscordMain extends JavaPlugin {
    public static ArrayList<IUser> verify;
    public static IChannel channel;
    public static IChannel channeladmin;
    public static String token;
    public static String guildid;
    public static String channelid;
    public static String channelidadmin;

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Only useful as plugin on spigot/bukkit Server");
    }

    public static IDiscordClient client;

    public void onEnable() {
        verify = new ArrayList<>();
        readConfig();
        client = createClient(token, true);
        this.getServer().getPluginManager().registerEvents(new Event(), this);
        client.getDispatcher().registerListener(new DiscordListener());




    }
    public void onDisable(){
        try {
            channel.sendMessage("Server closed");
        } catch (MissingPermissionsException e) {
            e.printStackTrace();
        } catch (RateLimitException e) {
            e.printStackTrace();
        } catch (DiscordException e) {
            e.printStackTrace();
        }
    }

    public static IDiscordClient createClient(String token, boolean login) { // Returns a new instance of the Discord client
        ClientBuilder clientBuilder = new ClientBuilder(); // Creates the ClientBuilder instance
        clientBuilder.withToken(token); // Adds the login info to the builder
        try {
            if (login) {
                return clientBuilder.login(); // Creates the client instance and logs the client in
            } else {
                return clientBuilder.build(); // Creates the client instance but it doesn't log the client in yet, you would have to call client.login() yourself
            }
        } catch (DiscordException e) { // This is thrown if there was a problem building the client
            e.printStackTrace();
            return null;
        }
    }

    private void searchChannel() {
        for (int i = 0; i > client.getChannels().size(); i++) {
            if (client.getGuilds().get(0).getChannels().get(i).getName().equalsIgnoreCase("#serverinformationen")) {
                channel = client.getGuilds().get(0).getChannels().get(i);
            }
        }
        if (channel == null) this.getServer().getPluginManager().disablePlugin(this);
    }
    private void readConfig(){
        this.getConfig().addDefault("config.connect.id", "Long ID");
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
            return false;
        }
        if (command.getLabel().equalsIgnoreCase("verify")){
            int random;
            verify.add(client.getGuildByID(guildid).getUsersByName(args[0]).get(0));
            try {
                client.getGuildByID(guildid).getUsersByName(args[0]).get(0).getOrCreatePMChannel().sendMessage("Answer to verify!");
            } catch (MissingPermissionsException e) {
                e.printStackTrace();
            } catch (RateLimitException e) {
                e.printStackTrace();
            } catch (DiscordException e) {
                e.printStackTrace();
            }
        }
        return  true;
    }
}
