/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lista.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.*;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import lista.android.conexion.ConnectionManager;

/**
 *
 * @author 66785320
 */
public class PantallaPrincipal extends Activity {

    /** Called when the activity is first created. */
    ConnectionManager conex;
    Activity p;
    //public static ProgressDialog pd;
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        p=this;
        conex = new ConnectionManager(this);
        // ToDo add your GUI initialization code here  
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.stylepantallainicial);
        
        Button btnNuevaConexion = (Button)findViewById(R.id.btnconexion);
        Button btnEntrarDisco = (Button)findViewById(R.id.btnAntiguaConexion);
        btnNuevaConexion.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(PantallaPrincipal.this, PantallaDatosServidor.class);
                
                startActivity(intent);
            }
        });        
        btnEntrarDisco.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                ConnectivityManager connMgr =(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);                    
                if(connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()){
                    try{
//                        pd = ProgressDialog.show(p, "Buscando servidor", "Espere unos segundos...", true, false);
                        String info=conex.buscarServer(8888);
                        if(info!=null){
                            String ip=info.split("\\|")[0];
                            int port=Integer.parseInt(info.split("\\|")[1]);
                            if(conex.conectar(ip,port,p)){
                                conex.conexion.startListeningServer();
                                Intent inte = new Intent(PantallaPrincipal.this, ListaCanciones.class);
                                startActivity(inte);
                            }
                            else
                                Toast.makeText(p, "Error al conectar al servidor", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(p, "Error al buscar al servidor", Toast.LENGTH_SHORT).show();
                    }catch(Exception ex){
                        Toast.makeText(p, ex.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(p);
                    dialogBuilder.setMessage("Se necesita estar conectado a una red wifi que disponga de servidor socialDJ");
                    dialogBuilder.setTitle("¡No esta conectado!");
                    dialogBuilder.setPositiveButton("Aceptar", new android.content.DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialogBuilder.show();
                }
            }
        });     
    }
}
