package aboe.enchantlib.config;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EnchantmentTableBlock;

import java.util.List;

public class DefaultConfig {
    //I don't recommend you getting any of the values from here.

    //Size to look the bigger bookshelf offset
    private static final List<BlockPos> better_cbs$bigger = BlockPos.betweenClosedStream(-4, 0, -4, 4, 1, 4).map(BlockPos::immutable).toList();

    private static final boolean better_cbs$bigger_table = true; //Decides if the bookshelves should be further away
    public static final List<BlockPos> NEW_BOOKSHELF_OFFSETS = (better_cbs$bigger_table) ? better_cbs$bigger : EnchantmentTableBlock.BOOKSHELF_OFFSETS;

    //The smaller the number, there more particles you will have, this can not be 0.
    public static final int particleChance = 2; //Default is 16

    //If set to false, it will go back to the default "Last Interacted Slot"
    public static final boolean ModifyRedstoneOutput = true;

    private static final boolean better_cbs$multiplyNormal = true;

    public static float better_cbs$normalBookPower = 0.1666666666666667f;

    public static byte multiplier = 2;
    private static final float enchantedPower = better_cbs$normalBookPower * multiplier;

    public static float better_cbs$enchantedBookPower =
            (better_cbs$multiplyNormal)
            ? better_cbs$normalBookPower * multiplier
            : enchantedPower;
}
