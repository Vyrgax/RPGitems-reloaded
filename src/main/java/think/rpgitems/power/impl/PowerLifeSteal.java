/*
 *  This file is part of RPG Items.
 *
 *  RPG Items is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  RPG Items is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with RPG Items.  If not, see <http://www.gnu.org/licenses/>.
 */
package think.rpgitems.power.impl;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import think.rpgitems.I18n;
import think.rpgitems.power.*;

import java.util.Random;

/**
 * Power lifesteal.
 * <p>
 * The lifesteal power will steal enemy life
 * in a chance of 1/{@link #chance}
 * </p>
 */
@PowerMeta(defaultTrigger = "HIT", generalInterface = PowerLivingEntity.class)
public class PowerLifeSteal extends BasePower implements PowerHit, PowerLivingEntity {

    /**
     * Chance of triggering this power
     */
    @Property(order = 0)
    public int chance = 20;
    /**
     * Cost of this power
     */
    @Property
    public int cost = 0;
    /**
     * Factor of life steal
     */
    @Property
    public double factor = 1;

    private Random random = new Random();

    @Override
    public PowerResult<Double> hit(Player player, ItemStack stack, LivingEntity entity, double damage, EntityDamageByEntityEvent event) {
        return fire(player, stack, entity, damage).with(damage);
    }

    @Override
    public PowerResult<Void> fire(Player player, ItemStack stack, LivingEntity entity, double damage) {
        if (random.nextInt(chance) == 0) {
            if (!getItem().consumeDurability(stack, cost)) return PowerResult.cost();
            player.setHealth(Math.max(Math.min(player.getHealth() + damage * factor, player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()), 0.01));
            return PowerResult.ok();
        }
        return PowerResult.noop();
    }

    @Override
    public String displayText() {
        return I18n.format("power.lifesteal", chance);
    }

    @Override
    public String getName() {
        return "lifesteal";
    }
}
