/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
