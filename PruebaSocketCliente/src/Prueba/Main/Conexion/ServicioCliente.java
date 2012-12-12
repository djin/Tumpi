/*
 * Clase de definición del servicio de escucha al servidor.
 * Es una forma especial que tiene android de declarar los servicios.
 * En la practica es como si fuera un thread y el metodo 'Run' fuera el
 * 'onHandleIntent'.
 */
package Prueba.Main.Conexion;

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
        }finally{
            this.stopSelf();            
        }
    }
    
}
