/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bridgesocialdj;

import Conexion.WebSocketServer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import Managers.ServerManager;

/**
 *
 * @author 66785270
 */
public class BridgeSocialDj {

    public static int port = 2223;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        
        ServerManager bridge=new ServerManager();
        if(bridge.arrancarBridge())
            System.out.println("BridgeSocialDj arrancado correctamente. A disfrutar!");
        
        //////////////////////////////////////////////////////////////////////////////////////
        
        //PRUEBAS WEBSOCKETS
        Server server = new Server(port);
 
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
 
        context.addServlet(new ServletHolder(new WebSocketServer()), "/*");
 
        server.start();
        System.out.println("Escuchando en el puerto " + port);
        server.join();     
    }
}
