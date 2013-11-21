/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.tumpi.servidor.modelo.datos;

import android.os.Environment;
import android.util.Log;
import app.tumpi.model.TumpiClient;
import app.tumpi.servidor.conexion.ConnectionManager;
import app.tumpi.servidor.conexion.ServerSocketListener;
import app.tumpi.servidor.interfaces.CambiarListaListener;
import app.tumpi.servidor.interfaces.ListenerListaCambio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import app.tumpi.servidor.multimedia.Player;

/**
 *
 * @author zellyalgo
 */
public class ListasManager implements ServerSocketListener {

    private static ListasManager INSTANCE = new ListasManager();
    public ArrayList<String> nombreLista;
    public ArrayList<ListaCanciones> listasCanciones;
    public ListaPromocionada promotedList;
    private CancionPromocionada cancionReproduciendo;
    List<CambiarListaListener> listeners = new LinkedList();
    List<ListenerListaCambio> listenersLista = new LinkedList();
    private ConnectionManager connectionManager;
    private HashMap<String, TumpiClient> clientMap;
    public Player player;
    public boolean conectado=false;
    public String nick="";

    private ListasManager() {
        nombreLista = new ArrayList<String>();
        listasCanciones = new ArrayList<ListaCanciones>();
        promotedList = new ListaPromocionada(new ListaCanciones());
        cancionReproduciendo = new CancionPromocionada(0, "", "", 1, "", 1234, "");
        clientMap = new HashMap<String, TumpiClient>();
        player = Player.getInstance();
        GuardarListas guardar = new GuardarListas(this);
        cargarDatos();
    }

    public void limpiarDatos(ArrayList<Cancion> datos) {
        int n = datos.size();
        for (int i = 0; i < n; i++) {
            datos.remove(0);
        }
    }

    public boolean abrirConexion() {
        if (connectionManager == null) {
        	conectado = true;
            connectionManager = new ConnectionManager();
            try {
                if (connectionManager.createSocket()) {
                    connectionManager.socket.addServerSocketListener(this);
                    Log.i("Conexion", "Socket creado con éxito");
                    return true;
                }
            } catch (Exception ex) {
                Log.e("Conexion", "Error al crear el socket: " + ex.toString());
                connectionManager = null;
                return false;
            }
            return false;
        }
        return true;
    }
    public boolean cerrarConexion(){
        try{
            if(connectionManager!=null){
                connectionManager.socket.removeServerSocketListener(this);
                connectionManager.closeSocket();
                connectionManager = null;
                conectado = false;
                return true;
            }
            throw new Exception("No esta abierta ninguna conexion");
        }catch(Exception ex){
            Log.e("Conexion","Error al cerrar la conexion: "+ex);
            return false;
        }
    }
    public boolean logInBridge(String nick, String uuid){
        if(abrirConexion()){
            try {
                if(connectionManager.socket.logIn(nick, uuid)){
                    connectionManager.socket.startListenBridge();
                    conectado = true;
                    this.nick=nick;
                    return true;
                }
            } catch (Exception ex) {
                Log.e("Conexion","Error al logarse en el bridge: "+ex);
                return false;
            }
            return false;
        }
        return false;
    }
    public void procesarVotos() {
        CancionPromocionada cancionASonar = promotedList.getMaxVoto();
        cancionReproduciendo = cancionASonar;
        if (cancionReproduciendo != null) {
            try {
                player.playSong(cancionReproduciendo.path);
                if(connectionManager!=null)
                    connectionManager.socket.enviarMensajeServer("*", "2|" + cancionReproduciendo.id);
            } catch (Exception ex) {
                Log.e("Multimedia", "Error al reproducir cancion " + cancionReproduciendo.nombreCancion);
            }
            fireModeloChanged();
        } else {
            if (promotedList.getCanciones().size() > 0) {
                try {
                    clientMap.clear();
                    if(connectionManager!=null)
                        connectionManager.socket.enviarMensajeServer("*", "0|" + promotedList.toString());
                    procesarVotos();
                } catch (IOException ex) {
                    Log.e("Conexion", "Error al actualizar lista: " + ex.toString());
                }
            }
        }
    }
    
