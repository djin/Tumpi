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
    static ServerSocket server=null;
    static Socket socket_cliente=null;
    static Runnable escucharClientes=new Runnable() {
        @Override
        public void run() {
            try {
                socket_cliente=server.accept();
                System.out.println("Cliente conectado: "+socket_cliente.getInetAddress());
                atenderCliente(socket_cliente);
                escucharClientes.run();
            } catch (IOException ex) {
                System.out.println("Error al escuchar clientes: "+ex.toString());
            }
        }
    };
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        // TODO code application logic here
        try {
            server = new ServerSocket(22222);
            if(server.isBound()){
                System.out.println("Socket creado con éxito");
                escucharClientes.run();
            }
        } catch (IOException ex) {
            System.out.println("Error al crear el socket: "+ex.toString());
        }
        
    }
    static void atenderCliente(Socket socket_cliente){
        InputStream input = null;
        String texto_recivido="";
        try {
            input = socket_cliente.getInputStream();
            OutputStream output = socket_cliente.getOutputStream();
            DataInputStream inputdata = new DataInputStream (input);
            DataOutputStream outputdata = new DataOutputStream (output);            
            do{
                    texto_recivido=inputdata.readUTF();
                    System.out.println(texto_recivido);
            }while(!texto_recivido.equals("exit"));
        } catch (IOException ex) {
            System.out.println("Cliente desconectado: "+socket_cliente.getInetAddress());
        } finally {
            try {
                input.close();
                socket_cliente.close();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
