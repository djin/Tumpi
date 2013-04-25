/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Managers;

import Modelos.ServidorSocialDj;
import conexion.ServerSocketListener;
import conexion.SocketServidor;
import java.util.ArrayList;

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
    @Override
    public void onMessageReceived(String id, String message) {
        System.out.println("Mensaje recibido de "+id+" : "+message);
    }

    @Override
    public void onClientConnected(String id) {
        System.out.println("Conexion abierta de "+id);
    }

    @Override
    public void onClientDisconnected(String id){
        //if()
        
    }
    private boolean isServer(String id){
        for(ServidorSocialDj server:servidores)
            if(server.nombre.equals(id) || server.id.equals(id))
                return true;
        return false;
    }
    private String getMessage(String men){
        return men.substring(men.indexOf("|"));
    }
    private String getId(String men){
        return men.split("\\:")[1];
    }
    private String getType(String men){
        return men.split("\\:")[0];
    }
}
