package tech.teamio.resonantio;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tech.teamio.resonantio.utils.ModBootstrap;
import tech.teamio.resonantio.utils.ModConstants;

//ModInitializer starts the mod
public class ResonantIO implements ModInitializer {

    public static final Logger LOGGER = LogManager.getLogger();

    //Creative tab
    public static final ItemGroup CREATIVE_GROUP = FabricItemGroupBuilder.create(new Identifier(ModConstants.MOD_ID, "item_group")).icon(Items.DIRT::getStackForRender).build();

    @Override
    public void onInitialize() {
        long startTime = System.currentTimeMillis();
        ModBootstrap.bootstrap(); // init things

        LOGGER.info("Initialization complete. (Took {}ms)", System.currentTimeMillis() - startTime);
    }
}