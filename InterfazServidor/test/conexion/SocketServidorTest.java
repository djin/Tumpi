/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author 66785320
 */
public class SocketServidorTest {
    
    public SocketServidorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of isBound method, of class SocketServidor.
     */
    @Test
    public void testIsBound() {
        System.out.println("isBound");
        SocketServidor instance = null;
        boolean expResult = false;
        boolean result = instance.isBound();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of startSearchClients method, of class SocketServidor.
     */
    @Test
    public void testStartSearchClients() {
        System.out.println("startSearchClients");
        SocketServidor instance = null;
        instance.startSearchClients();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of finishSearchClients method, of class SocketServidor.
     */
    @Test
    public void testFinishSearchClients() {
        System.out.println("finishSearchClients");
        SocketServidor instance = null;
        instance.finishSearchClients();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of enviarMensajeServer method, of class SocketServidor.
     */
    @Test
    public void testEnviarMensajeServer() throws Exception {
        System.out.println("enviarMensajeServer");
        String ip_cliente = "";
        String mensaje = "";
        SocketServidor instance = null;
        instance.enviarMensajeServer(ip_cliente, mensaje);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getClientsCount method, of class SocketServidor.
     */
    @Test
    public void testGetClientsCount() {
        System.out.println("getClientsCount");
        SocketServidor instance = null;
        int expResult = 0;
        int result = instance.getClientsCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of closeSocket method, of class SocketServidor.
     */
    @Test
    public void testCloseSocket() throws Exception {
        System.out.println("closeSocket");
        SocketServidor instance = null;
        instance.closeSocket();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addServerSocketListener method, of class SocketServidor.
     */
    @Test
    public void testAddServerSocketListener() {
        System.out.println("addServerSocketListener");
        ServerSocketListener listener = null;
        SocketServidor instance = null;
        instance.addServerSocketListener(listener);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeServerSocketListener method, of class SocketServidor.
     */
    @Test
    public void testRemoveServerSocketListener() {
        System.out.println("removeServerSocketListener");
        ServerSocketListener listener = null;
        SocketServidor instance = null;
        instance.removeServerSocketListener(listener);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fireClientConnectedEvent method, of class SocketServidor.
     */
    @Test
    public void testFireClientConnectedEvent() {
        System.out.println("fireClientConnectedEvent");
        String ip = "";
        SocketServidor instance = null;
        instance.fireClientConnectedEvent(ip);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fireClientDisconnectedEvent method, of class SocketServidor.
     */
    @Test
    public void testFireClientDisconnectedEvent() {
        System.out.println("fireClientDisconnectedEvent");
        String ip = "";
        SocketServidor instance = null;
        instance.fireClientDisconnectedEvent(ip);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fireMessageReceivedEvent method, of class SocketServidor.
     */
    @Test
    public void testFireMessageReceivedEvent() {
        System.out.println("fireMessageReceivedEvent");
        String ip = "";
        String message = "";
        SocketServidor instance = null;
        instance.fireMessageReceivedEvent(ip, message);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getIp method, of class SocketServidor.
     */
    @Test
    public void testGetIp() {
        System.out.println("getIp");
        SocketServidor instance = null;
        String expResult = "";
        String result = instance.getIp();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
