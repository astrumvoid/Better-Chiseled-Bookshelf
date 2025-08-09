package aboe.enchantlib.comp;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface ForgeComp {

    /**
     * DO NOT USE, IT'S ONLY TO ADD FORGE COMPATIBILITY AND I DON'T EVEN KNOW IF IT'S GOING TO WORK
     * @param blockState state of the block
     * @param world the level (world)
     * @param blockPos the block position
     * @return the power
     */
    float getEnchantPowerBonus(BlockState blockState, Level world, BlockPos blockPos);
}
