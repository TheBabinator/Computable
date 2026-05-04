package computable.network;

public class Network {
    private final NetworkEnergy networkEnergy = new NetworkEnergy(this);

    public NetworkEnergy getNetworkEnergy() {
        return networkEnergy;
    }
}
