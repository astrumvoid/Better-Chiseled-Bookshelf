package aboe.enchantlib.mixin;

import aboe.enchantlib.util.IEnchantmentPowerProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ChiseledBookshelfBlock;
import net.minecraft.block.entity.ChiseledBookshelfBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

import static aboe.enchantlib.config.ConfigGetter.getBookEnchantmentPower;
import static aboe.enchantlib.config.ConfigGetter.getBookRedstonePower;
import static aboe.enchantlib.config.Configs.modifyRedstoneOutput;

@Mixin(ChiseledBookshelfBlock.class)
public abstract class ChiseledBookShelfMixin extends BlockWithEntity implements IEnchantmentPowerProvider {

    protected ChiseledBookShelfMixin(Settings properties) {
        super(properties);
    }

    public int getComparatorOutput(BlockState blockState, World world, BlockPos blockPos) {
        if (world.isClient) return 0;

        if (world.getBlockEntity(blockPos) instanceof ChiseledBookshelfBlockEntity shelfEntity) {
            return modifyRedstoneOutput ? getRedstonePower(shelfEntity) : shelfEntity.getLastInteractedSlot() + 1;
        } else return 0;
    }

    private int getRedstonePower(ChiseledBookshelfBlockEntity shelfEntity) {
        int power = 0;

        for (int slot = 0; slot < 6; slot++) {
            if (!shelfEntity.getStack(slot).isEmpty())
                power += getBookRedstonePower(shelfEntity.getStack(slot));
        }
        return power;
    }

    @Override
    public float getEnchantmentPower(World world, BlockPos pos, BlockState state) {
        float enchantedPower = 0;

        if (world.getBlockEntity(pos) instanceof ChiseledBookshelfBlockEntity shelfBlock) {
            for (byte slot = 0; slot < 6; slot++)
                if (!shelfBlock.getStack(slot).isEmpty())
                    enchantedPower += getBookEnchantmentPower(shelfBlock.getStack(slot));
        }
        return enchantedPower;
    }
}
