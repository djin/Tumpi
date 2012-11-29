/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actions;

import conexion.ConnectionManager;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import main.Main;

/**
 *
 * @author 66786575
 */
public class Salir extends AbstractAction{

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            ConnectionManager.socket.closeSocket();
            System.exit(0);
        } catch (IOException ex) {
            Main.log("Error al intentar cerrar el socket: "+ex.toString());
        }
    }
    
}
