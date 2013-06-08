/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.tumpi.cliente.lista.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import app.tumpi.R;
import android.widget.EditText;
import android.widget.Toast;
import app.tumpi.cliente.lista.android.conexion.*;

/**
 *
 * @author 66785320
 */
public class PantallaDatosServidor extends Activity{
    ConnectionManager conex;
    
    EditText editIp;
    EditText editPort;
//    public static ProgressDialog pd = null;
    private PantallaDatosServidor p;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        // ToDo add your GUI initialization code here        
        setContentView(R.layout.stylepantallaconexion);
        Button btnConect = (Button)findViewById(R.id.btnConectar);
        p = this;
        btnConect.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                editIp = (EditText)findViewById(R.id.fieldIp);
                editPort = (EditText)findViewById(R.id.fieldPort);
                if(editIp.getText().toString().equals("") || editPort.getText().toString().equals("")){
                    if(editIp.getText().toString().equals("")){
                        Toast.makeText(p, "Tienes que poner una direccion IP", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(p, "Tienes que especificar un puerto", Toast.LENGTH_LONG).show();
                    }
                }else {
                    
                    String ip = editIp.getText().toString();
                    int port = Integer.parseInt(editPort.getText().toString());
//                    ConnectivityManager connMgr =(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);                    
//                    if(connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()){
                        try {
//                                pd = ProgressDialog.show(p, "Procesando", "Espere unos segundos...", true, false);
                                conex = new ConnectionManager();  
                                    
                                if(conex.conectar()){
                                    conex.conexion.startListeningServer();
                                    Intent inte = new Intent(PantallaDatosServidor.this, ListaCanciones.class);
                                    startActivity(inte);
                                }
                                else {
                                     AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(p);
                                     dialogBuilder.setMessage("El servidor y la ip dados parecen no ser correctos, consulte de nuevo los datos o intentelo mas tarde: \n"+ip+":"+port);
                                     dialogBuilder.setTitle("Error de conexion");
                                     dialogBuilder.setPositiveButton("Aceptar", new android.content.DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                     });
                                     dialogBuilder.show();
                                }
                                
                        }catch (Exception ex){
//                            pd.dismiss();
                            Toast.makeText(p, "Ha ocurrido un error al intentar conectar, intentelo mas tarde: \n"+ex.toString(), Toast.LENGTH_LONG).show();
                        }                  
//                    }
//                    else{
//                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(p);
//                        dialogBuilder.setMessage("Se necesita estar conectado a una red wifi que disponga de servidor socialDJ");
//                        dialogBuilder.setTitle("¡No esta conectado!");
//                        dialogBuilder.setPositiveButton("Aceptar", new android.content.DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//                        dialogBuilder.show();
//                    }
                }
            }
        });
    }    
}
