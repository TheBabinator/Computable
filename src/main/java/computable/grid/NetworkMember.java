package computable.grid;

import computable.api.Inspectable;
import net.minecraft.network.chat.Component;

import java.util.List;

public class NetworkMember implements Inspectable {
    private Network network = new Network();

    public Network getNetwork() {
        return network;
    }

    @Override
    public void inspect(List<Component> components) {
        network.inspect(components);
    }
}
