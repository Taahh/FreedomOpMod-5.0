package me.totalfreedom.totalfreedommod;

import me.totalfreedom.totalfreedommod.config.ConfigEntry;
import me.totalfreedom.totalfreedommod.util.FUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerPing extends FreedomService
{

    public ServerPing(TotalFreedomMod plugin)
    {
        super(plugin);
    }

    @Override
    protected void onStart()
    {
    }

    @Override
    protected void onStop()
    {
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onServerPing(ServerListPingEvent event)
    {
        final String ip = event.getAddress().getHostAddress();

        if (plugin.bm.isIpBanned(ip))
        {
            event.setMotd(ChatColor.RED + "You are banned.");
            return;
        }

        if (ConfigEntry.ADMIN_ONLY_MODE.getBoolean())
        {
            event.setMotd(ChatColor.RED + "Server is closed.");
            return;
        }

        if (Bukkit.hasWhitelist())
        {
            event.setMotd(ChatColor.RED + "Whitelist enabled.");
            return;
        }

        if (Bukkit.getOnlinePlayers().size() >= Bukkit.getMaxPlayers())
        {
            event.setMotd(ChatColor.RED + "Server is full.");
            return;
        }

        /*if (TFM_ConfigEntry.ENABLE_CHAOS.getBoolean())
        {
            event.setMotd(ChatColor.RED + "Server is currently in chaos mode, prepare for some crazy shit!");
            return;
        }*/

        if (!ConfigEntry.SERVER_COLORFUL_MOTD.getBoolean())
        {
            event.setMotd(FUtil.colorize(ConfigEntry.SERVER_MOTD.getString()
                    .replace("%mcversion%", plugin.si.getVersion())));
            return;
        }
        // Colorful MOTD

        String message = "Welcome to FreedomOP! Running on Spigot for Minecraft " + plugin.si.getVersion();

        final StringBuilder motd = new StringBuilder();

        for (String word : message.split(" "))
        {
            motd.append(FUtil.randomChatColor()).append(word).append(" ");
        }

        event.setMotd(FUtil.colorize(motd.toString()));
    }

}

