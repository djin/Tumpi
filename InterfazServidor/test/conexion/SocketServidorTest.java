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
    

    /*simple test, para empezar mejor por cositas pequeñas*/
    @Test
    public void testSuma(){
        int i = 0, j = 0;
        int resultado = suma(i, j);
        assertEquals(resultado, 0);
    }
    
    @Test
    public void testSuma1(){
        int i=1, j=19;
        int resultado = suma(i, j);
        assertEquals(resultado, 20);
    }
    
    public int suma(int i, int j){
        return i+j;
    }
    
    /*ahora vamos un poco mas enserio, con la chicha*/
    
    
}
