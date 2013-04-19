/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lista.android.conexion;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.widget.Toast;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import lista.android.ListaCanciones;
import lista.android.PantallaPrincipal;

/**
 *
 * @author 66785270
 */
public class ConnectionManager {
    public static SocketConnector conexion;
    ProgressDialog pd;
    private Context context;

    public ConnectionManager(Context cont) {
        context = cont;
    }
    
    
    public Boolean conectar(String ip,int port,Activity act) throws Exception{
        conexion=new SocketConnector(ip,port,act);
        return new conectar().execute().get();
    }
    public String buscarServer(int port) throws Exception{
        return new BuscarServer(port).execute().get();        
    }
    
    class conectar extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
           // pd = ProgressDialog.show(context, "Buscando servidor", "Espere unos segundos...", true, false);
        }
        
        
        
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                conexion.conectar();
                return conexion.isConnected();
            }catch (Exception ex){
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
          //  if(pd != null){
          //      pd.dismiss();
          //  }
        }
    }
     public String getIpAddr() {
       WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
       WifiInfo wifiInfo = wifiManager.getConnectionInfo();
       int ip = wifiInfo.getIpAddress();

       String ipString = String.format(
       "%d.%d.%d.%d",
       (ip & 0xff),
       (ip >> 8 & 0xff),
       (ip >> 16 & 0xff),
       (ip >> 24 & 0xff));

       return ipString;
    }
    class BuscarServer extends AsyncTask<Void, Void, String> {
        private int port;
        public BuscarServer(int _port){
            super();
            port=_port;
        }
        @Override
        protected void onPreExecute() {
//            pd = ProgressDialog.show(context, "Buscando servidor", "Espere unos segundos...", true, false);
        }
        @Override
        protected String doInBackground(Void... params) {
           DatagramSocket socket=null;
            String ip_cliente=getIpAddr();
            try {
                socket=new DatagramSocket(port);
                socket.setBroadcast(true);
                socket.setSoTimeout(1000);
                String identificacion="cliente_socialDj|"+ip_cliente+"|";
                byte[] mensaje_id=identificacion.getBytes("utf-8");
                socket.send(new DatagramPacket(mensaje_id,mensaje_id.length, new InetSocketAddress("255.255.255.255",port)));
                byte[] datos = new byte[50];
                DatagramPacket paquete;
                int cont=0;
                while(cont<5){
                    paquete=new DatagramPacket(datos,50);
                    socket.receive(paquete);                
                    String mensaje=new String(paquete.getData(),"utf-8");
                    if("servidor_socialDj".equals(mensaje.split("\\|")[0]))
                        return paquete.getAddress().getHostAddress();
                    cont++;
                }           
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
                    String ip=info;
                    int port_aux=2222;
                    if(conectar(ip,port_aux,(Activity)context)){
                        conexion.startListeningServer();
                        Intent inte = new Intent(context, ListaCanciones.class);
                        context.startActivity(inte);
                        
                    }
                    else
                        Toast.makeText(context, "Error al conectar al servidor: "+info, Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(context, "Error al buscar al servidor", Toast.LENGTH_SHORT).show();
            }catch(Exception ex){
                Toast.makeText(context, ex.toString(), Toast.LENGTH_SHORT).show();
            }
        }        
    }
}
