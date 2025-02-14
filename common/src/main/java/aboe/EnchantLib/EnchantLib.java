package aboe.EnchantLib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class EnchantLib {
    public static final String MOD_ID = "EnchantLib";
    public static final Logger logger = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        logger.info("Upgrading Chiseled Bookshelves...");
    }
}
