/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfazlistatest;


import lista.android.Cancion;
import org.robolectric.RobolectricTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

/**
 *
 * @author 66785320
 */

@RunWith(RobolectricTestRunner.class)
public class InterfazListaTestTest {
    
    @Test
    public void testSuma() throws Exception{
        int i=1, j=2;
        int valor = i+j;
        assertEquals(valor, 3);
    }
    
    @Test
    public void testCancionEquals() throws Exception{
        Cancion cancion1 = new Cancion("Los redondeles", "redondels", "n1", 1, false, false);
        Cancion cancion2 = new Cancion("Los redondeles", "redondels", "n1", 1, false, false);
        assertEquals(cancion1, cancion2);
    }
    
}