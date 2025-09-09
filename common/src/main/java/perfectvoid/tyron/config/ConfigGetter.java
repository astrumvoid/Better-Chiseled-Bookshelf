package perfectvoid.tyron.config;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;

import java.util.List;

import static perfectvoid.tyron.config.Configs.*;

public class ConfigGetter {
    public static final List<BlockPos> defaultSize = BlockPos.stream(-2, 0, -2, 2, 1, 2)
            .filter(pos -> Math.abs(pos.getX()) == 2 || Math.abs(pos.getZ()) == 2).map(BlockPos::toImmutable).toList();

    public static final List<BlockPos> biggerSize = BlockPos.stream(-3, 0, -3, 3, 2, 3)
            .filter(pos -> Math.abs(pos.getX()) >= 2 || Math.abs(pos.getZ()) >= 2).map(BlockPos::toImmutable).toList();


    public static float getEnchantedBookPower() {
        return (multiplyNormalBook) ? normalBookPower * multiplier : enchantedBookPower;
    }

    public static float getEnchantmentPowerOutput(ItemStack book){
        return book.isOf(Items.ENCHANTED_BOOK) ? getEnchantedBookPower() : normalBookPower;
    }

    public static int getRedstonePowerOutput(ItemStack book){
        return book.isOf(Items.ENCHANTED_BOOK) ? enchantedBookPowerOutput : normalBookPowerOutput;
    }


    public static List<BlockPos> getTableSize() {
        switch (tableSize) {
            case BIGGER -> {
                return biggerSize;
            }
            case CUSTOM -> {
                return BlockPos.stream(-XZSize, getBelow(), -XZSize, XZSize, YSize, XZSize)
                        .filter(pos -> Math.abs(pos.getX()) >= 2 || Math.abs(pos.getZ()) >= 2).map(BlockPos::toImmutable).toList();
            }
            case null, default -> {
                return defaultSize;
            }
        }
    }

    private static int getBelow(){
        return (yGoesDown) ? -YSize : 0;
    }
}
