package tech.teamio.resonantio.api.energy;


import alexiil.mc.lib.attributes.Simulation;
import io.github.cottonmc.energy.CottonEnergy;
import io.github.cottonmc.energy.api.*;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import team.reborn.energy.EnergyTier;
import tech.teamio.resonantio.blocks.blockentities.WireBlockEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author <a href="https://github.com/StellarHorizons">StellarHorizons</a>
 */
public class WireNetwork {

    public static final ConcurrentMap<WireNetwork, BlockPos> networkMap = new ConcurrentHashMap<>();

    static final ConcurrentMap<BlockPos, WireNetwork> networkMap_TEMP = new ConcurrentHashMap<>();

    /**
     * A set containing all the wires inside of a network.
     */
    final ConcurrentSet<WireBlockEntity> wires = new ConcurrentSet<>();

    /**
     * The id of this network.
     */
    private final UUID id = UUID.randomUUID();

    /**
     * Creates a new wire network.
     *
     * @param source The (Wire)BlockEntity that created the network
     */
    public WireNetwork(WireBlockEntity source) {
        networkMap_TEMP.put(source.getPos(), this);
        wires.add(source);
    }

    /**
     * Called when a wire is placed.
     */
    public static void blockPlaced() {
        //Every wire in every network
        networkMap.forEach((key, value) -> key.wires.forEach(key::blockPlacedLogic));

        if (!networkMap_TEMP.isEmpty()) {
            networkMap_TEMP.forEach(((blockPos, network) -> networkMap.put(network, blockPos)));
        }
        networkMap_TEMP.clear();
    }

    private void blockPlacedLogic(WireBlockEntity source) {
        List<WireBlockEntity> sourceWires = new ArrayList<>();
        sourceWires.add(source);
        do {
            for (WireBlockEntity wire : WireUtils.getAdjacentWires(sourceWires.get(0).getPos(), sourceWires.get(0).getWorld())) {
                if (wire != null) {
                    if (wire.networkId != this.getId()) {
                        this.wires.add(wire);
                        try {
                            if (WireUtils.getNetworkFromId(wire.networkId) != null) {
                                WireNetwork network = WireUtils.getNetworkFromId(wire.networkId);
                                networkMap.remove(WireUtils.getNetworkFromId(wire.networkId));
                                for (WireBlockEntity blockEntity : network.wires) {
                                    blockEntity.networkId = this.getId();
                                }
                            }
                        } catch (NullPointerException ignore) {
                        }

                        if (networkMap_TEMP.get(wire.getPos()) != null) {
                            networkMap_TEMP.remove(wire.getPos());
                        }

                        wire.networkId = this.getId();
                        sourceWires.add(wire);
                    }
                }
            }
            BlockEntity e = sourceWires.get(0);
            sourceWires.remove(e);
        } while (sourceWires.size() > 0);
    }

