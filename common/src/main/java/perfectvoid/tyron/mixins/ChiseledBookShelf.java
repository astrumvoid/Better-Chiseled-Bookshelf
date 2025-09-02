package perfectvoid.tyron.mixins;

import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ChiseledBookshelfBlock;
import net.minecraft.block.entity.ChiseledBookshelfBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import perfectvoid.tyron.util.IEnchantmentPowerProvider;

import static perfectvoid.tyron.config.ConfigGetter.getEnchantmentPowerOutput;
import static perfectvoid.tyron.config.ConfigGetter.getRedstonePowerOutput;
import static perfectvoid.tyron.config.Configs.modifyRedstoneOutput;

@Mixin(ChiseledBookshelfBlock.class)
public abstract class ChiseledBookShelf extends BlockWithEntity implements IEnchantmentPowerProvider {

    protected ChiseledBookShelf(Settings settings) {
        super(settings);
    }

    public int getComparatorOutput(BlockState blockState, World world, BlockPos blockPos){
        if (world.isClient) return 0;

        //Checks if the slot has a book in it. If it has, adds a value according to the type of book.
        if (world.getBlockEntity(blockPos) instanceof ChiseledBookshelfBlockEntity shelfEntity) {

            if (!modifyRedstoneOutput) return shelfEntity.getLastInteractedSlot() + 1;

            return getRedstonePower(shelfEntity);
        }
        else return 0;
    }

    private int getRedstonePower(ChiseledBookshelfBlockEntity shelfEntity){
        int power = 0;

        for (int slot = 0; slot < 6; slot++) {
            if (!shelfEntity.getStack(slot).isEmpty())
                power += getRedstonePowerOutput(shelfEntity.getStack(slot));
        }

        return power;
    }

    @Override
    public float getEnchantmentPower(World world, BlockPos pos, BlockState state) {
        float powerInShelf = 0;

        if (world.getBlockEntity(pos) instanceof ChiseledBookshelfBlockEntity shelfEntity) {
            for (int slot = 0; slot < 6; slot++) {

                if (!shelfEntity.getStack(slot).isEmpty())
                    powerInShelf += getEnchantmentPowerOutput(shelfEntity.getStack(slot));
            }
        }

        return powerInShelf;
    }
}
