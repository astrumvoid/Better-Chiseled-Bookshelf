package aboe.bcbs.util;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;

import java.util.List;


public class EnchantmentPowerUtils {

    public enum PathCheckMode {
        NONE, //Ignores everything in the way
        TAG,  //Ignores blocks that have the "ENCHANTMENT_POWER_TRANSMITTER" tag
        FULL  //Ignores non-full blocks
    }

    public static boolean isValidEnchantmentSource(Level world, BlockPos origin, BlockPos shelfOffset, PathCheckMode mode) {
        return canProvideEnchantmentPower(world, origin, shelfOffset, mode);
    }

    public static boolean canProvideEnchantmentPower(Level world, BlockPos origin, BlockPos offset, PathCheckMode mode) {
        BlockState blockToCheck = world.getBlockState(origin.offset(offset));
        switch (mode){
            case FULL -> {
                return (blockToCheck.is(BlockTags.ENCHANTMENT_POWER_PROVIDER) || blockToCheck.getBlock() instanceof EnchantmentPowerProvider)
                        && isNotBlocked(world, origin, offset);
            }
            case TAG -> {
                return (blockToCheck.is(BlockTags.ENCHANTMENT_POWER_PROVIDER) || blockToCheck.getBlock() instanceof EnchantmentPowerProvider)
                        && isPowerTransmitter(world, origin, offset);
            }
            default -> {
                return blockToCheck.is(BlockTags.ENCHANTMENT_POWER_PROVIDER) || blockToCheck.getBlock() instanceof EnchantmentPowerProvider;
            }
        }
    }


    /**
     * Retrieves the enchantment power value of a specified block.
     * <p>
     * The enchantment power is determined as follows:
     * <ul>
     *   <li>If the block implements {@link EnchantmentPowerProvider}, its provided value is returned.</li>
     *   <li>If the block does not implement {@code EnchantmentPowerProvider} but has the tag "ENCHANTMENT_POWER_PROVIDER", returns {@code 1}.</li>
     *   <li>If neither condition is met, returns {@code 0}.</li>
     * </ul>
     *
     * @param world The world (level) where the block is located.
     * @param origin The position of the main block (Ex: Position of the enchantment table)
     * @param offset The position to offset of the block in the world.
     * @param mode The mode to use for checking if the path to the block is blocked.
     * <ul>
     * <li><b>NONE</b>: No check is performed, and the path is assumed to be unblocked.</li>
     * <li><b>TAG</b>: Checks if the path is blocked by blocks that don't have the "ENCHANTMENT_POWER_TRANSMITTER" tag</li>
     * <li><b>FULL</b>: Checks if the path is block by blocks that are a full</li>
     * </ul>
     * @return The enchantment power value of the block.
     */
    public static float getEnchantmentPowerFromBlock(Level world, BlockPos origin, BlockPos offset, PathCheckMode mode) {
        if (canProvideEnchantmentPower(world, origin, offset, mode)) {
            BlockState blockState = world.getBlockState(origin.offset(offset));
            return (blockState.getBlock() instanceof EnchantmentPowerProvider power) ? power.getEnchantmentPower(blockState, world, origin.offset(offset)) : 1;
        }
        else return 0;
    }

    //Gets the total power level of the blocks around
    public static float getEnchantmentPower(Level world, BlockPos origin, List<BlockPos> blockOffsetList, PathCheckMode mode) {
        float power = 0;

        //Checks Each Block Within the Offset List - Might be expensive if you have too many blocks to check for.
        for (BlockPos offset : blockOffsetList) {
            power += getEnchantmentPowerFromBlock(world, origin, offset, mode);
        }

        return power;
    }

    private static int moveTowards(int currentPos, int target)
    {
        return (currentPos != target) ? currentPos - Integer.signum(currentPos - target) : currentPos;
    }

    // I have no idea of what im doing, im really sorry
    private static int why(int value){
        return (value != 0) ? value / Math.abs(value) : 0;
    }

    private static boolean isPowerTransmitter(Level world, BlockPos origin, BlockPos startPos) {

        //Offsets the current enchantment table position, which means: Ignores the table, we don't care about it
        BlockPos target = origin.offset(why(startPos.getX()), startPos.getY(), why(startPos.getZ()));

        //Gets the position of the limit
        int targetZ = target.getZ();
        int targetX = target.getX();

        // I mean, it's just an int, why would I use the offset function if all I need is one value?
        int currentX = origin.getX() + startPos.getX();
        int currentZ = origin.getZ() + startPos.getZ();

        boolean isNotBlocked = true;

        while (currentX != targetX || currentZ != targetZ) {
            currentX = moveTowards(currentX, targetX);
            currentZ = moveTowards(currentZ, targetZ);

            if (isNotBlocked && !world.getBlockState(new BlockPos(currentX, target.getY(), currentZ)).is(BlockTags.ENCHANTMENT_POWER_TRANSMITTER))
                isNotBlocked = false;
        }

        return isNotBlocked;
    }

    //Copy of the code on top, but this time it only checks if the block is full instead of the tag (the tag sucks, honestly)
    private static boolean isNotBlocked(Level world, BlockPos origin, BlockPos startPos) {

        //Offsets the current enchantment table position, which means: Ignores the table, we don't care about it
        BlockPos target = origin.offset(why(startPos.getX()), startPos.getY(), why(startPos.getZ()));

        //Gets the position of the limit
        int targetZ = target.getZ();
        int targetX = target.getX();

        // I mean, it's just an int, why would I use the offset function if all I need is one value?
        int currentX = origin.getX() + startPos.getX();
        int currentZ = origin.getZ() + startPos.getZ();

        boolean isNotBlocked = true;

        while (currentX != targetX || currentZ != targetZ) {
            currentX = moveTowards(currentX, targetX);
            currentZ = moveTowards(currentZ, targetZ);
            BlockPos currentPosition = new BlockPos(currentX, target.getY(), currentZ);

            if (isNotBlocked && world.getBlockState(currentPosition).getCollisionShape(world, currentPosition) == Shapes.block())
                isNotBlocked = false;
        }

        return isNotBlocked;
    }

}