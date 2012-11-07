/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Prueba;
import java.io.*;
import java.net.*;
import java.net.Socket.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public SocketServidor(int port){
         try {
            socket_server = new ServerSocket(2222);
            if(socket_server.isBound()){
                System.out.println("Socket creado con éxito");
            }
        } catch (IOException ex) {
            System.out.println("Error al crear el socket: "+ex.toString());
        }
    }
    public void startSearchClients(){
        thread_buscar_clientes=new Thread(){
            @Override
            public void run(){
                while(true)
                {
                    Socket socket_cliente=null;
                    try {
                        socket_cliente = socket_server.accept();
                    } catch (IOException ex) {
                        log("Error al buscar clientes: "+ex.toString());
                    }
                    System.out.println("Cliente conectado: "+socket_cliente.getInetAddress().getHostAddress());
                    Cliente cliente=new Cliente(socket_cliente);
                    clientes.add(cliente);
                    log("Número de clientes: "+clientes.size());
                    cliente.enviarMensaje("Bienvenido al servicio de chat, yeah!!");
                    cliente.startListenClient();
                }
            }
        };
        thread_buscar_clientes.start();
    }
    public void finishSearchClients(){
        thread_buscar_clientes.interrupt();
    }
    public void enviarMensajeServer(String ip_cliente,String mensaje){
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
            if(cont==clientes.size())
                log("No se encontro el cliente "+ip_cliente);
        }
        else
            for(cont=0;cont<clientes.size();cont++){
                cliente_aux=clientes.get(cont);
                cliente_aux.enviarMensaje(mensaje);
            }
    }
    public void closeSocket(){
        try {
            socket_server.close();
        } catch (IOException ex) {
            log("Error: El socket no esta conectado o creado: "+ex.toString());
        }
    }
    private void log(String cadena){
        System.out.println(cadena);
    }
    private class Cliente{
        Socket socket_cliente=null;
        String ip_cliente=null;
        Thread thread_escuchar_liente=null;
        InputStream input = null;
        OutputStream output=null;
        DataInputStream input_stream = null;
        DataOutputStream output_stream = null;     
        Cliente(Socket socket){            
            socket_cliente=socket;
            ip_cliente=socket.getInetAddress().getHostAddress();
            try {
                input = socket_cliente.getInputStream();
                output = socket_cliente.getOutputStream();
                input_stream = new DataInputStream (input);
                output_stream = new DataOutputStream (output);
            } catch (IOException ex) {
                log("Error al recoger el stream: "+ex.toString());
                finishListenClient();
            }
        }
        public void startListenClient(){
            thread_escuchar_liente=new Thread(){
                @Override
                public void run(){
                    String texto_recivido="";
                    try{
                        do{
                            texto_recivido=input_stream.readUTF();
                            if(!texto_recivido.equals("exit")){
                                log("- "+ip_cliente+": "+texto_recivido);
                                enviarMensajeServer("*","- "+ip_cliente+": "+texto_recivido);
                            }
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
            try {
                enviarMensaje("close");
                socket_cliente.close();
                clientes.remove(this);
                log("Cliente desconectado: "+ip_cliente+"\nNúmero de clientes: "+clientes.size());
            } catch (IOException ex) {
                log("Error al cerrar el socket del cliente");
            }
        }
        public void enviarMensaje(String mensaje){
            if(socket_cliente.isConnected() && !socket_cliente.isClosed()){            
                try {
                    output_stream.writeUTF(mensaje);
                    output_stream.flush();
                } catch (IOException ex) {
                    log("Error al enviar mensaje al cliente "+ip_cliente+": "+ex.toString());
                }
            }
            else
                log("No esta conectado...");
            }
    }
}
