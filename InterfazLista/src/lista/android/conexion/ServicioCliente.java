/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lista.android.conexion;

import android.app.*;
import android.content.Intent;

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
