/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Managers;

import Modelos.ClienteSocialDj;
import Modelos.ServidorSocialDj;
import conexion.ServerSocketListener;
import conexion.SocketServidor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 66785270
 */
public class ServerManager implements ServerSocketListener{
    private ArrayList<ServidorSocialDj> servidores;
    private SocketServidor socket;
    public ServerManager(){
        servidores=new ArrayList();
        
    }
    
    public boolean arrancarBridge(){
        try{
            socket=new SocketServidor(2222);
            socket.addServerSocketListener(this);
            socket.startSearchClients();
            
            return socket.isBound();
        }catch(Exception ex){
            System.out.println("Error al arrancar el BridgeSocialDj: "+ex.toString());
            return false;
        }
    }
    private void procesarLogIn(String id,String tipo,String nick){
        switch(tipo){
            case "c":
                if(isServer(nick)){
                    ServidorSocialDj server=getServer(nick);
                    if(!server.isClient(id)){
                        server.clientes.add(new ClienteSocialDj(id));
                        sendClientNotification(server.id, id,"on");
                        sendLoginResponse(id,1);
                    }
                }
                else
                    sendLoginResponse(id,0);
                break;
            case "s":
                if(!isServer(nick)){
                    servidores.add(new ServidorSocialDj(nick, id));
                    sendLoginResponse(id, 1);
                }
                else{
                    sendLoginResponse(id, 0);
                }
                break;
        }
    }
    
    private void procesarMessage(String origen,String tipo,String destino,String message){
        try{
            switch(tipo){
                case "c":
                    if(isServer(origen)){
                        ServidorSocialDj server=getServer(origen);
                        if("*".equals(destino))
                            for(ClienteSocialDj cliente:server.clientes)
                                socket.enviarMensajeServer(cliente.id, message);
                        else
                            if(server.isClient(destino))
                                socket.enviarMensajeServer(destino, message);
                    }                    
                    break;
                case "s":
                    if(isServer(destino))
                        if(getServer(destino).isClient(origen))
                            socket.enviarMensajeServer(getServer(destino).id, message);
                    break;
            }
        }catch(Exception ex){
            System.out.println("Error al procesar el mensaje recivido: "+ex);
        }
    }
    
    private void procesarExit(String id){
        try {
            socket.clientes.get(id).close();
        } catch (IOException ex) {
            System.out.println("Error al desconectar al cliente "+id+" : "+ex);
        }
    }
    
    private void sendClientNotification(String id_server,String id_cliente,String estado){
        try {
            socket.enviarMensajeServer(id_server, "b:client_"+estado+"|"+id_cliente);
        } catch (IOException ex) {
            System.out.println("Error al enviar la notificacion de nuevo cliente: "+ex);
        }        
    }
    
    private void sendLoginResponse(String id, int tipo){
        try {
            socket.enviarMensajeServer(id, "b:log|"+tipo);
        } catch (IOException ex) {
            System.out.println("Error al enviar respuesta al logIn: "+ex);
        }
    }
    
    @Override
    public void onMessageReceived(String id, String message) {
        System.out.println("Mensaje recibido de "+id+" : "+message);
        String tipo=getType(message);
        String id_dest=getId(message);
        switch(id_dest){
            case "log":
                procesarLogIn(id,tipo,getMessage(message));
                break;
            case "exit":
                procesarExit(id);
                break;
            default:
                procesarMessage(id,tipo,id_dest,getMessage(message)); 
                break;
        }           
    }

    @Override
    public void onClientConnected(String id) {
        System.out.println("Conexion abierta >> "+id);
    }

    @Override
    public void onClientDisconnected(String id){
        if(isServer(id)){
            ServidorSocialDj server=getServer(id);
            try {
                int size=server.clientes.size();
                for(int cont=0;cont<size;cont++){
                    socket.clientes.get(server.clientes.get(0).id).close();
                    server.clientes.remove(server.getClient(id));
                }
            } catch (IOException ex) {
                System.out.println("Error al desconectar al cliente");
            }
            servidores.remove(server);
        }
        else{
            for(ServidorSocialDj server:servidores){
                if(server.isClient(id)){
                    server.clientes.remove(server.getClient(id));
                    sendClientNotification(server.id, id, "off");
                }
            }
        }
        System.out.println("Cliente desconectado >> "+id);
        
    }
    private boolean isServer(String id){
        for(ServidorSocialDj server:servidores)
            if(server.nombre.equals(id) || server.id.equals(id))
                return true;
        return false;
    }
    private ServidorSocialDj getServer(String id){
        for(ServidorSocialDj server:servidores)
            if(server.nombre.equals(id) || server.id.equals(id))
                return server;
        return null;
    }
    private String getMessage(String men){
        return men.substring(men.indexOf("|")+1);
    }
    private String getId(String men){
        return men.substring(men.indexOf(":")+1,men.indexOf("|"));
    }
    private String getType(String men){
        return men.substring(0,men.indexOf(":"));
    }
}
