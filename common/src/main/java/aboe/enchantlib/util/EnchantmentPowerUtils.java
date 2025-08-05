package aboe.enchantlib.util;

import aboe.enchantlib.comp.ForgeComp;
import dev.architectury.platform.Platform;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;

import java.util.List;

public class EnchantmentPowerUtils {

    public enum PathCheck {
        NONE, //Ignores everything in the way
        TAG,  //Ignores blocks that have the "ENCHANTMENT_POWER_TRANSMITTER" tag
        FULL  //Ignores non-full blocks
    }

    /**
     *  Retrieves the enchantment power of the block at the specified coordinates if it has one.
     *  This one does not need an offset.
     *  <p>
     * The enchantment power is determined as follows:
     * <ul>
     *   <li>If the block implements {@link IEnchantmentPowerProvider}, its provided value is returned.</li>
     *   <li>If the block has the "ENCHANTMENT_POWER_PROVIDER" tag but does not implement the interface, returns {@code 1}.</li>
     *   <li>Otherwise, returns {@code 0}.</li>
     * </ul>
     * @param world The world (level)
     * @param blockPos The position of the block you want to check
     * @param blockState The state of the block
     * @return the power of the block
     */
    public static float getCurrentBlockPower(Level world, BlockPos blockPos, BlockState blockState){
        if (blockState.getBlock() instanceof IEnchantmentPowerProvider powerProvider)
            return powerProvider.getEnchantmentPower(blockState, world, blockPos);
        else if (Platform.isForge() && blockState.getBlock() instanceof ForgeComp power)
            return power.getEnchantPowerBonus(blockState, world, blockPos);
        else if (blockState.is(BlockTags.ENCHANTMENT_POWER_PROVIDER))
             return 1;
        else return 0;
    }

    /**
     * Retrieves the enchantment power value of the block at the specified offset.
     * <p>
     * The enchantment power is determined as follows:
     * <ul>
     *   <li>If the block implements {@link IEnchantmentPowerProvider}, its provided value is returned.</li>
     *   <li>If the block has the "ENCHANTMENT_POWER_PROVIDER" tag but does not implement the interface, returns {@code 1}.</li>
     *   <li>Otherwise, returns {@code 0}, also returns {@code 0} if the path is blocked according to the MODE.</li>
     * </ul>
     *
     * @param world  The world (level) where the block is located.
     * @param origin The position of the reference block (e.g., an enchantment table).
     * @param offset The relative offset from {@code origin} where the block is located. (use a full 0 position if you want to check the current block)
     * @param mode   The path-checking mode (see {@link #isValidPowerProvider} for details).
     * @return The enchantment power value of the block.
     */
    public static float getEnchantmentPowerFromValidBlock(Level world, BlockPos origin, BlockPos offset, PathCheck mode) {
        if (isValidPowerProvider(world, origin, offset, mode)) {
            BlockState blockState = world.getBlockState(origin.offset(offset));
            return (blockState.getBlock() instanceof IEnchantmentPowerProvider power)
                    ? power.getEnchantmentPower(blockState, world, origin.offset(offset))
                    : 1;
        }
        else return 0;
    }

