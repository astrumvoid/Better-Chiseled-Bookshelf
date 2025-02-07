package aboe.bcbs.mixin;

import aboe.bcbs.others.EnchantmentPower;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.ChiseledBookShelfBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import org.spongepowered.asm.mixin.Mixin;

@Mixin(ChiseledBookShelfBlock.class)
public abstract class ChiseledBookShelf extends BaseEntityBlock implements EnchantmentPower {

    protected ChiseledBookShelf(Properties properties) {
        super(properties);
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level world, BlockPos blockPos){
        if (world.isClientSide) return 0;

        BlockEntity shelf = world.getBlockEntity(blockPos);

        // Checks if the slot has a book in it. If it has, it will add a value according to the type of book.
        if (shelf instanceof ChiseledBookShelfBlockEntity shelfEntity) {
            int power = 0;
            for (int slot = 0; slot < 6; slot++)
                if (!shelfEntity.getItem(slot).isEmpty())
                    power += (shelfEntity.getItem(slot).is(Items.ENCHANTED_BOOK)) ? 2 : 1 ;

            return power;
        }
        else return 0;
    }

    @Override
    public float enchantmentPower(BlockState state,Level world, BlockPos pos) {
        float power = 0;
        if (world.getBlockEntity(pos) instanceof ChiseledBookShelfBlockEntity shelfBlock) {
            for (byte slot = 0; slot < 6; slot++)
                if (!shelfBlock.getItem(slot).isEmpty())
                    power += (shelfBlock.getItem(slot).is(Items.ENCHANTED_BOOK)) ? 0.21f : 0.1666666666666667f;
        }
        return power;
    }
}
