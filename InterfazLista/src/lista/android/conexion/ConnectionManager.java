/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lista.android.conexion;

import android.app.Activity;
import android.os.AsyncTask;

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
    }
}