    /**
     * Determines whether the block at the given offset can provide enchantment power.
     * <p>
     * A block is considered valid if:
     * <ul>
     *   <li>It implements {@link IEnchantmentPowerProvider} or has the "ENCHANTMENT_POWER_PROVIDER" tag.</li>
     *   <li>The path to it is not blocked, depending on the selected {@code mode}.</li>
     * </ul>
     *
     * @param world  The world (level) where the block is located.
     * @param origin The position of the reference block (e.g., an enchantment table).
     * @param offset The relative offset from {@code origin} where the block is located.
     * @param mode   The path-checking mode:
     * <ul>
     *   <li><b>NONE</b>: Ignores obstacles.</li>
     *   <li><b>TAG</b>: Ignores blocks tagged as "ENCHANTMENT_POWER_TRANSMITTER" but treats others as obstacles.</li>
     *   <li><b>FULL</b>: Ignores non-full blocks (e.g, slabs, stairs, carpet, etc.)</li>
     * </ul>
     * @return {@code true} if the block at {@code offset} can provide enchantment power, otherwise {@code false}.
     */
    public static boolean isValidPowerProvider(Level world, BlockPos origin, BlockPos offset, PathCheck mode) {
        BlockState blockToCheck = world.getBlockState(origin.offset(offset));
        switch (mode){
            case FULL -> {
                return (blockToCheck.is(BlockTags.ENCHANTMENT_POWER_PROVIDER) || blockToCheck.getBlock() instanceof IEnchantmentPowerProvider)
                        && isNotBlocked(world, origin, offset);
            }
            case TAG -> {
                return (blockToCheck.is(BlockTags.ENCHANTMENT_POWER_PROVIDER) || blockToCheck.getBlock() instanceof IEnchantmentPowerProvider)
                        && isPowerTransmitter(world, origin, offset);
            }
            default -> {
                return blockToCheck.is(BlockTags.ENCHANTMENT_POWER_PROVIDER) || blockToCheck.getBlock() instanceof IEnchantmentPowerProvider;
            }
        }
    }

    /**
     * Calculates the total enchantment power from the blocks around the origin position.
     * <p>
     * This method iterates through a list of block positions, accumulating their enchantment power
     * based on the {@link #getEnchantmentPowerFromValidBlock(Level, BlockPos, BlockPos, PathCheck)} method.
     *
     * @param world           The world (level) where the blocks are located.
     * @param origin          The position of the reference block (e.g., an enchantment table).
     * @param blockOffsetList A list of relative offsets to check.
     * @param mode            The path-checking mode (see {@link #isValidPowerProvider} for details).
     * @return The total enchantment power from all valid blocks.
     */
    public static float getTotalEnchantmentPower(Level world, BlockPos origin, List<BlockPos> blockOffsetList, PathCheck mode) {
        float power = 0;

        //Checks Each Block Within the Offset List - Might be expensive if you have too many blocks to check for and if you're using the path check mode.
        if (mode == PathCheck.FULL || mode == PathCheck.TAG)
            for (BlockPos offset : blockOffsetList) {
                power += getEnchantmentPowerFromValidBlock(world, origin, offset, mode);
            }
        else
            for (BlockPos offset : blockOffsetList) {
                BlockPos pos = origin.offset(offset);
                power += getCurrentBlockPower(world, pos, world.getBlockState(pos));
            }

        return power;
    }


    private static int moveTowards(int currentPos, int target) {
        return (currentPos != target) ? currentPos - Integer.signum(currentPos - target) : currentPos;
    }

    // I have no idea of what im doing, im really sorry
    private static int why(int value){
        return (value != 0) ? value / Math.abs(value) : 0;
    }

    private static boolean isPowerTransmitter(Level world, BlockPos origin, BlockPos startPos) {
        //Offsets the current enchantment table position, which means: Ignores the table/origin, we don't care about it
        BlockPos target = origin.offset(why(startPos.getX()), startPos.getY(), why(startPos.getZ()));

        //Gets the position of the limit
        int targetZ = target.getZ();
        int targetX = target.getX();

        // I mean, it's just an int, why would I use the offset function if all I need is one value? I'll do the offset manually!
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

        BlockPos currentPosition;
        boolean isNotBlocked = true;

        while (currentX != targetX || currentZ != targetZ) {
            currentX = moveTowards(currentX, targetX);
            currentZ = moveTowards(currentZ, targetZ);
            currentPosition = new BlockPos(currentX, target.getY(), currentZ);

            if (isNotBlocked && world.getBlockState(currentPosition).getCollisionShape(world, currentPosition) == Shapes.block())
                isNotBlocked = false;
        }

        return isNotBlocked;
    }

}