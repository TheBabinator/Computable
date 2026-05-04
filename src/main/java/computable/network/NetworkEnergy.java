package computable.network;

import net.neoforged.neoforge.energy.IEnergyStorage;

public class NetworkEnergy implements IEnergyStorage {
    private final Network network;
    private double energy = 0;
    private double capacity = 1000;

    public NetworkEnergy(Network network) {
        this.network = network;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public double add(double added, boolean simulate) {
        double result = Math.min(energy + added, capacity);
        double transferred = result - energy;
        if (!simulate) {
            energy = result;
        }
        return transferred;
    }

    public double use(double used, boolean simulate) {
        double result = Math.max(energy - used, 0);
        double transferred = energy - result;
        if (!simulate) {
            energy = result;
        }
        return transferred;
    }

    @Override
    public int receiveEnergy(int toReceive, boolean simulate) {
        int max = (int) add(toReceive, true);
        if (simulate) {
            return max;
        }
        return (int) add(max, false);
    }

    @Override
    public int extractEnergy(int toExtract, boolean simulate) {
        int max = (int) use(toExtract, true);
        if (simulate) {
            return max;
        }
        return (int) use(max, false);
    }

    @Override
    public int getEnergyStored() {
        return (int) energy;
    }

    @Override
    public int getMaxEnergyStored() {
        return (int) capacity;
    }

    @Override
    public boolean canExtract() {
        return false;
    }

    @Override
    public boolean canReceive() {
        return true;
    }
}
