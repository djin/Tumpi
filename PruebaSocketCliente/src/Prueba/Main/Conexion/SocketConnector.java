/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Prueba.Main.Conexion;
import Prueba.Main.MainActivity;
import android.app.Activity;
import android.content.Intent;
import java.io.*;
import java.net.*;
import java.net.Socket.*;
import java.util.ArrayList;
/**
 *
 * @author 66785270
 */
public class SocketConnector {    
    Socket socket_cliente=null;
    Activity act=null;
    Intent intent_service=null;
    String ip_servidor=null;
    int puerto_servidor=0;
    Thread thread_escuchar_server=null;
    InputStream input = null;
    OutputStream output = null;
    static DataInputStream inputdata = null;
    DataOutputStream outputdata = null;
    static ArrayList<ServerMessageListener> message_listeners=new ArrayList();
    public SocketConnector(String ip,int port,Activity activity){
        ip_servidor=ip;
        puerto_servidor=port;
        act=activity;
        intent_service=new Intent(act,ServicioCliente.class);
        MainActivity.log(ServicioCliente.class.toString());
    }
    public void conectar() throws UnknownHostException, IOException {
        if(socket_cliente==null || !isConnected()){
            socket_cliente = new Socket(ip_servidor,puerto_servidor);
            input = socket_cliente.getInputStream();
            output = socket_cliente.getOutputStream();
            inputdata = new DataInputStream (input);
            outputdata = new DataOutputStream (output);
        }
    }
    public void enviarMensaje(String mensaje) throws Exception {
        if(socket_cliente.isConnected() && !socket_cliente.isClosed()){            
            outputdata.writeUTF(mensaje);
            outputdata.flush();
        }
        else
            throw new Exception("No esta conectado...");
    }
    public static String listenServer() throws IOException{
        if(inputdata!=null){
                return inputdata.readUTF();
        }
        else
            return "No esta abierto el stream";
    }
    public boolean isConnected(){
        return socket_cliente.isConnected();
    }
    public void startListeningServer(){        
        act.startService(intent_service);
    }
    public void stopListeningServer(){
        if(isConnected())
            act.stopService(intent_service);
    }
    public void cerrarConexion() throws IOException, Exception {
        if(isConnected()){
            stopListeningServer();
            input.close();
            output.close();
            inputdata.close();
            outputdata.close();
            socket_cliente.close();
        }
        else
            throw new Exception("No esta conectado...");
    }
    public void addServerMessageListener(ServerMessageListener listener){
        message_listeners.add(listener);
    }
    public void removeServerMessageListener(ServerMessageListener listener){
        message_listeners.remove(listener);
    }
    public static void fireMessageEvent(String message){
        for(ServerMessageListener listener : message_listeners){
            listener.onMessageReceive(message);
        }
    }
}
