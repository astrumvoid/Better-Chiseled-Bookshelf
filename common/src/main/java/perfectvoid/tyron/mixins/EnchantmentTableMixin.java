package perfectvoid.tyron.mixins;

import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import perfectvoid.tyron.EnchantLib;
import perfectvoid.tyron.config.ConfigGetter;
import perfectvoid.tyron.util.EnchantmentPowerUtil;

import static perfectvoid.tyron.config.Configs.*;
import static perfectvoid.tyron.util.EnchantmentPowerUtil.getValidPowerProvidersInArea;

@Mixin(EnchantingTableBlock.class)
public abstract class EnchantmentTableMixin extends BlockWithEntity {
   @Unique private static int tickUntilUpdate = 0;
//    public List<BlockPos> lastValidProvider = new ArrayList<>();

    public EnchantmentTableMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "randomDisplayTick", at = @At(value = "HEAD"))
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random, CallbackInfo ci) {
        super.randomDisplayTick(state, world, pos, random);

//        This is just to try to avoid some massive lag that happens if your table size is too big. It sure doesn't avoid it, but makes it a little more bearable
//        if (tickUntilUpdate <= 0) {
//            lastValidProvider = getValidPowerProvidersInArea(world, pos, ConfigGetter.getTableSize(), obType);
//            tickUntilUpdate = 4 + getExtraTicks();
//        } else --tickUntilUpdate;

        performanceWarning();

        for (BlockPos providerOffSetPos : getValidPowerProvidersInArea(world, pos, ConfigGetter.getTableSize(), obType)) {
            if (random.nextInt(particleChance) == 0)
                world.addParticle(
                        ParticleTypes.ENCHANT,
                        pos.getX() + 0.5f, pos.getY() + 2f, pos.getZ() + 0.5,
                        (providerOffSetPos.getX() + random.nextFloat()) - 0.5f,
                        (providerOffSetPos.getY() - random.nextFloat()) - 1.0F,
                        (providerOffSetPos.getZ() + random.nextFloat()) - 0.5f
                );
        }
    }

    @Unique
    private static void performanceWarning(){
        if (XZSize > 15 && tickUntilUpdate <= 0) {
            EnchantLib.logger.warn("Enchantment Table is set to a size of: " + XZSize + ". Performance might be hurt!" );
            tickUntilUpdate = 560;
        } else --tickUntilUpdate;
    }

//    private static int getExtraTicks(){
//        return (Configs.XZSize > 10 || Configs.YSize > 10) ? (Configs.XZSize + Configs.YSize) / 4 : 0;
//    }

    @Inject(method = "canAccessPowerProvider", at = @At(value = "HEAD"), cancellable = true)
    private static void canAccessPowerProvider(World world, BlockPos tablePos, BlockPos providerOffset, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(EnchantmentPowerUtil.isPathObstructed(world, tablePos, providerOffset.mutableCopy(), obType));
    }
}
