/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.datos;

import android.content.Context;
import android.util.Log;
import conexion.ConnectionManager;
import conexion.ServerSocketListener;
import interfaces.CambiarListaListener;
import interfaces.ListenerListaCambio;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import multimedia.Player;

/**
 *
 * @author zellyalgo
 */
public class ListasManager implements ServerSocketListener {

    private static ListasManager INSTANCE = new ListasManager();
    public ArrayList<String> nombreLista;
    public ArrayList<ListaCanciones> listasCanciones;
    public ListaPromocionada lista_promocionada;
    private CancionPromocionada cancionReproduciendo;
    List<CambiarListaListener> listeners = new LinkedList();
    List<ListenerListaCambio> listenersLista = new LinkedList();
    private ConnectionManager conex;
    private HashMap<String, ArrayList> votos_cliente;
    public Player player;

    private ListasManager() {
        nombreLista = new ArrayList<String>();
        listasCanciones = new ArrayList<ListaCanciones>();
        lista_promocionada = new ListaPromocionada(new ListaCanciones());
        cancionReproduciendo = new CancionPromocionada(0, "Los Redondeles", "Siempre Fuertes", 1, "HUAE", 1234, "");
        votos_cliente = new HashMap();
        player = Player.getInstance();
//        GuardarListas guardar = new GuardarListas ();

    }

    public void limpiarDatos(ArrayList<Cancion> datos) {
        int n = datos.size();
        for (int i = 0; i < n; i++) {
            datos.remove(0);
        }
    }

    public boolean abrirConexion(Context c) {
        if (conex == null) {
            conex = new ConnectionManager(c);
            try {
                if (conex.createSocket(2222)) {
                    conex.socket.addServerSocketListener(this);
                    Log.i("Conexion", "Socket creado con éxito");
                }
            } catch (Exception ex) {
                Log.e("Conexion", "Error al crear el socket: " + ex.toString());
                conex = null;
                return false;
            }
        }
        return true;
    }

    public void procesarVotos() {
        CancionPromocionada cancionASonar = lista_promocionada.getMaxVoto();
        cancionReproduciendo = cancionASonar;
        if(cancionReproduciendo!=null){
            try {
                player.playSong(cancionReproduciendo.path);
                conex.socket.enviarMensajeServer("*", "2|" + cancionReproduciendo.id);
            } catch (Exception ex) {
                Log.e("Multimedia", "Error al reproducir cancion " + cancionReproduciendo.nombreCancion);
            }
            fireModeloChanged();
        }
        else{
            if(lista_promocionada.getCanciones().size()>0){
                try {
                    Collection<ArrayList> clientes=votos_cliente.values();
                    for(ArrayList<Integer> votos: clientes){
                        int n = votos.size();
                        for (int i = 0; i < n; i++) {
                            votos.remove(0);
                        }
                    }
                    conex.socket.enviarMensajeServer("*", "0|" + lista_promocionada.toString());
                    procesarVotos();
                } catch (IOException ex) {
                    Log.e("Conexion", "Error al actualizar lista: "+ex.toString());
                }
            }
        }    
    }

    public void anadirCanciones(int posicion, ArrayList<Cancion> canciones_anadir) {
        listasCanciones.get(posicion).addCanciones(canciones_anadir);
        fireListasChanged();
    }

    public Cancion getCancionReproduciendo() {
        return cancionReproduciendo;
    }

    public void setCancionReproduciendo(CancionPromocionada c) {
        cancionReproduciendo = c;
    }

    public static ListasManager getInstance() {
        return INSTANCE;
    }

    public ListaCanciones getLista(int posicion) {
        ListaCanciones dar = listasCanciones.get(posicion);
        return dar;
    }

    public void borrarCanciones(int posicion, ArrayList<Cancion> listaDeBorradas) {
        listasCanciones.get(posicion).removeCanciones(listaDeBorradas);
        fireListasChanged();
    }

    public void promocionar(int posicion) {
        lista_promocionada = new ListaPromocionada(listasCanciones.get(posicion));
        try {
            conex.socket.enviarMensajeServer("*", "0|" + lista_promocionada.toString());
        } catch (IOException ex) {
            Log.e("Conexion", "Error al enviar la lista promocionada a los clientes: " + ex.toString());
        }
    }

    private boolean votoCliente(int id_cancion, boolean tipo) {
        CancionPromocionada cancion = lista_promocionada.getCancionById(id_cancion);
        if (cancion != null) {
            if (tipo) {
                cancion.setVotos(cancion.getVotos() + 1);
            } else {
                cancion.setVotos(cancion.getVotos() - 1);
            }
            fireModeloChanged();
            return true;
        }
        return false;
    }

    public void addModeloChangedListener(CambiarListaListener l) {
        listeners.add(l);
    }

    public void removeModeloChangedListener(CambiarListaListener l) {
        listeners.remove(l);
    }

    protected void fireModeloChanged() {
        for (CambiarListaListener l : listeners) {
            l.modeloCambio();
        }
    }
    
    public void addListasChangedListener(ListenerListaCambio l) {
        listenersLista.add(l);
    }

    public void removeListasChangedListener(ListenerListaCambio l) {
        listenersLista.remove(l);
    }

    protected void fireListasChanged() {
        for (ListenerListaCambio l : listenersLista) {
            l.ListasChanged(listasCanciones, nombreLista);
        }
    }

    public void onMessageReceived(String ip, String message) {
        try {
            String men = message;
            int id_cancion;
            Log.i("Conexion", "Mensaje de " + ip + " : " + men);
            ArrayList<Integer> _votos_cliente = votos_cliente.get(ip);
            int tipo = Integer.parseInt(message.split("\\|")[0]);
            message = message.split("\\|")[1];
            switch (tipo) {
                case 0:
                    //conex.socket.enviarMensajeServer(ip,"0|empty");
                    if (lista_promocionada != null) {
                        Log.i("Conexion", "0|" + lista_promocionada.toString());
                        conex.socket.enviarMensajeServer(ip, "0|" + lista_promocionada.toString());
                    }
                    if (cancionReproduciendo != null) {
                        conex.socket.enviarMensajeServer(ip, "4|" + cancionReproduciendo.toString());
                    }
                    if (_votos_cliente != null) {
                        for (int id : _votos_cliente) {
                            conex.socket.enviarMensajeServer(ip, "1|" + id);
                        }
                    }
                    break;
                case 1:
                    id_cancion = Integer.parseInt(message);
                    if (votoCliente(id_cancion, true)) {
                        conex.socket.enviarMensajeServer(ip, "1|" + message);
                        if (_votos_cliente != null && !_votos_cliente.contains(id_cancion)) {
                            _votos_cliente.add(id_cancion);
                        }
                    } else {
                        conex.socket.enviarMensajeServer(ip, "1|0");
                    }
                    break;
                case 3:
                    id_cancion = Integer.parseInt(message);
                    if (votoCliente(id_cancion, false)) {
                        conex.socket.enviarMensajeServer(ip, "3|" + message);
                        if (_votos_cliente != null) {
                            _votos_cliente.remove((Integer) id_cancion);
                        }
                    } else {
                        conex.socket.enviarMensajeServer(ip, "3|0");
                    }
                    break;
            }
        } catch (Exception ex) {
            Log.e("Conexion", "Error al procesar mensaje recivido: " + ex.toString());
        }
    }

    public void onClientConnected(String ip) {
        if (votos_cliente.get(ip) == null) {
            votos_cliente.put(ip, new ArrayList<Integer>());
            Log.i("Modelo", "Hash de votos creado para el cliente " + ip);
        }
    }

    public void onClientDisconnected(String ip) {
    }
}