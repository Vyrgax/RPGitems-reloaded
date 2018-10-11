package think.rpgitems;

import cat.nyaa.nyaacore.configuration.PluginConfigure;
import org.bukkit.plugin.java.JavaPlugin;

public class Configuration extends PluginConfigure {
    private final RPGItems plugin;

    @Override
    protected JavaPlugin getPlugin() {
        return plugin;
    }

    public Configuration(RPGItems plugin) {
        this.plugin = plugin;
    }

    @Serializable
    public String language = "en_US";

    @Serializable
    public boolean localeInv = false;

    @Serializable(name = "itemperpage")
    public int itemPerPage = 9;

    @Serializable(name = "support.worldguard")
    public boolean useWorldGuard = true;

    @Serializable(name = "support.wgforcerefresh")
    public boolean wgForceRefresh = false;

    @Serializable(name = "give-perms")
    public boolean givePerms = false;

    @Serializable(name = "githubToken")
    public String githubToken = "";

    @Serializable(name = "publishGist")
    public boolean publishGist = true;

    @Serializable
    public boolean numericBar = false;

    @Serializable
    public boolean forceBar = false;
}
