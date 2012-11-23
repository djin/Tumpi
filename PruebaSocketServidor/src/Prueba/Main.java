/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Prueba;
import Conexion.ServerSocketListener;
import Conexion.SocketServidor;
import java.io.*;
import java.net.*;
import java.net.Socket.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author 66785270
 */
public class Main implements ServerSocketListener{
    static SocketServidor server=null;
    static Socket socket_cliente=null;
    public static void main(String[] args){
        try {
            server=new SocketServidor(2222);
            if(server.isBound())
                log("Socket creado con exito!!!");
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            log("Cliente conectado "+ip);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onClientDisconnected(String ip) {
        log("Cliente desconectado "+ip);
    }
}