    public void guardar (){
        fireListasChanged();
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
        promotedList = new ListaPromocionada(listasCanciones.get(posicion));
        try {
            if(connectionManager!=null)
                connectionManager.socket.enviarMensajeServer("*", "0|" + promotedList.toString());
        } catch (IOException ex) {
            Log.e("Conexion", "Error al enviar la lista promocionada a los clientes: " + ex.toString());
        }
    }

    private boolean votoCliente(int id_cancion, boolean tipo) {
        CancionPromocionada cancion = promotedList.getCancionById(id_cancion);
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
    
    public boolean noListsAvailable(){
    	if(listasCanciones.isEmpty() || (listasCanciones.size()<=1 && listasCanciones.get(0).getCanciones().size()<=1)){
    		return true;
    	} else {
    		return false;
    	}
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
            int songId;
            Log.i("Conexion", "Mensaje de " + ip + " : " + men);
            if(!"exit".equals(men)){
                int tipo = Integer.parseInt(message.split("\\|")[0]);
                message = message.split("\\|")[1];
            	final int delimiterIndex = message.indexOf("&");
                final String uuid = message.substring(0, delimiterIndex);
                message = message.substring(delimiterIndex + 1);
            	TumpiClient tumpiClient = clientMap.get(uuid);
                switch (tipo) {
                    case 0:
                    	sendPromotedList(uuid, ip);
                        if (cancionReproduciendo != null) {
                            if(connectionManager!=null)
                                connectionManager.socket.enviarMensajeServer(ip, "4|" + cancionReproduciendo.toString());
                        }
                        
                        break;
                    case 1:
                        songId = Integer.parseInt(message);
                        if (votoCliente(songId, true)) {
                            if(connectionManager!=null)
                                connectionManager.socket.enviarMensajeServer(ip, "1|" + message);
                            if (tumpiClient != null && !tumpiClient.contains(songId)) {
                                tumpiClient.add(songId);
                            }
                        } else {
                            if(connectionManager!=null)
                                connectionManager.socket.enviarMensajeServer(ip, "1|0");
                        }
                        break;
                    case 3:
                        songId = Integer.parseInt(message);
                        if (votoCliente(songId, false)) {
                            if(connectionManager!=null)
                                connectionManager.socket.enviarMensajeServer(ip, "3|" + message);
                            if (tumpiClient != null) {
                                tumpiClient.remove((Integer) songId);
                            }
                        } else {
                            if(connectionManager!=null)
                                connectionManager.socket.enviarMensajeServer(ip, "3|0");
                        }
                        break;
                }
            }
            else{
                conectado=false;
                cerrarConexion();
            }
        } catch (Exception ex) {
            Log.e("Conexion", "Error al procesar mensaje recivido: " + ex.toString());
        }
    }
    
    private void sendPromotedList(String uuid, String ip) throws IOException{
        if (promotedList != null) {
            if(connectionManager!=null){
            	TumpiClient tumpiClient = clientMap.get(uuid);
            	
            	final String votedSongsString = tumpiClient.votedSongsAsString();
                Log.i("Conexion", "0|" + votedSongsString + 
                		"&" + promotedList.toString());
                connectionManager.socket.enviarMensajeServer(ip, "0|" + votedSongsString + 
                		"&" + promotedList.toString());
            }
        }
    }

    @Override
    public void onClientConnected(String uuid) {
        if (clientMap.get(uuid) == null) {
        	clientMap.put(uuid, new TumpiClient(uuid));
        }
    }

    @Override
    public void onClientDisconnected(String ip) {
    }

    private void cargarDatos() {
        try {
            File ruta_sd = Environment.getExternalStorageDirectory();
            File carpeta = new File(ruta_sd.getAbsolutePath(), "Tumpi");
            File f = new File(carpeta.getAbsolutePath(), "ListasGuardadas.txt");
            
            if (f.exists()) {
                ObjectInputStream fin = new ObjectInputStream(new FileInputStream(f));
                nombreLista = (ArrayList<String>) fin.readObject();
                listasCanciones = (ArrayList<ListaCanciones>) fin.readObject();
                fin.close();
            }
        } catch (IOException ex) {
            Log.e("Cargar", "Error entrada salida");
        } catch (ClassNotFoundException ex) {
            Log.e("Cargar", "No se encontro la clase");
        }

    }
}