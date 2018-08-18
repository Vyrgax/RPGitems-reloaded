package think.rpgitems.power;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.CheckReturnValue;

/**
 * Triggers when left click
 */
public interface PowerLeftClick extends Power {
    /**
     * Calls when {@code player} using {@code stack} left clicks {@code clicked}
     *
     * @param player  Player
     * @param stack   Item that triggered this power
     * @param clicked Block clicked
     */
    @CheckReturnValue
    PowerResult<Void> leftClick(Player player, ItemStack stack, Block clicked, PlayerInteractEvent event);
}
