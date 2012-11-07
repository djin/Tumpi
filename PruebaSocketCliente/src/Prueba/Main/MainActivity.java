package Prueba.Main;


import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity
{
    /** Called when the activity is first created. */
    TextView texto_main=null;
    EditText input_mensaje=null;
    EditText input_ip=null;
    EditText input_puerto=null;
    Thread thread_escuchar_server=null;
    char[] texto_mensaje = null;
    SocketCliente cliente=null;    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        texto_main=(TextView) findViewById(R.id.texto_log);
        input_mensaje=(EditText) findViewById(R.id.mensaje);
        input_ip=(EditText) findViewById(R.id.ip_server);
        input_puerto=(EditText) findViewById(R.id.puerto_server);
        texto_main.setText("Conéctate");
        
    }
    public void conectar(final View view) {
        try {
            String ip=input_ip.getText().toString();
            String puerto=input_puerto.getText().toString();
            cliente = new SocketCliente(ip,Integer.parseInt(puerto));
            cliente.conectar();
            if(cliente.socket_cliente.isConnected()){
                texto_main.setText("Conexión realizada con éxito!!");                
                thread_escuchar_server=new Thread(){
                    @Override
                    public void run(){
                        String texto_recivido="";
                        Thread this_thread=Thread.currentThread();
                        try{
                            do{
                                texto_recivido=cliente.listenServer();
                                if(!texto_recivido.equals("close"))
                                    log("\n"+texto_recivido);
                            }while(!texto_recivido.equals("close") && thread_escuchar_server==this_thread);
                        }catch(Exception ex){
                            //log("\nError al escuchar al servidor: "+ex.toString()+ex.hashCode());
                        }
                    }
                };
                thread_escuchar_server.start();
            }
        } catch (Exception ex) {
             texto_main.append("\nError de conexion: "+ex.toString());
        }
    }
    private void log(final String cadena){
        texto_main.post(new Runnable() {
            @Override
            public void run(){
                texto_main.append(cadena);                
            }
        });
    }
    public void enviarMensaje(View view){
        try {              
            String mensaje=input_mensaje.getText().toString();
            cliente.enviarMensaje(mensaje);
            input_mensaje.setText("");
        } catch (Exception ex) {
           texto_main.append("\nError al enviar mensaje: "+ex.toString());
        }
    }
    public void cerrarConexion(View view) {        
        try {
            thread_escuchar_server.interrupt();
            thread_escuchar_server=null;
            cliente.cerrarConexion();
            texto_main.append("\nDesconectado del servidor");
        } catch (Exception ex) {
            texto_main.append("\nError al desconectar: "+ex.toString());
        }
    }
    public void limpiarLog(View view) {
        texto_main.setText("Conéctate!!");
    }
}
