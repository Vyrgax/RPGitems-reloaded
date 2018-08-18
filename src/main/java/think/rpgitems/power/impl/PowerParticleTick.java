package think.rpgitems.power.impl;

import org.bukkit.Effect;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import think.rpgitems.I18n;
import think.rpgitems.RPGItems;
import think.rpgitems.commands.Property;
import think.rpgitems.commands.Validator;
import think.rpgitems.power.PowerResult;
import think.rpgitems.power.PowerTick;

import static think.rpgitems.utils.PowerUtils.checkCooldown;

/**
 * Power particletick.
 * <p>
 * When item held in hand, spawn some particles around the user.
 * With the time {@link #interval} given in ticks.
 * </p>
 */
@SuppressWarnings("WeakerAccess")
public class PowerParticleTick extends BasePower implements PowerTick {
    /**
     * Name of particle effect
     */
    @Property(order = 0, required = true)
    @Validator(value = "acceptableEffect", message = "message.error.visualeffect")
    public String effect = Effect.MOBSPAWNER_FLAMES.name();
    /**
     * Interval of particle effect
     */
    @Property(order = 1)
    public int interval = 15;
    /**
     * Cost of this power
     */
    @Property
    public int consumption = 0;

    /**
     * Acceptable effect boolean.
     *
     * @param effect the effect
     * @return the boolean
     */
    public boolean acceptableEffect(String effect) {
        try {
            Effect eff = Effect.valueOf(effect.toUpperCase());
            return eff.getType() == Effect.Type.VISUAL;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    @Override
    public void init(ConfigurationSection s) {
        effect = s.getString("effect", Effect.MOBSPAWNER_FLAMES.name());
        try {
            Effect.valueOf(effect);
        } catch (IllegalArgumentException e) {
            RPGItems.logger.warning("Invalid effect name: " + effect);
            effect = Effect.MOBSPAWNER_FLAMES.name();
        }
        interval = s.getInt("interval");
        consumption = s.getInt("consumption", 0);
    }

    @Override
    public void save(ConfigurationSection s) {
        s.set("effect", effect);
        s.set("interval", interval);
        s.set("consumption", consumption);
    }

    @Override
    public String getName() {
        return "particletick";
    }

    @Override
    public String displayText() {
        return I18n.format("power.particletick");
    }

    @Override
    public PowerResult<Void> tick(Player player, ItemStack stack) {
        if (!checkCooldown(this, player, interval, false)) return PowerResult.cd();
        if (!getItem().consumeDurability(stack, consumption)) return PowerResult.cost();
        if (effect.equalsIgnoreCase("SMOKE")) {
            player.getWorld().playEffect(player.getLocation().add(0, 2, 0), Effect.valueOf(effect), 4);
        } else {
            player.getWorld().playEffect(player.getLocation(), Effect.valueOf(effect), 0);
        }
        return PowerResult.ok();
    }
}
