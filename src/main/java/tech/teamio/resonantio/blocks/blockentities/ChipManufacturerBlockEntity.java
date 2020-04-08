package tech.teamio.resonantio.blocks.blockentities;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.gui.screen.ingame.ContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultedList;
import tech.teamio.resonantio.blocks.ChipManufacturerBlock;
import tech.teamio.resonantio.client.screen.handler.CoalGeneratorScreenHandler;


public class ChipManufacturerBlockEntity extends BlockEntity  {

    private DefaultedList<ItemStack> fuel = DefaultedList.ofSize(2, ItemStack.EMPTY);

    public ChipManufacturerBlockEntity() {
        super(IOBlockEntities.CHIP_MANUFACTURER_ENTITY_TYPE);
    }


    public DefaultedList<ItemStack> getFuel() {
        return fuel;
    }


}
