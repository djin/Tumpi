/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.tumpi.cliente.lista.android.conexion;

import android.app.*;
import android.content.Intent;

/**
 *
 * @author 66785270
 */
public class ServicioCliente extends IntentService{
//    String texto_recivido;
    public ServicioCliente() {
        super("ServicioCliente");
    }
    
    @Override
    protected void onHandleIntent(Intent intent) {
//        texto_recivido="";
//        try{
//            do{
//                texto_recivido=SocketConnector.listenServer();            
//                SocketConnector.fireMessageEvent(texto_recivido);
//            }while(texto_recivido!="exit");
//        }catch(Exception ex){
//            SocketConnector.fireMessageEvent("exit");
//        }finally{
//            this.stopSelf();            
//        }
    }
    
}
