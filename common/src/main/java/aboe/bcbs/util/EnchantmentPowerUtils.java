package aboe.bcbs.util;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EnchantmentTableBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class EnchantmentPowerUtils {


    public static boolean isValidEnchantmentSource(Level world, BlockPos origin, BlockPos shelfOffset) {
        return canProvideEnchantmentPower(world, origin.offset(shelfOffset));
    }


    private static boolean canProvideEnchantmentPower(Level world, BlockPos checkPos) {
        BlockState blockToCheck = world.getBlockState(checkPos);
        return blockToCheck.is(BlockTags.ENCHANTMENT_POWER_PROVIDER) || blockToCheck.getBlock() instanceof EnchantmentPowerProvider;
    }


    //Self-explanatory, it gets the Enchantment Power from the block.
    public static float getEnchantmentPowerFromBlock(Level world, BlockPos blockPos) {
        BlockState blockState = world.getBlockState(blockPos);
        if (canProvideEnchantmentPower(world, blockPos))
            return (blockState.getBlock() instanceof EnchantmentPowerProvider power) ? power.getEnchantmentPower(blockState, world, blockPos) : 1;
        else return 0;
    }

    //Gets the total power level of the blocks around
    public static float getEnchantmentPower(Level world, BlockPos origin, List<BlockPos> locatonsList) {
        float power = 0;

        //Checks Each Block Within the Offset List
        for (BlockPos offset : locatonsList) {
            BlockPos ProviderPos = origin.offset(offset);
            power += getEnchantmentPowerFromBlock(world, ProviderPos);
        }

        return power;
    }
}