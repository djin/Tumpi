/*
 * Clase de conexion por sockets 
 * Resumen:
 * - Constructor: Hay que indicar la ip del servidor, el puerto del servidor
 *                y la actividad de la cual se quiere arrancar el servicio de
 *                escucha al servidor.
 * - conectar: conecta el socket y crea los streams para la comunicacion.
 * - enviarMensaje: Toma el mensaje como parametro y lo envia al servidor.
 * - [start/stop]ListenServer: Inician y paran el servicio de escucha.
 * - cerrarConexion: Cierra la conexion completamente.
 * - [add/remove]ServerMessageListener: Añade o remueve a un objeto de la lista
 *                                      de listeners.
 */

package Prueba.Main.Conexion;
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
        act.stopService(intent_service);
    }
    public void cerrarConexion() throws IOException, Exception {
        if(isConnected()){
            stopListeningServer();
            enviarMensaje("exit");
            input.close();
            output.close();
            inputdata.close();
            outputdata.close();
            socket_cliente.close();
            message_listeners.removeAll(message_listeners);
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
