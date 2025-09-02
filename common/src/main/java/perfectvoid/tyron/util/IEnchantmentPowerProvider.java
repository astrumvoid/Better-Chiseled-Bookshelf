package perfectvoid.tyron.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IEnchantmentPowerProvider {
    float getEnchantmentPower(World world, BlockPos pos, BlockState state);
}
