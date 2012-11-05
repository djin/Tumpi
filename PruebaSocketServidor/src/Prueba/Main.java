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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        ServerSocket server=new ServerSocket(22222);
       /* new Runnable() {
            @Override
            public void run() {
                try {
                    SocketAddress dir_server=new InetSocketAddress("192.168.173.1",22222);
                    Socket cliente=new Socket();
                    do{
                        cliente.connect(dir_server);
                    }while(!cliente.isConnected());
                    InputStream input = cliente.getInputStream();
                    OutputStream output = cliente.getOutputStream();
                    DataInputStream inputdata = new DataInputStream (input);
                    DataOutputStream outputdata = new DataOutputStream (output);
                    outputdata.writeUTF("HOLA MUNDO");
                    
                } catch (Exception ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }                
            }
        }.run();*/
        System.out.println(server.getInetAddress());
        Socket socket_cliente=server.accept();
        InputStream input = socket_cliente.getInputStream();
        OutputStream output = socket_cliente.getOutputStream();
        DataInputStream inputdata = new DataInputStream (input);
        DataOutputStream outputdata = new DataOutputStream (output);
        System.out.println(inputdata.readUTF());
        server.close();
        //System.out.println(socket_cliente.getChannel());
    }
}
