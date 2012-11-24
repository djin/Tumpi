/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Prueba;

import Conexion.*;
import java.io.IOException;
import java.util.logging.*;

/**
 *
 * @author 66785270
 */
public class ChatServer implements ServerSocketListener{
    
    SocketServidor server=null;
    
    public ChatServer(){
        try {
            server=new SocketServidor(2222);
            if(server.isBound())
                log("Socket creado con exito!!!");
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        server.addServerSocketListener(this);
        server.startSearchClients();
    }
    private static void log(String cadena){
        System.out.println(cadena);
    }
    @Override
    public void onMessageReceived(String ip, String message) {
        log(ip+": "+message);
        try {
            server.enviarMensajeServer("*","- "+ip+" : "+message);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onClientConnected(String ip) {
        try {
            server.enviarMensajeServer(ip, "Bienvenido");
            log("Cliente conectado "+"\nNumero de clientes: "+server.getClientsCount());
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onClientDisconnected(String ip) {
        log("Cliente desconectado "+ip+"\nNumero de clientes: "+server.getClientsCount());
    }
    
}
