package tumpiold.app.servidor.conexion;

import android.os.AsyncTask;
import android.util.Log;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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
        AsyncTask<String, Void, Boolean> thread_log = new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... _nick) {
                try {
                    output.writeUTF("s:log|" + _nick[0]);
                    String resp = input.readUTF();
                    Log.i("Conexion", "Server: " + resp);
                    if ("b:log|1".equals(resp)) {
                        nick = _nick[0];
                        return true;
                    }
                    return false;
                } catch (IOException ex) {
                    Log.e("Conexion", "Error al mandar peticion login: " + ex.toString());
                    return false;
                }
            }
        };
        return thread_log.execute(_nick).get(5, TimeUnit.SECONDS);
    }

    private void mensajeRecivido(String mensaje) {
        if ("b".equals(mensaje.split("\\:")[0])) {
            String tipo = mensaje.substring(mensaje.indexOf(":") + 1, mensaje.indexOf("|"));
            Log.i("Conexion", "Bridge: " + mensaje);
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
                    String mensaje = "";
                    try {
                        mensaje = input.readUTF();
                        mensajeRecivido(mensaje);
                    } catch (IOException ex) {
                        try {
                            closeSocket();
                            fireMessageReceivedEvent("", "exit");
                        } catch (IOException ex1) {
                            Log.e("Conexion", "Error al cerrar la conexion con el bridge: " + ex);
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
//    class ThreadBuscarClientes extends AsyncTask<Void, Void, Boolean> {
//        @Override
//        protected void onPreExecute() {
//           // pd = ProgressDialog.show(context, "Buscando servidor", "Espere unos segundos...", true, false);
//        }        
//        @Override
//        protected Boolean doInBackground(Void... params) {
//            while(!socket_server.isClosed())
//            {
//                try {
//                    Cliente cliente=null;
//                    cliente=new Cliente(socket_server.accept());
//                    clientes.add(cliente);
//                    cliente.startListenClient();
//                    fireClientConnectedEvent(cliente.ip_cliente);
//                } catch (IOException ex) {
//                    Log.e("Conexion","Error al escuchar clientes: "+ex.toString());
//                    return false;
//                }                    
//            }
//            return true;
//        }
//        @Override
//        protected void onPostExecute(Boolean result) {
//          //  if(pd != null){
//          //      pd.dismiss();
//          //  }
//        }
//    }
}
