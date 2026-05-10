package computable.grid;

import computable.api.Inspectable;
import net.minecraft.network.chat.Component;

import java.util.List;

public class Network implements Inspectable {
    private final NetworkEnergy networkEnergy = new NetworkEnergy(this);

    public NetworkEnergy getNetworkEnergy() {
        return networkEnergy;
    }

    @Override
    public void inspect(List<Component> components) {
        components.add(Component.translatable("computable.analyzer.network_energy",
                networkEnergy.getEnergyStored(),
                networkEnergy.getMaxEnergyStored()));
    }
}
