/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lista.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import lista.android.conexion.SocketConnector;

/**
 *
 * @author 66785320
 */
public class PantallaDatosServidor extends Activity {
    private SocketConnector conex;
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
                EditText editIp = (EditText)findViewById(R.id.fieldIp);
                EditText editPort = (EditText)findViewById(R.id.fieldPort);
                if(editIp.getText().toString().equals("") || editPort.getText().toString().equals("")){
                    if(editIp.getText().toString().equals("")){
                        TextView txtErr = (TextView)findViewById(R.id.txtMensageError);
                        txtErr.setText("Tienes que poner una direccion IP");
                    }else {
                        TextView txtErr = (TextView)findViewById(R.id.txtMensageError);
                        txtErr.setText("Tienes que especificar el puerto");
                    }
                }else {
                    String ip = editIp.getText().toString();
                    int port = Integer.parseInt(editPort.getText().toString());
    //                try {
    //                    conex = new SocketConnector(ip, port, p);
    //                    conex.conectar();
    //                    if(conex.isConnected()){
                            Intent inte = new Intent(PantallaDatosServidor.this, ListaCanciones.class);
                            startActivity(inte);
    //                    }
    //                    
    //                }catch (Exception ex){
    //                    TextView txtErr = (TextView)findViewById(R.id.txtMensageError);
    //                    txtErr.setText("No se ha podido realizar la conexion, intentelo mas tarde");
    //                }
                }
            }
        });
    }
}