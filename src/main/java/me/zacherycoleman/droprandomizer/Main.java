package me.zacherycoleman.droprandomizer;

import org.bukkit.plugin.java.JavaPlugin;

import me.zacherycoleman.droprandomizer.Listeners.BlockDropListener;

public final class Main extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new BlockDropListener(), this);
        getLogger().info("DropRandomizer successfully enabled! :D");
    }

    @Override
    public void onDisable()
    {
        // Plugin shutdown logic
        getLogger().info("DropRandomizer successfully disabled! :D");
    }
}
