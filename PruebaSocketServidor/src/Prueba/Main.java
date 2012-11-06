/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Prueba;
import java.io.*;
import java.net.*;
import java.net.Socket.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author 66785270
 */
public class Main {
    static SocketServidor server=null;
    static Socket socket_cliente=null;
    public static void main(String[] args){
        // TODO code application logic here
        server=new SocketServidor(2222);
        server.startSearchClients();
        
    }
}
