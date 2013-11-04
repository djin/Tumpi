package app.tumpi.cliente.lista.android.conexion;

import android.os.AsyncTask;
import android.util.Log;

import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author 66785270
 */
public class ConnectionManager {
    public static SocketConnector conexion;

    public ConnectionManager() {
    }
    
    
    public Boolean conectar() throws Exception{
        return new conectar().execute().get(5,TimeUnit.SECONDS);
    }
    public Boolean logInBridge(String nick, String uuid) throws Exception{
        AsyncTask<String, Void, Boolean> thread_log=new  AsyncTask<String, Void, Boolean>(){
            @Override
            protected Boolean doInBackground(String... nick) {
                return conexion.logBridge(nick[0], uuid);
            }
        };
        return thread_log.execute(nick).get(5,TimeUnit.SECONDS);
    }
    
    class conectar extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                String ip="";
                ip=InetAddress.getByName("tumpi.net").getHostAddress();
                conexion=new SocketConnector(ip,2244);
                conexion.conectar();
                return conexion.isConnected();
            }catch (Exception ex){
                Log.e("Conexion","Error al realizar la conexion con el bridge: "+ex);
                return false;
            }
        }
    }
}
