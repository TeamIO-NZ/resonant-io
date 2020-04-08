package tech.teamio.resonantio.blocks;

import io.github.cottonmc.energy.api.EnergyAttributeProvider;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import tech.teamio.resonantio.api.energy.WireConnectable;
import tech.teamio.resonantio.api.energy.WireNetwork;
import tech.teamio.resonantio.api.energy.WireUtils;
import tech.teamio.resonantio.blocks.blockentities.WireBlockEntity;

public class WireBlock extends Block implements WireConnectable, BlockEntityProvider {

    private static final VoxelShape NONE = createCuboidShape(4, 1, 4, 12, 4, 12);

    private static BooleanProperty ATTACHED_NORTH = BooleanProperty.of("attached_north");
    private static BooleanProperty ATTACHED_EAST = BooleanProperty.of("attached_east");
    private static BooleanProperty ATTACHED_SOUTH = BooleanProperty.of("attached_south");
    private static BooleanProperty ATTACHED_WEST = BooleanProperty.of("attached_west");
    private static BooleanProperty ATTACHED_UP = BooleanProperty.of("attached_up");
    private static BooleanProperty ATTACHED_DOWN = BooleanProperty.of("attached_down");

    public WireBlock() {
        super(FabricBlockSettings.of(Material.METAL).build());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> factory) {
        super.appendProperties(factory);
        factory.add(ATTACHED_NORTH, ATTACHED_EAST, ATTACHED_SOUTH, ATTACHED_WEST, ATTACHED_UP, ATTACHED_DOWN);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        BlockState state = this.getDefaultState();
        for (Direction direction : Direction.values()) {
            Block block = context.getWorld().getBlockState(WireUtils.getPosFromDirection(direction, context.getBlockPos())).getBlock();
            if (block instanceof WireConnectable) {
                if (((WireConnectable) block).canConnect(context.getWorld(), direction.getOpposite(), context.getBlockPos(), WireUtils.getPosFromDirection(direction, context.getBlockPos())) != WireNetwork.WireConnectionType.NONE) {
                    state = state.with(getProp(direction), true);
                }
            } else if (block instanceof EnergyAttributeProvider) {
                state = state.with(getProp(direction), true);
            } else {
                state = state.with(getProp(direction), false);
            }
        }

        return state;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext context) {
        return NONE;
    }

    private BooleanProperty getProp(Direction direction) {
        switch (direction) {
            case NORTH:
                return ATTACHED_NORTH;
            case SOUTH:
                return ATTACHED_SOUTH;
            case EAST:
                return ATTACHED_EAST;
            case WEST:
                return ATTACHED_WEST;
            case UP:
                return ATTACHED_UP;
            case DOWN:
                return ATTACHED_DOWN;
            default:
                return null;
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public boolean isSimpleFullBlock(BlockState state, BlockView blockView_1, BlockPos pos) {
        return false;
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView view, BlockPos pos) {
        return true;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction_1, BlockState blockState_2, IWorld world, BlockPos thisWire, BlockPos otherConnectable) {
        return state.with(getProp(direction_1), (
                !(blockState_2).isAir()
                        && blockState_2.getBlock() instanceof WireConnectable
                        // get opposite of direction so the WireConnectable can check from its perspective.
                        && (((WireConnectable) blockState_2.getBlock()).canConnect(world, direction_1.getOpposite(), thisWire, otherConnectable) != WireNetwork.WireConnectionType.NONE)
        ));
    }

    @Override
    public WireNetwork.WireConnectionType canConnect(IWorld world, Direction direction, BlockPos target, BlockPos source) {
        return WireNetwork.WireConnectionType.WIRE;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView view) {
        return new WireBlockEntity();
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity livingEntity, ItemStack stack) {
        if (world.getBlockEntity(pos) instanceof WireBlockEntity) {
            ((WireBlockEntity) world.getBlockEntity(pos)).onPlaced();
        }
    }
}
