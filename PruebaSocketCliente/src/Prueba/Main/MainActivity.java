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
    public void conectar(View view) {
        try {
            String ip=input_ip.getText().toString();
            String puerto=input_puerto.getText().toString();
            cliente = new SocketCliente(ip,Integer.parseInt(puerto));
            cliente.conectar();
            if(cliente.socket_cliente.isConnected()){
                texto_main.append("\nConexión realizada con éxito!!");
                cliente.startListenServer(texto_main);
            }
        } catch (Exception ex) {
             texto_main.append("\nError de conexion: "+ex.toString());
        }
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
        } catch (Exception ex) {
            texto_main.append("\nError al desconectar: "+ex.toString());
        }
    }
    public void limpiarLog(View view) {
        texto_main.setText("Conéctate!!");
    }
}
