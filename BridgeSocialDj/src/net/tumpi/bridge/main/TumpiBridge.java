/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tumpi.bridge.main;

import net.tumpi.bridge.conexion.WebSocketServer;
import net.tumpi.bridge.config.Config;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import net.tumpi.bridge.managers.ServerManager;

/**
 *
 * @author 66785270
 */
public class TumpiBridge {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        Config config = Config.instance();
        config.loadProperties();

        ServerManager bridge = new ServerManager();
        if (bridge.arrancarBridge()) {
            System.out.println("BridgeSocialDj arrancado correctamente. A disfrutar!");
        }

        //////////////////////////////////////////////////////////////////////////////////////

        //PRUEBAS WEBSOCKETS
        Server server = new Server(config.getPuerto());

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new WebSocketServer()), "/*");

        server.start();
        System.out.println("Escuchando en el puerto " + config.getPuerto());
        server.join();
    }
}
