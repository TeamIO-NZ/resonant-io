package tech.teamio.resonantio.blocks.blockentities;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import tech.teamio.resonantio.api.energy.WireNetwork;

import java.util.UUID;

public class WireBlockEntity extends BlockEntity {

    public boolean tickedOnce;

    public UUID networkId;

    public WireBlockEntity() {
        super(null);
    }

    public void onPlaced() {
        networkId = new WireNetwork(this).getId();
        tickedOnce = true;
        WireNetwork.blockPlaced();
    }
}
