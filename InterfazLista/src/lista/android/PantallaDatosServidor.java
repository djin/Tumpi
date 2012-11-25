/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lista.android;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import lista.android.conexion.SocketConnector;

/**
 *
 * @author 66785320
 */
public class PantallaDatosServidor extends Activity {
    public static SocketConnector conex;
    EditText editIp;
    EditText editPort;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        // ToDo add your GUI initialization code here        
        setContentView(R.layout.stylepantallaconexion);
        Button btnConect = (Button)findViewById(R.id.btnConectar);
        
        final PantallaDatosServidor p = this;
        btnConect.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                editIp = (EditText)findViewById(R.id.fieldIp);
                editPort = (EditText)findViewById(R.id.fieldPort);
                if(editIp.getText().toString().equals("") || editPort.getText().toString().equals("")){
                    if(editIp.getText().toString().equals("")){
                        TextView txtErr = (TextView)findViewById(R.id.txtMensageError);
                        txtErr.setText("Tienes que poner una direccion IP");
                    }else {
                        TextView txtErr = (TextView)findViewById(R.id.txtMensageError);
                        txtErr.setText("Tienes que especificar el puerto");
                    }
                }else {
                    /*final String ip = editIp.getText().toString();
                    final int port = Integer.parseInt(editPort.getText().toString());
                    try {
                        conex = new SocketConnector(ip, port, p);
                        conex.conectar();
                        if(conex.isConnected()){
                            Intent inte = new Intent(PantallaDatosServidor.this, ListaCanciones.class);
                            startActivity(inte);
                        }
                        
                    }catch (Exception ex){
                        TextView txtErr = (TextView)findViewById(R.id.txtMensageError);
                        txtErr.setText("No se ha podido realizar la conexion, intentelo mas tarde: "+ex.toString());
                    }  */
                    new conectar().execute(p);                           
                }
            }
        });
    }
    class conectar extends AsyncTask<Activity, Void, Void> {

    private Exception exception;


    @Override
    protected Void doInBackground(Activity... params) {
        try {
            final String ip = editIp.getText().toString();
            final int port = Integer.parseInt(editPort.getText().toString());
            conex = new SocketConnector(ip, port, params[0]);
            conex.conectar();
            if(conex.isConnected()){
                Bundle b=new Bundle();
                Intent inte = new Intent(PantallaDatosServidor.this, ListaCanciones.class);
                startActivity(inte);
            }

        }catch (Exception ex){
            TextView txtErr = (TextView)findViewById(R.id.txtMensageError);
            txtErr.setText("No se ha podido realizar la conexion, intentelo mas tarde: "+ex.toString());
        }
        return null;
    }
 }
}
