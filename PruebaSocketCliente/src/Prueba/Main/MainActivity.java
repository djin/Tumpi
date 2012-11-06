package Prueba.Main;


import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.view.*;
import android.widget.EditText;
import android.widget.TextView;
import java.io.*;
import java.net.*;
import java.net.Socket.*;

public class MainActivity extends Activity
{
    /** Called when the activity is first created. */
    TextView texto_main=null;
    EditText input_mensaje=null;
    EditText input_ip=null;
    EditText input_puerto=null;
    char[] texto_mensaje = null;
    Socket cliente=null;
    InputStream input = null;
    OutputStream output = null;
    DataInputStream inputdata = null;
    DataOutputStream outputdata = null;
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
            cliente = new Socket(ip,Integer.parseInt(puerto));
            if(cliente.isConnected())
                texto_main.append("\nConexión realizada con éxito!!");
        } catch (Exception ex) {
             texto_main.append("\nError de conexion: "+ex.toString());
        }
    }
    public void enviarMensaje(View view) {
        if(cliente.isConnected() && !cliente.isClosed()){
            try {
                input = cliente.getInputStream();
                output = cliente.getOutputStream();
                inputdata = new DataInputStream (input);
                outputdata = new DataOutputStream (output);               
                String mensaje=input_mensaje.getText().toString();
                outputdata.writeUTF(mensaje);
                input_mensaje.setText("");
                outputdata.flush();
            } catch (IOException ex) {
               texto_main.append("\nError al enviar mensaje: "+ex.toString());
            }
        }
        else
            texto_main.append("\nNo esta conectado al servidor!!!");
    }
    public void cerrarConexion(View view) {
        if(cliente.isConnected() && !cliente.isClosed()){
            try {
                cliente.close();
            } catch (IOException ex) {
                texto_main.append("\nError al desconectar: "+ex.toString());
            }
        }
        else
            texto_main.append("\nNo esta conectado al servidor!!!");
    }
    public void limpiarLog(View view) {
        texto_main.setText("Conéctate!!");
    }
}
