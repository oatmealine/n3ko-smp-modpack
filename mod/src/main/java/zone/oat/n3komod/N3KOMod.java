package zone.oat.n3komod;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.unascribed.lib39.core.api.AutoRegistry;
import com.unascribed.lib39.dessicant.api.*;

public class N3KOMod implements ModInitializer {
	public static String NAMESPACE = "n3ko";

	public static final AutoRegistry autoreg = AutoRegistry.of(NAMESPACE);
	public static final Logger LOGGER = LoggerFactory.getLogger("n3ko");

	@Override
	public void onInitialize() {
		DessicantControl.optIn(NAMESPACE);
		LOGGER.info("burunyuu");
	}
}