package reproductor;

import uk.co.caprica.vlcj.discovery.NativeDiscovery;

public abstract class configVlcj {

    static {
        new NativeDiscovery().discover();
    }
}
