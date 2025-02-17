package aboe.enchantlib;

import dev.architectury.platform.Platform;
import org.objectweb.asm.tree.ClassNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

import static aboe.enchantlib.EnchantLib.MOD_ID;

public class mixinLoader implements IMixinConfigPlugin
{
    @Override
    public boolean shouldApplyMixin(String s, String mixinClassName) {
        if ("aboe.enchantlib.mixin.EasyMagicComp".equals(mixinClassName))
            return Platform.isModLoaded("easymagic");
        if ("aboe.enchantlib.mixin.EnchPowerJade".equals(mixinClassName))
            return Platform.isModLoaded("jade");
        return true;
    }

    @Override
    public void onLoad(String s) {

    }

    @Override
    public String getRefMapperConfig() {
        return "";
    }

    @Override
    public void acceptTargets(Set<String> set, Set<String> set1) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) {

    }

    @Override
    public void postApply(String s, ClassNode classNode, String s1, IMixinInfo iMixinInfo) {
    }
}
