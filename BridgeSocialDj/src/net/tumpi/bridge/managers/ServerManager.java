package net.tumpi.bridge.managers;

import net.tumpi.bridge.modelos.TumpiClient;
import net.tumpi.bridge.modelos.TumpiServer;
import net.tumpi.bridge.conexion.Cliente;
import net.tumpi.bridge.conexion.ServerSocketListener;
import net.tumpi.bridge.conexion.SocketServidor;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.tumpi.bridge.config.Config;

/**
 *
 * @author 66785270
 */
public class ServerManager implements ServerSocketListener {

    private ConcurrentHashMap<String, TumpiServer> servidores;
    private SocketServidor socket;
    private Config config;

    public ServerManager() {
        servidores = new ConcurrentHashMap<>();
        config = Config.instance();
    }

    public boolean arrancarBridge() {
        try {
            socket = new SocketServidor(config.getPuerto());
            socket.addServerSocketListener(this);
            socket.startSearchClients();

            return socket.isBound();
        } catch (Exception ex) {
            System.out.println("Error al arrancar el BridgeSocialDj: " + ex.toString());
            return false;
        }
    }

    private void procesarLogIn(String id, String tipo, String nick) {
        switch (tipo) {
            case "c":
                String id_server=getServerIdByNick(nick);
                if (id_server!=null) {
                    TumpiServer server = getServer(id_server);
                    if (!server.isClient(id)) {
                        server.putClient(new TumpiClient(id));
                        sendClientNotification(server.id, id, "on");
                        sendLoginResponse(id, 1);
                    }
                } else {
                    sendLoginResponse(id, 0);
                }
                break;
            case "s":
                if (!isServer(nick)) {
                    servidores.put(id, new TumpiServer(nick, id));
                    sendLoginResponse(id, 1);
                } else {
                    sendLoginResponse(id, 0);
                }
                break;
        }
    }

    private void procesarMessage(String origen, String tipo, String destino, String message) {
        try {
            switch (tipo) {
                case "c":
                    if (isServer(origen)) {
                        TumpiServer server = getServer(origen);
                        if ("*".equals(destino)) {
                            for (TumpiClient cliente : server) {
                                socket.enviarMensajeServer(cliente.id, message);
                            }
                        } else if (server.isClient(destino)) {
                            socket.enviarMensajeServer(destino, message);
                        }
                    }
                    break;
                case "s":
                    String id_server=getServerIdByNick(destino);
                    if (id_server!=null) {
                        if (getServer(id_server).isClient(origen)) {
                            socket.enviarMensajeServer(getServer(id_server).id, origen + "|" + message);
                        }
                    }
                    break;
            }
        } catch (Exception ex) {
            System.out.println("Error al procesar el mensaje recivido: " + ex);
        }
    }

    private void procesarExit(String id) {
        try {
            socket.clientes.get(id).close();
        } catch (IOException ex) {
            System.out.println("Error al desconectar al cliente " + id + " : " + ex);
        }
    }

    private void sendClientNotification(String idServer, String idCliente, String estado) {
        try {
            socket.enviarMensajeServer(idServer, "b:client_" + estado + "|" + idCliente);
        } catch (IOException ex) {
            System.out.println("Error al enviar la notificacion de nuevo cliente: " + ex);
        }
    }

    private void sendLoginResponse(String id, int tipo) {
        try {
            socket.enviarMensajeServer(id, "b:log|" + tipo);
        } catch (IOException ex) {
            System.out.println("Error al enviar respuesta al logIn: " + ex);
        }
    }

    @Override
    public void onMessageReceived(String id, String message) {
        System.out.println("Mensaje recibido de " + id + " : " + message);
        String tipo = getType(message);
        String idDest = getId(message);
        switch (idDest) {
            case "log":
                procesarLogIn(id, tipo, getMessage(message));
                break;
            case "exit":
                procesarExit(id);
                break;
            default:
                procesarMessage(id, tipo, idDest, getMessage(message));
                break;
        }
    }

    @Override
    public void onClientConnected(String id) {
        System.out.println("Conexion abierta >> " + id);
    }

    @Override
    public void onClientDisconnected(String id) {
        if (isServer(id)) {
            TumpiServer server = getServer(id);
            try {
                Map<String, Cliente> clientes = socket.clientes;
                Set<String> clavesClientes = clientes.keySet();
                for (String clientId : clavesClientes) {
                    Cliente cliente = clientes.get(clientId);
                    cliente.close();
                }
                server.removeAllClients();

            } catch (IOException ex) {
                System.out.println("Error al desconectar al cliente");
            }
            servidores.remove(id);
        } else {
            Collection<TumpiServer> values = servidores.values();
            for (TumpiServer server : values) {
                if (server.isClient(id)) {
                    server.removeCliente(id);
                    sendClientNotification(server.id, id, "off");
                }
            }
        }
        System.out.println("Cliente desconectado >> " + id);

    }

    private String getServerIdByNick(String nick){
        Collection<TumpiServer> values = servidores.values();
        for (TumpiServer server : values) {
            if (server.nombre.equals(nick))
                return server.id;
        }
        return null;
    }    
    
    private boolean isServer(String id) {
        return servidores.containsKey(id);
    }

    private TumpiServer getServer(String id) {
        return servidores.get(id);
    }

    private String getMessage(String men) {
        return men.substring(men.indexOf("|") + 1);
    }

    private String getId(String men) {
        return men.substring(men.indexOf(":") + 1, men.indexOf("|"));
    }

    private String getType(String men) {
        return men.substring(0, men.indexOf(":"));
    }
}
