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
        texto_main.setText("Conéctate");
        
    }
    public void conectar(View view) {
        try {            
            cliente = new Socket("192.168.1.24",22222);
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
                Editable datos=input_mensaje.getText();
                texto_mensaje=new char[250];
                datos.getChars(0, datos.length(), texto_mensaje,0);
                String mensaje=new String(texto_mensaje);
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
