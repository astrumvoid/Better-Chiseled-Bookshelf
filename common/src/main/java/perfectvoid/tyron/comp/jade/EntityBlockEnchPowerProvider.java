package perfectvoid.tyron.comp.jade;

import dev.architectury.platform.Platform;
import net.minecraft.block.Blocks;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import perfectvoid.tyron.util.IEnchantmentPowerProvider;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.StreamServerDataProvider;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.theme.IThemeHelper;
import snownee.jade.overlay.DisplayHelper;

public enum EntityBlockEnchPowerProvider implements StreamServerDataProvider<BlockAccessor, Float> {
    INSTANCE;

    @Override
    public @NotNull Float streamData(BlockAccessor blockAccessor) {
        if (blockAccessor.getBlock() instanceof IEnchantmentPowerProvider powerProvider) {
            if (Platform.isModLoaded("easymagic") && blockAccessor.getBlockState().isOf(Blocks.CHISELED_BOOKSHELF)) return 0f;

            return powerProvider.getEnchantmentPower(blockAccessor.getLevel(), blockAccessor.getPosition(), blockAccessor.getBlockState());
        }

        return 0f;
    }

    @Override
    public PacketCodec<RegistryByteBuf, Float> streamCodec() {
        return PacketCodecs.FLOAT.cast();
    }

    public Identifier getUid() {
        return Identifier.ofVanilla("enchantment_power");
    }

    public enum Client implements IBlockComponentProvider {
        INSTANCE;

        @Override
        public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
            if (EntityBlockEnchPowerProvider.INSTANCE.decodeFromData(blockAccessor).get() > 0f) {

                iTooltip.add(Text.translatable("enchantlib.ench_power", IThemeHelper.get().info(
                        DisplayHelper.dfCommas.format(EntityBlockEnchPowerProvider.INSTANCE.decodeFromData(blockAccessor).get()))));
            }
        }

        public Identifier getUid() {
            return Identifier.ofVanilla("enchantment_power");
        }
    }
}
