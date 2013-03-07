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
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import lista.android.conexion.ConnectionManager;

/**
 *
 * @author 66785320
 */
public class PantallaPrincipal extends Activity {

    /** Called when the activity is first created. */
    ConnectionManager conex;
    Activity p;
    public static ProgressDialog pd;
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        p=this;
        conex = new ConnectionManager(this);
        // ToDo add your GUI initialization code here  
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.stylepantallainicial);
        
        Button btnEntrarDisco = (Button)findViewById(R.id.btnAntiguaConexion);
        btnEntrarDisco.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                ConnectivityManager connMgr =(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);                    
                if(connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()){
                    new BuscarServer(8888).execute();
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
    
    public void focusStng(View v){
        Intent intent = new Intent(PantallaPrincipal.this, PantallaDatosServidor.class);
        startActivity(intent);
    }
    
    class BuscarServer extends AsyncTask<Void, Void, String> {
        private int port;
        public BuscarServer(int _port){
            super();
            port=_port;
        }
        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(p, "Buscando servidor", "Espere unos segundos...", true, false);
        }
        @Override
        protected String doInBackground(Void... params) {
            DatagramSocket socket=null;
            try {
                socket=new DatagramSocket(8888);
                socket.setBroadcast(true);
                socket.setSoTimeout(5000);
                String identificacion="socialDj|192.168.43.250";
                byte[] mensaje_id=identificacion.getBytes("utf-8");
                socket.send(new DatagramPacket(mensaje_id,mensaje_id.length, new InetSocketAddress("255.255.255.255",8888)));
                byte[] datos = new byte[50];
                DatagramPacket paquete=new DatagramPacket(datos,50);
                socket.receive(paquete);                
                String mensaje=new String(paquete.getData(),"utf-8");
                if("socialDj".equals(mensaje.split("\\|")[0]))
                    return mensaje.split("\\|")[1];
                else
                    return null;
            }catch (Exception ex){
                return null;
            }finally{
                if(socket!=null)
                    socket.close();
            }
        }

        @Override
        protected void onPostExecute(String info) {
            if(pd != null)
                pd.dismiss();
            try{
                if(info!=null){
                    String ip=info.split("\\|")[0];
                    int port=2222;
                    if(conex.conectar(ip,port,p)){
                        conex.conexion.startListeningServer();
                        Intent inte = new Intent(PantallaPrincipal.this, ListaCanciones.class);
                        startActivity(inte);
                    }
                    else
                        Toast.makeText(p, "Error al conectar al servidor: "+info, Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(p, "Error al buscar al servidor", Toast.LENGTH_SHORT).show();
            }catch(Exception ex){
                Toast.makeText(p, ex.toString(), Toast.LENGTH_SHORT).show();
            }
        }        
    }
}
