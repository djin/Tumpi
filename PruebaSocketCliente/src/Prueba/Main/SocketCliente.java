/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Prueba.Main;
import android.widget.TextView;
import java.io.*;
import java.net.*;
import java.net.Socket.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author 66785270
 */
public class SocketCliente {    
    public Socket socket_cliente=null;
    String ip_servidor=null;
    int puerto_servidor=0;
    Thread thread_escuchar_server=null;
    InputStream input = null;
    OutputStream output = null;
    DataInputStream inputdata = null;
    DataOutputStream outputdata = null;
    public SocketCliente(String ip,int port){
        ip_servidor=ip;
        puerto_servidor=port;
    }
    public void conectar() throws UnknownHostException, IOException {
        socket_cliente = new Socket(ip_servidor,puerto_servidor);
        input = socket_cliente.getInputStream();
        output = socket_cliente.getOutputStream();
        inputdata = new DataInputStream (input);
        outputdata = new DataOutputStream (output);  
    }
    public void enviarMensaje(String mensaje) throws Exception {
        if(socket_cliente.isConnected() && !socket_cliente.isClosed()){            
            outputdata.writeUTF(mensaje);
            outputdata.flush();
        }
        else
            throw new Exception("No esta conectado...");
    }
    public String listenServer() throws IOException{
        if(inputdata!=null){
                return inputdata.readUTF();
        }
        else
            return "No esta abierto el stream";
    }
    public void cerrarConexion() throws IOException, Exception {
        if(socket_cliente.isConnected() && !socket_cliente.isClosed()){
            enviarMensaje("exit");
            input.close();
            output.close();
            inputdata.close();
            outputdata.close();
            socket_cliente.close();
        }
        else
            throw new Exception("No esta conectado...");
    }
}
