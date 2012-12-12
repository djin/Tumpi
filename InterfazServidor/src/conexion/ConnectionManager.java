/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import java.util.ArrayList;
import main.Main;
import modelos.Cancion;
import modelos.ListasCancionesManager;

/**
 *
 * @author 66785270
 */
public class ConnectionManager implements ServerSocketListener{
    public static SocketServidor socket=null;
    int port; 
    
    public ConnectionManager(){
        
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
    
    @Override
    public void onMessageReceived(String ip, String men) {
        Main.log(ip+": "+men);
        try {
            String message=men;
            int tipo=Integer.parseInt(message.split("\\|")[0]);
            message=message.split("\\|")[1]; 
            switch(tipo){
                case 0:
                    socket.enviarMensajeServer(ip,"0|"+ListasCancionesManager.lista_sonando.toString());
                    break;
                case 1:
                    if(ListasCancionesManager.procesarVoto(Integer.parseInt(message),true))
                        socket.enviarMensajeServer(ip,"1|"+message);
                    else
                        socket.enviarMensajeServer(ip,"1|0");   
                    break;
                case 3:
                    if(ListasCancionesManager.procesarVoto(Integer.parseInt(message),false))
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
    
}
