/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Prueba;

import Conexion.*;
import java.io.IOException;
import java.util.Scanner;
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
        String texto;
        Scanner input=new Scanner(System.in);
        do{
            texto=input.next();
            if(texto.equals("lista")){
                try {
                    server.enviarMensajeServer("*", "1|1*Cancion 1*Autor 1*Album 1;2*Cancion 2*Autor 2*Album 2;3*Cancion 3*Autor 3*Album 3;4*Cancion 4*Autor 4*Album 4;5*Cancion 5*Autor 5*Album 5");
                } catch (IOException ex) {
                    log("Error al enviar lista: "+ex.toString());
                }
            }
            else{
                try {
                    server.enviarMensajeServer("*", texto);
                } catch (IOException ex) {
                    log("Error al enviar lista: "+ex.toString());
                }
            }
        }while(!texto.equals("exit"));
        log("Se deja de recoger comandos...");
        try {
            server.closeSocket();
        } catch (IOException ex) {
            log("Error al cerrar el socket: "+ex.toString());
        }
    }
    private static void log(String cadena){
        System.out.println(cadena);
    }
    @Override
    public void onMessageReceived(String ip, String message) {
        log(ip+": "+message);
        try {
            server.enviarMensajeServer("*",/*"- "+ip+" : "+*/"2|"+message);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onClientConnected(String ip) {
        try {
            server.enviarMensajeServer(ip, "Bienvenido");
            log("Cliente conectado "+"\nNumero de clientes: "+server.getClientsCount());
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onClientDisconnected(String ip) {
        log("Cliente desconectado "+ip+"\nNumero de clientes: "+server.getClientsCount());
    }
    
}
