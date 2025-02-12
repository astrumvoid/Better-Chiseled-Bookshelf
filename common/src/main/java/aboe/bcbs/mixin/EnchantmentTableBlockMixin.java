package aboe.bcbs.mixin;

import aboe.bcbs.util.EnchantmentPowerUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.EnchantmentTableBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

import static aboe.bcbs.util.EnchantmentPowerUtils.isValidEnchantmentSource;

@Mixin(EnchantmentTableBlock.class)
public abstract class EnchantmentTableBlockMixin extends BaseEntityBlock {

    @Unique
    private static final boolean better_cbs$bigger_table = true;
    @Unique
    private static final List<BlockPos> better_cbs$bigger = BlockPos.betweenClosedStream(-4, 0, -4, 4, 1, 4).map(BlockPos::immutable).toList();
    @Unique
    private static final List<BlockPos> BOOKSHELF_OFFSETS = (better_cbs$bigger_table) ? better_cbs$bigger : EnchantmentTableBlock.BOOKSHELF_OFFSETS;

    protected EnchantmentTableBlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "isValidBookShelf", at = @At(value = "HEAD"), cancellable = true)
    private static void checkValidEnchantProvider(Level world, BlockPos blockPos, BlockPos offset, CallbackInfoReturnable<Boolean> cir){
        cir.setReturnValue(isValidEnchantmentSource(world, blockPos, offset, EnchantmentPowerUtils.PathCheckMode.FULL));
    }

    @Inject(method = "animateTick", at = @At(value = "HEAD"))
    public void betterAnimation(BlockState blockState, Level world, BlockPos blockPos, RandomSource randomSource, CallbackInfo ci) {
        super.animateTick(blockState, world, blockPos, randomSource);
        for (BlockPos providerPos : BOOKSHELF_OFFSETS) {
            if (randomSource.nextInt(16) == 0 && isValidEnchantmentSource(world, blockPos, providerPos, EnchantmentPowerUtils.PathCheckMode.FULL)) {
                world.addParticle(
                        ParticleTypes.ENCHANT,
                        (double) blockPos.getX() + 0.5, (double) blockPos.getY() + 2.0, (double) blockPos.getZ() + 0.5,
                        (double) ((float) providerPos.getX() + randomSource.nextFloat()) - 0.5, (double) ((float) providerPos.getY() - randomSource.nextFloat() - 1.0F), (double) ((float) providerPos.getZ() + randomSource.nextFloat()) - 0.5);
            }
        }

    }

}
