package Prueba.Main;


import Prueba.Main.Conexion.ServerMessageListener;
import Prueba.Main.Conexion.SocketConnector;
import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements ServerMessageListener
{
    /** Called when the activity is first created. */
    static TextView texto_main=null;
    EditText input_mensaje=null;
    EditText input_ip=null;
    EditText input_puerto=null;
    public SocketConnector cliente=null;
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
        String ip=input_ip.getText().toString();
        String puerto=input_puerto.getText().toString();
        try {            
            cliente = new SocketConnector(ip,Integer.parseInt(puerto),this);
            cliente.conectar();
            if(cliente.isConnected()){
                texto_main.setText("Conexión realizada con éxito!!");
                cliente.addServerMessageListener(this);
                cliente.startListeningServer();
            }
        } catch (Exception ex) {
             texto_main.append("\nError de conexion: "+ex.getMessage());
        }
    }
    public static void log(final String cadena){
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
            cliente.cerrarConexion();
            texto_main.append("\nDesconectado del servidor");
        } catch (Exception ex) {
            texto_main.append("\nError al desconectar: "+ex.toString());
        }
    }
    public void limpiarLog(View view) {
        texto_main.setText("Conéctate!!");
    }

    public void onMessageReceive(String message) {
        log("\n"+message);
    }
}
