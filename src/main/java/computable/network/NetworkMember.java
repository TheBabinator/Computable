package computable.network;

import net.minecraft.network.chat.Component;

import java.util.List;

public class NetworkMember {
    private Network network = new Network();

    public Network getNetwork() {
        return network;
    }

    public void inspect(List<Component> components) {
        network.inspect(components);
    }
}