    /**
     * Handles the energy transfer in a network.
     * Runs every tick.
     */
    public void update() {
        ConcurrentSet<BlockEntity> consumers = new ConcurrentSet<>();
        ConcurrentSet<BlockEntity> producers = new ConcurrentSet<>();
        ConcurrentSet<BlockEntity> storage = new ConcurrentSet<>();
        int energyAvailable = 0;
        int energyNeeded = 0;

        if (wires.isEmpty()) {
            networkMap.remove(this);
            return;
        }

        for (WireBlockEntity wire : wires) {
            if (!(wire.getWorld().getBlockEntity(wire.getPos()) instanceof WireBlockEntity)) {
                wires.remove(wire);
                for (WireBlockEntity blockEntity1 : wires) {
                    blockEntity1.onPlaced();
                }
                wires.clear();
                return;
            } else {
                wire.networkId = getId();
            }
            for (BlockEntity consumer : WireUtils.getAdjacentConsumers(wire.getPos(), wire.getWorld())) {
                if (consumer != null) {
                    EnergyAttribute consumerEnergy = ((EnergyAttributeProvider) consumer).getEnergyAttribute();
                    if (consumerEnergy.getCurrentEnergy() < consumerEnergy.getMaxEnergy()) {
                        consumers.add(consumer); //Amount the machine needs
                        energyNeeded += (consumerEnergy.getMaxEnergy() - consumerEnergy.getCurrentEnergy());
                    }
                }
            }

            for (BlockEntity producer : WireUtils.getAdjacentProducers(wire.getPos(), wire.getWorld())) {
                if (producer != null) {
                    if (consumers.contains(producer)) {
                        consumers.remove(producer);
                        storage.add(producer);
                    } else {
                        producers.add(producer);
                        energyAvailable += ((EnergyAttributeProvider) producer).getEnergyAttribute().getCurrentEnergy();
                    }
                }
            }
        }

        if (energyNeeded > 0) {
            int amountPerConsumer = (consumers.size() > 0 && energyAvailable > 0) ? energyAvailable / consumers.size() : 0;
            for (BlockEntity consumer : consumers) {
                energyAvailable -= amountPerConsumer;
                int amountExtracted = 0;
                for (BlockEntity producer : producers) {
                    amountExtracted += ((EnergyAttributeProvider) producer).getEnergyAttribute().extractEnergy(DefaultEnergyTypes.ULTRA_HIGH_VOLTAGE, amountPerConsumer - amountExtracted, Simulation.ACTION);

                    if (amountExtracted <= amountPerConsumer) {
                        if (((EnergyAttributeProvider) producer).getEnergyAttribute().getCurrentEnergy() <= 0) {
                            producers.remove(producer);
                        }
                    }

                    if (amountExtracted == amountPerConsumer) {
                        break;
                    }
                }
                energyAvailable += ((EnergyAttributeProvider) consumer).getEnergyAttribute().insertEnergy(DefaultEnergyTypes.ULTRA_HIGH_VOLTAGE, amountPerConsumer, Simulation.ACTION);
                if (((EnergyAttributeProvider) consumer).getEnergyAttribute().getCurrentEnergy() >= ((EnergyAttributeProvider) consumer).getEnergyAttribute().getMaxEnergy()) {
                    consumers.remove(consumer);
                }
            }

            if (!consumers.isEmpty() && producers.isEmpty()) {
                energyAvailable = 0;
                for (BlockEntity battery : storage) {
                    if (battery != null) {
                        energyAvailable += ((EnergyAttributeProvider) battery).getEnergyAttribute().getCurrentEnergy();
                    }
                }

                amountPerConsumer = (consumers.size() > 0 && energyAvailable > 0) ? energyAvailable / consumers.size() : 0;
                for (BlockEntity consumer : consumers) {
                    energyAvailable -= amountPerConsumer;
                    int amountExtracted = 0;
                    for (BlockEntity battery : storage) {
                        amountExtracted += ((EnergyAttributeProvider) battery).getEnergyAttribute().extractEnergy(DefaultEnergyTypes.ULTRA_HIGH_VOLTAGE, amountPerConsumer - amountExtracted, Simulation.ACTION);

                        if (amountExtracted <= amountPerConsumer) {
                            if (((EnergyAttributeProvider) battery).getEnergyAttribute().getCurrentEnergy() <= 0) {
                                storage.remove(battery);
                            }
                        }

                        if (amountExtracted == amountPerConsumer) {
                            break;
                        }
                    }
                    energyAvailable += ((EnergyAttributeProvider) consumer).getEnergyAttribute().insertEnergy(DefaultEnergyTypes.ULTRA_HIGH_VOLTAGE, amountPerConsumer, Simulation.ACTION);
                    if (((EnergyAttributeProvider) consumer).getEnergyAttribute().getCurrentEnergy() >= ((EnergyAttributeProvider) consumer).getEnergyAttribute().getMaxEnergy()) {
                        consumers.remove(consumer);
                    }
                }
            } else if (!producers.isEmpty() && consumers.isEmpty()) {
                energyAvailable = 0;
                for (BlockEntity producer : producers) {
                    if (producer != null) {
                        energyAvailable += ((EnergyAttributeProvider) producer).getEnergyAttribute().getCurrentEnergy();
                    }
                }

                amountPerConsumer = (storage.size() > 0 && energyAvailable > 0) ? energyAvailable / storage.size() : 0;
                for (BlockEntity battery : storage) {
                    energyAvailable -= amountPerConsumer;
                    int amountExtracted = 0;
                    for (BlockEntity producer : producers) {
                        amountExtracted += ((EnergyAttributeProvider) producer).getEnergyAttribute().extractEnergy(DefaultEnergyTypes.ULTRA_HIGH_VOLTAGE, amountPerConsumer - amountExtracted, Simulation.ACTION);

                        if (amountExtracted <= amountPerConsumer) {
                            if (((EnergyAttributeProvider) producer).getEnergyAttribute().getCurrentEnergy() <= 0) {
                                producers.remove(producer);
                            }
                        }

                        if (amountExtracted == amountPerConsumer) {
                            break;
                        }
                    }
                    energyAvailable += ((EnergyAttributeProvider) battery).getEnergyAttribute().insertEnergy(DefaultEnergyTypes.ULTRA_HIGH_VOLTAGE, amountPerConsumer, Simulation.ACTION);
                    if (((EnergyAttributeProvider) battery).getEnergyAttribute().getCurrentEnergy() >= ((EnergyAttributeProvider) battery).getEnergyAttribute().getMaxEnergy()) {
                        storage.remove(battery);
                    }
                }
            }
        }
    }

    /**
     * @return The ID of the network
     */
    public UUID getId() {
        return id;
    }


    public enum WireConnectionType {

        /**
         * The wire is not connected to anything.
         */
        NONE,

        /**
         * The wire is connected to another wire.
         */
        WIRE,

        /**
         * The wire is connected to some sort of energy consuming block.
         */
        ENERGY_INPUT,

        /**
         * The wire is connected to some sort of energy generating block.
         */
        ENERGY_OUTPUT

    }
}
