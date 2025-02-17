package aboe.enchantlib.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface IEnchantmentPowerProvider {

    /**
     * Return the enchantment power for this block. (Normal bookshelves return 1)
     *
     * @param blockState The block state.
     * @param world The level (world).
     * @param blockPos   The block position.
     * @return The enchantment power value.
     */
    float getEnchantmentPower(BlockState blockState, Level world, BlockPos blockPos);
}
