package aboe.enchantlib.mixin;

import aboe.enchantlib.util.EnchantmentPowerUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.EnchantmentTableBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static aboe.enchantlib.config.DefaultConfig.NEW_BOOKSHELF_OFFSETS;
import static aboe.enchantlib.config.DefaultConfig.particleChance;
import static aboe.enchantlib.util.EnchantmentPowerUtils.isValidPowerProvider;

@Mixin(EnchantmentTableBlock.class)
public abstract class EnchantmentTableMixin extends BaseEntityBlock {

    protected EnchantmentTableMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "isValidBookShelf", at = @At(value = "HEAD"), cancellable = true)
    private static void checkValidEnchantProvider(Level world, BlockPos blockPos, BlockPos offset, CallbackInfoReturnable<Boolean> cir){
        cir.setReturnValue(isValidPowerProvider(world, blockPos, offset, EnchantmentPowerUtils.PathCheck.FULL));
    }

    //The animation is actually the same-
    @Inject(method = "animateTick", at = @At(value = "HEAD"))
    public void betterAnimation(BlockState blockState, Level world, BlockPos blockPos, RandomSource randomSource, CallbackInfo ci) {
        super.animateTick(blockState, world, blockPos, randomSource);
        for (BlockPos providerPos : NEW_BOOKSHELF_OFFSETS) {
            if (randomSource.nextInt(particleChance) == 0 && isValidPowerProvider(world, blockPos, providerPos, EnchantmentPowerUtils.PathCheck.FULL)) {
                world.addParticle(
                        ParticleTypes.ENCHANT,
                        blockPos.getX() + 0.5, blockPos.getY() + 2.0, blockPos.getZ() + 0.5,
                        (providerPos.getX() + randomSource.nextFloat()) - 0.5,
                        (providerPos.getY() - randomSource.nextFloat()) - 1.0F,
                        (providerPos.getZ() + randomSource.nextFloat()) - 0.5);
            }
        }
    }

}
