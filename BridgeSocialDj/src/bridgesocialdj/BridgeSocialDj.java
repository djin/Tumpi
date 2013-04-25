/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bridgesocialdj;

import Managers.ServerManager;

/**
 *
 * @author 66785270
 */
public class BridgeSocialDj {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ServerManager bridge=new ServerManager();
        if(bridge.arrancarBridge())
            System.out.println("BridgeSocialDj arrancado correctamente. A disfrutar!");
    }
}
