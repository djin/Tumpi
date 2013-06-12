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
import java.util.ArrayList;

/**
 *
 * @author 66785270
 */
public class SocketServidor {

    private Socket socket_server = null;
//    private InetAddress ip_server=InetAddress.getLocalHost();
//    private int puerto=0;
    //private ThreadBuscarClientes thread_buscar_clientes=null;
    private DataInputStream input = null;
    private DataOutputStream output = null;
    //private ArrayList<Cliente> clientes=new ArrayList();
    private ArrayList<String> clientes = new ArrayList();
    private String nick = "";
    private Thread thread_server;
    private ArrayList<ServerSocketListener> listeners = new ArrayList();

    public SocketServidor(String ip, int port) throws IOException {
        //puerto=port;
        socket_server = new Socket(ip, port);
        if (socket_server.isConnected()) {
            input = new DataInputStream(socket_server.getInputStream());
            output = new DataOutputStream(socket_server.getOutputStream());
        }
    }

    public boolean isBound() {
        return socket_server.isBound();
    }
//    public void startSearchClients(){
//        thread_buscar_clientes=new ThreadBuscarClientes();
//        thread_buscar_clientes.execute();
//    }
//    public void finishSearchClients(){
//        thread_buscar_clientes.cancel(true);
//    }

    public void enviarMensajeServer(String id_cliente, String mensaje) throws IOException {
        try {
            output.writeUTF("c:" + id_cliente + "|" + mensaje);
        } catch (IOException ex) {
            System.out.println("Error al enviar el mensaje al bridge: " + ex.toString());
        }
    }

    public int getClientsCount() {
        return clientes.size();
    }

    public boolean logIn(String _nick) throws Exception {
        boolean logIn;
        try {
            output.writeUTF("s:log|" + _nick);
            socket_server.setSoTimeout(5000);
            String resp = input.readUTF();
            System.out.println("Server: " + resp);
            if ("b:log|1".equals(resp)) {
                nick = _nick;
                logIn = true;
            } else {
                logIn = false;
            }
        } catch (IOException ex) {
            System.err.println("El logIn no funciono: " + ex);
            logIn = false;
        }
        return logIn;
    }

    private void mensajeRecibido(String mensaje) {
        if ("b".equals(mensaje.split("\\:")[0])) {
            String tipo = mensaje.substring(mensaje.indexOf(":") + 1, mensaje.indexOf("|"));
            System.out.println("Bridge: " + mensaje);
            if ("client_on".equals(tipo)) {
                clientes.add(mensaje.substring(mensaje.indexOf("|") + 1));
                fireClientConnectedEvent(mensaje.substring(mensaje.indexOf("|") + 1));
            } else if ("client_off".equals(tipo)) {
                String id_cliente = mensaje.substring(mensaje.indexOf("|") + 1);
                clientes.remove(id_cliente);
                fireClientDisconnectedEvent(id_cliente);
            }
        } else {
            String id_cliente = mensaje.substring(0, mensaje.indexOf("|"));
            mensaje = mensaje.substring(mensaje.indexOf("|") + 1);
            fireMessageReceivedEvent(id_cliente, mensaje);
        }
    }

    public void startListenBridge() {
        thread_server = new Thread() {
            @Override
            public void run() {
                while (thread_server != null && socket_server.isConnected()) {
                    String mensaje;
                    try {
                        socket_server.setSoTimeout(0);
                        mensaje = input.readUTF();
                        mensajeRecibido(mensaje);
                    } catch (IOException ex) {
                        try {
                            System.err.println("Error al escuchar al bridge 1: " + ex);
                            closeSocket();
                        } catch (IOException ex1) {
                            System.err.println("Error al escuchar al bridge 2: " + ex1);
                        }
                        thread_server = null;
                    }
                }
            }
        };
        thread_server.start();
    }

    public void closeSocket() throws IOException {
        //finishSearchClients();
//        for(Cliente cliente : clientes){
//            cliente.enviarMensaje("exit");
//            cliente.finishListenClient();
//        }
        if (!socket_server.isClosed()) {
            output.writeUTF("s:exit");
            thread_server = null;
            socket_server.close();
        }
    }

    public void addServerSocketListener(ServerSocketListener listener) {
        listeners.add(listener);
    }

    public void removeServerSocketListener(ServerSocketListener listener) {
        listeners.remove(listener);
    }

    public void fireClientConnectedEvent(String id) {
        for (ServerSocketListener listener : listeners) {
            listener.onClientConnected(id);
        }
    }

    public void fireClientDisconnectedEvent(String id) {
        for (ServerSocketListener listener : listeners) {
            listener.onClientDisconnected(id);
        }
    }

