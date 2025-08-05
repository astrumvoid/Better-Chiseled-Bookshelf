package aboe.enchantlib.config;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EnchantmentTableBlock;

import java.util.List;

public class DefaultConfig {
    //I don't recommend you getting any of the values from here.

    //Size to look the bigger bookshelf offset
    private static final List<BlockPos> better_cbs$bigger = BlockPos.betweenClosedStream(-4, 0, -4, 4, 1, 4).map(BlockPos::immutable).toList();

    private static final boolean better_cbs$bigger_table = false; //Decides if the bookshelves should be further away
    public static final List<BlockPos> NEW_BOOKSHELF_OFFSETS = (better_cbs$bigger_table)
            ? better_cbs$bigger
            : EnchantmentTableBlock.BOOKSHELF_OFFSETS;

    //The smaller the number, there more particles you will have, this can not be 0. Default is 16.
    public static final int particleChance = 16;

    //If set to false, it will go back to the default "Last Interacted Slot", instead of the output being in pair with the items in the shelf.
    public static final boolean ModifyRedstoneOutput = false;

    private static final boolean better_cbs$multiplyNormalBook = true;

    //Default power for normal books. its 0.1666666666666667f since it is 1 if multiplied by 6.
    public static float better_cbs$normalBookPower = 0.1666666666666667f;

    //Sets the multiplier for the enchanted book. Default is 2, which means that enchanted books will be two times more powerful than normal ones.
    public static byte multiplier = 2;

    //Default power for enchanted books. It's set to be double the normal one by default.
    private static final float enchantedPower = 0.3333333333333334f;

    public static float better_cbs$enchantedBookPower =
            (better_cbs$multiplyNormalBook)
            ? better_cbs$normalBookPower * multiplier
            : enchantedPower;
}
