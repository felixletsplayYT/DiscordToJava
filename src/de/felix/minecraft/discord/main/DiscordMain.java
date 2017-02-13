package de.felix.minecraft.discord.main;

import org.bukkit.plugin.java.JavaPlugin;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.Status;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;


import javax.swing.*;

/**
 * Main Plugin
 * Created by felix on 10.02.2017.
 */
public class DiscordMain extends JavaPlugin {
    public static IChannel channel;
    private String token;
    private String guildid;
    private String channelid;

    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Only useful as plugin on spigot/bukkit Server");
    }

    public static IDiscordClient client;

    public void onEnable() {
        readConfig();
        client = createClient(token, true);
        this.getServer().getPluginManager().registerEvents(new Event(), this);
        this.getServer().getScheduler().runTaskLaterAsynchronously(this, new Runnable() {
            @Override
            public void run() {
                System.out.println("Connect to Channel");
                channel = client.getGuildByID(guildid).getChannelByID(channelid);
                try {
                    channel.sendMessage("Server started!(You can join now)");
                } catch (MissingPermissionsException e) {
                    e.printStackTrace();
                } catch (RateLimitException e) {
                    e.printStackTrace();
                } catch (DiscordException e) {
                    e.printStackTrace();
                }
                client.getDispatcher().registerListener(new DiscordListener());
            }
        }, 100);


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
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        token = getConfig().getString("config.connect.id");
        guildid = getConfig().getString("config.connect.Serverid");
        channelid = getConfig().getString("config.connect.channelid");

    }
}
