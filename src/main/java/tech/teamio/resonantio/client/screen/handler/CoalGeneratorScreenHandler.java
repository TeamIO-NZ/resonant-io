package tech.teamio.resonantio.client.screen.handler;

import net.fabricmc.api.Environment;
import net.minecraft.container.Container;
import net.minecraft.entity.player.PlayerEntity;


public class CoalGeneratorScreenHandler extends Container {

    protected CoalGeneratorScreenHandler(int syncId) {
        super(null, syncId);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
