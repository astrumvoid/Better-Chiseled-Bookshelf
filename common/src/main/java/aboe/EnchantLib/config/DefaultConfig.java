package aboe.EnchantLib.config;

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
}
