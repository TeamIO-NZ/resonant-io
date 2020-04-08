package tech.teamio.resonantio;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import tech.teamio.resonantio.blocks.IOBlocks;

@Environment(EnvType.CLIENT)
public class ResonantIOClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(IOBlocks.WIRE, RenderLayer.getCutout());
    }
}
