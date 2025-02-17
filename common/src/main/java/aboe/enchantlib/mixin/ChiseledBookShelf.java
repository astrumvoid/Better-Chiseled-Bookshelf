package aboe.enchantlib.mixin;

import aboe.enchantlib.util.IEnchantmentPowerProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.ChiseledBookShelfBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import org.spongepowered.asm.mixin.Mixin;

import static aboe.enchantlib.config.DefaultConfig.*;

@Mixin(ChiseledBookShelfBlock.class)
public abstract class ChiseledBookShelf extends BaseEntityBlock implements IEnchantmentPowerProvider {

    protected ChiseledBookShelf(Properties properties) {
        super(properties);
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level world, BlockPos blockPos){
        if (world.isClientSide) return 0;


        BlockEntity shelf = world.getBlockEntity(blockPos);

        //Checks if the slot has a book in it. If it has, adds a value according to the type of book.
        if (shelf instanceof ChiseledBookShelfBlockEntity shelfEntity) {
            if (ModifyRedstoneOutput){
            int power = 0;

            for (int slot = 0; slot < 6; slot++) //Checks each slot.
                if (!shelfEntity.getItem(slot).isEmpty()) //Adds 2 of power to enchanted books and 1 to normal books.
                    power += (shelfEntity.getItem(slot).is(Items.ENCHANTED_BOOK)) ? 2 : 1;

            return power;
            } else return shelfEntity.getLastInteractedSlot() + 1; //Minecraft Default
        }
        else return 0;
    }

    //It's just a copy of the "GetAnalogOutputSignal" code, but it returns a float value instead.
    //The float is converted into an int in the end, but it's useful to make whole numbers with more shelf that provides less or more power
    @Override
    public float getEnchantmentPower(BlockState state, Level world, BlockPos pos) {
        float power = 0;

        if (world.getBlockEntity(pos) instanceof ChiseledBookShelfBlockEntity shelfBlock) {
            for (byte slot = 0; slot < 6; slot++)
                if (!shelfBlock.getItem(slot).isEmpty())
                    power += (shelfBlock.getItem(slot).is(Items.ENCHANTED_BOOK)) ? better_cbs$enchantedBookPower : better_cbs$normalBookPower;
        }
        return power;
    }
}
