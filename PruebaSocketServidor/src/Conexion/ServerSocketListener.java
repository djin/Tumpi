/*
 * Interfaz de definicion de los eventos lanzados por la clase de comunicacion 
 * del servidor.
 * Resumen:
 * - onMessageReceived: Se lanzara al recivir un mensaje de algun cliente y 
 *                      se proporciona la ip del cliente y el mensaje.
 * - onClientConnected: Se lanza con la conexion de un nuevo cliente al socket
 *                      y se da la ip de este cliente.
 * - onClientDisconnected: Se lanza con la desconexion de un cliente ya 
 *                         conectado indicando la ip.
 */
package Conexion;

/**
 *
 * @author 66785270
 */
public interface ServerSocketListener {
    public void onMessageReceived(String ip,String message);
    public void onClientConnected(String ip);
    public void onClientDisconnected(String ip);
}
