package perfectvoid.tyron;

import eu.midnightdust.lib.config.MidnightConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import perfectvoid.tyron.config.Configs;

public final class EnchantLib {
    public static final String MOD_ID = "enchantlib";
    public static final Logger logger = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        MidnightConfig.init(MOD_ID, Configs.class);
        logger.info("Initializing EnchantLib!");
    }
}
