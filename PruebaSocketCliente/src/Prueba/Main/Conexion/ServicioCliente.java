/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Prueba.Main.Conexion;

import Prueba.Main.Conexion.SocketConnector;
import Prueba.Main.MainActivity;
import android.app.*;
import android.content.Intent;
import android.os.IBinder;

/**
 *
 * @author 66785270
 */
public class ServicioCliente extends IntentService{
    
    public ServicioCliente() {
        super("ServicioCliente");
    }
    
    @Override
    protected void onHandleIntent(Intent intent) {
        String texto_recivido="";
        try{
            do{
                texto_recivido=SocketConnector.listenServer();
                SocketConnector.fireMessageEvent(texto_recivido);
            }while(true);
        }catch(Exception ex){
            //log("\nError al escuchar al servidor: "+ex.toString()+ex.hashCode());
        }finally{
            this.stopSelf();            
        }
    }
    
}
