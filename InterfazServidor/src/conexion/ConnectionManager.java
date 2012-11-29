/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import java.util.ArrayList;
import main.Main;
import modelos.Cancion;
import modelos.ListaCanciones;
import tablas.Tabla;

/**
 *
 * @author 66785270
 */
public class ConnectionManager implements ServerSocketListener{
    public static SocketServidor socket=null;
    int port; 
    private ListaCanciones lista_sonando;//Variable basura para utilizar en la demo
    private Tabla tabla_sonando;//Variable basura para utilizar en la demo
    
    public ConnectionManager(Tabla tabla,ListaCanciones lista){
        lista_sonando=lista;
        tabla_sonando=tabla;
    }
    
    public boolean createSocket(int _port) throws Exception{
        socket=new SocketServidor(_port);
        port=_port;
        socket.startSearchClients();
        socket.addServerSocketListener(this);
        return socket.isBound();
    }
    
    public void closeSocket() throws Exception{
        socket.removeServerSocketListener(this);
        socket.closeSocket();
        socket=null;
        port=0;
    }
    
    private boolean procesarVoto(int id_cancion,boolean tipo){
        ArrayList <Cancion> canciones=lista_sonando.getCanciones();
        int x=0;
        for(Cancion p: canciones){
            if(p.getId()==id_cancion){
                int votos=Integer.parseInt((String)tabla_sonando.getValueAt(x, 1));
                if(tipo)
                    votos++;
                else
                    votos--;
                tabla_sonando.setValueAt(votos, x, 1);
                return true;
            }
            x++;
        }
        return false;
    }
    
    @Override
    public void onMessageReceived(String ip, String men) {
        Main.log(ip+": "+men);
        try {
            String message=men;
            int tipo=Integer.parseInt(message.split("\\|")[0]);
            message=message.split("\\|")[1]; 
            switch(tipo){
                case 1:
                    //Procesa la cancion que quiere votar el cliente y despues manda la confirmacion
                    //que es un 0 para no o el id recivido para si.
                    if(procesarVoto(Integer.parseInt(message),true))
                        socket.enviarMensajeServer(ip,"1|"+message);
                    else
                        socket.enviarMensajeServer(ip,"1|0");   
                    break;
                case 3:
                    if(procesarVoto(Integer.parseInt(message),false))
                        socket.enviarMensajeServer(ip,"3|"+message);
                    else
                        socket.enviarMensajeServer(ip,"3|0"); 
                    break;
            }
        } catch (Exception ex) {
            Main.log("Error al procesar mensaje recivido: "+ex.toString());
        }
    }

    @Override
    public void onClientConnected(String ip) {
        try{
            Main.log("Cliente conectado: "+ip+"\nNumero de clientes: "+socket.getClientsCount());
        }catch(Exception ex){
            Main.log(ex.toString());
        }
    }

    @Override
    public void onClientDisconnected(String ip) {
        Main.log("Cliente desconectado "+"\nNumero de clientes: "+socket.getClientsCount());
    }

    /**
     * @param lista_sonando the lista_sonando to set
     */
    public void setLista(ListaCanciones lista_sonando) {
        this.lista_sonando = lista_sonando;
    }

    /**
     * @param tabla_sonando the tabla_sonando to set
     */
    public void setTabla(Tabla tabla_sonando) {
        this.tabla_sonando = tabla_sonando;
    }
    
}
