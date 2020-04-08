package tech.teamio.resonantio.utils;


import tech.teamio.resonantio.blocks.IOBlocks;
import tech.teamio.resonantio.blocks.blockentities.IOBlockEntities;
import tech.teamio.resonantio.items.IOItems;

public class ModBootstrap {

    // initialize everything.
    public static void bootstrap() {
        IOBlocks.init();
        IOItems.init();
        IOBlockEntities.init();
    }
}
