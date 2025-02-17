package aboe.enchantlib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class EnchantLib {
    public static final String MOD_ID = "enchantlib";
    public static final Logger logger = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        logger.info("Upgrading Chiseled Bookshelves...");
    }
}
