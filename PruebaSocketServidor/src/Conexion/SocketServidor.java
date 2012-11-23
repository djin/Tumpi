/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;
import java.io.*;
import java.net.*;
import java.net.Socket.*;
import java.util.ArrayList;
/**
 *
 * @author 66785270
 */
public class SocketServidor {
    private ServerSocket socket_server=null;
    private InetAddress ip_server=null;
    private int puerto=0;
    private Thread thread_buscar_clientes=null;
    private ArrayList<Cliente> clientes=new ArrayList();
    private ArrayList<ServerSocketListener> listeners=new ArrayList();
    public SocketServidor(int port) throws IOException{
        socket_server = new ServerSocket(2222);
    }
    public boolean isBound(){
        return socket_server.isBound();
    }
    public void startSearchClients(){
        thread_buscar_clientes=new Thread(){
            @Override
            public void run(){
                Cliente cliente=null;
                while(true)
                {
                    try {
                        cliente=new Cliente(socket_server.accept());
                        clientes.add(cliente);
                        cliente.startListenClient();
                        fireClientConnectedEvent(cliente.ip_cliente);
                    } catch (IOException ex) {
                    }
                    
                }
            }
        };
        thread_buscar_clientes.start();
    }
    public void finishSearchClients(){
        thread_buscar_clientes.interrupt();
    }
    public void enviarMensajeServer(String ip_cliente,String mensaje) throws IOException{
        Cliente cliente_aux=null;
        int cont=0;
        if(!ip_cliente.equals("*")){
            for(cont=0;cont<clientes.size();cont++){
                cliente_aux=clientes.get(cont);
                if(cliente_aux.ip_cliente.equals(ip_cliente)){
                    cliente_aux.enviarMensaje(mensaje);
                    break;
                }
            }
        }
        else
            for(Cliente cliente : clientes)
                cliente.enviarMensaje(mensaje);
    }
    public void closeSocket() throws IOException{
        finishSearchClients();
        for(Cliente cliente : clientes){
            cliente.finishListenClient();
            cliente.close();
        }
        socket_server.close();
    }
    public void addServerSocketListener(ServerSocketListener listener){
        listeners.add(listener);
    }
    public void removeServerSocketListener(ServerSocketListener listener){
        listeners.remove(listener);
    }
    public void fireClientConnectedEvent(String ip){
        for(ServerSocketListener listener : listeners){
            listener.onClientConnected(ip);
        }
    }
    public void fireClientDisconnectedEvent(String ip){
        for(ServerSocketListener listener : listeners){
            listener.onClientDisconnected(ip);
        }
    }
    public void fireMessageReceivedEvent(String ip,String message){
        for(ServerSocketListener listener : listeners){
            listener.onMessageReceived(ip, message);
        }
    }
    private class Cliente{
        Socket socket_cliente=null;
        String ip_cliente=null;
        Thread thread_escuchar_liente=null;
        InputStream input = null;
        OutputStream output=null;
        DataInputStream input_stream = null;
        DataOutputStream output_stream = null;     
        Cliente(Socket socket) throws IOException{            
            socket_cliente=socket;
            ip_cliente=socket.getInetAddress().getHostAddress();
            input = socket_cliente.getInputStream();
            output = socket_cliente.getOutputStream();
            input_stream = new DataInputStream (input);
            output_stream = new DataOutputStream (output);
        }
        public void startListenClient(){
            thread_escuchar_liente=new Thread(){
                @Override
                public void run(){
                    String texto_recivido="";
                    try{
                        do{
                            texto_recivido=input_stream.readUTF();
                            fireMessageReceivedEvent(ip_cliente, texto_recivido);
                        }while(!texto_recivido.equals("exit"));
                    }catch (IOException ex) {
                    } finally {
                        finishListenClient();
                    }
                }
            };
            thread_escuchar_liente.start();
        }
        public void finishListenClient(){
            thread_escuchar_liente.interrupt();
        }
        public void enviarMensaje(String mensaje) throws IOException{
            if(socket_cliente.isConnected() && !socket_cliente.isClosed()){
                output_stream.writeUTF(mensaje);
                output_stream.flush();
            }
        }
        public void close() throws IOException{
            socket_cliente.close();
        }
    }
}
