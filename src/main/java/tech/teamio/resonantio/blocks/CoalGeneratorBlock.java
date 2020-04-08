package tech.teamio.resonantio.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import tech.teamio.resonantio.api.energy.WireConnectable;
import tech.teamio.resonantio.api.energy.WireNetwork;
import tech.teamio.resonantio.blocks.blockentities.CoalGeneratorBlockEntity;

import java.util.Random;


public class CoalGeneratorBlock extends Block implements BlockEntityProvider, WireConnectable {

    private static final BooleanProperty active = BooleanProperty.of("active");
    private static final DirectionProperty facing = Properties.HORIZONTAL_FACING;

    public CoalGeneratorBlock() {
        super(FabricBlockSettings.of(Material.METAL).build());
    }

    @Override
    public BlockEntity createBlockEntity(BlockView view) {
        return new CoalGeneratorBlockEntity();
    }

    @Override
    public void onBlockRemoved(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CoalGeneratorBlockEntity) {
                ItemScatterer.spawn(world, pos, ((CoalGeneratorBlockEntity) blockEntity).getFuel());
                world.updateHorizontalAdjacent(pos, this);
            }
            super.onBlockRemoved(state, world, pos, newState, moved);
        }
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if(state.get(active)) {
            world.playSound(pos.getX() + 0.5d,
                    pos.getY() + 0.5d,
                    pos.getZ() + 0.5d,
                    SoundEvents.BLOCK_CAMPFIRE_CRACKLE,
                    SoundCategory.BLOCKS,
                    0.2F + random.nextFloat() * 0.2F,
                    0.9F + random.nextFloat() * 0.15F,
                    false
            );
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(active);
        builder.add(facing);
    }


    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx)  {
        return this.getDefaultState()
                .with(active, false)
                .with(facing, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    public WireNetwork.WireConnectionType canConnect(IWorld world, Direction direction, BlockPos target, BlockPos source) {
        return WireNetwork.WireConnectionType.ENERGY_OUTPUT;
    }
}
