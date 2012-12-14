/*
 * Clase de comunicacion por sockets en un servidor para varios clientes.
 * Tambien se define la subclase Cliente.
 * Resumen SocketServidor:
 * - Constructor: unicamente crea y 'conecta' el socket.
 * - isBound: devuelve si el socket esta o no 'conectado'.
 * - startSearchClients: arranca un thread que busca clientes poniendose en un
 *                       modo de espera. Cuando se conecta alguien lo añade a
 *                       la lista de clientes y dispara el evento. Despues
 *                       vuelve a esperar.
 * - finishSearchClients: detiene la busqueda de clientes.
 * - enviarMensajeServer: envia un mensaje a un destino en concreto o a todos
 *                        los clientes de la lista si en la direccion de destino
 *                        se pone un '*'.
 * - getClientsCount: devuelve el numero de clientes conectados.
 * - closeSocket: Cierra la comunicación. No se puede volver a conectar.
 * - [add/remove]ServerSocketListener: añade o remueve un listener concreto de 
 *                                     la lista de listeners.
 * - fire[evento]: dispara el evento que sea.
 * 
 * Resumen Cliente:
 * - Constructor: Dandole el socket ya conectado guarda los datos (ip) del
 *                cliente y inicia los streams de comunicacion.
 * - [start/stop]ListenClient: inicia o detiene el thread que escucha los
 *                             mensajes del cliente en concreto.
 * - enviarMensaje: envia un mensaje al cliente.
 * - close: Cierra la conexion con el cliente.
 */
package conexion;
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
    private InetAddress ip_server=InetAddress.getLocalHost();
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
                while(!socket_server.isClosed())
                {
                    try {
                        Cliente cliente=null;
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
    public int getClientsCount(){
        return clientes.size();
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
    public String getIp(){
        return ip_server.getHostAddress();
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
                            if(!texto_recivido.equals("exit"))
                                fireMessageReceivedEvent(ip_cliente, texto_recivido);
                        }while(!texto_recivido.equals("exit"));
                    }catch (IOException ex) {
                    } finally {
                        finishListenClient();
                        try {
                            close();
                        } catch (IOException ex) {
                        }
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
            clientes.remove(this);
            fireClientDisconnectedEvent(ip_cliente);
        }
    }
}