    public void fireMessageReceivedEvent(String id, String message) {
        for (ServerSocketListener listener : listeners) {
            listener.onMessageReceived(id, message);
        }
    }
//    private ServerSocket socket_server=null;
//    private InetAddress ip_server=InetAddress.getLocalHost();
//    private int puerto=0;
//    private Thread thread_buscar_clientes=null;
//    private ArrayList<Cliente> clientes=new ArrayList();
//    private ArrayList<ServerSocketListener> listeners=new ArrayList();
//    public SocketServidor(int port) throws IOException{
//        socket_server = new ServerSocket(2222);
//    }
//    public boolean isBound(){
//        return socket_server.isBound();
//    }
//    public void startSearchClients(){
//        thread_buscar_clientes=new Thread(){
//            @Override
//            public void run(){
//                while(!socket_server.isClosed())
//                {
//                    try {
//                        Cliente cliente=null;
//                        cliente=new Cliente(socket_server.accept());
//                        clientes.add(cliente);
//                        cliente.startListenClient();
//                        fireClientConnectedEvent(cliente.ip_cliente);
//                    } catch (IOException ex) {
//                    }                    
//                }
//            }
//        };
//        thread_buscar_clientes.start();
//    }
//    public void finishSearchClients(){
//        thread_buscar_clientes.interrupt();
//    }
//    public void enviarMensajeServer(String ip_cliente,String mensaje) throws IOException{
//        Cliente cliente_aux=null;
//        int cont=0;
//        if(!ip_cliente.equals("*")){
//            for(cont=0;cont<clientes.size();cont++){
//                cliente_aux=clientes.get(cont);
//                if(cliente_aux.ip_cliente.equals(ip_cliente)){
//                    cliente_aux.enviarMensaje(mensaje);
//                    break;
//                }
//            }
//        }
//        else
//            for(Cliente cliente : clientes)
//                cliente.enviarMensaje(mensaje);
//    }
//    public int getClientsCount(){
//        return clientes.size();
//    }
//    public void closeSocket() throws IOException{
//        finishSearchClients();
//        for(Cliente cliente : clientes){
//            cliente.enviarMensaje("exit");
//            cliente.finishListenClient();
//        }
//        socket_server.close();
//    }
//    public void addServerSocketListener(ServerSocketListener listener){
//        listeners.add(listener);
//    }
//    public void removeServerSocketListener(ServerSocketListener listener){
//        listeners.remove(listener);
//    }
//    public void fireClientConnectedEvent(String ip){
//        for(ServerSocketListener listener : listeners){
//            listener.onClientConnected(ip);
//        }
//    }
//    public void fireClientDisconnectedEvent(String ip){
//        for(ServerSocketListener listener : listeners){
//            listener.onClientDisconnected(ip);
//        }
//    }
//    public void fireMessageReceivedEvent(String ip,String message){
//        for(ServerSocketListener listener : listeners){
//            listener.onMessageReceived(ip, message);
//        }
//    }
//    public String getIp(){
//        return ip_server.getHostAddress();
//    }
//    private class Cliente{
//        Socket socket_cliente=null;
//        String ip_cliente=null;
//        Thread thread_escuchar_liente=null;
//        InputStream input = null;
//        OutputStream output=null;
//        DataInputStream input_stream = null;
//        DataOutputStream output_stream = null;     
//        Cliente(Socket socket) throws IOException{            
//            socket_cliente=socket;
//            ip_cliente=socket.getInetAddress().getHostAddress();
//            input = socket_cliente.getInputStream();
//            output = socket_cliente.getOutputStream();
//            input_stream = new DataInputStream (input);
//            output_stream = new DataOutputStream (output);
//        }
//        public void startListenClient(){
//            thread_escuchar_liente=new Thread(){
//                @Override
//                public void run(){
//                    String texto_recivido="";
//                    try{
//                        do{
//                            texto_recivido=input_stream.readUTF();
//                            if(!texto_recivido.equals("exit"))
//                                fireMessageReceivedEvent(ip_cliente, texto_recivido);
//                        }while(!texto_recivido.equals("exit") && input_stream!=null);
//                    }catch (IOException ex) {
//                    } finally {
//                        try {
//                            close();
//                        } catch (IOException ex) {
//                        }
//                    }
//                }
//            };
//            thread_escuchar_liente.start();
//        }
//        public void finishListenClient() throws IOException{
//            if(input_stream!=null){
//                input_stream.close();
//                input_stream=null;
//            }
//        }
//        public void enviarMensaje(String mensaje) throws IOException{
//            if(socket_cliente.isConnected() && !socket_cliente.isClosed()){
//                output_stream.writeUTF(mensaje);
//                output_stream.flush();
//            }
//        }
//        public void close() throws IOException{
//            finishListenClient();
//            socket_cliente.close();
//            clientes.remove(this);
//            fireClientDisconnectedEvent(ip_cliente);
//        }
//    }
}
