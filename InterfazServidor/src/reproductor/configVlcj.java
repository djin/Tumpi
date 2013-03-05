package reproductor;

<<<<<<< HEAD
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
=======
import com.sun.jna.Native;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
>>>>>>> e87b8886bd78854bf46e7f70586bd9ca6aee56ea

public abstract class configVlcj {

    static {
        new NativeDiscovery().discover();
    }
}
