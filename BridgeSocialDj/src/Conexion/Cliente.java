/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author 66785270
 */
public class Cliente extends Object{
    Socket socket_cliente=null;
    private String ip_cliente=null;
    private int puerto_cliente=0;
    private SocketServidor servidor;
    String id="";
    Thread thread_escuchar_liente=null;
    InputStream input = null;
    OutputStream output=null;
    DataInputStream input_stream = null;
    DataOutputStream output_stream = null;     
    public Cliente(Socket socket,SocketServidor server) throws IOException{            
        socket_cliente=socket;
        ip_cliente=socket.getInetAddress().getHostAddress();
        puerto_cliente=socket.getPort();
        id=ip_cliente+":"+puerto_cliente;
        input = socket_cliente.getInputStream();
        output = socket_cliente.getOutputStream();
        input_stream = new DataInputStream (input);
        output_stream = new DataOutputStream (output);
        servidor=server;
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
                            servidor.fireMessageReceivedEvent(id, texto_recivido);
                    }while(!texto_recivido.equals("exit") && input_stream!=null);
                }catch (IOException ex) {
                } finally {
                    try {
                        close();
                    } catch (IOException ex) {
                    }
                }
            }
        };
        thread_escuchar_liente.start();
    }
    public void finishListenClient() throws IOException{
        if(input_stream!=null){
            input_stream.close();
            input_stream=null;
        }
    }
    public void enviarMensaje(String mensaje) throws IOException{
        if(socket_cliente.isConnected() && !socket_cliente.isClosed()){
            output_stream.writeUTF(mensaje);
            output_stream.flush();
        }
    }
    public void close() throws IOException{
        finishListenClient();
        socket_cliente.close();
        servidor.clientes.remove(this);
        servidor.fireClientDisconnectedEvent(id);
    }
}
