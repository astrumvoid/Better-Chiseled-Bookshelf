package perfectvoid.enchantlib.mixins;

import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ChiseledBookshelfBlock;
import net.minecraft.block.entity.ChiseledBookshelfBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import perfectvoid.enchantlib.util.IEnchantmentPowerProvider;

import static perfectvoid.enchantlib.config.ConfigGetter.getBookEnchantmentPower;
import static perfectvoid.enchantlib.config.ConfigGetter.getBookRedstonePower;
import static perfectvoid.enchantlib.config.Configs.modifyRedstoneOutput;

@Mixin(ChiseledBookshelfBlock.class)
public abstract class ChiseledBookShelfMixin extends BlockWithEntity implements IEnchantmentPowerProvider {

    protected ChiseledBookShelfMixin(Settings settings) {
        super(settings);
    }

    public int getComparatorOutput(BlockState blockState, World world, BlockPos blockPos) {
        if (world.isClient) return 0;

        if (world.getBlockEntity(blockPos) instanceof ChiseledBookshelfBlockEntity shelfEntity) {
            return modifyRedstoneOutput ? getRedstonePower(shelfEntity) : shelfEntity.getLastInteractedSlot() + 1;
        } else return 0;
    }

    private int getRedstonePower(ChiseledBookshelfBlockEntity shelfEntity){
        int power = 0;

        for (int slot = 0; slot < 6; slot++) {
            if (!shelfEntity.getStack(slot).isEmpty())
                power += getBookRedstonePower(shelfEntity.getStack(slot));
        }
        return power;
    }

    @Override
    public float getEnchantmentPower(World world, BlockPos pos, BlockState state) {
        float powerInShelf = 0;

        if (world.getBlockEntity(pos) instanceof ChiseledBookshelfBlockEntity shelfEntity) {
            for (int slot = 0; slot < 6; slot++) {
                if (!shelfEntity.getStack(slot).isEmpty())
                    powerInShelf += getBookEnchantmentPower(shelfEntity.getStack(slot));
            }
        }

        return powerInShelf;
    }
}
