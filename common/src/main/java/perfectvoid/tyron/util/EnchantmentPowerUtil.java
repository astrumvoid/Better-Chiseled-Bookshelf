package perfectvoid.tyron.util;

import net.minecraft.block.BlockState;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import perfectvoid.tyron.config.Configs.obstructionType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class EnchantmentPowerUtil {

    private static final List<BlockPos> centerOffset = BlockPos.stream(-1, 0, -1, 1, 0, 1).map(BlockPos::toImmutable).toList();
    public static Predicate<BlockState> isPowerProvider = state -> state.getBlock() instanceof IEnchantmentPowerProvider || state.isIn(BlockTags.ENCHANTMENT_POWER_PROVIDER);

    public static float getPowerFromBlock(World world, BlockPos pos, BlockState state) {
        if (state.getBlock() instanceof IEnchantmentPowerProvider provider) return provider.getEnchantmentPower(world, pos, state);
        else if (state.isIn(BlockTags.ENCHANTMENT_POWER_PROVIDER)) return 1;
        else return 0;
    }

    public static List<BlockPos> getValidPowerProvidersInArea(World world, BlockPos origin, List<BlockPos> areaToSearch, obstructionType type) {
        List<BlockPos> foundProviders = new ArrayList<>();
        BlockPos.Mutable searchPos = new BlockPos.Mutable();

        for (BlockPos pos : areaToSearch) {
            if (isPowerProvider.test(world.getBlockState(searchPos.set(origin, pos))) && !isPathObstructed(world, origin, pos.mutableCopy(), type))
                foundProviders.add(pos);
        }

        return foundProviders;
    }

    public static float getPowerFromValidProviders(World world, BlockPos origin, List<BlockPos> areaOffSetToSearch, obstructionType obstructionType) {
        float power = 0;

        for (BlockPos providerOffset : getValidPowerProvidersInArea(world, origin, areaOffSetToSearch, obstructionType)) {
            power += getPowerFromBlock(world, origin.add(providerOffset), world.getBlockState(origin.add(providerOffset)));
        }

        return power;
    }

    public static boolean isPathObstructed(World world, BlockPos origin, BlockPos.Mutable providerOffset, obstructionType type) {
        if (type == obstructionType.NONE) return false;

        BlockPos.Mutable searchPos = new BlockPos.Mutable();
        BlockPos originOffset = centerOffset.stream().sorted(Comparator.comparing(providerOffset::getManhattanDistance)).toList().getFirst();
        //BlockPos.stream(-1, 0, -1, 1, 0, 1).map(BlockPos::toImmutable).sorted(Comparator.comparing(providerOffset::getManhattanDistance)).toList().getFirst();

        while (providerOffset.getZ() != originOffset.getZ() || providerOffset.getX() != originOffset.getX()) {

            if (providerOffset.getX() != originOffset.getX()) providerOffset.setX(moveByOne(providerOffset.getX()));
            if (providerOffset.getZ() != originOffset.getZ()) providerOffset.setZ(moveByOne(providerOffset.getZ()));
            if (Math.abs(providerOffset.getY()) > 2) providerOffset.setY(moveByOne(providerOffset.getY()));

            searchPos.set(origin, providerOffset);

            if (type == obstructionType.SOLID && world.getBlockState(searchPos).isFullCube(world, searchPos))
                return true;
            if (type == obstructionType.DEFAULT && !world.getBlockState(searchPos).isIn(BlockTags.ENCHANTMENT_POWER_TRANSMITTER))
                return true;

            //if (!world.isClient) world.setBlockState(searchPos, Blocks.HONEY_BLOCK.getDefaultState());
        }

        return false;
    }

    private static int moveByOne(int pos){
        return pos - pos / Math.abs(pos);
    }
}
