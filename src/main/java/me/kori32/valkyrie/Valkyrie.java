package me.kori32.valkyrie;

import me.kori32.valkyrie.listener.OreBreakListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Valkyrie extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new OreBreakListener(), this);
    }

    @Override
    public void onDisable() {
    }
}
