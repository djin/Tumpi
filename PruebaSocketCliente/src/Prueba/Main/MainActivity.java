package Prueba.Main;


import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.widget.TextView;
import java.io.*;
import java.net.*;
import java.net.Socket.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TextView label=(TextView) findViewById(R.id.main);
        label.setText("Adios");
        try {
            SocketAddress dir_server=new InetSocketAddress("192.168.173.1",22222);
            Socket cliente=new Socket();
            do{
                cliente.connect(dir_server);
            }while(!cliente.isConnected());
            
            InputStream input = cliente.getInputStream();
            OutputStream output = cliente.getOutputStream();
            DataInputStream inputdata = new DataInputStream (input);
            DataOutputStream outputdata = new DataOutputStream (output);
            outputdata.writeUTF("HOLA MUNDO");
        } catch (Exception ex) {
             label.setText(ex.toString());
        }  
    }
}
