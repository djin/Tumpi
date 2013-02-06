/*
 * Interfaz de listeners de la comunicación.
 * Por ahora no se han requerido mas eventos que el 'onMessageReceive' pero
 * si hiciera falta se podrian añadir sin problemas.
 */
package Prueba.Main.Conexion;

/**
 *
 * @author 66785270
 */
public interface ServerMessageListener {
    void onMessageReceive(String message);
}
