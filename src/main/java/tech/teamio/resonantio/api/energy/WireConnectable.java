package tech.teamio.resonantio.api.energy;


import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;

public interface WireConnectable {
    WireNetwork.WireConnectionType canConnect(IWorld world, Direction direction, BlockPos target, BlockPos source);
}
