package aboe.enchantlib.util;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EnchantmentTableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;

import java.util.List;

import static aboe.enchantlib.config.Configs.biggerTable;

public class EnchantmentPowerUtils {

    public static List<BlockPos> GetBookShelfOffsets(){
        if (biggerTable)
            return BlockPos.betweenClosedStream(-4, 0, -4, 4, 2, 4)
                    .filter((blockPos) -> Math.abs(blockPos.getX()) >= 2 || Math.abs(blockPos.getZ()) >= 2)
                    .map(BlockPos::immutable).toList();
        else
            return EnchantmentTableBlock.BOOKSHELF_OFFSETS;
    }

    public enum PathCheck {
        NONE,
        TAG,
        FULL
    }

    public static float getCurrentBlockPower(Level world, BlockPos blockPos, BlockState blockState){
        if (blockState.getBlock() instanceof IEnchantmentPowerProvider powerProvider)
            return powerProvider.getEnchantmentPower(blockState, world, blockPos);
        else if (blockState.is(BlockTags.ENCHANTMENT_POWER_PROVIDER))
             return 1;
        else return 0;
    }

    public static float getEnchantmentPowerFromValidBlock(Level world, BlockPos origin, BlockPos offset, PathCheck mode) {
        if (isValidPowerProvider(world, origin, offset, mode)) {
            BlockState blockState = world.getBlockState(origin.offset(offset));
            return (blockState.getBlock() instanceof IEnchantmentPowerProvider power)
                    ? power.getEnchantmentPower(blockState, world, origin.offset(offset))
                    : 1;
        }
        else return 0;
    }

    public static boolean isValidPowerProvider(Level world, BlockPos origin, BlockPos offset, PathCheck mode) {
        BlockState blockToCheck = world.getBlockState(origin.offset(offset));
        switch (mode){
            case FULL -> {
                return (blockToCheck.is(BlockTags.ENCHANTMENT_POWER_PROVIDER) || blockToCheck.getBlock() instanceof IEnchantmentPowerProvider)
                        && isNotBlocked(world, origin, offset, true);
            }
            case TAG -> {
                return (blockToCheck.is(BlockTags.ENCHANTMENT_POWER_PROVIDER) || blockToCheck.getBlock() instanceof IEnchantmentPowerProvider)
                        && isNotBlocked(world, origin, offset, false);
            }
            default -> {
                return blockToCheck.is(BlockTags.ENCHANTMENT_POWER_PROVIDER) || blockToCheck.getBlock() instanceof IEnchantmentPowerProvider;
            }
        }
    }

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

    private static boolean isNotBlocked(Level world, BlockPos origin, BlockPos startPos, boolean FullBlockSearch) {
        //Offsets the current enchantment table position, which means: Ignores the table/origin, we don't care about it
        BlockPos target = origin.offset(why(startPos.getX()), startPos.getY(), why(startPos.getZ()));

        BlockPos.MutableBlockPos searchPos = new BlockPos.MutableBlockPos();

        //Gets the position of the limit
        int targetZ = target.getZ();
        int targetX = target.getX();

        searchPos.setY(target.getY());

        // I mean, it's just an int, why would I use the offset function if all I need is one value? I'll do the offset manually!
        searchPos.setX(origin.getX() + startPos.getX());
        searchPos.setZ(origin.getZ() + startPos.getZ());

        boolean isNotBlocked = true;

        while (searchPos.getX() != targetX || searchPos.getZ() != targetZ) {
            searchPos.setX(moveTowards(searchPos.getX(), targetX));
            searchPos.setZ(moveTowards(searchPos.getZ(), targetZ));


            //IDK why it changes the bool to false instead of just returning false, but... I'll change that in the yarn mapping version I'm working on.
            if (isNotBlocked && !FullBlockSearch && !world.getBlockState(searchPos).is(BlockTags.ENCHANTMENT_POWER_TRANSMITTER))
                isNotBlocked = false;

            if (isNotBlocked && FullBlockSearch && world.getBlockState(searchPos).getCollisionShape(world, searchPos) == Shapes.block())
                isNotBlocked = false;
        }

        return isNotBlocked;
    }
}