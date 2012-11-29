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
                    server.enviarMensajeServer("*", "0|1*Cancion 1*Autor 1*Album 1;2*Cancion 2*Autor 2*Album 2;3*Cancion 3*Autor 3*Album 3;4*Cancion 4*Autor 4*Album 4;5*Cancion 5*Autor 5*Album 5;6*Cancion 6*Autor 6*Album 6;7*Cancion 7*Autor 7*Album 7;8*Cancion 8*Autor 8*Album 8;9*Cancion 9*Autor 9*Album 9;10*Cancion 10*Autor 10*Album 10;11*Cancion 11*Autor 11*Album 11;12*Cancion 12*Autor 12*Album 12;13*Cancion 13*Autor 13*Album 13");
                } catch (IOException ex) {
                    log("Error al enviar lista: "+ex.toString());
                }
            }
            else if(texto.split("\\_")[0].equals("cancion")){
                try {
                    server.enviarMensajeServer("*", "2|"+texto.split("\\_")[1]);
                } catch (IOException ex) {
                    log("Error al enviar la cancion sonando: "+ex.toString());
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
    public void onMessageReceived(String ip, String men) {
        log(ip+": "+men);
        try {
            String message=men;
            int tipo=Integer.parseInt(message.split("\\|")[0]);
            message=message.split("\\|")[1]; 
            switch(tipo){
                case 1:
                    //Procesa la cancion que quiere votar el cliente y despues manda la confirmacion
                    //que es un 0 para no o el id recivido para si.
                    server.enviarMensajeServer(ip,"1|"+message);
                    break;
                case 3:
                    server.enviarMensajeServer(ip,"3|"+message);
                    break;
            }
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onClientConnected(String ip) {
        try {
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
