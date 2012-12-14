/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lista.android.conexion;

import android.app.Activity;
import android.os.AsyncTask;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import lista.android.PantallaDatosServidor;

/**
 *
 * @author 66785270
 */
public class ConnectionManager {
    public static SocketConnector conexion;
    public Boolean conectar(String ip,int port,Activity act) throws Exception{
        conexion=new SocketConnector(ip,port,act);
        return new conectar().execute().get();
    }
    public String buscarServer(int port) throws Exception{
        return new BuscarServer(port).execute().get();        
    }
    
    class conectar extends AsyncTask<Void, Void, Boolean> {
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
            if (PantallaDatosServidor.pd != null) {
                 PantallaDatosServidor.pd.dismiss();
             }
        }        
    }
    class BuscarServer extends AsyncTask<Void, Void, String> {
        private int port;
        public BuscarServer(int _port){
            super();
            port=_port;
        }
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected String doInBackground(Void... params) {
            try {
                DatagramSocket socket=new DatagramSocket(port);
                socket.setBroadcast(true);
                socket.setSoTimeout(5000);
                byte[] datos = new byte[50];
                DatagramPacket paquete=new DatagramPacket(datos,50);
                socket.receive(paquete);
                String mensaje=new String(paquete.getData(),"utf-8");
                if("socialDj".equals(mensaje.split("\\|")[0]))
                    return mensaje.split("\\|")[1]+"|"+mensaje.split("\\|")[2];
                else
                    return null;
            }catch (Exception ex){
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
        }        
    }
}
