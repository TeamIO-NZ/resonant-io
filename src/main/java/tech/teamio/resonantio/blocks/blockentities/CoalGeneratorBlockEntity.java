package tech.teamio.resonantio.blocks.blockentities;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.screen.ingame.ContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultedList;
import tech.teamio.resonantio.client.screen.handler.CoalGeneratorScreenHandler;


public class CoalGeneratorBlockEntity extends BlockEntity implements ContainerProvider<CoalGeneratorScreenHandler> {

    private DefaultedList<ItemStack> fuel = DefaultedList.ofSize(2, ItemStack.EMPTY);

    public CoalGeneratorBlockEntity() {
        super(IOBlockEntities.COAL_GENERATOR_TYPE);
    }

    @Override
    public CoalGeneratorScreenHandler getContainer() {
        return null;
    }

    public DefaultedList<ItemStack> getFuel() {
        return fuel;
    }
}
