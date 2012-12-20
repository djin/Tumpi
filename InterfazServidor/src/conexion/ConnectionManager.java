/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import java.net.*;
import java.util.ArrayList;
import main.Main;
import modelos.ListasCancionesManager;

/**
 *
 * @author 66785270
 */
public class ConnectionManager implements ServerSocketListener{
    public static SocketServidor socket=null;
    private DatagramSocket dsocket;
    private Thread publicador;
    int port; 
    
    public ConnectionManager(){
        
    }
    
    public boolean createSocket(final int _port) throws Exception{
        socket=new SocketServidor(_port);
        dsocket=new DatagramSocket(8888);
        dsocket.setBroadcast(true);
        publicador=new Thread(){
            @Override
            public void run(){
                String identificacion="socialDj|"+socket.getIp()+"|"+_port+"|";
                try {
                    byte[] mensaje=identificacion.getBytes("utf-8");
                    while(publicador.equals(currentThread())){
                        dsocket.send(new DatagramPacket(mensaje,mensaje.length, new InetSocketAddress("255.255.255.255",8888)));
                        sleep(3000);
                    }
                    
                } catch (Exception ex) {
                    Main.log(ex.toString());
                }
            }
        };
        publicador.start();
        port=_port;
        socket.startSearchClients();
        socket.addServerSocketListener(this);
        return socket.isBound();
    }
    
    public void closeSocket() throws Exception{
        publicador=null;
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
            ArrayList<Integer> votos_cliente=ListasCancionesManager.votos_cliente.get(ip);
            int tipo=Integer.parseInt(message.split("\\|")[0]);
            message=message.split("\\|")[1]; 
            switch(tipo){
                case 0:
                    if(ListasCancionesManager.lista_sonando!=null)
                        socket.enviarMensajeServer(ip,"0|"+ListasCancionesManager.lista_sonando.toString());
                    if(ListasCancionesManager.cancion_sonando!=null)
                        socket.enviarMensajeServer(ip,"4|"+ListasCancionesManager.cancion_sonando.toString());
                    if(votos_cliente!=null){
                        for(int id_cancion:votos_cliente)
                            socket.enviarMensajeServer(ip,"1|"+id_cancion);
                    }
                    break;
                case 1:
                    if(ListasCancionesManager.procesarVoto(Integer.parseInt(message),true)){
                        socket.enviarMensajeServer(ip,"1|"+message);
                        if(votos_cliente!=null && !votos_cliente.contains(Integer.decode(message)))
                           votos_cliente.add(Integer.parseInt(message)); 
                    }
                    else
                        socket.enviarMensajeServer(ip,"1|0");   
                    break;
                case 3:
                    if(ListasCancionesManager.procesarVoto(Integer.parseInt(message),false)){
                        socket.enviarMensajeServer(ip,"3|"+message);
                        if(votos_cliente!=null)
                            votos_cliente.remove(Integer.decode(message));
                    }
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
            if(ListasCancionesManager.votos_cliente.get(ip)==null){
                ListasCancionesManager.votos_cliente.put(ip, new ArrayList<Integer>());
                Main.log("Hash de votos creado para el cliente");
            }
        }catch(Exception ex){
            Main.log(ex.toString());
        }
    }

    @Override
    public void onClientDisconnected(String ip) {
        Main.log("Cliente desconectado "+"\nNumero de clientes: "+socket.getClientsCount());
    }
    
}
