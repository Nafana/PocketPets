package net.nafana.pocketpets;

import net.milkbowl.vault.economy.Economy;
import net.nafana.pocketpets.commands.PetGiveCommand;
import net.nafana.pocketpets.helpers.PetMetadataFactory;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class PocketPetsPlugin extends JavaPlugin {

    private static final Logger log = Logger.getLogger("Minecraft");
    private static PocketPetsPlugin instance;
    private final PetMetadataFactory factory = new PetMetadataFactory();
    private static Economy economy = null;
    private PocketPetsCooldownManager cooldownManager;
    private PocketPetsLoader loader;

    public void onEnable () {

        instance = this;
        loadConfig();

        factory.setNewPetMetadataFactoryInstance();
        loader = new PocketPetsLoader();
        cooldownManager = new PocketPetsCooldownManager();
        new PocketPetsLoader();

        if (!setupEconomy() ) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        registerCommands();
        registerEvents();
    }

    public void onDisable () {

    }

    private void registerCommands() {

        getCommand("petgive").setExecutor(new PetGiveCommand());
    }

    private void registerEvents () {

        Bukkit.getPluginManager().registerEvents(new PocketPetsEventManager(), this);

    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }


    public static Economy getEcon () {
        return economy;
    }

    public Configuration getPluginConfig () {

        return getConfig();

    }

    public void loadConfig () {

        getConfig().options().copyDefaults(true);
        saveConfig();

    }

    public static PocketPetsPlugin getInstance () {
        return instance;
    }

}
